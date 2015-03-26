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

package com.github.a2g.core.action;

import java.util.ArrayList;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromSayAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromSayAction;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.action.ChainedAction;



public class SayAction extends ChainedAction {
	private ArrayList<String> speech;
	private ArrayList<Double> startingTimeForEachLine;
	private double totalDurationInSeconds;
	private IScenePresenterFromSayAction scene;
	private ITitleCardPresenterFromSayAction titleCard;
	private int numberOfFramesTotal;
	private static final ColorEnum defaultTalkingColor = ColorEnum.Purple; 
	private NonIncrementing nonIncrementing;
	private boolean isHoldLastFrame;
	private boolean isParallel;
	private String atid;
	private String otid;
	public static String DEFAULT_SAY_ANIM = "DEFAULT_SAY_ANIM";// obviously this gets said by object of default say anim
	enum NonIncrementing
	{ 
		True,
		False,
		FromAPI
	}

	public SayAction(BaseAction parent, String atid, String fullSpeech) {
		super(parent,true);
		this.numberOfFramesTotal = 0;
		this.nonIncrementing = NonIncrementing.False;
		this.isHoldLastFrame = false;
		this.isParallel = false;
		this.atid = atid; 
		speech = new ArrayList<String>();
		startingTimeForEachLine = new ArrayList<Double>();
		String[] lines = fullSpeech.split("\n");
		this.totalDurationInSeconds = 0;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			speech.add(line);
			int milliseconds = getMillisecondsForSpeech(line);
			totalDurationInSeconds = totalDurationInSeconds + milliseconds;
		}

		// set ceilings (for easy calcluation)
		double rollingStartingTimeForLine = 0;
		for (int i = 0; i < lines.length; i++) 
		{
			startingTimeForEachLine.add(new Double(rollingStartingTimeForLine / totalDurationInSeconds));
			String line = lines[i];
			rollingStartingTimeForLine += getMillisecondsForSpeech(line);
		}

	}

	int getAdjustedNumberOfFrames(String speech, double approxDuration, int animFramesCount, double duration)
	{
		// but if we need an animation, we find out how long it takes
		// to play a single play of the animation to play whilst talking.
		int durationOfSingleAnimation = (int)(duration * 1000.0);

		// ... then we find how many times the animation should repeat
		// so that it fills up the totalDuration.
		double numberOfTimesAnimRepeats = approxDuration
				/ durationOfSingleAnimation;

		// ... and the number of frames that occurs during that
		// many plays of the animation.
		int numberOfFramesTotal = (int) (numberOfTimesAnimRepeats * animFramesCount);
		if(numberOfFramesTotal==0)
			numberOfFramesTotal = animFramesCount;
		// The effect of this is that there is a little bit of 'over-play'
		// where the amount of time it takes to 'say' something
		// when there is a talking animation, is usually a
		// little bit more than the time calculated from the speech.
		// This is to ensure that the animation always ends whilst
		// the head is down, mouth is shut. The smaller the number of
		// frames in a talking animation the less over-play.
		return numberOfFramesTotal;
	}
	
	@Override
	public void runGameAction() {
		if(atid == DEFAULT_SAY_ANIM)
		{
			atid = scene.getAtidOfDefaultSayAnim();

			otid = scene.getOtidOfAtid(atid);
		}
		else
		{
			
		}
		
		//only now do 
		if(otid!="")
		{
			if (atid=="")
			{
				// if theres no animation then we just wait for the totalDuration;
				double framesPerSecond = 40;
				numberOfFramesTotal = (int) (totalDurationInSeconds * framesPerSecond);
			}
			else
			{
				numberOfFramesTotal = getAdjustedNumberOfFrames(
						speech.get(0),
						totalDurationInSeconds,
						scene.getNumberOfFramesByAtid(atid),
						scene.getDurationByAtid(atid)
						);
			}

			if(numberOfFramesTotal<1)
			{
				titleCard.displayTitleCard("error!! id=<"+ atid + "> numberOfFramesTotal="+numberOfFramesTotal);
				assert(false);
			}

			if (atid != "" && otid!="") {
				scene.setAsACurrentAnimationByAtid(atid);
				scene.setVisibleByOtid(otid, true);
			}

			boolean visible = true;
			ColorEnum color = defaultTalkingColor;
			if(otid!="")
			{
				color = scene.getTalkingColorByOtid(otid);
			}
			scene.setStateOfPopup( visible, .1,.1, color, speech.get(0),this);

		}
		this.run((int) totalDurationInSeconds);
	}

	@Override
	protected void onUpdateGameAction(double progress) {

		if(!otid.isEmpty())
		{
			if (atid != "" && otid != "") {
				scene.setVisibleByOtid(otid,true);
			}


			// update text in bubble
			for (int i = startingTimeForEachLine.size()-1; i>=0; i--) {
				// go backwards thru the loop to find text that should be valid
				if (progress > startingTimeForEachLine.get(i)) {
					scene.setStateOfPopup( true, .1,.1, scene.getTalkingColorByOtid(otid), speech.get(i),null);
					break;
				}
			}

			// if theres an associated animation, then use it
			if (this.atid != "" && nonIncrementing==NonIncrementing.False) {
				int numberOfFramesSoFar = (int) (progress * numberOfFramesTotal);
				int frame = numberOfFramesSoFar % scene.getNumberOfFramesByAtid(atid);

				// all frames of the animation should be shown
				this.scene.setCurrentFrameByOtid(otid, frame);
			}
		}
	}

	@Override
	protected void onCompleteGameAction() {
		if(!isHoldLastFrame )
		{
			if (this.otid != "") 
			{
				scene.setToInitialAnimationWithoutChangingFrameByOtid(otid);
			}
		}

		scene.setStateOfPopup( false, .1,.1, null, "",null);

	}

	@Override
	public boolean isParallel() {

		return isParallel;
	}


	int getMillisecondsForSpeech(String speech) {
		double popupDisplayDuration = titleCard.getPopupDisplayDuration()*1000;
		// int delay = how;
		// int duration = (speech.length() * (2 + delay)) * 40;
		return (int)popupDisplayDuration;
	}

	public void setNonBlocking(boolean b) {
		isParallel = b;
	}

	public void setHoldLastFrame(boolean isHoldLastFrame ) {
		this.isHoldLastFrame = isHoldLastFrame ;
	}

	public void setNonIncrementing(NonIncrementing nonIncrementing ) {
		this.nonIncrementing = nonIncrementing ;
	}

	public IScenePresenterFromSayAction getscene() {
		return scene;
	}

	public void setScene(IScenePresenterFromSayAction scene) {
		this.scene = scene;
	}


	public void setTitleCard(ITitleCardPresenterFromSayAction titleCard) {
		this.titleCard = titleCard;
	}

	@Override
	public void setAll(IScenePresenterFromActions scene, IDialogTreePresenterFromActions dialogTree, ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		setTitleCard(titleCard);
		
		setScene(scene);
		
	}


	
	

}
