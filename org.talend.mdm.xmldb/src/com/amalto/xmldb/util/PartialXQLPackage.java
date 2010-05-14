package com.amalto.xmldb.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartialXQLPackage {
	
	    private static final Pattern pPattern=Pattern.compile("\\$pivot\\d+");
    	
    	private LinkedHashMap<String,String> forInCollectionMap=null;
    	
    	private Map<String,String> pivotWhereMap=null;
    	
    	private String xqWhere;
    	
    	private boolean useSubsequenceFirst;
    	
    	public PartialXQLPackage() {
    		forInCollectionMap=new LinkedHashMap<String, String>();
    		useSubsequenceFirst=false;
		}
    	
    	public void addForInCollection(String pivotName,String collectionXpathExpr) {
			
    		this.forInCollectionMap.put(pivotName, collectionXpathExpr);

		}

		public LinkedHashMap<String, String> getForInCollectionMap() {
			return forInCollectionMap;
		}
		
		public void genPivotWhereMap() {
			
			pivotWhereMap = getPivotWhereMap(xqWhere);
			if(pivotWhereMap.size()==1) {
				String replacedXQWhere=xqWhere.replaceAll("\\$pivot\\d+/", "");
				String pivotName=getPivotName(xqWhere);
				pivotWhereMap.put(pivotName, replacedXQWhere);
			}else if(pivotWhereMap.size()>1) {
				String[] whereItems=xqWhere.split("and");//FIXME:only support and under mix mode
				for (int i = 0; i < whereItems.length; i++) {
					String whereItem=whereItems[i].trim();
					String pivotName=getPivotName(whereItem);
					String replacedWhereItem=whereItem.replaceAll("\\$pivot\\d+/", "");
					if(pivotWhereMap.get(pivotName)==null) {
						pivotWhereMap.put(pivotName, replacedWhereItem);
					}else {
						pivotWhereMap.put(pivotName, pivotWhereMap.get(pivotName)+" and "+replacedWhereItem);
					}
				}
				
			}
			//System.out.println(pivotWhereMap);
			
		}

		public Map<String, String> getPivotWhereMap() {
			return pivotWhereMap;
		}

		public String getXqWhere() {
			return xqWhere;
		}

		public void setXqWhere(String xqWhere) {
			this.xqWhere = xqWhere;
		}

		private Map<String,String> getPivotWhereMap(String xqWhere) {
			Map<String,String> pivotWhereMap=new HashMap<String,String>();
			Matcher m = pPattern.matcher(xqWhere);
			while (m.find()) {
				pivotWhereMap.put(m.group(), null);      
			}
			return pivotWhereMap;
		}
		
		private String getPivotName(String input) {
			String pivotName=null;
			if(input==null||input.length()==0)return pivotName;
			Matcher m = pPattern.matcher(input);
			while (m.find()) {
				pivotName=m.group(0);
				break;
			}
			return pivotName;
		}

		public boolean isUseSubsequenceFirst() {
			return useSubsequenceFirst;
		}

		public void setUseSubsequenceFirst(boolean useSubsequenceFirst) {
			this.useSubsequenceFirst = useSubsequenceFirst;
		}
		
    	
}
