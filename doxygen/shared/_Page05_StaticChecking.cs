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
@page StaticChecking
@section blah Static Checking
The a2g way of doing things tries to maximise the amount of red squigglies, warnings and 
other type checking. The idea is that if you make changes: either graphics or code, that once
you iron out all the errors and warnings, that the code will work as before. 

@section blah2 Handling Single Object Verbs
For a particular @ref Verb, you may want to make sure you handle every possible
object that a user might use that verb with.  If this is the case, you can use the 
enums in the @ref o-java and @ref i-java. 

@code
@Override
public BaseAction onDoCommand(IOnDoCommand api, BaseAction ba, int verb, SentenceUnit sentenceItemA, SentenceUnit sentenceItemB, double x, double y) 
{
    if(verb==EXAMINE)
    {
        o.names enumified = i.names.valueOf(sentenceItemA.getTextualId());
        switch(enumified)
        {
	case KNIFE:
		return doOneThing(ba);
	case ROPE: 
		return doAnotherThing(ba);
	case KNIFE://<-- a duplicate case will generate a compiler error.	
		return doSomethingElse(ba);
	// ** the compiler will warn for any items of the enum aren't handled. 
        }
    }
}
@endcode

@section blah3 Handling Multiple Item Verbs
Alternatively, you might be interesting in handling using of inventory items with 
something in the scene, inventory items with other inventory items, or items in the 
scene with other items in the scene, 

In thie case, you can can use the static constants in the @ref o-java and @ref i-java, 
and do something like the following:

@code
public BaseAction onDoCommand(IOnDoCommand api, BaseAction ba, int verb, SentenceUnit sentenceItemA, SentenceUnit sentenceItemB, double x, double y) 
{
    int code = verb + sentenceItemA.getCode() * sentenceItemB.getCode();
    switch(code)
    { 
    case USE+ i.BONE_A * i.SKULL:
        return doSomething(ba);
    case USE+ i.COMBO * o.LILY:
        return useComboWithLily(ba);
    case USE+ i.MEAT_A * o.WATER_BACKGROUND:
        return useMeatWithWater();
    case USE+ i.MEAT_B * o.WATER_BACKGROUND:
        return useMeatWithWater();
    case USE+  i.SKULL * i.BONE_A: //<-- a duplicate case will generate a compiler error.
	return doSomethingElse();
    }
}    
@endcode

@section blah4 Binding to Graphic Resources
In the handlers defined above, you may want to react by doing something with some
of the graphic resources. This is done with the aforementioned constants - since 
each of the constants refers to a graphic element, or a series of graphic elements, 
as is the case with animations. If the constants were all enumerations, this would greatly
increase the size of the javascript, when compiled with GWT. As a result, the constants
are native types:
- @ref com.github.a2g.core.objectmodel.InventoryItem InventoryItem graphics are specified using @b integer constants.
- @ref com.github.a2g.core.objectmodel.SceneObject SceneObject graphics are specified using @b short constants.
- @ref com.github.a2g.core.objectmodel.Animation Animation graphics specified using @b string constants.

This still provides a good level of type checking, as follows:

@code
api.getAnimation(a.TORCH_FLICKERING);
api.getAnimation(i.TORCH);// <-- will generate error
api.getAnimation(o.TORCH);// <-- will generate error
@endcode

@code
api.getObject(a.TORCH_FLICKERING);// <-- will generate error
api.getObject(i.TORCH);// <-- will generate error
api.getObject(o.TORCH);
@endcode

@code
api.getInventory(a.TORCH_FLICKERING);// <-- will generate error
api.getInventory(i.TORCH);
api.getInventory(o.TORCH);// <- will not generate error :(
@endcode


By having animation constants represent both an object and an animation:
 - ie  getAnimation(TORCH_FLICKERING)
 
rather than having to supply these separately in to a method:
 - ie getAnimation(TORCH, FLICKERING)

avoids the error of supplying two mismatched constants:
 - ie getAnimation(TV, FLICKERING) <-- flickering doesn't exist as an animation on TV

@section blah5 Example to polish
- When you dont care about handling every possible combo
- When you want to handle every possible verb with inventory
- When you want to handle every possible verb with scene object
- When use X with Y is the same as use Y with X (eg "Use" x "with" y)
- When use X with Y is different to use Y with X (eg "Throw" x "at" y)
- How to
@code
if(itemB.isEmpty() && verb==EXAMINE)
		{
			O.names examine = O.names.valueOf(itemA.getTextualId());
			switch(examine)
			{
			case /*EXAMINE*/ BONDS:
			case /*EXAMINE*/ COGS:
			case /*EXAMINE*/ DOG_A:
			case /*EXAMINE*/ DOG_B:
			case /*EXAMINE*/ DOG_B_TO_C:
			case /*EXAMINE*/ DOG_C:
			case /*EXAMINE*/ DOG_D:
			case /*EXAMINE*/ DOG_ROPE_D_UPPER:
			case /*EXAMINE*/ DOG_SLEEP:
			case /*EXAMINE*/ FIRE:
				return ba.say( "It looks hot.");

			case /*EXAMINE*/ FORK_LEFT_BACKGROUND:
			case /*EXAMINE*/ FORK_LEFT_FOREGROUND:
			case /*EXAMINE*/ FORK_RIGHT_BACKGROUND:
			case /*EXAMINE*/ FORK_RIGHT_FOREGROUND:
				return ba.say( "Despite it's appearance, \n it's full metal construction");

			case /*EXAMINE*/ FULLMOON:
			case /*EXAMINE*/ GROUND:
			case /*EXAMINE*/ HANDLE:
			case /*EXAMINE*/ HARRY_AND_FAYE:
			case /*EXAMINE*/ KNIFE_SHORT:
			case /*EXAMINE*/ KNIFE_THROWN_SHORT:
			case /*EXAMINE*/ MAIN_POLE:
				return ba.say( "We are tied to an iron skewer.");
			case /*EXAMINE*/ MEATBALL_A:
			case /*EXAMINE*/ MEATBALL_B:
			case /*EXAMINE*/ MEATBALL_C:
			case /*EXAMINE*/ MEATBALL_RIGHT:
			case /*EXAMINE*/ MEATBALLS_LEFT:
			case /*EXAMINE*/ MEATBALLS_RIGHT:
				return ba.say( "They are close enough to grab.");

			case /*EXAMINE*/ MONUMENT:
				return ba.say( "It houses the gears that drive the rotisserie");
			case /*EXAMINE*/ ROPE_UPPER:
			case /*EXAMINE*/ STEAK_A:
			case /*EXAMINE*/ STEAK_B:
			case /*EXAMINE*/ STEAK_SPIKE:
			case /*EXAMINE*/ STEAKLESS_SPIKE:
			case /*EXAMINE*/ TREE:
				return ba.say( "It's a thin-trunked palm tree");
			default: 
			}
		}
		else if(itemB.isEmpty() && verb==GRAB)
		{
			O.names grab = O.names.valueOf(itemA.getTextualId());
			switch(grab)
			{

			case /*GRAB*/ BONDS:
			case /*GRAB*/ COGS:
				return ba.say( "I can't reach it");
			case /*GRAB*/ DOG_A:
			case /*GRAB*/ DOG_B:
			case /*GRAB*/ DOG_B_TO_C:
			case /*GRAB*/ DOG_C:
			case /*GRAB*/ DOG_D:
			case /*GRAB*/ DOG_ROPE_D_UPPER:
			case /*GRAB*/ DOG_SLEEP:
			case /*GRAB*/ FIRE:
			case /*GRAB*/ FORK_LEFT_BACKGROUND:
			case /*GRAB*/ FORK_LEFT_FOREGROUND:
			case /*GRAB*/ FORK_RIGHT_BACKGROUND:
			case /*GRAB*/ FORK_RIGHT_FOREGROUND:

			case /*GRAB*/ FULLMOON:
			case /*GRAB*/ GROUND:
			case /*GRAB*/ HANDLE:
			case /*GRAB*/ HARRY_AND_FAYE:
				return ba.doNothing();
			case /*GRAB*/ KNIFE_SHORT:
				return ba.waitForFrame(O.HARRY_AND_FAYE, 23)
						.setActiveAnimation(A.HARRY_AND_FAYE_GRAB_STEAK)
						.waitForFrame(O.HARRY_AND_FAYE, 25)
						.setActiveAnimation(A.HARRY_AND_FAYE_TURNING)
						.setVisible(O.KNIFE_SHORT, false)
						.setInventoryVisible(I.KNIFE_SHORT, true);
			case /*GRAB*/ KNIFE_THROWN_SHORT:
			case /*GRAB*/ MAIN_POLE:
			case /*GRAB*/ MEATBALL_A:
			case /*GRAB*/ MEATBALL_B:
			case /*GRAB*/ MEATBALL_C:
				return ba.say( "I can't reach it.");
			case /*GRAB*/ MEATBALL_RIGHT:
			case /*GRAB*/ MEATBALLS_LEFT:
			case /*GRAB*/ MEATBALLS_RIGHT:
				if(api.getInventoryItem(I.MEATBALL).isVisible())
				{
					return ba.say( "One is enough.");
				}
				return
						ba
						.say( "Ok.")
						.waitForFrame(O.HARRY_AND_FAYE, 23)
						.setActiveAnimation(A.HARRY_AND_FAYE_GRAB_MEATBALL)
						.waitForFrame(O.HARRY_AND_FAYE, 25)
						.setActiveAnimation(A.HARRY_AND_FAYE_TURNING)
						.setInventoryVisible(I.MEATBALL, true)
						.say( "There are lots still left.");

			case /*GRAB*/ MONUMENT:
			case /*GRAB*/ ROPE_UPPER:
			case /*GRAB*/ STEAK_A:
			case /*GRAB*/ STEAK_B:
				return ba.say( "I can't reach it.");

			case /*GRAB*/ STEAKLESS_SPIKE:
				return
						ba.waitForFrame(O.HARRY_AND_FAYE, 23)
						.setActiveAnimation(A.HARRY_AND_FAYE_GRAB_STEAK)
						.waitForFrame(O.HARRY_AND_FAYE, 25)
						.setActiveAnimation(A.HARRY_AND_FAYE_TURNING)
						.setVisible(O.STEAKLESS_SPIKE, false)
						.setInventoryVisible(I.STEAKLESS_SPIKE, true);
			case /*GRAB*/ STEAK_SPIKE:
				return
						ba.waitForFrame(O.HARRY_AND_FAYE, 23)
						.setActiveAnimation(A.HARRY_AND_FAYE_GRAB_STEAK)
						.waitForFrame(O.HARRY_AND_FAYE, 25)
						.setActiveAnimation(A.HARRY_AND_FAYE_TURNING)
						.setVisible(O.STEAKLESS_SPIKE, true)
						.setVisible(O.STEAK_SPIKE, false)
						.setInventoryVisible(I.STEAK, true);
			case /*GRAB*/ TREE:
				if(ba.getApi().getObject(O.TREE).getCurrentFrame() ==0)
				{
					return ba.say( "I can't reach it.");
				}
				else
				{
					return ba.say( "OK. I'm intermittently holding on to it.");
				}
			default:
			}
		}else if(verb==USE)
		{ 
			boolean aIsInv = itemA.isInventory();
			boolean bIsInv = itemB.isInventory();
			if(aIsInv && bIsInv)
			{
				switch(itemA.getCode()*itemB.getCode())
				{
				case I.KNIFE_SHORT * I.MEATBALL:
				case I.KNIFE_SHORT * I.STEAK:
				case I.KNIFE_SHORT * I.STEAKLESS_SPIKE:
				case I.MEATBALL * I.STEAK:
				case I.MEATBALL * I.STEAKLESS_SPIKE:
				case I.STEAK * I.STEAKLESS_SPIKE:
				}
			}else if(aIsInv || bIsInv)
			{
				SentenceItem theInv = aIsInv? itemA : itemB;
				SentenceItem theNonInv = aIsInv? itemB : itemA;
				I.names theInvName = I.names.valueOf(theInv.getTextualId());
				O.names theNonInvName = O.names.valueOf(theNonInv.getTextualId());

				switch(theNonInvName)
				{
				case /*USE ANY_INVENTORY WITH */ BONDS:
					switch(theInvName)
					{
					case KNIFE_SHORT: 
					case STEAKLESS_SPIKE:return ba.say("Sadly we are clamped on here with a metal clamp");
					case STEAK: 
					case MEATBALL: return ba.say("Thats just silly");
					}
				case /*USE ANY_INVENTORY WITH */ FIRE:
					return ba.say("I'll burn myself before I ever heat it up");
				case /*USE INVENTORY WITH */ FORK_LEFT_BACKGROUND:
				case /*USE INVENTORY WITH */ FORK_LEFT_FOREGROUND:
				case /*USE INVENTORY WITH */ FORK_RIGHT_BACKGROUND:
				case /*USE INVENTORY WITH */ FORK_RIGHT_FOREGROUND:
				case /*USE INVENTORY WITH */ HANDLE:
				case /*USE INVENTORY WITH */ MAIN_POLE:
					switch(theInvName)
					{
					case KNIFE_SHORT: 
					case STEAKLESS_SPIKE:return ba.say("That's not going to work\nIt's all metal, covered in a sexy wooden veneer");
					case STEAK: 
					case MEATBALL: return ba.say("Thats just silly");
					}
				case /*USE INVENTORY WITH */ HARRY_AND_FAYE:
					switch(theInvName)
					{
					case KNIFE_SHORT: 
					case STEAKLESS_SPIKE:return ba.say("We don't need to kill ourselves just yet!"); 
					case STEAK: 
					case MEATBALL: return ba.say("It's actually quite tastey!");
					}
				case /*USE INVENTORY WITH */ MEATBALLS_LEFT:
				case /*USE INVENTORY WITH */ MEATBALLS_RIGHT:
				case /*USE INVENTORY WITH */ MEATBALL_RIGHT:

					switch(theInvName)
					{
					case STEAKLESS_SPIKE: return ba.say("There's no advantage over just grabbing a meatball");
					case STEAK: return ba.say("I can just grab a meatball instead.");
					case KNIFE_SHORT: return ba.say("Nah, I like the spherical form of the meatball");
					case MEATBALL: return ba.say("I don't want to put it back, could be useful");
					}
				case /*USE INVENTORY WITH */ STEAK_SPIKE:
					switch(theInvName)
					{
					case STEAKLESS_SPIKE: 
					case STEAK: return ba.doNothing();
					case KNIFE_SHORT: 
					case MEATBALL: return ba.say("I can just grab the steak instead.");
					}
				case /*USE INVENTORY WITH */ STEAKLESS_SPIKE:
					switch(theInvName)
					{
					case STEAKLESS_SPIKE: return ba.doNothing();
					case STEAK: return ba.say("I don't want to put it back, could be useful");
					case KNIFE_SHORT: 
					case MEATBALL: return ba.say("I can just grab the spike.");
					}
				case /*USE INVENTORY WITH */ STEAK_A:
					if(theInv.getCode() == I.STEAKLESS_SPIKE)
					{
						return
								ba.waitForFrame(O.HARRY_AND_FAYE, 1)
								.setActiveAnimation(A.HARRY_AND_FAYE_USE_SPIKE)
								.waitForFrame(O.HARRY_AND_FAYE, 10)
								.setActiveAnimation(A.HARRY_AND_FAYE_TURNING)
								.setVisible(O.STEAK_A, false)
								.setVisible(O.STEAK_B, true)
								.subroutine( TriggerPalmIfDogExistsAssumeSteakBDoes(ba));
					}
					//deliberate fallthru
				case /*USE INVENTORY WITH */ COGS:
				case /*USE INVENTORY WITH */ DOG_A:
				case /*USE INVENTORY WITH */ DOG_B:
				case /*USE INVENTORY WITH */ DOG_B_TO_C:
				case /*USE INVENTORY WITH */ DOG_C:
				case /*USE INVENTORY WITH */ DOG_D:
				case /*USE INVENTORY WITH */ DOG_ROPE_D_UPPER:
				case /*USE INVENTORY WITH */ DOG_SLEEP:
				case /*USE INVENTORY WITH */ FULLMOON:
				case /*USE INVENTORY WITH */ GROUND:
				case /*USE INVENTORY WITH */ KNIFE_SHORT:
				case /*USE INVENTORY WITH */ KNIFE_THROWN_SHORT:
				case /*USE INVENTORY WITH */ MEATBALL_A:
				case /*USE INVENTORY WITH */ MEATBALL_B:
				case /*USE INVENTORY WITH */ MEATBALL_C:
				case /*USE INVENTORY WITH */ MONUMENT:
				case /*USE INVENTORY WITH */ STEAK_B:
				case /*USE INVENTORY WITH */ ROPE_UPPER:
				case /*USE INVENTORY WITH */ TREE:
				default:
					return ba.say("I can't reach it");
				}
			}
		}
		int code = verb + a * b;
		switch(code)
		{
		case EXAMINE+ O. BONDS:
@endcode
@endsection
*/