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


import com.github.a2g.core.gwt.mouse.ImageMouseClickHandler;
import com.github.a2g.core.gwt.mouse.VerbMouseOverHandler;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


public class VerbsPanel 
extends Grid 
implements VerbsPanelAPI
{
    public VerbsPanel(EventBus bus, InternalAPI api) {
        super(4, 3);
        for (int i = 0; i
                < (getColumnCount()
                        * getRowCount()); i++) {
            int row = i / getColumnCount();
            int column = i % getColumnCount();

//            Verbs verbs = api.getVerbsGui().getVerbsModel();
//            String sentenceText = verbs.items().get(i).getSentenceText();
//            String buttonText = verbs.items().get(i).getButtonText();
//            String code = "" + i;
//            Label widget = new Label(
//                    buttonText);
//
//            this.setWidget(row, column, widget);
//            widget.addMouseMoveHandler(
//                    new VerbMouseOverHandler(
//                            bus, sentenceText,
//                            code, i));
//            widget.addClickHandler(
//                    new ImageMouseClickHandler(
//                            bus, null));
//            
//            DOM.setStyleAttribute(
//                    this.getElement(), "color",
//                    "Purple");
        }
    }

}
