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


package com.github.a2g.core.canvas.panel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.MouseToVerbsPresenterAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.objectmodel.Verb;
import com.github.a2g.core.objectmodel.Verbs;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.canvas.mouse.VerbMouseClickHandler;
import com.github.a2g.core.canvas.mouse.VerbMouseOverHandler;



@SuppressWarnings("serial")
public class VerbsPanel
extends JPanel implements VerbsPanelAPI
{
	InternalAPI api;
	Verbs verbs;
	GridLayout grid;
	MouseToVerbsPresenterAPI mouseToPresenter;
	private int preferredWith;
	private int preferredHeight;
	public VerbsPanel(InternalAPI api, MouseToVerbsPresenterAPI mouseToPresenter, ColorEnum fore, ColorEnum back)
	{
		this.mouseToPresenter = mouseToPresenter;
		this.api =  api;
		this.preferredWith = 160;
		this.preferredHeight = 80;
		this.setForeground(new Color(fore.r, fore.g, fore.b));
		this.setBackground(new Color(back.r, back.g, back.b));
		grid = new GridLayout();
		grid.setVgap(0);
		grid.setHgap(0);
		this.setLayout(grid);
	}

	@Override
	public Dimension	getPreferredSize()
	{
		return new Dimension(this.preferredWith,this.preferredHeight);
	}

	@Override
	public void setVerbs(Verbs verbs)
	{
		this.verbs = verbs;
		update();
	}

	@Override
	public void update()
	{
		this.removeAll();
		//this.add(new Label("Verbs"));

		int numberOfRows = verbs.getNumberOfRows();
		int numberOfColumns = verbs.getNumberOfColumns();
		grid.setRows(numberOfRows);
		grid.setColumns(numberOfColumns);

		for (int i = 0; i < (grid.getColumns()
				* grid.getRows()); i++)
		{
			if(i<verbs.items().size())
			{
				Verb v = verbs.items().get(i);
				int code = v.getCode();
				String textualId = v.gettextualId();
				Label label = new Label(textualId);
				String displayText = v.getdisplayText();


				label.addMouseListener
				(
					new VerbMouseOverHandler( mouseToPresenter, displayText, textualId, code)
				);
				label.addMouseListener
				(
					new VerbMouseClickHandler(mouseToPresenter, displayText, textualId, code)
				);

				this.add(label);
			}
		}
	}

	@Override
	public void setWidth(int i) {
		this.preferredWith = i;
		this.preferredHeight = 80;
		update();

	}

}
