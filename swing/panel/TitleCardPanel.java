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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;

@SuppressWarnings("serial")
public class TitleCardPanel 
extends JPanel implements TitleCardPanelAPI
{
	Label panel;
	int width;
	int height;
	public TitleCardPanel(final InternalAPI api) {
		{
			panel = new Label();

			panel.setText("(text not set)");
			this.add(panel);
		}

		{
			BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
			this.setLayout(layout);	
		}
	}

	@Override
	public void setText(String text) 
	{

		panel.setText(text);

	}

	@Override
	public void setColor(ColorEnum colorEnum) 
	{
		Color color = new java.awt.Color(255,1,1);
		panel.setForeground(color);
	}
	
	@Override
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		super.setSize(width, height);
	}


	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(this.width,this.height);
	}
}



