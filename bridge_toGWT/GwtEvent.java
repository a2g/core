package com.github.a2g.core.bridge;

import com.github.a2g.core.bridge.EventHandler;
import com.google.web.bindery.event.shared.Event;

public abstract class GwtEvent<H extends EventHandler> extends Event<H> 
{

  public static class Type<H> extends Event.Type<H> {
  }

  private boolean dead;

  protected GwtEvent() {
  }

  @Override
  public abstract GwtEvent.Type<H> getAssociatedType();

  @Override
  public Object getSource() {
    assertLive();
    return super.getSource();
  }

   protected void assertLive() {
    assert (!dead) : "This event has already finished being processed by its original handler manager, so you can no longer access it";
  }

  
  protected abstract void dispatch(H handler);

 
  protected final boolean isLive() {
    return !dead;
  }

  protected void kill() {
    dead = true;
    setSource(null);
  }

  protected void revive() {
    dead = false;
    setSource(null);
  }

  void overrideSource(Object source) {
    super.setSource(source);
  }
}