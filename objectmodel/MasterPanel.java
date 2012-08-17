package com.github.a2g.core.objectmodel;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MasterPanel extends VerticalPanel {
    SimplePanel hostForCommandLine;
    SimplePanel hostForInventory;
    SimplePanel hostForVerbs;
    SimplePanel hostForScene;
    SimplePanel hostForDialogTree;
    SimplePanel hostForLoading;
    
    MasterPanel() {

    	// create all the host panels, that we want to arrange.
        hostForCommandLine = new SimplePanel();
        hostForInventory = new SimplePanel();
        hostForVerbs = new SimplePanel();
        hostForScene = new SimplePanel();
        hostForDialogTree = new SimplePanel();
        hostForLoading = new SimplePanel();
    	

        // will be constructed from two vertical stacks.
    	AbsolutePanel stackForSceneAndLoading = new AbsolutePanel();
    	AbsolutePanel stackForDialogTreeInvAndCommand = new AbsolutePanel();
 
        DOM.setStyleAttribute(
        		stackForDialogTreeInvAndCommand.getElement(),
                "backgroundColor", "Black");
        DOM.setStyleAttribute(
        		stackForSceneAndLoading.getElement(),
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
        	stackForDialogTreeInvAndCommand.add(commandLineAndVerbsAndInventory);
        	stackForDialogTreeInvAndCommand.add(hostForDialogTree);
        }
        {
        	stackForSceneAndLoading.add(hostForScene);
        	stackForSceneAndLoading.add(hostForLoading,0,0);
        }
        
        this.add(stackForSceneAndLoading);
        this.add(stackForDialogTreeInvAndCommand);
    	
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


	public SimplePanel getHostForScene() {
		return hostForScene;
	}


	public SimplePanel getHostForDialogTree() {
		return hostForDialogTree;
	}
	
	public SimplePanel getHostForLoading() {
		return hostForLoading;
	}
	
	void setDialogTreeActive()
	{
		hostForDialogTree.setVisible(true);
		hostForLoading.setVisible(false);
	}
	
	void setLoadingActive()
	{
		hostForDialogTree.setVisible(false);
		hostForLoading.setVisible(true);
	}
	
	void setGameActive()
	{
		hostForDialogTree.setVisible(false);
		hostForLoading.setVisible(false);
	
	}
	
	
}	
