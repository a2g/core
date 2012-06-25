/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.google.gwt.event.shared.GwtEvent;


public class PropertyChangeEvent extends GwtEvent<PropertyChangeEventHandlerAPI> {
    public static Type<PropertyChangeEventHandlerAPI> TYPE = new Type<PropertyChangeEventHandlerAPI>();
    // private final FriendSummaryDTO friend;
    private final String name;
    private final int id;
    private int value;

    public PropertyChangeEvent(String name, int id, int value) {
        this.name = name;
        this.id = id;
        this.value = value;
    }

    @Override
    public Type<PropertyChangeEventHandlerAPI> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PropertyChangeEventHandlerAPI handler) {
        handler.onPropertyChange(this);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
