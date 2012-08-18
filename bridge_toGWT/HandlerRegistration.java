package com.github.a2g.core.bridge;

public interface HandlerRegistration {

  /**
   * Deregisters the handler associated with this registration object if the
   * handler is still attached to the event source. If the handler is no longer
   * attached to the event source, this is a no-op.
   */
  void removeHandler();
}