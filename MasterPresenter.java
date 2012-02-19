/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChoicesBaseAction;
import com.github.a2g.core.authoredroom.IAmARoom;
import com.github.a2g.core.authoredroom.IAmTheMainAPI;
import com.github.a2g.core.authoredroom.RoomBase;
import com.github.a2g.core.event.SaySpeechCallChoiceEvent;
import com.github.a2g.core.event.SaySpeechCallChoiceEventHandler;
import com.github.a2g.core.mouse.ImageMouseClickHandler;
import com.github.a2g.core.mouse.InventoryItemMouseOverHandler;
import com.github.a2g.core.mouse.RoomObjectMouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;


public class MasterPresenter  
        implements IAmTheMainAPI, SaySpeechCallChoiceEventHandler {
    
    private CommandLinePresenter commandLinePresenter;
    private InventoryPresenter inventoryPresenter;
    private VerbsPresenter verbsPresenter;
    private RoomPresenter roomPresenter;
    private ChoicesPresenter choicesPresenter;
    
    private IAmARoom callbacks;
    private TreeMap<Integer, RoomObject> theObjectMap;
    private TreeMap<Integer, Animation> theAnimationMap;
  
    private EventBus bus;
    private IAmHostingTheMasterPanel parent;
    private int noImagesAreGreaterThanThis;
    private Timer timer;
    private Room room;
    private MasterPanel masterPanel;

    public MasterPresenter(/* final AcceptsOneWidget panel*/EventBus bus, IAmHostingTheMasterPanel parent) {
        this.bus = bus;
        this.timer = null;
        this.parent = parent;
        this.noImagesAreGreaterThanThis = 0;
        this.theObjectMap = new TreeMap<Integer, RoomObject>();
        this.theAnimationMap = new TreeMap<Integer, Animation>();
     
        bus.addHandler(
                SaySpeechCallChoiceEvent.TYPE,
                this);
       
        AbsolutePanel stackedGuiItems = new AbsolutePanel(); 

        DOM.setStyleAttribute(
                stackedGuiItems.getElement(),
                "backgroundColor", "Black");
        
        SimplePanel hostForCommandLine = new SimplePanel();
        SimplePanel hostForInventory = new SimplePanel();
        SimplePanel hostForVerbs = new SimplePanel();
        SimplePanel hostForRoom = new SimplePanel();
        SimplePanel hostForChoices = new SimplePanel(); {

            HorizontalPanel verbsAndInventory = new HorizontalPanel();

            verbsAndInventory.add(hostForVerbs);
            verbsAndInventory.add(
                    hostForInventory);

            VerticalPanel commandLineAndVerbsAndInventory = new VerticalPanel();

            commandLineAndVerbsAndInventory.add(
                    hostForCommandLine);
            commandLineAndVerbsAndInventory.add(
                    verbsAndInventory);
            stackedGuiItems.add(
                    commandLineAndVerbsAndInventory);
        } {
            stackedGuiItems.add(hostForChoices);
        }
        
        this.masterPanel = new MasterPanel();
        masterPanel.add(hostForRoom);
        masterPanel.add(stackedGuiItems);
        
        this.commandLinePresenter = new CommandLinePresenter(
                hostForCommandLine, bus, this);
        this.inventoryPresenter = new InventoryPresenter(
                hostForInventory, bus, parent);
        this.verbsPresenter = new VerbsPresenter(
                hostForVerbs, bus, parent);
        this.roomPresenter = new RoomPresenter(
                hostForRoom, bus, parent);

        this.choicesPresenter = new ChoicesPresenter(
                hostForChoices, bus, this);
        
        roomPresenter.setWorldViewSize(
                roomPresenter.getWidth(),
                roomPresenter.getHeight());
        
    }
    
    public void setCallbacks(IAmARoom callbacks) {
        this.callbacks = callbacks;
        this.getCommandLineGui().setCallbacks(
                callbacks);
    }

    public MasterPresenter getHeaderPanel() {
        return this;
    }

    public void initRoom() {
        this.theObjectMap.clear();
        this.theAnimationMap.clear();
        noImagesAreGreaterThanThis = 0;

        this.room = new Room();
    }

    public boolean addInventory(int numberPrefix, int x, int y, String objectKeyword, String animationKeyword, int objectCode, int objPlusAnimCode, ImageResource imageResource) {

        if (this.callbacks == null) {
            return true;
        }
        InventoryItem item = this.inventoryPresenter.getInventory().items().at(
                objectKeyword);
        boolean result = true;

        if (item == null) {
            final com.google.gwt.user.client.ui.Image image = new com.google.gwt.user.client.ui.Image(
                    imageResource);

            image.addMouseMoveHandler(
                    new InventoryItemMouseOverHandler(
                            bus, this,
                            objectKeyword,
                            objectCode));
            image.addClickHandler(
                    new ImageMouseClickHandler(
                            bus,
                            this.roomPresenter.getView()));
            
            result = inventoryPresenter.addInventory(
                    objectKeyword, objectCode,
                    image);
        }
        
        return result;
    } 

    public boolean insertObjectsAndAnimation(int numberPrefix, int x, int y, String objectKeyword, String animationKeyword, int objectCode, int objPlusAnimCode, ImageResource imageResource) {
        // RemoveAllImagesFromPanelGreaterThan(numberPrefix);
        boolean result = addObjectsAndAnimation(
                numberPrefix, x, y,
                objectKeyword,
                animationKeyword, objectCode,
                objPlusAnimCode, imageResource);

        // AddAllImagesToPanelGreaterThan(numberPrefix);
        return result;
    }

    public boolean addObjectsAndAnimation(int numberPrefix, int x, int y, String objectKeyword, String animationKeyword, int objectCode, int objPlusAnimCode, ImageResource imageResource) {
        if (this.callbacks == null) {
            return true;
        }

        // objects and animations
        RoomObject roomObject = this.room.objectCollection().at(
                objectKeyword);

        if (roomObject == null) {
            roomObject = new RoomObject(
                    objectKeyword,
                    roomPresenter.getWidth(),
                    roomPresenter.getHeight());
            roomObject.setNumberPrefix(
                    numberPrefix);
            roomObject.setObjectCode(
                    objectCode);
            int code = objectCode;

            if (code == -1) {
                Window.alert(
                        "Missing initial image for "
                                + objectKeyword
                                + " "
                                + animationKeyword);
                return true;
            }
            ;

            this.theObjectMap.put(code,
                    roomObject);
            this.room.objectCollection().add(
                    roomObject);
        }

        // if its in the animation map already then we need to be careful 
        // because we might have run in to a prematurely added animation.
        Animation animation = this.theAnimationMap.get(
                objPlusAnimCode);

        if (animation == null) {
            // much simpler if not in the animation map. 
            animation = new Animation(
                    animationKeyword,
                    roomObject);
            this.theAnimationMap.put(
                    objPlusAnimCode, animation);
            roomObject.animations().add(
                    animation);
        } else // ... it already exists but isn't in the roomObjectList...
        {
            Animation animation2 = roomObject.animations().at(
                    animationKeyword);

            if (animation2 == null) {

                animation.setRoomObject(
                        roomObject);
                animation.setKeyword(
                        animationKeyword);
                roomObject.animations().add(
                        animation);
                // then update all properties that could have already been set on the anim
                if (animation.getWasSetAsHomeAnimation()) {
                    animation.setAsHomeAnimation();
                }
                if (animation.getWasSetAsTalkingAnimation()) {
                    animation.setAsTalkingAnimation();
                }
                if (animation.getWasSetAsSpecialAnimation()) {
                    animation.setAsSpecialAnimation(
                            animation.getDesignatedSpecialAnimation());
                }
                if (animation.getWasSetAsCurrentAnimation()) {
                    animation.setAsCurrentAnimation();
                }

            }
        }

        assert (imageResource != null);
        final com.google.gwt.user.client.ui.Image image = new com.google.gwt.user.client.ui.Image(
                imageResource);
        Image imageAndPos = new Image(image,
                this.roomPresenter.getView(),
                x, y);

        animation.getImageAndPosCollection().add(
                imageAndPos);
        int before = getIndexOfFirstElementHigherThan(
                numberPrefix);

        imageAndPos.addImageToPanel(0, 0,
                before);

        image.addMouseMoveHandler(
                new RoomObjectMouseOverHandler(
                        bus, this,
                        objectKeyword,
                        roomObject.getObjectCode()));
        image.addClickHandler(
                new ImageMouseClickHandler(bus,
                this.roomPresenter.getView()));

        if (numberPrefix
                > noImagesAreGreaterThanThis) {
            noImagesAreGreaterThanThis = numberPrefix;
        }		

        return true;
    }

    public RoomObject getObject(int code) {
        RoomObject ob = this.theObjectMap.get(
                code);

        if (ob == null) {
            int placeBreakpointHere = 0;

            placeBreakpointHere++;
        }
        return ob;
    }

    public Animation getAnimation(int code) {
        Animation anim = this.theAnimationMap.get(
                code);

        if (anim == null) {
            // first param is name, second is parent;
            anim = new Animation("", null);
            this.theAnimationMap.put(code,
                    anim);
        }
        return anim;
    }

    public InventoryItem getInventoryItem(int i) {
        InventoryItem inv = inventoryPresenter.getInventoryItem(
                i);

        return inv;
    }

    public int getIndexOfFirstElementHigherThan(int numberPrefix) {
        int numberOfImages = -1;
        ArrayList<RoomObject> list = this.room.objectCollection().getSortedList();

        Iterator<RoomObject> it = list.iterator();

        while (it.hasNext()) {
            RoomObject o = it.next();

            if (o.getNumberPrefix()
                    > numberPrefix) {
                return numberOfImages;
            }

            for (int i = 0; i
                    < o.animations().getCount(); i++) {
                Animation a = o.animations().at(
                        i);
                ImageCollection frames = a.getFrames();

                for (int j = 0; j
                        < frames.count(); j++) {
                    Image image = frames.at(j);

                    assert(image != null);
                    numberOfImages++;
                }
            }
        }
        return numberOfImages;
    }

    public void onEnterRoom() {
        BaseAction a = this.callbacks.onEnterRoom();

        executeActionBaseOrChoiceActionBaseAndProcessReturnedInteger(
                a);
    }

    public void executeActionBaseOrChoiceActionBaseAndProcessReturnedInteger(BaseAction a) {
        RoomBase b = new RoomBase();
        int result = b.execute(a);

        result++;
    }

    public void showEverything() {
        for (int i = 0; i
                < this.room.objectCollection().count(); i++) {
            RoomObject roomObject = this.room.objectCollection().at(
                    i);

            if (roomObject != null) {
                if (roomObject.animations().at(
                        RoomBase.INITIAL)
                                != null) {
                    roomObject.animations().at(RoomBase.INITIAL).setAsCurrentAnimation();
                } else {
                    boolean b = true;

                    b = (b) ? true : false;
                }

            }
        }

    }

    public void loadVitalResources() {
        this.callbacks.onLoadResources();

    }

    public void prepareRoomForFocus() {
        this.callbacks.onPrepareRoomForFocus();
    }	

    public void doEveryFrame() {
        this.callbacks.onEveryFrame();
    }

    public void setRoom(IAmARoom roomCallbacks) {

        roomCallbacks.onReceiveGameAPIObject(
                this);
        setCallbacks(roomCallbacks);
        initRoom();
        loadVitalResources();
        showEverything();
        prepareRoomForFocus();
        loadInventoryFromAPI();
        this.choicesPresenter.setInChoicesMode(
                false);
        startEveryFrameCallbacks();
        onEnterRoom();

    }

    public void loadInventoryFromAPI() {
        inventoryPresenter.updateInventory();
    }

    public void saveInventoryToAPI() {
        InventoryItemCollection items = this.inventoryPresenter.getInventory().items();

        for (int i = 0; i < items.getCount(); i++) {
            String name = items.at(i).getKeyword();

            int isCarrying = items.at(i).isVisible()
                    ? 1
                    : 0;

            setValue(
                    "CARRYING_"
                            + name.toUpperCase(),
                            isCarrying);
        }
    }

    @Override
    public void setValue(String name, int value) {
        parent.setValue(name, value);

    }

    @Override
    public int getValue(String name) {
        int i = parent.getValue(name);

        return i;
    }

    @Override
    public boolean isTrue(String name) {
        int property = getValue(name);

        return property != 0;
    }

    public void switchToRoom(String room) {
        this.parent.instantiateRoomThenCreateNewMasterPanelInitializedToIt(
                room);

    }

    @Override
    public String getLastRoom() {

        return null;
    }

    @Override
    public boolean isInDebugMode() {
        return true;
    }

    public void startEveryFrameCallbacks() {
        this.timer = new Timer() {
            @Override
            public void run() {
                doEveryFrame();
            }
        };

        // Schedule the timer to run once in 5 seconds.
        this.timer.scheduleRepeating(40);
    }

    public void cancelTimer() {
        this.timer.cancel();

    }

    @Override
    public void setLastCommand(double x, double y, int v,
            String a, String b) {
        parent.setLastCommand(x, y, v, a, b);

    }

    public void setCommandLineGui(CommandLinePresenter commandLinePanel) {
        this.commandLinePresenter = commandLinePanel;
    }

    @Override
    public CommandLinePresenter getCommandLineGui() {
        return commandLinePresenter;
    }

    @Override
    public InventoryPresenter getInventoryGui() {
        return inventoryPresenter;
    }

    public Inventory getInventory() {
        return inventoryPresenter.getInventory();
    }

    @Override
    public IAmARoom getCurrentRoom() {
        return this.getCurrentRoom();
    }

    @Override
    public void executeChoiceOnCurrentRoom(int place) {
        ChoicesBaseAction actionChain = this.callbacks.onChoice(
                place);

        executeActionBaseOrChoiceActionBaseAndProcessReturnedInteger(
                actionChain);
    }
		
    @Override
    public void saySpeechAndThenCallChoiceWithSpecifiedInteger(String speech, int place) {
        this.choicesPresenter.clear();
		
        // this code is dodgey because it doesn't use the current room to execute..
        // it uses a dynamically created room to execute it. This seems ok, if it has the same api pointer.
        int objId = getChoicesGui().getChoiceTalker();
        ChoicesBaseAction actionChain = this.callbacks.onChoice(
                place);
        
        RoomBase a = new RoomBase();

        a.setApi(this);
        BaseAction  executeMe = a.say(objId, speech).subroutine(
                actionChain);
        
        executeActionBaseOrChoiceActionBaseAndProcessReturnedInteger(
                executeMe);
		
    }
	
    @Override
    public VerbsPresenter getVerbsGui() {
        return this.verbsPresenter;
    }

    @Override
    public ChoicesPresenter getChoicesGui() {
        return this.choicesPresenter;
    }

    @Override
    public RoomPresenter getRoomGui() {
        return this.roomPresenter;
    }

    @Override
    public void onSaySpeechCallChoice(String speech, int choice) {
        saySpeechAndThenCallChoiceWithSpecifiedInteger(
                speech, choice);
    }

    public Widget getPanel() {
        return masterPanel;
    }
}

