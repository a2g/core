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

@page ExampleCode

For example, a basic authored scene might start out like this:

@dot
digraph G {
    rankdir=LR
    subgraph cluster_one 
    {
        fillcolor="blue:cyan" 
label="public class MyScene implements  SceneAPI                                                                                        \l{\l" 
        fontcolor="black" 
        style="filled" 
        gradientangle="90"
        fontname=Courier
        fontsize=10
			
           node [
label="public MyScene(){                                                                                                                     \l   \l}\l"
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180  fontname=Courier  fontsize=10
         ] anode;
            node [
label="public LoadKickStarter onFillLoadList(IOnFillLoadListImpl api){                                                                     \l   // Click here for how to implement this method.\l}\l"  
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180  fontname=Courier  fontsize=10
URL="@ref how-to-implement-onFillLoadList"
            ] cnode;
            node [
label="public void onPreEntry(IOnPreEntry api){                                                                                            \l   // Click here for how to implement this method.\l}\l"  
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
URL="@ref how-to-implement-onPreEntry"
] dnode;
           node [
label="public void onEveryFrame(IOnEveryFrame api){                                                                                        \l   // Click here for how to implement this method.\l}\l"
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
URL="@ref how-to-implement-onEveryFrame"
           ] fnode;
           node [
label="public BaseAction onEntry(IOnEntry api, BaseAction ba){                                                                             \l   // Click here for how to implement this method.\l   return doNothing();\l}\l"
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
URL="@ref how-to-implement-onEntry"
           ] gnode;         
           node [
label="public BaseAction onDoCommand(IOnDoCommand api, BaseAction ba, int verb, SentenceItem objA, SentenceItem objB,  double x, double y){\l   // Click here for how to implement this method.\l   return doNothing();\l}\l" 
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180  fontname=Courier  fontsize=10
URL="@ref how-to-implement-onDoCommand"
            ]
             hnode;         
           node [
label="public BaseDialogTreeAction onDialogTree(IOnDialogTree api, BaseAction ba, int branchId) {                                          \l   // Click here for how to implement this method.\l   return null;\l}\l" 
shape=box fillcolor="#eec87f:#f4e9bb" style="filled" gradientangle=180 fontname=Courier  fontsize=10
URL="@ref how-to-implement-onDialogTree"
            ] inode;         	
    }

 }
@enddot


@section how-to-implement-onFillLoadList
<h2>onFillLoadList</h2>

@code
api.addEssential(new com.resource._70_Piranha_Inventory._32x16.ResLoader());
api.addEssential(new com.resource._70_Piranha_Objects._320x180.InitialLoader() );
api.addNonEssential(new com.resource._70_Piranha_Objects._320x180.ResLoader() );
return api.createLoadKickStarter();
@endcode


eg
@code
pi.setWorldViewSize(320, 180);
api.addEssential(new com.resource.slideshow._01_Slideshow_Main._320x180.OnlyLoader() );
api.addEssential(new com.resource.slideshow._01_Slideshow_Animation_Harry._320x180.OnlyLoader() );
api.addEssential(new com.resource.slideshow._01_Slideshow_Animation_Sergeant._320x180.OnlyLoader() );
return api.createLoadKickStarter();
@endcode

@section how-to-implement-onPreEntry ..
<h2>onPreEntry</h2>

@section how-to-implement-onEntry ..
<h2>onEntry</h2>

@section how-to-implement-onEveryFrame ..
<h2>onEveryFrame</h2>

@section how-to-implement-onDoCommand ..
<h2>onDoCommand</h2>

@section how-to-implement-onDialogTree ..
<h2>onDialogTree</h2>
@code
	public DialogTreeBaseAction onDialogTree(int place) 
	{
		switch (place) 
		{
		case 1:
			if (getInventoryItem(i.IDOL).isVisible()) 
			{
				return 
				say(o.BOUNCER, "The buyer will be waiting for us at the docks in Gibraltar")
				.choice(3, "Thanks for sorting that out Mitch. So what next for us?");
			} else {
				return 
				say(o.BOUNCER,	"So what happened to that being a sure thing?")
				.choice(2, "Sorry Mitch, looks like I can't pay you this month");
			}
		case 2:
			return say(o.BOUNCER,  "Don't sweat it. I know you're good for it")
				.gotoChoice(3);
		case 3:
			return say(o.BOUNCER, "Well, I'm off to Barcelona.")
					.say(o.BOUNCER, "I'm going diving for Atlantis again")
					.say(o.BOUNCER, "How about you Harry, anything planned?")
					.choice(4, "In my home town there's a wishing well. I'm going to finally get to the bottom of it")
					.choice(4, "I'm going to change careers. Instead of a Fortune Hunter, I'm going to be a Fortune Teller")
					.choice(4, "I'm going to hit the library. Books are the source of true adventure.");

		default:
			return say(o.BOUNCER, "Well, if you're hard up..")
					.say(o.BOUNCER, "..there's talk of an expedition.")
					.say(o.BOUNCER, "Did you ever hear of a guy called Ratsinger?")
					.say(o.BOUNCER, "He was an archaeologist..")
					.say(o.BOUNCER, ".. desk tied mostly..")
					.say(o.BOUNCER,	"..but he occasionally did some field work...")
					.say(o.BOUNCER, "..like uncovering the Tomb of Rahnemen")
					.say(o.BOUNCER, "Anyway, he's dead now.")
					.say(o.BOUNCER, "He died last month.")
					.say(o.BOUNCER,	"But I got word that his daughter is hunting around for an expedition party...")
					.say(o.BOUNCER, "Or so I heard before we left..")
					.switchTo(RoomEnum._10_Fire.toString());
		}
	}
@endcode
*/