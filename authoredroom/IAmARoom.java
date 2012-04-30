/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredroom;


import com.github.a2g.core.SentenceUnit;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChoicesBaseAction;

public interface IAmARoom {
    public void onReceiveGameAPIObject(IAmTheMainAPI api);
    public void onSpecifyResources(IAmTheLoaderAPI api);
    public void onPrepareRoomForFocus();
    public void onEveryFrame();
    public BaseAction onEnterRoom();
    public BaseAction onCommandLineExecute(int verb, SentenceUnit objA, SentenceUnit objB, double x, double y);  
    public ChoicesBaseAction onChoice(int i);
}
