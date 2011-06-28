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
package org.talend.mdm.webapp.recyclebin.client.mvc;

import org.talend.mdm.webapp.recyclebin.client.RecycleBinEvents;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;

/**
 * DOC Administrator  class global comment. Detailled comment
 */
public class RecycleBinView extends View {

    public RecycleBinView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent event) {
        if (event.getType() == RecycleBinEvents.InitFrame) {
            onInitFrame(event);
        }

    }

    private void onInitFrame(AppEvent event) {
        if (Log.isInfoEnabled())
            Log.info("Init frame... ");//$NON-NLS-1$
    }

}
