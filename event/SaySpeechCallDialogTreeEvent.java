/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.github.a2g.core.bridge.GwtEvent;


public class SaySpeechCallDialogTreeEvent extends GwtEvent<SaySpeechCallDialogTreeEventHandlerAPI> {
    public static Type<SaySpeechCallDialogTreeEventHandlerAPI> TYPE = new Type<SaySpeechCallDialogTreeEventHandlerAPI>();
    // private final FriendSummaryDTO friend;
    private final String speech;
    private final int branchId;
    
    public SaySpeechCallDialogTreeEvent(String speech, int branchId) {
        this.speech = speech;
        this.branchId = branchId;
    }

    @Override
    public Type<SaySpeechCallDialogTreeEventHandlerAPI> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SaySpeechCallDialogTreeEventHandlerAPI handler) {
        handler.onSaySpeechCallBranch(speech,
                branchId);
        		
    }
}
