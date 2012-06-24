package com.github.a2g.core;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MasterPanel extends VerticalPanel {
    SimplePanel hostForCommandLine;
    SimplePanel hostForInventory;
    SimplePanel hostForVerbs;
    SimplePanel hostForRoom;
    SimplePanel hostForChoices;
    SimplePanel hostForLoading;
    
    MasterPanel() {

    	// create all the host panels, that we want to arrange.
        hostForCommandLine = new SimplePanel();
        hostForInventory = new SimplePanel();
        hostForVerbs = new SimplePanel();
        hostForRoom = new SimplePanel();
        hostForChoices = new SimplePanel();
        hostForLoading = new SimplePanel();
    	

        // will be constructed from two vertical stacks.
    	AbsolutePanel stackForRoomAndLoading = new AbsolutePanel();
    	AbsolutePanel stackForChoicesInvAndCommand = new AbsolutePanel();
 
        DOM.setStyleAttribute(
        		stackForChoicesInvAndCommand.getElement(),
                "backgroundColor", "Black");
        DOM.setStyleAttribute(
        		stackForRoomAndLoading.getElement(),
                "backgroundColor", "Black");
     


        {
        	HorizontalPanel verbsAndInventory = new HorizontalPanel();

        	verbsAndInventory.add(hostForVerbs);
        	verbsAndInventory.add(
        			hostForInventory);

        	VerticalPanel commandLineAndVerbsAndInventory = new VerticalPanel();

        	commandLineAndVerbsAndInventory.add(
        			hostForCommandLine);
        	commandLineAndVerbsAndInventory.add(
        			verbsAndInventory);
        	stackForChoicesInvAndCommand.add(commandLineAndVerbsAndInventory);
        	stackForChoicesInvAndCommand.add(hostForChoices);
        }
        {
        	stackForRoomAndLoading.add(hostForRoom);
        	stackForRoomAndLoading.add(hostForLoading,0,0);
        }
        
        this.add(stackForRoomAndLoading);
        this.add(stackForChoicesInvAndCommand);
    	
    }

	public SimplePanel getHostForCommandLine() {
		return hostForCommandLine;
	}


	public SimplePanel getHostForInventory() {
		return hostForInventory;
	}

	public SimplePanel getHostForVerbs() {
		return hostForVerbs;
	}


	public SimplePanel getHostForRoom() {
		return hostForRoom;
	}


	public SimplePanel getHostForChoices() {
		return hostForChoices;
	}
	
	public SimplePanel getHostForLoading() {
		return hostForLoading;
	}
	
	void setChoicesActive()
	{
		hostForChoices.setVisible(true);
		hostForLoading.setVisible(false);
	}
	
	void setLoadingActive()
	{
		hostForChoices.setVisible(false);
		hostForLoading.setVisible(true);
	}
	
	void setGameActive()
	{
		hostForChoices.setVisible(false);
		hostForLoading.setVisible(false);
	
	}
	
	
}	
