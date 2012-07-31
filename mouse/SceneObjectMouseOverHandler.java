/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.mouse;


import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.github.a2g.core.SceneObject;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.event.SetRolloverEvent;


public class SceneObjectMouseOverHandler implements MouseMoveHandler {
    private final EventBus bus;
    private final String textualId;
    private final short code;
    private final InternalAPI api;

    public SceneObjectMouseOverHandler(EventBus bus, InternalAPI api, String textualId, short code) {
        this.bus = bus;
        this.textualId = textualId;
        this.code = code;
        this.api = api;

    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        SceneObject ob = api.getObject(
                this.code);
        String displayName = "";

        if (ob != null) {
            displayName = ob.getDisplayName(); 
        }
        bus.fireEvent(
                new SetRolloverEvent(
                        displayName,
                        this.textualId,
                        this.code));
    }
}
