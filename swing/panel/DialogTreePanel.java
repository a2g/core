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


import java.awt.GridLayout;

import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.objectmodel.DialogTree;
import com.github.a2g.core.swing.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.swing.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.swing.mouse.DialogTreeMouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import java.awt.Label;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DialogTreePanel 
extends JPanel 
implements DialogTreePanelAPI
{

	java.awt.GridLayout gridLayout;
    public DialogTreePanel() {
    	this.add(new Label("DialogTreePanel"));
    	gridLayout = new GridLayout();
    	gridLayout.setColumns(4);
    	gridLayout.setRows(1);
    	setLayout(gridLayout);
    
    }

    public void update(DialogTree dialogTree, final EventBus bus) {
        // destroy old
    	gridLayout = new GridLayout();
    	setLayout(gridLayout);
    	gridLayout.setColumns(4);
    	gridLayout.setRows(1);
    
        for (int i = 0; i < dialogTree.getSubBranchIds().size(); i++) 
        {
            int subBranchId = dialogTree.getSubBranchIds().get(i).intValue();
            String lineOfDialog = dialogTree.getLinesOfDialog().get(
                    i);
            
            Label label =  new java.awt.Label(lineOfDialog);
            gridLayout.addLayoutComponent("item"+i, label);

            
            label.addMouseListener(
                    new DialogTreeMouseOverHandler(label));
            label.addMouseListener(
                    new DialogTreeMouseOutHandler(
                            label));
            label.addMouseListener(
                    new DialogTreeMouseClickHandler(
                            bus, label, subBranchId));

        }	
    }



}
