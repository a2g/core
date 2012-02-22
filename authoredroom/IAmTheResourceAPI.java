/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredroom;

import com.google.gwt.resources.client.ImageResource;



/*!
@page CodeGenerator
<h2>Code Generator</h2>
The a2g Code Generator generates code to help link the images of a room with the 
code of an authored room. This can be done manually, but code generation has benefits:
- it replaces a whole lot of repetitive hand coding.
- it generates codes. See @ref Codes
- it generates special codes @ref SpecialPropertiesOfCodesMadeByCodeGeneration

But to recieve these benefits you need to layout your game images in a 
strict structure of folders. The structure is perhaps best illustrated by describing
what the code generator looks for when it traverses a root folder.

1. The code generator takes the root folder and recurses that folder looking for folders
named a certain way. It looks for
- Only folders that are prefixed with underscore ("_"). 
- Folders containing "_Objects".  You can include only one of these per @ref Room.
- Folders containing "_Inventory". You can include only one of these in your @ref Room.
- Folders containing "_Animations". You can include multiple of these in your @ref Room.

2. The code generator goes through each of the above folders, and traverses only those
files:
- that are prefixed with an underscore ("_").

It doesn't make any assumptions regarding names, but it may help to name the subfolders
after the resolution that the images within are targetted to, in @b pixels.

3. The code generator goes through each of the "pixel" subfolders, and assumes:
- they are prefixed with underscore("_"), then two digits, then another underscore("_")
- after the prefix the names found are to be used as the @ref TextualIds of the @b 
objects they represent.

4. The code generator goes through each of the "object" subfolders, and assumes:
- each name found represents the name of an @b animation of the object.

5. The code generator then goes through each of the "animation" subfolder and expects to
find a whole heap of images:
- prefixed with the word "orig_".
- of the ".png" extension.

6. The code generator then performs a @ref crop on these images, saves the cropped file
out with the "cropped_orig_" prefix, and then generates the code to add this cropped
image to the current Room: as a frame, of the parent animation, on the parent object.

If the folder was an "_Inventory" folder then, in Step 2 when its going through the "pixel"
subfolders, it expects to find folders named after @TextualIds of inventory items, that
contain a single PNG that is to be used as the icon to represent that image in the 
inventory. It only uses the first image it finds, and ignores the rest. 
<br>
<br>
The two digit numbers are used as the drawing order the objects (ie their Z value). 
The size of the integer supported by the code generator is limited to two digits. The 
size of the integer in the @ref com.github.a2g.core package is not limited in this way.
<br>
<br>
To read a explanation of the code that is generated see @ref CodeGeneratorOutput.
<br>
Here is an example of the folder structure:
@image html CodeGenerationFolders.png

*/

/*! @page CodeGeneratorOutput
<h2> Code Generator Output </h2>

*/

/*!
@page TextualIds 
<h2> Textural Ids </h2>
TextualIds exist for each @ref InventoryItem each @ref RoomObject, and each @ref Animation.
*/

/*! 
@page Codes
<h2> Codes </h2>
Codes exist for each  @ref InventoryItem, each @ref RoomObject, and each @ref Animation.
They are mainly used as a means means of identifying objects, inventory and animation.  
This is because they are used as the first parameter of many methods of the 
@ref BaseAction class.

If you use the @ref CodeGenerator these are set to specially calculated ids.
See SpecialPropertiesOfCodesMadeByCodeGeneration.

If not are crafting a list of ids by hand then you need to know the requirements of each 
codes:
- The codes for ecah @ref InventoryItem and @ref RoomObject must be unique in the scope of a single room. 
- The codes for each @ref Animation need also be unique in the scope of a single room. 

Both the above points are because most of the methods of @ref BaseAction use the codes to uniquely identify
either an @InventoryItem or a @RoomObject or an @Animation.
For example:
@code
	case USE+ i.BOWL * o.FIRE_DEMON: // place bowl
			return this
			.walkTo(o.HARRY, fireDemonAfter)
			.setActiveAnimation(a.HARRY_WALK_BACK)
			.sleep(700)
			.setInventoryVisible(i.BOWL,false)
			.setVisible(o.BOWL_UNDER_FIRE, true)
@endcode

In the above example, the first parameter of:
- @ref BaseAction.walkTo - is an object code.
- @ref BaseAction.setActiveAnimation - is an animation code.
- @ref BaseAction.setInventoryVisible - is an inventory code.

/*!
@page SpecialPropertiesOfCodesMadeByCodeGeneration
<h2>Special Properties of Codes Made By Code Generator for InventoryItems and RoomObjects</h2>
The codes for ecah @ref InventoryItem and @ref RoomObject calculated by the
@ref CodeGenerator are special by way of the property that multiplying the ids of two
objects (either RoomObjects or InventoryItems) will yield a number that is unique for 
that combination. This allows you to construct a switch statement to handle all the 
command line combinations that are possible. For example:

@code
	public BaseAction onCommandLineExecute(int verb, SentenceUnit objA, SentenceUnit objB,  double x, double y)
	{	
		int code = verb + objA.getCode() * objB.getCode();
		switch(code)
		{ 
		case EXAMINE + o.BACKBOAT:
		case EXAMINE + o.BOAT:
			return this.say(o.HARRY, "It's the boat we just came up this river in.");
		case EXAMINE + o.CAVE:
		case EXAMINE + o.CAVEROCK:
			return this.say(o.HARRY, "It looks as if there's an opening in the rock.");
		case EXAMINE + o.RIVER: 
			return this.say(o.HARRY, "It's the river.");
		case EXAMINE + o.ROCK:
			return this.say(o.HARRY, "The river is very rocky at this point.");
		case EXAMINE + o.SKY:
			return this.say(o.HARRY, "There's smoke billowing up from the volcano.");
		case EXAMINE + o.WATERFALL:
@endcode
Using a switch statement to handle the combinations has the following advantages:
- The compiler will generate an error if you try to handle the same command line twice.
- Ease of reading, including handling multiple commands with the same result.
- It will automatically handle using A with B and using B with A in the same handlier.

<h2>Special Properties of Codes Made By Code Generator for Animations</h2>
The codes for each @ref Animation, when calculated by the @ref CodeGenerator
are made to be unique across the entire set of resources included in a code generation 
pass -  not just unique within a room. This allows you to use more than one set of 
animations in a room - effectively allowing you to share/reuse animations across 
rooms. A room can still only use one set of Object Codes per room. So the animations 
must pertain to objects that have ids in your object code list.
*/



/*! @interface IAmTheResourceAPI
@brief
A2g expects the @ref InventoryItemCollection and @ref RoomObjectCollection to be populated 
by two methods on the Api (see below).
  
It expects these calls to be made during the @ref IAmARoom::onLoadResources() method of a room.

These calls can either be made:
- Manually - use the documentation below
- Automatically via code generation - see @ref CodeGenerator

*/
public interface IAmTheResourceAPI {
       
    /*!
	If the same textualId had already been used in a previous call to this method method, then the call will fail, and return false.
	@param textualIdForInventory this specifies which inventory item the image represents (see @ref TextualIds).
	@param codeForInventory this code is registered as the code for the inventory item  (see @ref Codes).
	@param imageResource the image to add.

	@return true on success, otherwise false.
     */
    public boolean addImageForAnInventoryItem(String textualIdForInventory, int codeForInventory, ImageResource imageResource);
    
    /*!
	If the same textualIds have already been used in a previous call to this method method, then the ImageResource is added to the
	list of animation frames for the animation specified by the ids.
	@param numberPrefix
	@param x specifies the horizontal offset (in pixels) used to position the image in its default location in the scene.
	@param y specifies the vertical offset (in pixels) used to position the image in its default location in the scene.
	@param textualIdForObject this specifies which object to add the image to (see @ref TextualIds).
	@param textualIdForAnimation this specifies which animation of the object to add the image to  (see @ref TextualIds).
	@param codeForObject this code is registered as the code for the object (unless the texturalIdForObject has been used before). (see @ref Codes).
	@param codeForAnimation this code is registered as the code for the animation (unless the textualIdForAnimation has been used before). (see @ref Codes).
	@param imageResource the image to add.

	@return true on success, otherwise false.
     */
    public boolean addImageForARoomObject(int numberPrefix, int x, int y, String textualIdForObject, String textualIdForAnimation, int codeForObject, int codeForAnimation, ImageResource imageResource);
    
};



