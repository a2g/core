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
@mainpage
<table border="0">
<tr>
<td><img width=120 height=120 src="dot_inline_dotgraph_1.png" border="0" usemap="#dot_inline_dotgraph_3.map"/><td> 
</tr>
</table>

So .....an Adventure games API!

<br>
Adventure games should conjure up concepts of Verbs, InventoryItems, and whole heap  SceneObjects .. and Animations if you're lucky.
And an API is a Whole Bunch of Methods"
So naturally, this API is a whole lot of methods dealing with verbs, inventory items

In the method calls of this API:
- Verbs are represented by an @a @b enum.
- InventoryItems are represented by an @a @b int.
- SceneObjects get a @a @b short
- and Animations get a @a @b  String.

This helps static type checking - helping prevent methods arne't used with the wrong ids.
In addition, they help the handlers that handle all the verb\noun combinations.
The verb/inventoryitem/sceneobject ones are integers because the main verb/noun handler is just a big switch that handles a whole bunch
of verb*object and object*object numerics.

A whole bunch of methods. See docuemntation for AllGameMethods.cpp

The 5 Handlers.
All those methods need a place to run. 
These are the five handlers.

Some methods are not allowed to be called in some handlers, because of this each handler has
its own api with their own restricted set of methods. each handler is called with the api object as the first parameter.

you may have noticed the methods refer to systems - s
Now you might want to check out these systems.
Otherwise, you may want to check out what happens in each of the handlers. 




The five handlers.
the methods available in each of the five handlers
The systems that each of the methods relate to.


As you can see, the methods influence various s



========================
Back on page one I said that animations are the other object type
Each scene is just like a big collection of flicker books. Even the smallest are single page flicker books.
For example, a person walking is just  a person animating on the spot, on a flicker book that is being moved along x axis.  giving the illusion of walking.
On the other hand, if you don;t want the person to slide, and want proper foot placement, then you can do a flicker book of a person taking a two steps and this time not stay on the spot, so moving from the left edge to the right edge of a flicker book. then for the next two steps you can jump the flicker book position by the distance those two steps covered, and repeat the same animation. This will give the illusion of non sliding walking.
You can use methods like
AlignXXXX to facilitate this.

 }
@enddot

 
*/
