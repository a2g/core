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

package com.github.a2g.core.swing.panel;
import java.awt.FontMetrics;
import java.awt.Label;

import javax.swing.JFrame;

import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;

public class PopupPanel
implements PopupPanelAPI
{
	private JFrame popup;
	private Label labelInPopup;
	
	public PopupPanel(String speech, ColorEnum color)
	{
		// create popup
		this.popup = new JFrame();
		this.popup.setUndecorated(true);
	
		// create label and add to 
		this.labelInPopup = new Label(speech);
		popup.add(labelInPopup);
		
		// set popup to be same size as label text
		FontMetrics fm = labelInPopup.getFontMetrics(labelInPopup.getFont()); // or another font
		int strw = fm.stringWidth(speech);
		popup.setSize(strw*2, fm.getHeight()*2);
		
		
	}
	
	@Override
	public void show()
	{
		popup.setVisible(true);
		
	}
	
	@Override
	public void setPopupPosition(int x, int y)
	{
		popup.setLocation(x, y);
	}
	@Override
	public void updateText(String string)
	{
		labelInPopup.setText(string);
	}
	@Override
	public void hide()
	{
		popup.setVisible(false);
	}
	
}
