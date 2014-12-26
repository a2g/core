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

package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MouseToLoaderPresenterAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoaderPanel
extends SimplePanel
implements LoaderPanelAPI
{

	Label progress;
	Button reload;
	Button clickToContinue;
	MouseToLoaderPresenterAPI api;
	private Grid containerGrid;
	private final int TOTAL_NUMBER_OF_CELLS = 36;
	private int lastNumberOfCellsFilled;


	public LoaderPanel(final MouseToLoaderPresenterAPI api, ColorEnum fore, ColorEnum back) {
		this.api = api;
		VerticalPanel layout = new VerticalPanel();
		{
			progress = new Label();

			progress.getElement().getStyle().setProperty("color",fore.toString());
			progress.getElement().getStyle().setProperty( "backgroundColor",back.toString());

			progress.setText("Loading...");

			layout.add(progress);
		}
		{
			reload = new Button("Reload");
			layout.add(reload);
		}
		{
			clickToContinue = new Button("Click to continue");
			clickToContinue.setEnabled(false);
			layout.add(clickToContinue);
		}
		{
			// Initialize the progress elements
			containerGrid = new Grid(1, TOTAL_NUMBER_OF_CELLS);
			containerGrid.getElement().getStyle().setProperty( "margin","0px");
			containerGrid.getElement().getStyle().setProperty( "border","0px solid white");
			containerGrid.setCellPadding(0);
			containerGrid.setCellSpacing(0);

			for (int i = 0; i < TOTAL_NUMBER_OF_CELLS; i++) {
				Grid grid = new Grid(1, 1);
				grid.setHTML(0, 0, "");
				grid.getElement().getStyle().setProperty( "width","5px");
				grid.getElement().getStyle().setProperty( "height","15px");
				grid.getElement().getStyle().setProperty( "margin","1px");
				grid.getElement().getStyle().setProperty( "background","#eee");
				containerGrid.setWidget(0, i, grid);
			}

			layout.add(containerGrid);
		}
		addHandler(api);
		this.add(layout);

	}


	void addHandler(final MouseToLoaderPresenterAPI api)
	{
		reload.addClickHandler
		(
				new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event) {
						api.restartReloading();
					}
				}
				);
		clickToContinue.addClickHandler
		(
				new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event) {
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

		double percentage = current*100.0/total;
		{
			// Make sure we are error-tolerant
			if (percentage > 100) percentage = 100;
			if (percentage < 0) percentage = 0;

			// Set the internal variable
			int progress = (int)percentage;

			// Update the elements in the progress grid to
			// reflect the status
			int numberOfCellsFilled = (current==1)? 1 : (TOTAL_NUMBER_OF_CELLS-1) * progress / 100 + 1;

			if(numberOfCellsFilled != lastNumberOfCellsFilled)
			{
				if(numberOfCellsFilled<lastNumberOfCellsFilled)
				{
					for(int i=numberOfCellsFilled;i<lastNumberOfCellsFilled;i++)
					{
						Grid grid = (Grid) containerGrid.getWidget(0, i);
						if(grid!=null)
						{
							grid.getElement().getStyle().setProperty( "background","#eee");
						}
					}
				}
				else	
				{
					for(int i=lastNumberOfCellsFilled;i<numberOfCellsFilled;i++)
					{
						Grid grid = (Grid) containerGrid.getWidget(0, i);
						if(grid!=null)
						{
							grid.getElement().getStyle().setProperty( "background","blue");
						}
					}
				}
				lastNumberOfCellsFilled = numberOfCellsFilled;
			}
		}
	}

	@Override
	public void setScenePixelSize(int width, int height)
	{
		this.setSize("" + width + "px",
				"" + height + "px");
	}


	@Override
	public void enableClickToContinue() {
		reload.setEnabled(false);
		clickToContinue.setEnabled(true);
	}
}



