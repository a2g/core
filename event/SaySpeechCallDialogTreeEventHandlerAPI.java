/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.google.gwt.event.shared.EventHandler;


public interface SaySpeechCallDialogTreeEventHandlerAPI extends EventHandler {
    void onSaySpeechCallChoice(String speech, int choice);
  
}
