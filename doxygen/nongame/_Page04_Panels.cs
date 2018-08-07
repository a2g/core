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

@page Panels

This is how the default GUI is set out. <br>
The class that does the actualy layout can be found at  @ref com.github.a2g.core.objectmodel.MasterPanel. <br>
So changing it around can be done by modifying this one class. <br>

@dot
digraph G 
{
	rankdir=RL
	subgraph cluster_three 
	{
		fillcolor="blue:cyan" 
		label="Master Panel" 
		fontcolor="black" 
		style="filled" 
		gradientangle="90"
		fontname=Courier
		fontsize=10
		URL="@ref com.github.a2g.core.objectmodel.MasterPanel"
		
		node [
			label=
			"\n\n\n\n\n                         Scene Panel                            \n\n\n\n\n "
			shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
			URL="@ref com.github.a2g.core.objectmodel.ScenePanel"
		] fnode;
		
		node [
			label=
			"                       Command Line Panel                       "  
			shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
			URL="@ref com.github.a2g.core.objectmodel.CommandLinePanel"
		] gnode;
		
		struct3 [
			shape=record, 
			label="{{Shufffle|Cut|Push} |{Talk|Swing|Pull} |{Examine|Give|Throw}| {Grab|Use|Look}|  .   .   .   .   .   Inventory   .   .   .   .   .   }"
			URL="@ref com.github.a2g.core.objectmodel.VerbsPanel"
		];   
	}
	
}
@enddot
*/