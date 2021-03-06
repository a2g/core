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
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformScenePanel;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromSwingPopupPanel;
import com.github.a2g.core.platforms.swing.ScenePanelForSwing;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;

public class PopupPanelForSwing
{
	private JFrame popup;
	private JLabel labelInPopup;
	private IScenePresenterFromSwingPopupPanel toScene;

	public PopupPanelForSwing(IScenePresenterFromSwingPopupPanel toScene)
	{
		this.toScene = toScene;
		// create popup
		this.popup = new JFrame();
		this.popup.setUndecorated(true);
		//this.popup.setBackground(new Color(color.r, color.g, color.b));
		// create label and add to
		this.labelInPopup = new JLabel("");
		labelInPopup.setHorizontalAlignment(JLabel.CENTER);

		popup.add(labelInPopup);
	}

	public void setVisible(boolean isVisible)
	{
		popup.setVisible(isVisible);
	}

	public void setPopupPosition(int x, int y)
	{
		//api.getlocationOnScreen
		IPlatformScenePanel sv = toScene.getView();
		ScenePanelForSwing spj = (ScenePanelForSwing)sv;
		PointI p = spj.getTopLeft();
		popup.setLocation(p.getX()+ x, p.getY()+y);
	}
	public void setText(String string)
	{
		updateLabelSize(string);
		labelInPopup.setText(string);
	}


	void updateLabelSize(String text)
	{
		// set popup to be same size as label text
		FontMetrics fm = labelInPopup.getFontMetrics(labelInPopup.getFont()); // or another font
		double stringWidthInPixels = 18+fm.stringWidth(text);
		popup.setSize((int)stringWidthInPixels, fm.getHeight()*2);
	}

	public void setColor(ColorEnum color)
	{
		if(color!=null)
		{
			this.labelInPopup.setForeground(new Color(color.r, color.g, color.b));
		}
		this.labelInPopup.setBackground(new Color(0,0,0));//black
	}

	public void setCancelCallback(final BaseAction ba)
	{
		labelInPopup.addMouseListener
		(
				new MouseListener()
				{
					@Override
					public void mouseClicked(MouseEvent arg0)
					{
						ba.cancel();
					}

					@Override
					public void mouseEntered(MouseEvent arg0)
					{
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseExited(MouseEvent arg0)
					{
						// TODO Auto-generated method stub
					}

					@Override
					public void mousePressed(MouseEvent arg0)
					{


					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}
				}
				);
	}

}

