package com.github.a2g.core;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
public class LoadingPanel extends SimplePanel {

	Label progress;
    LoadingPanel() {

        progress = new Label();
        DOM.setStyleAttribute(
                progress.getElement(), "color",
                "Red");
        progress.setText("Loading...");
        this.add(progress);
    }

    public void update(int current, int total, String name) {
    	progress.setText(" "+current+"/"+total+ " " + name);
    }
}
    
    

