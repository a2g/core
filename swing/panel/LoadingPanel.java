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

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;

@SuppressWarnings("serial")
public class LoadingPanel 
extends JPanel 
implements LoaderPanelAPI
{
	Label progress;
	Button reload; 
	InternalAPI api;
	int width;
	int height;
	
    public LoadingPanel(final InternalAPI api, ColorEnum fore, ColorEnum back) 
    {
    	this.api = api;
    	this.setForeground(new Color(fore.css[0], fore.css[1], fore.css[2]));	
   		this.setBackground(new Color(back.css[0], back.css[1], back.css[2]));
    	
    	{
    	
    		{
    			progress = new Label();

    			progress.setText("Loading...");
    			this.add(progress);
    		}
    		{
    			reload = new Button("Reload");
    			this.add(reload);
    			addHandler(api);
    		}
    		{
    			BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
    			this.setLayout(layout);	
    		}
    		
    	}
    }
    
    void addHandler(final InternalAPI api)
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
    }

    @Override
	public void update(int current, int total, String name) {
    	progress.setText(" "+current+"/"+total+ " " + name);
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
}
    
    

