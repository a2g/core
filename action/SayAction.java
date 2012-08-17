/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;

import java.util.ArrayList;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredscene.ColorEnum;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.SceneObject;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class SayAction extends BaseAction {
	private ArrayList<String> speech;
	private ArrayList<Double> ceilings;
	private PopupPanel popup;
	private Label labelInPopup;
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

		this.popup = new PopupPanel();
		this.popup.setTitle(speech.get(0));
		this.labelInPopup = new Label(speech.get(0));

		this.popup.setWidget(labelInPopup);

		ColorEnum color = ColorEnum.Blue;

		if (object != null && object.getTalkingColor() != null) {
			color = object.getTalkingColor();
		}
		DOM.setStyleAttribute(labelInPopup.getElement(), "color",
				color.toString());
		DOM.setStyleAttribute(popup.getElement(), "borderColor",
				color.toString());

		if (object != null) {
			String talkingAnimTextualId = object.getTalkingAnimation();
			anim = object.animations().at(talkingAnimTextualId);
			if (anim!=null) {
				int durationOfSingleAnimation = anim.getFrames().count()
						* (40 * object.getTalkingAnimationDelay());

				double numberOfTimesAnimRepeats = totalDuration
						/ durationOfSingleAnimation;
				numberOfFramesTotal = (int) (numberOfTimesAnimRepeats * anim
						.getFrames().count());
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
				popup.setWidget(new Label(speech.get(i)));
				break;
			}
		}

		// if theres an associated animation, then use it
		if (this.anim != null) {
			int numberOfFramesSoFar = (int) (progress * numberOfFramesTotal);
			int frame = numberOfFramesSoFar % anim.getFrames().count();

			this.object.setCurrentFrame((int) frame);
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
