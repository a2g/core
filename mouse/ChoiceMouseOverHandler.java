/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.mouse;


import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;


public class ChoiceMouseOverHandler implements MouseOverHandler {
    private final Label label;

    public ChoiceMouseOverHandler(Label label) {
        this.label = label;
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        DOM.setStyleAttribute(
                label.getElement(), "color",
                "#f00");
    }
}
