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

package com.github.a2g.core.interfaces;

import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.interfaces.internal.IChainRootForDialog;
import com.github.a2g.core.interfaces.internal.IChainRootForScene;
import com.github.a2g.core.interfaces.methods.IOnDialogTree;
import com.github.a2g.core.interfaces.methods.IOnDoCommand;
import com.github.a2g.core.interfaces.methods.IOnEntry;
import com.github.a2g.core.interfaces.methods.IOnEveryFrame;
import com.github.a2g.core.interfaces.methods.IOnPreEntry;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.A2gException;

/**
 * Each scene  is represented by a class that implements the following 6 methods:
 * @author Admin
 *
 */
public interface IGameScene
{
	/*!
	<table>
	<tr>
	<td>
	 @image html IGameScene0001.png
	 onPreEntry
	</td>
	<td>
	 Next up is our last chance to change things before the lights are turned on and
	 everything is displayed. So here you should hide the things you don't want to be
	 seen. And set the display names of all the objects.
	</td>
	</tr>
	</table>
	 */
	public abstract void onPreEntry(IOnPreEntry api);

	/*!
		<table>
		<tr>
		<td>
		 @image html IGameScene0002.png
		onEnterScene
		</td>
		<td>
		onEnterScene is for the cut-scenes to be performed at the start of the scene.
		There isn't really a provision for cut scenes, in the middle of the scene. So
		this is as good as it gets.
		</td>
		</tr>
		</table>
	 */
	public abstract ChainEndAction onEntry(IOnEntry api, IChainRootForScene ba) throws A2gException ;

	/*!
	<table>
	<tr>
	<td>
	 @image html IGameScene0003.png
	onEveryFrame
	</td>
	<td>
	 This is called just after the lights go on, and then 25 times per second for the rest
	 of the duration of the @ref Scene. It is the place where you perform animation.  
	</td>
	</tr>
	</table>
	 */

	public abstract void onEveryFrame(IOnEveryFrame api);

	/*!

	<table>
	<tr>
	<td>
	 @image html IGameScene0004.png
	onDoCommand
	</td>
	<td>
	onDoCommand is executed when the user constructs a @ref Sentence and executes it 
	</td>
	</tr>
	</table>
	 */
	public abstract ChainEndAction onDoCommand(IOnDoCommand api, IChainRootForScene ba,
			int verb, SentenceItem itemA, SentenceItem itemB, double x, double y) throws A2gException ;

	/*!
	<table>
	<tr>
	<td>
	 @image html IGameScene0005.png
	 onDialogTree
	</td>
	<td>
	A2g uses the term @ref DialogTree to refer to a conversation tree.
	This method is triggered by calling @ref doDialogBranch, with the id of the branch.
	Since this method is typically holds cases for all the branches, this method is called @ref onDialogTree.
	A2g also uses the term "sub-branch" to refer to one of the multiple options presenter to the user at a given branch.

	</td>
	</tr>
	</table>
	 */
	public abstract DialogChainEndAction onDialogTree(IOnDialogTree api,
			IChainRootForDialog ba, int branch) throws A2gException ;

}
