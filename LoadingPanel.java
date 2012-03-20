package com.github.a2g.core;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class LoadingPanel extends SimplePanel {

	Label progress;
    LoadingPanel() {
        progress = new Label();
        this.add(progress);
    }

    public void update(int current, int total) {
    	progress.setText(" "+current+"/"+total);
    }
}
