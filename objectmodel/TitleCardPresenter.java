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

import com.github.a2g.core.interfaces.IFactory;
import com.github.a2g.core.interfaces.IHostingPanel;
import com.github.a2g.core.interfaces.IMasterPresenterFromTitleCard;
import com.github.a2g.core.interfaces.ITitleCardPresenter;
import com.github.a2g.core.interfaces.ITitleCardPanelFromTitleCardPresenter;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class TitleCardPresenter implements ITitleCardPresenter {
	private ITitleCardPanelFromTitleCardPresenter view;
	private String text;
	private double popupDisplayDuration;

	public TitleCardPresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromTitleCard master, IFactory factory) {
		this.text = "";
		this.popupDisplayDuration = .8;
		this.view = factory
				.createTitleCardPanel(ColorEnum.Red, ColorEnum.Black);
		panel.setThing(view);
	}

	public void setText(String string) {
		text = string;
		view.setText(text);
	}

	public void clear() {
		text = "";
	}

	public void setScenePixelSize(int width, int height) {
		this.view.setScenePixelSize(width, height);

	}

	public ITitleCardPanelFromTitleCardPresenter getView() {
		return view;
	}

	public double getPopupDisplayDuration() {
		return popupDisplayDuration;
	}

	public void decrementPopupDisplayDuration() {
		popupDisplayDuration *= .9;
	}

	public void incrementPopupDisplayDuration() {
		popupDisplayDuration *= 1.1;
	}

}
