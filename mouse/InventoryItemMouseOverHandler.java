/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.mouse;


import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.objectmodel.InventoryItem;


public class InventoryItemMouseOverHandler implements MouseMoveHandler {
    private final EventBus bus;
    private final String textualId;
    private final int code;
    private final InternalAPI api;

    public InventoryItemMouseOverHandler(EventBus bus, InternalAPI api, String textualId, int code) {
        this.bus = bus;
        this.textualId = textualId;
        this.code = code;
        this.api = api;

    }

    public void onMouseMove(MouseMoveEvent event) {
        InventoryItem ob = api.getInventoryItem(
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
