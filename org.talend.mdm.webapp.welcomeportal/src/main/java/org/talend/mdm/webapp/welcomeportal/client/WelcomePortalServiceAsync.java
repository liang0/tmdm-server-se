/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package org.talend.mdm.webapp.welcomeportal.client;

/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
import java.util.List;
import java.util.Map;

import org.talend.mdm.webapp.base.shared.AppHeader;
import org.talend.mdm.webapp.welcomeportal.client.mvc.PortalProperties;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface WelcomePortalServiceAsync {

    void isHiddenWorkFlowTask(AsyncCallback<Boolean> callback);

    void isHiddenTDSTask(AsyncCallback<Boolean> callback);

    void getWorkflowTaskMsg(AsyncCallback<Integer> callback);

    void getStandaloneProcess(String language, AsyncCallback<Map<String, String>> callback);

    void runProcess(String transformerPK, AsyncCallback<String> callback);

    void isEnterpriseVersion(AsyncCallback<Boolean> callback);

    void getMenuLabel(String language, String id, AsyncCallback<String> callback);

    void getCurrentDataContainer(AsyncCallback<String> callback);

    void getWelcomePortletConfig(AsyncCallback<Map<Boolean, Integer>> callback);

    void getPortalConfig(AsyncCallback<PortalProperties> callback);

    void savePortalConfig(PortalProperties config, AsyncCallback<Void> callback);

    void savePortalConfig(String key, String value, AsyncCallback<Void> callback);

    void savePortalConfig(String key, String portletName, String value, AsyncCallback<Void> callback);

    void savePortalConfigAutoAndSetting(String portletName, List<String> coinfig, AsyncCallback<Void> callback);

    void getAppHeader(AsyncCallback<AppHeader> callback);

    void getCurrentDataModel(AsyncCallback<String> callback);
}
