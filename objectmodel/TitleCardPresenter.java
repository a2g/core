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


import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;


public class TitleCardPresenter {
	private TitleCardPanelAPI view;
	private String text;
	private ColorEnum color;

	public TitleCardPresenter(final HostingPanelAPI panel, EventBus bus, InternalAPI api, MasterPresenterHostAPI parent) {
		this.text = "";
		this.view = api.getFactory().createTitleCardPanel(ColorEnum.Red, ColorEnum.Black);
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

	public void setPixelSize(int width, int height)
	{
		this.view.setScenePixelSize(width , height);

	}

	public TitleCardPanelAPI getView()
	{
		return view;
	}

}
