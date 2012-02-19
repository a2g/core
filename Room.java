/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


public class Room {	
    private RoomObjectCollection roomObjectCollection;

    public Room() {
        this.roomObjectCollection = new RoomObjectCollection();
    }

    public RoomObjectCollection objectCollection() {
        return roomObjectCollection;
    }
}
