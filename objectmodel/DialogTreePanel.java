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


import com.github.a2g.core.gwt.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.gwt.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.gwt.mouse.DialogTreeMouseOverHandler;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;


public class DialogTreePanel 
extends Grid 
implements DialogTreePanelAPI
{
    public DialogTreePanel() {
        super(4, 1);
        for (int i = 0; i < getRowCount(); i++) {
            Label widget = new Label("");

            this.setWidget(i, 0, widget);
        }
    }

    @Override
	public void update(DialogTree dialogTree, final EventBus bus) {
        // destroy old
        for (int i = 0; i < getRowCount(); i++) {
            this.setWidget(i, 0, null);
        }

        for (int i = 0; i < getRowCount()
                && i
                        < dialogTree.getSubBranchIds().size(); i++) {
            int subBranchId = dialogTree.getSubBranchIds().get(i).intValue();
            String lineOfDialog = dialogTree.getLinesOfDialog().get(
                    i);
            Label label = new Label(lineOfDialog);

            this.setWidget(i, 0, label);

            label.addMouseOverHandler(
                    new DialogTreeMouseOverHandler(
                            label));
            label.addMouseOutHandler(
                    new DialogTreeMouseOutHandler(
                            label));
            label.addClickHandler(
                    new DialogTreeMouseClickHandler(
                            bus, label, subBranchId));

        }	
    }

	@Override
	public void setPixelSize(int width, int height) 
	{
	
	}
}
