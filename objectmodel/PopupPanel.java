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


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;


public class PopupPanel
implements PopupPanelAPI
{
	private com.google.gwt.user.client.ui.PopupPanel popup;
	private Label labelInPopup;

	public PopupPanel(String speech, ColorEnum color, final BaseAction toCancel)
	{
		this.popup = new com.google.gwt.user.client.ui.PopupPanel();
		this.popup.setTitle(speech);
		this.labelInPopup = new Label(speech);

		this.popup.setWidget(labelInPopup);

		if(color!=null)
		{
			DOM.setStyleAttribute(labelInPopup.getElement(), "color",color.toString());
			DOM.setStyleAttribute(popup.getElement(), "borderColor",color.toString());
		}

		labelInPopup.addClickHandler(
				new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event) {
						toCancel.cancel();

					}

				}
				);

	}
	@Override
	public void show()
	{
		popup.show();
	}

	@Override
	public void setPopupPosition(int x, int y)
	{
		popup.setPopupPosition(x,y);
	}
	@Override
	public void updateText(String string)
	{
		popup.setWidget(new Label(string));
	}
	@Override
	public void hide()
	{
		popup.hide();
	}

}
