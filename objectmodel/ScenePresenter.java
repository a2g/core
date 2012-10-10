package com.github.a2g.core.objectmodel;


import com.google.gwt.event.shared.EventBus;
import com.github.a2g.bridge.image.Image;
import com.github.a2g.bridge.image.LoadHandler;
import com.github.a2g.bridge.panel.ScenePanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.sceneobject.Scene;


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
	private Scene scene;
	  
    public ScenePresenter(final AcceptsOneThing panel, EventBus bus, InternalAPI api) {
        this.setWidth(320);
        this.setHeight(180);
        this.scene = new Scene();
        this.eventBus = bus;
        // this.theScene = new Scene();
        this.view = new ScenePanel(bus,api);
        panel.setThing(view);
        view.setVisible(true);
       
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
	
	public void inititateLoadingOfImage(LoadHandler lh, Image image) {
		view.inititateLoadingOfImage(image,lh);
	}

	public Scene getModel() {
		// TODO Auto-generated method stub
		return scene;
	}

	public void reset()
	{
		this.scene = new Scene();
	}
	
    
}


;
