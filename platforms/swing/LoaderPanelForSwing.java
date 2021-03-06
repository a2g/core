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


package com.github.a2g.core.platforms.swing;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformLoaderPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromLoaderMouse;
import com.github.a2g.core.primitive.ColorEnum;

@SuppressWarnings("serial")
public class LoaderPanelForSwing
extends JPanel
implements IPlatformLoaderPanel
{
	Label progress;
	Button reload;
	JProgressBar bar;
	IMasterPresenterFromLoaderMouse api;
	Button clickToContinue;
	int width;
	int height;

	public LoaderPanelForSwing(final IMasterPresenterFromLoaderMouse api, ColorEnum fore, ColorEnum back)
	{
		this.api = api;
		this.setForeground(new Color(fore.r, fore.g, fore.b));
		this.setBackground(new Color(back.r, back.g, back.b));

		{

			{
				progress = new Label();
				progress.setFont(new Font("Arial", 0, 20));
				this.add(progress);
			}
			{
				reload = new Button("Reload");
				this.add(reload);
			}
			{
				clickToContinue= new Button("Click to continue");
				clickToContinue.setEnabled(false);
				this.add(clickToContinue);
			}
			bar = new JProgressBar(0,35);
			bar.setStringPainted(true);
			Border border = BorderFactory.createTitledBorder("Loading...");
		    bar.setBorder(border);
			this.add(bar);
			{
				this.setLayout(new FlowLayout());
			}
			addHandler(api);
		}
	}

	void addHandler(final IMasterPresenterFromLoaderMouse  api)
	{
		reload.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event) {
						api.restartReloading();
					}
				}
				);
		clickToContinue.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event) {
						api.clickToContinue();
					}
				}
				);

	}

	@Override
	public void update(int current, int total, String name) {
		reload.setEnabled(true);
		clickToContinue.setEnabled(false);
		progress.setText(" "+current+"/"+total+ " " + name);
		double percent = 35.0*current/(double)total;
		
		bar.setValue((int)percent);
		//bar.updateUI();
	}

	@Override
	public void setScenePixelSize(int width, int height)
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

	@Override
	public void enableClickToContinue() {
		reload.setEnabled(false);
		clickToContinue.setEnabled(true);
	}
}



