package com.github.a2g.core.objectmodel;


import com.github.a2g.bridge.Image;
import com.github.a2g.bridge.Point;
import com.github.a2g.bridge.panel.ScenePanel;
import com.google.gwt.event.shared.EventBus;
import com.github.a2g.bridge.AcceptsOneThing;


public class ScenePresenter {
    private int width;
    private int height;
   
    // private Scene theScene;
    private ScenePanel view;
    public ScenePanel getView() {
        return view;
    }

    public void setView(ScenePanel view) {
        this.view = view;
    }

    EventBus eventBus;
    MasterPresenterHostAPI parent;
	  
    public ScenePresenter(final AcceptsOneThing panel, EventBus bus, MasterPresenterHostAPI parent) {
        this.setWidth(320);
        this.setHeight(180);

        this.eventBus = bus;
        this.parent = parent;
        // this.theScene= new Scene();
        this.view = new ScenePanel();
        panel.setThing(view);
        // this.theInventoryItemMap = new TreeMap<Integer, InventoryItem>();
    }

    // start go in panel
     
    public void setWorldViewSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        this.view.setSize("" + width + "px",
                "" + height + "px");
    }

    public Point getSizeOfSceneArea() {
        // int width = this.sceneArea.
        return new Point(this.getWidth(),
                this.getHeight());
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
    // end go in panel.

	public void clear() {
		view.clear();
	}
	
	public void inititateLoadingOfImage(Image image) {
		view.add(image);
	}
    
}


;
