/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.google.gwt.event.shared.GwtEvent;


public class SetRolloverEvent extends GwtEvent<SetRolloverEventHandler> {
    public static Type<SetRolloverEventHandler> TYPE = new Type<SetRolloverEventHandler>();
    // private final FriendSummaryDTO friend;
    private final String displayName;
    private final String textualId;
    private final int code;
    
    public String getDisplayName() {
        return displayName;
    }

    public String getTextualId() {
        return textualId;
    }

    public int getCode() {
        return code;
    }

    public SetRolloverEvent(String displayName, String textualId, int code) {
        this.displayName = displayName;
        this.textualId = textualId;
        this.code = code;
    }

    @Override
    public Type<SetRolloverEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetRolloverEventHandler handler) {
        handler.onSetRollover(displayName,
                textualId, code);
    }
}
