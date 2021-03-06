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

package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.nongame.IHostingPanel;

@SuppressWarnings("serial")
public class HostingPanelForSwing extends JPanel implements IHostingPanel
{

	public HostingPanelForSwing(HostingPanelForSwing another)
	{
		setTheOnlyLayoutNecessary();
		this.add(another);
	}
	public HostingPanelForSwing()
	{
		setTheOnlyLayoutNecessary();
	}
	private void setTheOnlyLayoutNecessary()
	{
		this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		this.setBackground(new Color(0,0,0));
	}
	@Override
	public void setThing(Object w) {
		this.add((Component) w);

	}
}
