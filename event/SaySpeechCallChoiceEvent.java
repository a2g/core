/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.google.gwt.event.shared.GwtEvent;


public class SaySpeechCallChoiceEvent extends GwtEvent<SaySpeechCallChoiceEventHandler> {
    public static Type<SaySpeechCallChoiceEventHandler> TYPE = new Type<SaySpeechCallChoiceEventHandler>();
    // private final FriendSummaryDTO friend;
    private final String speech;
    private final int choice;
    
    public SaySpeechCallChoiceEvent(String speech, int choice) {
        this.speech = speech;
        this.choice = choice;
    }

    @Override
    public Type<SaySpeechCallChoiceEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SaySpeechCallChoiceEventHandler handler) {
        handler.onSaySpeechCallChoice(speech,
                choice);
        		
    }
}
