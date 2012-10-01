package com.github.a2g.core.authoredscene;

import com.github.a2g.core.objectmodel.CommandLinePresenter;
import com.github.a2g.core.objectmodel.DialogTreePresenter;
import com.github.a2g.core.objectmodel.InventoryItem;
import com.github.a2g.core.objectmodel.InventoryPresenter;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.VerbsPresenter;
import com.github.a2g.core.sceneobject.Animation;
import com.github.a2g.core.sceneobject.SceneObject;

public interface OnEveryFrameAPI {
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
 
}
