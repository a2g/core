/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.mouse;


import com.github.a2g.core.event.SaySpeechCallChoiceEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;


public class ChoiceMouseClickHandler implements ClickHandler {
    private final Label label;
    private int place;
    private EventBus bus;

    public ChoiceMouseClickHandler(EventBus bus, Label label, int place) {
        this.label = label;
        this.bus = bus;
        this.place = place;
    }

    @Override
    public void onClick(ClickEvent event) {
        bus.fireEvent(
                new SaySpeechCallChoiceEvent(
                        label.getText(), place));
        
    }
}
