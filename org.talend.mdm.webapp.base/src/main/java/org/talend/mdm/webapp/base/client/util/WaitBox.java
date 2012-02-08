package org.talend.mdm.webapp.base.client.util;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.MessageBox.MessageBoxType;


public class WaitBox {

    private static final MessageBox waitBox = new MessageBox();
    static {
        waitBox.setType(MessageBoxType.WAIT);
        waitBox.setButtons(""); //$NON-NLS-1$
        waitBox.setClosable(false);
    }
    
    public static void show(String title, String msg, String progressText) {
        waitBox.setTitle(title);
        waitBox.setMessage(msg);
        waitBox.setProgressText(progressText);
        waitBox.show();
    }
    
    public static void hide() {
        waitBox.close();
    }
}
