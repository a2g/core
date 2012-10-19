/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;

import java.util.ArrayList;

import com.github.a2g.bridge.panel.PopupPanelWithLabel;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.primitive.ColorEnum;



public class CueCardAction extends BaseAction {
	String text;
	ColorEnum color;

	public CueCardAction(BaseAction parent, String text, ColorEnum color) {
		super(parent, parent.getApi());
		this.text = text;
		this.color = color;
	}

	@Override
	public void runGameAction() {

		int totalDuration = getApi().getPopupDelay();
		
		getApi().displayCueCard(text, color);
		this.run((int) totalDuration);
	}

	@Override
	protected void onUpdateGameAction(double progress) {

	}

	@Override
	protected void onCompleteGameAction() {
		getApi().displayCueCard("", color);	
	}

	@Override
	public boolean isParallel() {

		return false;
	}
	
}
