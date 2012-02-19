/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.google.gwt.event.shared.GwtEvent;


public class ExecuteCommandEvent extends GwtEvent<ExecuteCommandEventHandler> {
    public static Type<ExecuteCommandEventHandler> TYPE = new Type<ExecuteCommandEventHandler>();
    // private final FriendSummaryDTO friend;
    private final double x;
    private final double y;

    public ExecuteCommandEvent(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Type<ExecuteCommandEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExecuteCommandEventHandler handler) {
        handler.onExecuteCommand(x, y);
    }
  
}
