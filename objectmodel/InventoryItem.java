/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;


import com.github.a2g.core.event.PropertyChangeEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.Image;


public class InventoryItem {

    private final Image image;
    private final String textualId;
    private String displayName;
    private boolean visible;
    private EventBus bus;
    private int code;
    
    public InventoryItem(EventBus bus, final String textualId, final Image image, int code, boolean isVisible) {
        assert(bus != null);
        this.code = code;
        this.image = image;
        this.textualId = textualId;
        this.visible = isVisible;
        this.displayName = textualId;
        this.bus = bus;
    }

    public String getTextualId() {
        return textualId;
    }

    public void setVisible(boolean visible) {	
        this.visible = visible;
        bus.fireEvent(
                new PropertyChangeEvent(
                        "CARRYING_" + textualId,
                        code, visible ? 1 : 0));
    }

    public boolean isVisible() {
        return visible;
    }

    public final Image getImage() {
        return image;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
