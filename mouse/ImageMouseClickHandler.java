/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.mouse;


import com.github.a2g.core.event.ExecuteCommandEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class ImageMouseClickHandler implements ClickHandler {
    private final EventBus bus;
    private final AbsolutePanel absolutePanel;

    public ImageMouseClickHandler(EventBus bus, AbsolutePanel abs) {
        this.bus = bus;
        this.absolutePanel = abs;

    }

    @Override
    public void onClick(ClickEvent event) {
        double x = -1;
        double y = -1;
 
        if (this.absolutePanel != null) {
            x = event.getRelativeX(
                    this.absolutePanel.getElement());
            y = event.getRelativeY(
                    this.absolutePanel.getElement());
            y /= this.absolutePanel.getOffsetHeight();
            x /= this.absolutePanel.getOffsetWidth();
        }
    	 
        bus.fireEvent(
                new ExecuteCommandEvent(x, y));
    }
}
