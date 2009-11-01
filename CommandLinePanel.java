package com.github.a2g.core;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;


public class CommandLinePanel extends Label {
    CommandLinePanel() {
        
        DOM.setStyleAttribute(
                this.getElement(), "color",
                "Blue");
        this.setText(" ");
    }
	
}
