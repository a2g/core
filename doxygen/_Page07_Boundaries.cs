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

@page Boundaries

This is how the default GUI is set out. <br>

@dot
digraph H 
{
 
	graph[
		rankdir="RL"
		, "rank"="max"]
	
	subgraph [fontcolor="black", 
		style="filled" ,
		gradientangle="90",
		fillcolor="blue:cyan",
		shape=record,
		fontname=Courier,
		fontsize=10]
		
	node [
		shape=record
		, fontname=arial
		, fontsize=6
		, style=filled 
		, pad=0.0
	];

	 
		
	 
	subgraph cluster_four 
	{
	 	label="Walk Boundaries"   
		URL="@ref ConceptOfBoundary"
		node [] IAddBoundaryGate;
		node [] IAddBoundaryPoint;
		node [] IAddObstacleRect;
		node [] IGetDefaultSceneObject;
		node [] ISetDefaultSceneObject; 
	}
	
	subgraph "cluster_five"
	{
	 	label="Walking" 
		URL="@ref ConceptOfBaseMiddle"
		node [] IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation;
		node [] IGetBaseMiddleX;
		node [] IGetBaseMiddleY;
		node [] ISetBaseMiddleX;
		node [] ISetBaseMiddleY;
		node [] ISetAnimationAsObjectSpecial;
		node [] ISetScreenCoordsPerSecond;
	}
	
	subgraph cluster_89
	{
		label = "Talking"
		node [] IGetIsTalkingAlwaysWithoutIncrementing;
		node [] ISetAnimationAsSceneTalker;
		node [] ISetAnimationMaxTalkRect;
		node [] ISetTalkingColor;
		node [] ISetIsTalkingAlwaysWithoutIncrementing;
		node [] ISetAnimationAsSceneAnswerer;
		node [] ISetAnimationAsSceneAsker;
	}
	
	subgraph cluster_DisplayModes
	{
		label = "OtherDisplayModes"
		node [] ISetTitleCard;
		node [] IDisplayTitleCard;
		node [] ISetActiveGuiState;
		node [] IShareWinning;
	}
	
}
@enddot
*/