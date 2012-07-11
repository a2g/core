package com.github.a2g.core.authoredroom;

import com.github.a2g.core.Animation;
import com.github.a2g.core.DialogTreePresenter;
import com.github.a2g.core.CommandLinePresenter;
import com.github.a2g.core.InventoryItem;
import com.github.a2g.core.InventoryPresenter;
import com.github.a2g.core.RoomObject;
import com.github.a2g.core.RoomPresenter;
import com.github.a2g.core.VerbsPresenter;

public interface OnDoCommandAPI {
	   // get by ID
    public RoomObject getObject(short code);
    public Animation getAnimation(int a);
    public InventoryItem getInventoryItem(int i);

    // property access methods
    public void setValue(String name, int value);
    public int getValue(String name);
    public boolean isTrue(String name);
    
    // gui methods
    public DialogTreePresenter getDialogTreeGui();
    public VerbsPresenter getVerbsGui();
    public InventoryPresenter getInventoryGui();
    public CommandLinePresenter getCommandLineGui();
    public RoomPresenter getRoomGui();

    // nearly a gui method
    public RoomAPI getCurrentRoom();

    // helpful for game
    public void switchToRoom(String room);
    public String getLastRoom();
    public boolean isInDebugMode();
 
}
