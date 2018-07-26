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

package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformVerbsPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.objectmodel.Verb;
import com.github.a2g.core.objectmodel.Verbs;
import com.github.a2g.core.platforms.html4.mouse.VerbMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.VerbMouseOverHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class VerbsPanelHtml4 extends Grid
implements IPlatformVerbsPanel
{
	ColorEnum rolloverColor;
	final IVerbsPresenterFromVerbsPanel mouseToPresenter;
	private int fontSize;
	private Verbs verbs;
	public VerbsPanelHtml4(IVerbsPresenterFromVerbsPanel mouseToPresenter,
			ColorEnum fore, ColorEnum back) {
		this.fontSize = 12;
		this.mouseToPresenter = mouseToPresenter;
		getElement().getStyle().setProperty("color", fore.toString());
		getElement().getStyle().setProperty("backgroundColor", back.toString());
		this.verbs = verbs;

	}

	@Override
	public void setVerbs(Verbs verbs) {
		this.verbs = verbs;
		update();
	}
	
	@Override
	public void incrementFontSize()
	{
		fontSize++;
		update();
	}
	
	@Override
	public void decrementFontSize()
	{
		fontSize--;
		update();
	}
	
	@Override
	public void update() {
		int rows = verbs.getNumberOfRows();
		int columns = verbs.getNumberOfColumns();
		this.resize(rows, columns);
		for (int i = 0; i < (rows * columns); i++) {
			int row = i / columns;
			if (i >= verbs.items().size())
				continue;
			int column = i % columns;

			Verb verb = verbs.items().getByIndex(i);
			String vtid = verb.getVtid();
			String displayText = verb.getdisplayText();
			int vcode = verb.getVCode();
			Label widget = new Label(vtid);
			widget.getElement().getStyle().setFontSize(fontSize, Unit.PX);

			this.setWidget(row, column, widget);
			widget.addMouseMoveHandler(new VerbMouseOverHandler(
					mouseToPresenter, displayText, vtid, vcode));
			widget.addClickHandler(new VerbMouseClickHandler(mouseToPresenter,
					displayText, vtid, vcode));
		}
	}
	
	

	@Override
	public void setWidth(int i) {
		// do nothing
		// since the panel hierarchy
		// in html evaluates in such a way
		// as to keep the width of the verbs
		// panel reasonable.

	}
}
