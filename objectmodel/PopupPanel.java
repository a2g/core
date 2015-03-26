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
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;


public class PopupPanel
{
	private com.google.gwt.user.client.ui.PopupPanel popup;
	private Label labelInPopup;
	private int sceneWidth;
	private int sceneHeight;

	public PopupPanel(int sceneWidth, int sceneHeight)
	{
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		this.popup = new com.google.gwt.user.client.ui.PopupPanel();
		this.labelInPopup = new Label();

		this.popup.setWidget(labelInPopup);
	}

	public void setVisible(boolean isVisible)
	{
		if(isVisible)
		{
			popup.show();
		}
		else
		{
			popup.hide();
		}
	}

	public void setPopupPosition(double x, double y)
	{
		popup.setPopupPosition((int)(x*sceneWidth),(int)(y*sceneHeight));
	}
	public void setText(String speech)
	{
		this.popup.setTitle(speech);
		popup.setWidget(new Label(speech));
	}

	public void setColor(ColorEnum color)
	{
		if(color!=null)
		{
			labelInPopup.getElement().getStyle().setProperty("color","#ff0000");
			labelInPopup.getElement().getStyle().setProperty("fontColor","#ff0000");
			labelInPopup.getElement().getStyle().setProperty("textColor","#ff0000");
			//labelInPopup.getElement().getStyle().setProperty("fontColor",color.toString());
			popup.getElement().getStyle().setProperty("borderColor",color.toString());
		}
		//labelInPopup.getElement().getStyle().setProperty( "backgroundColor", ColorEnum.Black.toString());
		//popup.getElement().getStyle().setProperty("backgroundColor", ColorEnum.Black.toString());


	}
	public void setCancelCallback(final BaseAction ba)
	{
		if(ba==null)
			return;
		labelInPopup.addClickHandler
		(
				new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event) {
						ba.cancel();
					}

				}
				);

	}

}
