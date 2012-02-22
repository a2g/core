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
    
    MasterPanel() {
        
        AbsolutePanel stackedGuiItems = new AbsolutePanel(); 

        DOM.setStyleAttribute(
                stackedGuiItems.getElement(),
                "backgroundColor", "Black");
        
        hostForCommandLine = new SimplePanel();
        hostForInventory = new SimplePanel();
        hostForVerbs = new SimplePanel();
        hostForRoom = new SimplePanel();
        hostForChoices = new SimplePanel(); 
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
            stackedGuiItems.add(
                    commandLineAndVerbsAndInventory);
        } 
        {
            stackedGuiItems.add(hostForChoices);
        }
        this.add(hostForRoom);
        this.add(stackedGuiItems);
    	
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

	
}	
