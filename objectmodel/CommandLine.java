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

package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.IMasterPresenterFromCommandLine;
import com.github.a2g.core.primitive.CodesForVerbs;


public class CommandLine {

	private SentenceItem defaultVerb;
	private SentenceItem lockedInVerb;
	private SentenceItem lockedInObject;
	// private SentenceItem lockedInObject2;
	private SentenceItem rolledOverItem;
	private String typeOfRollover;

	private boolean isMouseable; // whether rolling over the verbs will update the commandline
	private boolean isVisible;

	public CommandLine(IMasterPresenterFromCommandLine api) {
		this.defaultVerb = new SentenceItem( "Walk to AAA",
				"Walk", CodesForVerbs.getCodeForVerb(0));
		this.lockedInVerb = defaultVerb;
		this.lockedInObject = new SentenceItem();
		// this.lockedInObject2 = new SentenceItem();
		this.rolledOverItem = new SentenceItem();
		this.typeOfRollover = "";
		this.isMouseable = true;
		this.isVisible = true;
	}

	public void setMouseOver(String displayName, String otid, int code) {
		if(!isMouseable)
			return;
		this.rolledOverItem = new SentenceItem( displayName,
				otid, code);
	}

	static boolean isAVerb(SentenceItem snc) {
		return snc.getDisplayName().contains(
				"AAA")
				|| snc.getDisplayName().contains(
						"BBB");
	}


	public Sentence getSentence() {
		Sentence text = new Sentence();

		int rolloverCode = this.rolledOverItem.getCode();
		boolean isRolledOverAVerb = CodesForVerbs.isAVerb(rolloverCode);
		boolean isLockedInVerb = this.lockedInVerb.getLength() >0;
		boolean isLockedInVerbATwoForm = this.lockedInVerb.getDisplayName().contains("BBB");
		//    	boolean isDefaultVerbATwoForm = this.defaultVerb.getDisplayName().contains("BBB");
		boolean isObjectALockedIn = !this.lockedInObject.getDisplayName().isEmpty();
		//  	boolean isObjectBLockedIn = !this.lockedInObject2.getDisplayName().isEmpty();


		if (isRolledOverAVerb)
		{
			text.setVerb( rolledOverItem, false);
			text.setAAA(lockedInObject);
			typeOfRollover="V";
		}
		else if(isLockedInVerb)
		{
			if(isLockedInVerbATwoForm && isObjectALockedIn)
			{
				text.setVerb(lockedInVerb, true);
				text.setAAA(lockedInObject);
				text.setBBB(rolledOverItem);
				typeOfRollover="B";
			}
			else
			{
				text.setVerb(lockedInVerb, false);
				text.setAAA(rolledOverItem);
				typeOfRollover="A";
			}
		}
		else
		{
			text.setVerb( this.defaultVerb, false);
			text.setAAA(rolledOverItem);
			typeOfRollover="A";
		}

		return text;
	}

	public void clear() {
		if(!isMouseable)
			return;
		this.lockedInObject = new SentenceItem();
		//this.lockedInObject2 = new SentenceItem();
		this.lockedInVerb = defaultVerb;
		this.rolledOverItem = new SentenceItem();
		this.typeOfRollover = "";
	}

	public void doNextBestThingToExecute() {
		if(!isMouseable)
			return;
		if (this.typeOfRollover == "A") {
			this.lockedInObject = this.rolledOverItem;
			this.rolledOverItem = new SentenceItem();
			this.typeOfRollover = "";
		} else if (this.typeOfRollover == "B") {
			// this.lockedInObject2 = this.rolledOver;
			this.rolledOverItem = new SentenceItem();
			this.typeOfRollover = "";
		} else if (this.typeOfRollover == "V") {
			this.lockedInVerb = this.rolledOverItem;
			this.rolledOverItem = new SentenceItem();
			this.typeOfRollover = "";
		}
	}

	public boolean isOkToExecute() {
		if (!this.isMouseable) {
			return false;
		}
		boolean isObjectOrInv = this.rolledOverItem.isObjectOrInv();
		if(!isObjectOrInv)
			return false;

		boolean isObjectALockedIn = !this.lockedInObject.getDisplayName().isEmpty();
		if(!isObjectALockedIn && !lockedInVerb.getDisplayName().contains("BBB"))
			return true;
		if(isObjectALockedIn && lockedInVerb.getDisplayName().contains("BBB"))
			return true;

		return false;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getVerbAsVerbEnumeration() {
		int currentVerb = getSentence().getVerbAsVerbEnumeration();

		return currentVerb;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void setMouseable(boolean mouseable) {
		this.isMouseable = mouseable;
	}
}
