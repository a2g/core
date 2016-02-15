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
	rankdir=RL
	subgraph cluster_three 
	{
		fillcolor="blue:cyan" 
		label="Boundary Methods" 
		fontcolor="black" 
		style="filled" 
		gradientangle="90"
		fontname=Courier
		fontsize=10
		URL="@ref com.github.a2g.core.objectmodel.MasterPanel"
		
		subgraph cluster_four 
		{
			fillcolor="blue:cyan" 
			label="Boundary Methods" 
			fontcolor="black" 
			style="filled" 
			gradientangle="90"
			fontname=Courier
			fontsize=10
			URL="@ref ConceptOfBoundary"
			
			node [
				label="IAddBoundaryGate"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.IAddBoundaryGate"
			] b1;
			
			node [
				label="IAddBoundaryPoint"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.IAddBoundaryPoint"
			] b2;
			
			node [
				label=
				"IAddObstacleRect"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.IAddObstacleRect"
			] b3;
						
			node [
				label=
				"ISetDefaultSceneObject"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.ISetDefaultSceneObject"
			] b4;  
		}
		
		subgraph cluster_five
		{
			fillcolor="blue:cyan" 
			label="Concept of BaseMiddle" 
			fontcolor="black" 
			style="filled" 
			gradientangle="90"
			fontname=Courier
			fontsize=10
			URL="@ref ConceptOfBaseMiddle"
			
			node [
				label="IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation"
			] w1;
			
			node [
				label="IGetBaseMiddleX"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleX"
			] w2;
			
			node [
				label="IGetBaseMiddleY"
				shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
				URL="@ref com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleY"
			] w3;

						
			struct3 [
				rankdir=TB,
				shape=record, 
				label="{{Shufffle|Cut|Push} |{Talk|Swing|Pull} |{Examine|Give|Throw}| {Grab|Use|Look}|  .   .   .   .   .   Inventory   .   .   .   .   .   }"
				URL="@ref com.github.a2g.core.objectmodel.VerbsPanel"
			] ab;
		}
	}
}
@enddot
*/