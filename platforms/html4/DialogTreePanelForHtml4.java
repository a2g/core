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

package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformDialogTreePanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromDialogTreeMouse;
import com.github.a2g.core.objectmodel.DialogTree;
import com.github.a2g.core.platforms.html4.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.platforms.html4.mouse.DialogTreeMouseOverHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class DialogTreePanelForHtml4 extends Grid implements
IPlatformDialogTreePanel {
	ColorEnum rolloverColor;
	ColorEnum foregroundColor;
	private IMasterPresenterFromDialogTreeMouse master;

	public DialogTreePanelForHtml4(IMasterPresenterFromDialogTreeMouse master, ColorEnum foregroundColor,
			ColorEnum backgroundColor, ColorEnum rolloverColor) {
		super(4, 1);
		this.rolloverColor = rolloverColor;
		this.foregroundColor = foregroundColor;
		this.master = master;

		getElement().getStyle()
		.setProperty("Color", foregroundColor.toString());
		getElement().getStyle().setProperty("BackgroundColor",
				backgroundColor.toString());

		for (int i = 0; i < getRowCount(); i++) {
			Label label = new Label("");
			this.setWidget(i, 0, label);
			// DOM.setStyleAttribute(label.getElement(), "color",
			// foregroundColor.toString());

		}
	}

	@Override
	public void update(DialogTree dialogTree) {
		// destroy old
		for (int i = 0; i < getRowCount(); i++) {
			this.setWidget(i, 0, null);
		}

		for (int i = 0; i < getRowCount()
				&& i < dialogTree.getSubBranchIds().size(); i++) {
			int subBranchId = dialogTree.getSubBranchIds().get(i).intValue();
			String lineOfDialog = dialogTree.getLinesOfDialog().get(i);
			Label label = new Label(lineOfDialog);

			this.setWidget(i, 0, label);
			label.getElement().getStyle()
			.setProperty("color", foregroundColor.toString());

			label.addMouseOverHandler(new DialogTreeMouseOverHandler(label,
					rolloverColor));
			label.addMouseOutHandler(new DialogTreeMouseOutHandler(label,
					foregroundColor));
			label.addClickHandler(new DialogTreeMouseClickHandler(master, label,
					subBranchId));

		}
	}

	@Override
	public void setWidthAndHeight(int width, int height) {
		//this.width = width;
		//this.height = height;
		
	}


}
