/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredroom;


import com.github.a2g.core.Animation;
import com.github.a2g.core.ChoicesPresenter;
import com.github.a2g.core.CommandLinePresenter;
import com.github.a2g.core.InventoryItem;
import com.github.a2g.core.InventoryPresenter;
import com.github.a2g.core.RoomObject;
import com.github.a2g.core.RoomPresenter;
import com.github.a2g.core.VerbsPresenter;
import com.github.a2g.core.action.BaseAction;


public interface IAmTheMainAPI 
extends IAmTheResourceAPI{
    // get by ID
    public RoomObject getObject(int code);
    public Animation getAnimation(int a);
    public InventoryItem getInventoryItem(int i);

    // property access methods
    public void setValue(String name, int value);
    public int getValue(String name);
    public boolean isTrue(String name);
    
    // gui methods
    public ChoicesPresenter getChoicesGui();
    public VerbsPresenter getVerbsGui();
    public InventoryPresenter getInventoryGui();
    public CommandLinePresenter getCommandLineGui();
    public RoomPresenter getRoomGui();

    // nearly a gui method
    public IAmARoom getCurrentRoom();

    // helpful for game
    public void switchToRoom(String room);
    public String getLastRoom();
    public boolean isInDebugMode();
    public void executeActionBaseOrChoiceActionBaseAndProcessReturnedInteger(BaseAction a);
    public void setLastCommand(double x, double y, int v, String a, String b);

    // choice methods
    public void executeChoiceOnCurrentRoom(int place);
	public int getPopupDelay();
 
}


