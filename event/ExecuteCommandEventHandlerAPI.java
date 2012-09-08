/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.event;


import com.github.a2g.bridge.EventHandler;


public interface ExecuteCommandEventHandlerAPI extends EventHandler {
    void onExecuteCommand(double x, double y);
  
}
