package com.amalto.webapp.v3.itemsbrowser.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.talend.mdm.commmon.util.datamodel.management.BusinessConcept;
import org.talend.mdm.commmon.util.datamodel.management.ReusableType;

import com.amalto.webapp.core.bean.Configuration;
import com.amalto.webapp.core.dmagent.SchemaWebAgent;
import com.amalto.webapp.core.json.JSONObject;
import com.amalto.webapp.core.util.Util;
import com.amalto.webapp.util.webservices.WSDataClusterPK;
import com.amalto.webapp.util.webservices.WSGetItemPKsByCriteria;
import com.amalto.webapp.util.webservices.WSGetItemPKsByFullCriteria;
import com.amalto.webapp.util.webservices.WSItemPKsByCriteriaResponse;
import com.amalto.webapp.util.webservices.WSItemPKsByCriteriaResponseResults;

public class ExportingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.apache.log4j.Logger.getLogger(this.getClass()).info("SERVLET exporting for excel ");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        String theReportFile = "Reporting_" + df.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + theReportFile + "\"");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");
        sheet.setDefaultColumnWidth((short) 20);

        String cluster = request.getParameter("cluster");
        String parametersValues = request.getParameter("params");
        if (parametersValues == null)
            parametersValues = "";

        org.apache.log4j.Logger.getLogger(this.getClass()).debug("params =" + parametersValues);

        boolean splitEnd = false;
        String tmpSplit = parametersValues;
        Vector<String> paramVector = new Vector<String>();
        while (!splitEnd) {
            int indexMatch = tmpSplit.indexOf("###");
            if (indexMatch == -1) {
                paramVector.add(tmpSplit);
                splitEnd = true;
            } else {
                if (indexMatch > 0) {
                    String tmpParam = tmpSplit.substring(0, indexMatch);
                    paramVector.add(tmpParam);
                } else
                    paramVector.add("");

                if (indexMatch + 3 >= tmpSplit.length())
                    tmpSplit = "";
                else
                    tmpSplit = tmpSplit.substring(indexMatch + 3);
            }
        }

        // String []parameters = parametersValues.split("###");
        String[] parameters = new String[paramVector.size()];
        for (int i = 0; i < paramVector.size(); i++) {
            parameters[i] = paramVector.get(i);
        }

        org.apache.log4j.Logger.getLogger(this.getClass()).debug("nb params =" + parameters.length);

        try {
            WSDataClusterPK wsDataClusterPK = new WSDataClusterPK();
            String entity = null;
            String contentWords = null;
            String keys = null;
            Long fromDate = new Long(-1);
            Long toDate = new Long(-1);
            String fkvalue = null;
            String dataObject = null;

            if (parametersValues != null && parametersValues.length() > 0) {
                JSONObject criteria = new JSONObject(parametersValues);

                Configuration configuration = Configuration.getInstance(true);
                wsDataClusterPK.setPk(configuration.getCluster());
                entity = !criteria.isNull("entity") ? (String) criteria.get("entity") : "";
                keys = !criteria.isNull("key") && !"*".equals(criteria.get("key")) ? (String) criteria.get("key") : "";
                fkvalue = !criteria.isNull("fkvalue") && !"*".equals(criteria.get("fkvalue")) ? (String) criteria.get("fkvalue")
                        : "";
                dataObject = !criteria.isNull("dataObject") && !"*".equals(criteria.get("dataObject")) ? (String) criteria
                        .get("dataObject") : "";
                contentWords = !criteria.isNull("keyWords") ? (String) criteria.get("keyWords") : "";

                if (!criteria.isNull("fromDate")) {
                    String startDate = (String) criteria.get("fromDate");
                    SimpleDateFormat dataFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date date = dataFmt.parse(startDate);
                    fromDate = date.getTime();
                }

                if (!criteria.isNull("toDate")) {
                    String endDate = (String) criteria.get("toDate");
                    SimpleDateFormat dataFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date date = dataFmt.parse(endDate);
                    toDate = date.getTime();
                }
            }

            BusinessConcept businessConcept = SchemaWebAgent.getInstance().getBusinessConcept(entity);
            Map<String, String> foreignKeyMap = businessConcept.getForeignKeyMap();
            Set<String> foreignKeyXpath = foreignKeyMap.keySet();
            Set<String> xpathes = new HashSet<String>();

            for (String path : foreignKeyXpath) {
                String dataObjectPath = foreignKeyMap.get(path);
                if (dataObjectPath.indexOf(dataObject) != -1) {
                    xpathes.add(path.substring(1));
                }
            }
            
            List<String> types = SchemaWebAgent.getInstance().getBindingType(businessConcept.getE());
            for (String type : types) {
                List<ReusableType> subTypes = SchemaWebAgent.getInstance().getMySubtypes(type);
                for (ReusableType reusableType : subTypes) {
                    Map<String, String> fks = SchemaWebAgent.getInstance().getReferenceEntities(reusableType, dataObject);
                    Collection<String> fkPaths = fks != null ? fks.keySet() : null;
                    for (String fkpath : fkPaths) {
                        if (fks.get(fkpath).indexOf(dataObject) != -1) {
                            xpathes.add(fkpath);
                        }
                    }
                }
            }        
     
            Map<String, String> inheritanceForeignKeyMap = businessConcept.getInheritanceForeignKeyMap();
            if (inheritanceForeignKeyMap.size() > 0) {
                Set<String> keySet = inheritanceForeignKeyMap.keySet();
                String dataObjectPath = null;
                for (String path : keySet) {
                    dataObjectPath = inheritanceForeignKeyMap.get(path);
                    if (dataObjectPath.indexOf(dataObject) != -1) {
                        xpathes.add(path.substring(1));
                    }
                }
            } 

            StringBuilder keysb = new StringBuilder();
            keysb.append(keys);
            keysb.append("$"); //$NON-NLS-1$
            keysb.append(joinSet(xpathes, ",")); //$NON-NLS-1$
            keysb.append("$"); //$NON-NLS-1$
            keysb.append(fkvalue);

            WSItemPKsByCriteriaResponse results = Util.getPort().getItemPKsByFullCriteria(
                    new WSGetItemPKsByFullCriteria(new WSGetItemPKsByCriteria(wsDataClusterPK, entity, contentWords, keysb
                            .toString(), fromDate, toDate, 0, Integer.MAX_VALUE), false));

            // create a cell style
            HSSFCellStyle cs = wb.createCellStyle();
            HSSFFont f = wb.createFont();
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cs.setFont(f);
            HSSFRow row = sheet.createRow((short) 0);

            if (results.getResults().length > 0) {
                row.createCell((short) 0).setCellValue("date");
                row.createCell((short) 1).setCellValue("entity");
                row.createCell((short) 2).setCellValue("key");
            }

            // set a style for these cells
            for (int i = 0; i < 3; i++) {
                row.getCell((short) i).setCellStyle(cs);
            }

            for (int i = 0; i < results.getResults().length; i++) {
                WSItemPKsByCriteriaResponseResults result = results.getResults()[i];
                if (i == 0) {
                    continue;
                }

                row = sheet.createRow((short) i);
                SimpleDateFormat dataFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dataFmt.format(result.getDate());
                row.createCell((short) 0).setCellValue(date);
                row.createCell((short) 1).setCellValue(result.getWsItemPK().getConceptName());
                String[] ids = result.getWsItemPK().getIds();
                StringBuilder sb = new StringBuilder();

                if (ids != null) {
                    for (String id : ids) {
                        sb.append(id);
                    }
                }

                row.createCell((short) 2).setCellValue(sb.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write the output
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.close();
    }

    private String joinSet(Set<String> set, String decollator) {
        if (set == null)
            return "";  //$NON-NLS-1$
        StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for (String str : set) {
            if (isFirst) {
                sb.append(str);
                isFirst = false;
                continue;
            }
            sb.append(decollator + str);
        }
        return sb.toString();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
