	/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredscene;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.CommandLinePresenter;
import com.github.a2g.core.objectmodel.DialogTreePresenter;
import com.github.a2g.core.objectmodel.InventoryItem;
import com.github.a2g.core.objectmodel.InventoryPresenter;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.VerbsPresenter;


public interface InternalAPI 
extends ImageAddAPI, OnDoCommandAPI {
    // get by ID
    public SceneObject getObject(short code);
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
    public ScenePresenter getSceneGui();

    // nearly a gui method
    public SceneAPI getCurrentScene();

    // helpful for game
    public void switchToScene(String scene);
    public String getLastScene();
    public boolean isInDebugMode();
    public void executeBaseActionAndProcessReturnedInteger(BaseAction a);
    public void setLastCommand(double x, double y, int v, String a, String b);

    // dialog tree methods
    public void executeBranchOnCurrentScene(int place);
	public int getPopupDelay();
	public void restartReloading();
 
}


