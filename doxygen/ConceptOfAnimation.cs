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

@page Animation The Animation System
You know those little flickerbooks, where each frame was a drawing, and you'd see 
a little animation if you flicked the book through your fingers? Well that's
pretty much how this animation system works. The frames of each animation are
always the same size, and so if you have a long animation of a character moving
from left to right, you don't need to worry about changing the character's position
in between frames. If you want to change a characters position in between frames,
then that's fine too. It's possible with the @ref Walking Walking system, that is 
built on top of this one.
<br>
<br>
Every graphical element is considered atleast a single page flickerbook.
- eg a static background is a single page flickerbook.
<br>
<br>
A scene is constructed with many flickerbooks, each overlayed on top of each
other - some of them invisible (if they aren't meant to be seen)
<br>
<br>
To help manage visibility, there is a concept of a @ref SceneObject, which holds 
and manages many flickerbooks. A SceneObject can only display one flickerbook, at
a time, this is called the @ref CurrentAnimation.
<br>
<br>
In a draw of the entire scene, SceneObject's CurrentAnimation s are drawn
on top of each other, in the order of the @ref SceneObject::DrawingOrder 
property - lowest first. Only one frame of the currentAnimation is drawn
at a time - the SceneObject::CurrentFrame.
<br>
<br>
Having the currentframe be a property of the SceneObject is handy when switching
animations that are synced up. For example if the character is stuck in some
default animation, eg walking, and you want them to talk at the same time, then
you can switch the CurrentAnimation from WALKING to TALKING_WHILST_WALKING, and
the transition will be seamless - your character won't miss a step!  
<br>
<br>
The position of the each flickerbook is set with an X and Y in pixel space.
<br>
<br>
*/