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

@page Experiment

Stuffed potato
@dot
digraph I
{ 
	pack=true
	packedMode="node"
	rankdir=LR
	node [
		shape=record
		, fontname=arial
		, fontsize=6
		, style=filled 
		, pad=0.0
	];
	subgraph [
		 shape=record
		 , fontname=arial
		 , fontsize=6
		 , style=filled
		 , fillcolor="blue:cyan"
		 , gradientangle="90"   
	];

	
		subgraph ConceptOfWalkingBoundaryChecking
		{
			label="Concept of Boundary" 
			URL="@ref ConceptOfBoundary" 
				
			node [] IAddBoundaryGate;
			node [] IAddBoundaryPoint;
			node [] IAddObstacleRect;
			node [] IGetDefaultSceneObject;
			node [] ISetDefaultSceneObject;
		}
		
		subgraph ConceptOfWalking
		{
			label="Concept of Walking" 
			URL="@ref ConceptOfWalking" 
			node [] IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation;
			node [] IGetBaseMiddleX;
			node [] IGetBaseMiddleY;
			node [] ISetBaseMiddleX;
			node [] ISetBaseMiddleY;
			node [] ISetAnimationAsObjectSpecial;
			node [] ISetScreenCoordsPerSecond;
		}
		
		subgraph ConceptOfTalking
		{
			node [] IGetIsTalkingAlwaysWithoutIncrementing;
			node [] ISetAnimationAsSceneTalker;
			node [] ISetAnimationMaxTalkRect;
			node [] ISetTalkingColor;
			node [] ISetIsTalkingAlwaysWithoutIncrementing;
			node [] ISetAnimationAsSceneAnswerer;
			node [] ISetAnimationAsSceneAsker;
		}
		 
		
		subgraph ConceptOfOtherDisplayModes
		{
			node [] ISetTitleCard;
			node [] IDisplayTitleCard;
			node [] ISetActiveGuiState;
			node [] IShareWinning;
		}
		
		subgraph ConceptOfExecutingChains
		{
			node [] ICreateChainRootAction;	
			node [] IExecuteChainedAction;
		}
		
		subgraph ConceptOfInventory
		{
			node [] IShowInventoryItem;
			node [] ISetInventoryItemDisplayName;
			node [] ISetInventoryItemVisible;
			node [] IHideAllInventory;
			node [] IHideInventoryItem;
			node [] IIsInventoryItemVisible;
		}
		
		subgraph ConceptOfValueStore
		{
			node [] ISetValue;
			node [] IGetValue;
			node [] IIsTrue;
		}
		
		subgraph ConceptOfRoomSwitching
		{
			node [] ISwitchToScene;
			node [] IGetLastSceneName;
			node [] IGetCurrentScene;
		}
		
		subgraph ConceptOfVerbs
		{
					
			node [] IUpdateVerbUI;
			node [] IRemoveVerbByCode;
		}
		
		subgraph ConceptOfBasicAnimationSystem
		{
			node [] IGetAnimationLastFrame;
			node [] IGetAnimationLength;
			node [] IGetCurrentAnimation;
			node [] IGetCurrentFrame;
			node [] IGetSceneGuiHeight;
			node [] IGetSceneGuiWidth;
			node [] IGetX;
			node [] IGetY;
			node [] IHide;
			node [] IIncremementFrameWithWraparound;
			node [] IIsAnimation;
			node [] IIsInDebugMode;
			node [] IIsVisible;
			node [] ISetAnimationAsObjectCurrent;
			node [] ISetAnimationAsObjectCurrentAndSetFrame;
			node [] ISetAnimationAsObjectInitial;
			node [] ISetAnimationDuration;
			node [] ISetCurrentFrame;
			node [] ISetDisplayName;
			node [] ISetParallaxX;
			node [] ISetVisible;
			node [] ISetX;
			node [] ISetY;
			node [] IShow;
			node [] IUpdateObjectToCorrectImage;
	}
}
@enddot
*/