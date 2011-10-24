// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.webapp.browserecords.client.widget;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * DOC HSHU class global comment. Detailled comment
 */
public class ItemsSearchContainer extends LayoutContainer {

    private static ItemsSearchContainer instance;

    private BorderLayoutData northData;

    public static ItemsSearchContainer getInstance() {
        if (instance == null) {
            instance = new ItemsSearchContainer();
        }
        return instance;
    }

    protected void onDetach() {
    	super.onDetach();
    	instance = null;
    }
    
    private ItemsSearchContainer() {
        setLayout(new BorderLayout());
        setBorders(false);

        ContentPanel topPanel = new ContentPanel();
        topPanel.setLayout(new FitLayout());
        topPanel.setHeaderVisible(false);

        topPanel.add(ItemsToolBar.getInstance());
        topPanel.add(ItemsToolBar.getInstance().getAdvancedPanel());
        northData = new BorderLayoutData(LayoutRegion.NORTH);
        northData.setSize(30);
        northData.setSplit(true);
        add(topPanel, northData);

        BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 400);
        westData.setSplit(true);
        westData.setMargins(new Margins(0, 5, 0, 0));
        westData.setFloatable(true);
        westData.setMaxSize(800);
        add(ItemsListPanel.getInstance(), westData);

        add(ItemsMainTabPanel.getInstance(), new BorderLayoutData(LayoutRegion.CENTER));
    }

    public void resizeTop(float size) {
        northData.setSize(size);
        this.layout(true);
    }

}
