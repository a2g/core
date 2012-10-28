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

import java.awt.Component;
import java.awt.Dimension;

import com.github.a2g.core.interfaces.CommandLinePanelAPI;


@SuppressWarnings("serial")
public class CommandLinePanel 
extends java.awt.Label 
implements CommandLinePanelAPI
{

    public CommandLinePanel() {
        this.setText("command line panel command line panel");
        //this.setSize(320, 50);
    }
    

	@Override
	public Dimension	getPreferredSize(){
		return new Dimension(400,50);
	}
			
	@Override
	public void setText(String text) {
		super.setText(text);
		
	}

	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
		
	}


	
}