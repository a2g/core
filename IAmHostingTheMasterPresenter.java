/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


public interface IAmHostingTheMasterPresenter {

    /**
     * Is there to persist state data at the time the state data change occurs.
     * The setting of most types of state data is ultimately done by this method.
     * It is needed for the inventory to remember it contains an object
     * when moving from one room to the next. It is also called in reponse
     * to calls of @ref com.github.a2g.core.authoredroom.IAmTheMainAPI::setValue(String name, int value);	
     */
    public void setValue(String name, int value);

    /**
     * The retrieval of most types of state data is ultimately done by this method.
     * It is needed by the inventory to remember it contains an object when moving from
     * one room to the next. It is also called in response
     * to calls of @ref com.github.a2g.core.authoredroom.IAmTheMainAPI::getValue(String name);	
     */
    public int getValue(String name);

    /**
     * Is there to switchRooms.
     * 
     */
    public void instantiateRoomThenCreateNewMasterPanelInitializedToIt(String room);

    /**
     * Is there merely to export analytical data so it may be analyzed, and perhaps used to
     * improve the game. A stub implementation will suffice if analytics data is to be ignored.
     */	
    public void setLastCommand(double x, double y, int v, String a, String b);
  
}
