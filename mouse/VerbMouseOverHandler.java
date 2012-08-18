/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.mouse;


import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.event.SetRolloverEvent;


public class VerbMouseOverHandler implements MouseMoveHandler {
    private final EventBus bus;
    ;
    private final String textualId;
    private final int code;
    private final String displayName;

    public VerbMouseOverHandler(EventBus bus, String displayName, String textualId, int code) {
        this.bus = bus;
        this.textualId = textualId;
        this.code = code;
        this.displayName = displayName;

    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        bus.fireEvent(
                new SetRolloverEvent(
                        displayName,
                        this.textualId,
                        this.code));
    }
}
