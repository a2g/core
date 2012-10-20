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

import com.github.a2g.bridge.panel.PopupPanelWithLabel;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.primitive.ColorEnum;



public class SayAction extends BaseAction {
	private ArrayList<String> speech;
	private ArrayList<Double> ceilings;
	private PopupPanelWithLabel popup;
	private short objId;
	private double totalDuration;
	private Animation anim;
	private SceneObject object;
	private int numberOfFramesTotal;

	public SayAction(BaseAction parent, short objId, String fullSpeech) {
		super(parent, parent.getApi());
		this.objId = objId;
		this.popup = null;
		this.anim = null;
		this.numberOfFramesTotal = 0;
		speech = new ArrayList<String>();
		ceilings = new ArrayList<Double>();
		String[] lines = fullSpeech.split("\n");
		this.totalDuration = 0;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			speech.add(line);
			int milliseconds = getMillisecondsForSpeech(line);
			totalDuration = totalDuration + milliseconds;
		}

		// set ceilings (for easy calcluation)
		double rollingCeiling = 0;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			rollingCeiling += getMillisecondsForSpeech(line);
			ceilings.add(new Double(rollingCeiling / totalDuration));
		}

		InternalAPI api = getApi();
		this.object = api.getObject(this.objId);

	}

	@Override
	public void runGameAction() {

		ColorEnum color = ColorEnum.Blue;

		if (object != null && object.getTalkingColor() != null) {
			color = object.getTalkingColor();
		}
		this.popup = new PopupPanelWithLabel(speech.get(0), color);
				
	

		if (object != null) {
			String talkingAnimTextualId = object.getTalkingAnimation();
			anim = object.getAnimations().at(talkingAnimTextualId);
			if (anim!=null) {
				int durationOfSingleAnimation = anim.getFrames().getCount()
						* (40 * object.getTalkingAnimationDelay());

				double numberOfTimesAnimRepeats = totalDuration
						/ durationOfSingleAnimation;
				numberOfFramesTotal = (int) (numberOfTimesAnimRepeats * anim
						.getFrames().getCount());
			} else {
				double framesPerSecond = 40;
				numberOfFramesTotal = (int) (totalDuration * framesPerSecond);
			}

		}

		if (anim != null && object != null) {
			object.setCurrentAnimation(anim.getTextualId());
			object.setVisible(true);
		}

		this.popup.setPopupPosition(20, 20);
		this.popup.show();

		this.run((int) totalDuration);
	}

	@Override
	protected void onUpdateGameAction(double progress) {

		// update text bubble
		for (int i = 0; i < ceilings.size(); i++) {
			if (progress < ceilings.get(i)) {
				popup.updateText(speech.get(i));
				break;
			}
		}

		// if theres an associated animation, then use it
		if (this.anim != null) {
			int numberOfFramesSoFar = (int) (progress * numberOfFramesTotal);
			int frame = numberOfFramesSoFar % anim.getFrames().getCount();

			this.object.setCurrentFrame(frame);
		}
	}

	@Override
	protected void onCompleteGameAction() {
		if (this.anim != null) {
			this.anim.getObject().setCurrentAnimation(
					this.anim.getObject().getHomeAnimation());
		}

		if (this.popup != null) {
			this.popup.hide();
		}
	}

	@Override
	public boolean isParallel() {

		return false;
	}

	int getMillisecondsForSpeech(String speech) {
		int popupDelay = getApi().getPopupDelay();
		// int delay = how;
		// int duration = (speech.length() * (2 + delay)) * 40;
		return popupDelay * 100;
	}
}
