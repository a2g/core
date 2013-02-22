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

import com.github.a2g.core.interfaces.CommandLineCallbackAPI;
import com.github.a2g.core.primitive.CodesForVerbs;


public class CommandLine {

	private SentenceItem defaultVerb;
	private SentenceItem lockedInVerb;
	private SentenceItem lockedInObject1;
	// private SentenceItem lockedInObject2;
	private SentenceItem rolledOver;
	private String typeOfRollover;

	private boolean isMouseable; // whether rolling over the verbs will update the commandline
	private boolean isVisible;

	public CommandLine(CommandLineCallbackAPI api) {
		this.defaultVerb = new SentenceItem( "Walk to AAA",
				"Walk", CodesForVerbs.getCodeForVerb(0));
		this.lockedInVerb = defaultVerb;
		this.lockedInObject1 = new SentenceItem();
		// this.lockedInObject2 = new SentenceItem();
		this.rolledOver = new SentenceItem();
		this.typeOfRollover = "";
		this.isMouseable = true;
		this.isVisible = true;
	}

	public void setMouseOver(String displayName, String textualId, int code) {
		this.rolledOver = new SentenceItem( displayName,
				textualId, code);
	}

	static boolean isAVerb(SentenceItem snc) {
		return snc.getDisplayName().contains(
				"AAA")
				|| snc.getDisplayName().contains(
						"BBB");
	}


	public Sentence getSentence() {
		Sentence text = new Sentence();

		int rolloverCode = this.rolledOver.getCode();
		boolean isRolledOverAVerb = CodesForVerbs.isAVerb(rolloverCode);
		boolean isLockedInVerb = this.lockedInVerb.getLength() >0;
		boolean isLockedInVerbATwoForm = this.lockedInVerb.getDisplayName().contains("BBB");
		//    	boolean isDefaultVerbATwoForm = this.defaultVerb.getDisplayName().contains("BBB");
		boolean isObjectALockedIn = !this.lockedInObject1.getDisplayName().isEmpty();
		//  	boolean isObjectBLockedIn = !this.lockedInObject2.getDisplayName().isEmpty();


		if (isRolledOverAVerb)
		{
			text.setVerb( rolledOver, false);
			text.setAAA(lockedInObject1);
			typeOfRollover="V";
		}
		else if(isLockedInVerb)
		{
			if(isLockedInVerbATwoForm && isObjectALockedIn)
			{
				text.setVerb(lockedInVerb, true);
				text.setAAA(lockedInObject1);
				text.setBBB(rolledOver);
				typeOfRollover="B";
			}
			else
			{
				text.setVerb(lockedInVerb, false);
				text.setAAA(rolledOver);
				typeOfRollover="A";
			}
		}
		else
		{
			text.setVerb( this.defaultVerb, false);
			text.setAAA(rolledOver);
			typeOfRollover="A";
		}

		// text += "Verb "+ this.lockedInVerb + "obj1 " + this.lockedInObject1 + "obj2 " + this.lockedInObject2;

		return text;
	}

	public void clear() {
		this.lockedInObject1 = new SentenceItem();
		//this.lockedInObject2 = new SentenceItem();
		this.lockedInVerb = defaultVerb;
		this.rolledOver = new SentenceItem();
		this.typeOfRollover = "";
	}

	public void doNextBestThingToExecute() {
		if (this.typeOfRollover == "A") {
			this.lockedInObject1 = this.rolledOver;
			this.rolledOver = new SentenceItem();
			this.typeOfRollover = "";
		} else if (this.typeOfRollover == "B") {
			// this.lockedInObject2 = this.rolledOver;
			this.rolledOver = new SentenceItem();
			this.typeOfRollover = "";
		} else if (this.typeOfRollover == "V") {
			this.lockedInVerb = this.rolledOver;
			this.rolledOver = new SentenceItem();
			this.typeOfRollover = "";
		}
	}

	public boolean isOkToExecute() {
		if (!this.isMouseable) {
			return false;
		}
		boolean isObjectOrInv = this.rolledOver.isObjectOrInv();
		if(!isObjectOrInv)
			return false;

		boolean isObjectALockedIn = !this.lockedInObject1.getDisplayName().isEmpty();
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
