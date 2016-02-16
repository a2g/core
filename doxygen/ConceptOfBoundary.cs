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

@page Boundary The Boundary Concept
Without the concept of a boundary each scene would need to add
logic in to the OnEveryFrame handler to detect when the SceneObject
being controlled by the main character has walked beyond a certain point.
And the coordinates would need to be there in the code. 
If you were trying to prevent the character from walking beyond an
arbitrary polygonal fence, then you'd be up for some pretty long
maths equations.
<br>
<br>
It point that it checks for out-of-boundsness is the base middle, so this can
be seen to build on the concept of @ref Walking Walking.
Thus adding something to do boundary checking was pretty important.
<br>
<br>
The @ref com.github.a2g.core.interfaces.methods.game.ISwitchToScene "switchToScene" method
makes use of the Boundary concept by allowing the specification of a 
second parameter which refers to the segment by which to enter the
next scene through.  Without this, each scene would need to add its own
positioning code in every OnPreEntry handler that checked what the 
previous room was, and positioned the main character at some hard coded
value. 
<br>
<br>
<br>
<br>
The @ref com.github.a2g.core.interfaces.methods.game.ISetDefaultSceneObject "setDefaultSceneObject" 
method is only essential for the concept of switching in a new room.
The defaultSceneObject is only otherwise used as shorthand, to reduce the amount
of paramters in an action call: -1 as an object code ends up with the DefaultSceneObject.
And also SceneTalker will default back to DefaultSceneObject if none is specified.


 

*/