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

package com.github.a2g.core.platforms.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import com.github.a2g.core.interfaces.internal.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromDialogTreeMouse;
import com.github.a2g.core.objectmodel.DialogTree;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.platforms.java.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.platforms.java.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.platforms.java.mouse.DialogTreeMouseOverHandler;

import java.awt.Label;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DialogTreePanelForJava
extends JPanel
implements IDialogTreePanelFromDialogTreePresenter
{
	ColorEnum fore;
	ColorEnum back;
	ColorEnum roll;
	private int width;
	private int height;
	private IMasterPresenterFromDialogTreeMouse master;
	public DialogTreePanelForJava(IMasterPresenterFromDialogTreeMouse master, ColorEnum fore, ColorEnum back, ColorEnum roll)
	{
		GridLayout grid = new GridLayout();
		this.setLayout(grid);
		grid.setRows(10);
		grid.setColumns(1);
		this.width = 320;
		//this.height = 180;
		this.master = master;

		this.fore = fore;
		this.back = back;
		this.roll = roll;
		this.setForeground(new Color(fore.r, fore.g, fore.b));
		this.setBackground(new Color(back.r, back.g, back.b));
	}

	@Override
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(width,height);
	}

	

	@Override
	public void update(DialogTree dialogTree) {
		// destroy old
		this.removeAll();
		int numberOfSubbranches = dialogTree.getSubBranchIds().size();
		for (int i = 0; i < numberOfSubbranches; i++)
		{
			int subBranchId = dialogTree.getSubBranchIds().get(i).intValue();
			String lineOfDialog = dialogTree.getLinesOfDialog().get(i);

			Label label = new Label(lineOfDialog);
			label.addMouseListener(
					new DialogTreeMouseOverHandler(label, new Color(roll.r, roll.g, roll.b))
					);
			label.addMouseListener(
					new DialogTreeMouseOutHandler(label, new Color(fore.r, fore.g, fore.b))
					);

			label.addMouseListener(
					new DialogTreeMouseClickHandler(
							master, label, subBranchId));

			add(label);
		}

		validate();
		repaint();
	}

	@Override
	public void setWidthAndHeight(int width, int height) {
		this.width = width;
		this.height = height;
	}



}
