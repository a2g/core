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


package com.github.a2g.core.platforms.java.panel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.interfaces.IVerbsPanelFromVerbsPresenter;
import com.github.a2g.core.objectmodel.Verb;
import com.github.a2g.core.objectmodel.Verbs;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.platforms.java.mouse.VerbMouseClickHandler;
import com.github.a2g.core.platforms.java.mouse.VerbMouseOverHandler;



@SuppressWarnings("serial")
public class VerbsPanelForJava
extends JPanel implements IVerbsPanelFromVerbsPresenter
{
	Verbs verbs;
	GridLayout grid;
	IVerbsPresenterFromVerbsPanel mouseToPresenter;
	private int preferredWith;
	private int preferredHeight;
	public VerbsPanelForJava(IVerbsPresenterFromVerbsPanel mouseToPresenter, ColorEnum fore, ColorEnum back)
	{
		this.mouseToPresenter = mouseToPresenter;
		this.preferredWith = 160;
		this.preferredHeight = 80;
		this.setForeground(new Color(fore.r, fore.g, fore.b));
		this.setBackground(new Color(back.r, back.g, back.b));
		
	}

	@Override
	public Dimension	getPreferredSize()
	{
		return new Dimension(this.preferredWith,this.preferredHeight);
	}

	@Override
	public void setVerbs(Verbs verbs)
	{

		assert(verbs.items().size()<15);
		this.verbs = verbs;
		update();
	}

	@Override
	public void update()
	{
		this.removeAll();
		grid = new GridLayout();
		grid.setVgap(0);
		grid.setHgap(0);
		this.setLayout(grid);

		int numberOfRows = verbs.getNumberOfRows();
		int numberOfColumns = verbs.getNumberOfColumns();
		grid.setRows(1);
		grid.setColumns(1);
		grid.setRows(numberOfRows);
		grid.setColumns(numberOfColumns);

		for (int i = 0; i < (grid.getColumns()
				* grid.getRows()); i++)
		{
			if(i<verbs.items().size())
			{
				Verb v = verbs.items().getByIndex(i);
				int vcode = v.getVCode();
				String vtid = v.getVtid();
				Label label = new Label(vtid);
				String displayText = v.getdisplayText();


				label.addMouseListener
				(
						new VerbMouseOverHandler( mouseToPresenter, displayText, vtid, vcode)
						);
				label.addMouseListener
				(
						new VerbMouseClickHandler(mouseToPresenter, displayText, vtid, vcode)
						);

				this.add(label);
			}
		}
	}

	@Override
	public void setWidth(int i) {
		this.preferredWith = i;
		this.preferredHeight = 80;
	}

}
