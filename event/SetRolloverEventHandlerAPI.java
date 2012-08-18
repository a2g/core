/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.github.a2g.core.bridge.EventHandler;

public interface SetRolloverEventHandlerAPI extends EventHandler {
    void onSetRollover(String displayName, String textualId, int code);
  
}
