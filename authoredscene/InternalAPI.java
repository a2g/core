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
import com.github.a2g.core.primitive.ColorEnum;


public interface InternalAPI 
extends ImageAddAPI, OnDoCommandAPI {
    // get by ID
    @Override
	public SceneObject getObject(short code);
    @Override
	public Animation getAnimation(int a);
    @Override
	public InventoryItem getInventoryItem(int i);

    // property access methods
    @Override
	public void setValue(String name, int value);
    @Override
	public int getValue(String name);
    @Override
	public boolean isTrue(String name);
    
    // gui methods
    @Override
	public DialogTreePresenter getDialogTreeGui();
    @Override
	public VerbsPresenter getVerbsGui();
    @Override
	public InventoryPresenter getInventoryGui();
    @Override
	public CommandLinePresenter getCommandLineGui();
    @Override
	public ScenePresenter getSceneGui();

    // nearly a gui method
    @Override
	public SceneAPI getCurrentScene();

    // helpful for game
    @Override
	public void switchToScene(String scene);
    @Override
	public String getLastScene();
    @Override
	public boolean isInDebugMode();
    public void executeBaseActionAndProcessReturnedInteger(BaseAction a);
    public void setLastCommand(double x, double y, int v, String a, String b);

    // dialog tree methods
    public void executeBranchOnCurrentScene(int place);
	public int getPopupDelay();
	public void restartReloading();
	
	public void displayCueCard(String text, ColorEnum color);
 
}


