package com.github.a2g.core;


import com.github.a2g.core.authoredroom.Point;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class RoomPresenter {
    private int width;
    private int height;
   
    // private Room theRoom;
    private RoomPanel view;
    public RoomPanel getView() {
        return view;
    }

    public void setView(RoomPanel view) {
        this.view = view;
    }

    EventBus eventBus;
    IAmHostingTheMasterPresenter parent;
	  
    public RoomPresenter(final AcceptsOneWidget panel, EventBus bus, IAmHostingTheMasterPresenter parent) {
        this.setWidth(320);
        this.setHeight(180);

        this.eventBus = bus;
        this.parent = parent;
        // this.theRoom= new Room();
        this.view = new RoomPanel();
        panel.setWidget(view);
        // this.theInventoryItemMap = new TreeMap<Integer, InventoryItem>();
    }

    // start go in panel
     
    public void setWorldViewSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        this.view.setSize("" + width + "px",
                "" + height + "px");
    }

    public Point getSizeOfRoomArea() {
        // int width = this.roomArea.
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
    
}


;
