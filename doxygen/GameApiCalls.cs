/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
 
/*!

@page GameApiCalls Game API Calls

This was going to be one of those framework diagrams with 
@ref Boundary "Boundary" sitting on top of @ref Walking "Walking", sitting on top
of @ref Animation "Animation". But there are not too many dependent
systems - so its really just a link page.
 
@dot
digraph I
{ 
	compound=true;
	graph[
		rankdir="TB"
		, compound=true
	];
	
	node [
		shape=record
		, fontname=arial
		, fontsize=8
		, style=filled 
		, pad=0.0
	];
	
	subgraph [
		 shape=record
		 , fontname=arial
		 , fontsize=8
		 , style=filled
		 , fillcolor="blue:cyan"
		 , gradientangle="90"   
		 , center=false
	];


	subgraph cluster_Animation
	{
		URL="@ref AnimationSystem"
		label="Animation System" 
		labeljust="l"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetAnimationLastFrame" label="api.getAnimationLastFrame"] IGetAnimationLastFrame;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetAnimationLength" label="api.getAnimationLength"] IGetAnimationLength;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetCurrentAnimation" label="api.getCurrentAnimation"] IGetCurrentAnimation;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetCurrentFrame" label="api.getCurrentFrame"] IGetCurrentFrame;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IIncremementFrameWithWraparound" label="api.IIncremementFrameWithWraparound"] IIncremementFrameWithWraparound;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IIsAnimation" label="api.isAnimation"] IIsAnimation;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IIsInDebugMode" label="api.isInDebugMode"] IIsInDebugMode;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IIsVisible" label="api.isVisible"] IIsVisible;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectCurrent" label="api.setAnimationAsObjectCurrent"] ISetAnimationAsObjectCurrent;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectCurrentAndSetFrame" label="api.setAnimationAsObjectCurrentAndSetFrame"] ISetAnimationAsObjectCurrentAndSetFrame;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectInitial" label="api.setAnimationAsObjectInitial"] ISetAnimationAsObjectInitial;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationDuration" label="api.setAnimationDuration"] ISetAnimationDuration;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetCurrentFrame" label="api.setCurrentFrame"] ISetCurrentFrame;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetDisplayName" label="api.setDisplayName"] ISetDisplayName;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetParallaxX" label="api.setParallaxX"] ISetParallaxX;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetVisible" label="api.setVisible"] ISetVisible;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IUpdateObjectToCorrectImage" label="api.updateObjectToCorrectImage"] IUpdateObjectToCorrectImage;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IHide" label="api.hide"] IHide;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IShow" label="api.show"] IShow;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetY" label="api.setY"] ISetY;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetX" label="api.setX"] ISetX;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetY" label="api.getY"] IGetY;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetX" label="api.getX"] IGetX;
	}
	
	
	subgraph cluster_OtherDisplayModes
	{
		URL="@ref GUI"
		label="GUI"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IDisplayTitleCard" label="api.displayTitleCard"] IDisplayTitleCard;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetActiveGuiState"  label="api.setActiveGuiState"] ISetActiveGuiState;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IShareWinning" label="api.shareWinning"] IShareWinning;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetSceneGuiHeight" label="api.getSceneGuiHeight"] IGetSceneGuiHeight;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetSceneGuiWidth" label="api.getSceneGuiWidth"] IGetSceneGuiWidth;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetTitleCard" label="api.setTitleCard"] ISetTitleCard;
	}
	
	subgraph cluster_ExecutingChains
	{	
		URL="@ref ExecutingChains"
		label="Executing Chains"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ICreateChainRootAction" label="api.createChainRootAction"] ICreateChainRootAction;	
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IExecuteChainedAction" label="api.executeChainedAction"] IExecuteChainedAction;
	}
	
	subgraph cluster_Inventory
	{
		URL="@ref Inventory"
		label="Inventory"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IShowInventoryItem" label="api.showInventoryItem"] IShowInventoryItem;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetInventoryItemDisplayName" label="api.setInventoryItemDisplayName"] ISetInventoryItemDisplayName;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetInventoryItemVisible" label="api.setInventoryItemVisible"] ISetInventoryItemVisible;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IHideAllInventory" label="api.hideAllInventory"] IHideAllInventory;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IHideInventoryItem" label="api.hideInventoryItem"] IHideInventoryItem;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IIsInventoryItemVisible" label="api.isInventoryItemVisible"] IIsInventoryItemVisible;
	}
	
	subgraph cluster_ValueStore
	{
		URL="@ref ValueStore"
		label="ValueStore"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetValue" label="api.setValue"] ISetValue;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetValue" label="api.getValue"] IGetValue;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IIsTrue" label="api.isTrue"] IIsTrue;
	}
	
	subgraph cluster_RoomSwitching
	{
		URL="@ref RoomSwitching"
		label="RoomSwitching"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISwitchToScene" label="api.switchToScene"] ISwitchToScene;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetLastSceneName" label="api.getLastSceneName"] IGetLastSceneName;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetCurrentSceneName" label="api.getCurrentSceneName"] IGetCurrentSceneName;
	}
	
	subgraph cluster_Verbs
	{
		URL="@ref Verbs"
		label="Verbs"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IUpdateVerbUI" label="api.updateVerbUI"] IUpdateVerbUI;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IRemoveVerbByCode" label="api.removeVerbByCode"] IRemoveVerbByCode;
	}
	
	subgraph cluster_Boundary
	{
		 
		URL="@ref Boundary" 
		labeljust="l"	
		label="Boundary"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetDefaultSceneObject" label="api.getDefaultSceneObject"] IGetDefaultSceneObject;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetDefaultSceneObject" label="api.setDefaultSceneObject"] ISetDefaultSceneObject;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IAddBoundaryPoint" label="api.addBoundaryPoint"] IAddBoundaryPoint;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IAddObstacleRect" label="api.addObstacleRect"] IAddObstacleRect;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IAddBoundaryGate" label="api.addBoundaryGate"] IAddBoundaryGate;
	} 
	
	subgraph cluster_Walking
	{
		URL="@ref Walking" 
		label="Walking"
		labeljust="l"
		
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation" label="api.alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation"] IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectWalkDirection" label="api.setAnimationAsObjectWalkDirection"] ISetAnimationAsObjectWalkDirection;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetScreenCoordsPerSecond" label="api.setScreenCoordsPerSecond"] ISetScreenCoordsPerSecond;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleY" label="api.setBaseMiddleY"] ISetBaseMiddleY;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleX" label="api.setBaseMiddleX"] ISetBaseMiddleX;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleY" label="api.getBaseMiddleY"] IGetBaseMiddleY;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleX" label="api.getBaseMiddleX"] IGetBaseMiddleX;
	} 
	
	
	subgraph cluster_Talking
	{
		URL="@ref Talking" 
		label="Talking"
		labeljust="l"
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneTalker" label="api.setAnimationAsSceneTalker"] ISetAnimationAsSceneTalker;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationMaxTalkRect" label="api.setAnimationMaxTalkRect"] ISetAnimationMaxTalkRect;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.IGetIsTalkingAlwaysWithoutIncrementing" label="api.getIsTalkingAlwaysWithoutIncrementing"] IGetIsTalkingAlwaysWithoutIncrementing;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetIsTalkingAlwaysWithoutIncrementing" label="api.setIsTalkingAlwaysWithoutIncrementing"] ISetIsTalkingAlwaysWithoutIncrementing;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneAnswerer" label="api.setAnimationAsSceneAnswerer"] ISetAnimationAsSceneAnswerer;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneAsker" label="api.setAnimationAsSceneAsker"] ISetAnimationAsSceneAsker;
		node [URL="@ref com.github.a2g.core.interfaces.methods.game.ISetTalkingColor" label="api.setTalkingColor"] ISetTalkingColor;
	}
	 
	

	IAddBoundaryGate->IGetBaseMiddleX [style="invis"];
	IGetBaseMiddleX->IGetX [style="invis"];
	ISetAnimationAsSceneTalker->IGetAnimationLastFrame [style="invis"];
	
}
@enddot

@dot
digraph G {
  compound=true;
  subgraph cluster0 {
    a -> b;
    a -> c;
    b -> d;
    c -> d;
  }
  subgraph cluster1 {
    e -> g;
    e -> f;
  }
  b -> f [lhead=cluster1];
  d -> e;
  c -> g [ltail=cluster0,lhead=cluster1];
  c -> e [ltail=cluster0];
  d -> h;
}
@enddot
*/