/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;


import com.github.a2g.bridge.panel.CueCardPanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;


public class CueCardPresenter {
    private CueCardPanel view;
    private String text;
	private ColorEnum color;	
    public CueCardPresenter(final AcceptsOneThing panel, EventBus bus, InternalAPI api) {
    	this.text = "";
        this.view = new CueCardPanel(api);
        panel.setThing(view);
    }

	public void setText(String string) {
		text = string;
		view.setText(text);
	}

	public void clear() {
		text = "";
	}

	public void setColor(ColorEnum color) {
		this.color = color;
		view.setColor(this.color);
		
	}

}
