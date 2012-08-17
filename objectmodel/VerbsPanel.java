/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;


import com.github.a2g.core.mouse.ImageMouseClickHandler;
import com.github.a2g.core.mouse.VerbMouseOverHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;


public class VerbsPanel extends Grid {
   
    public VerbsPanel(Verbs verbs, EventBus bus) {
        super(4, 3);
        for (int i = 0; i
                < (getColumnCount()
                        * getRowCount()); i++) {
            int row = i / getColumnCount();
            int column = i % getColumnCount();

            String sentenceText = verbs.items().get(i).getSentenceText();
            String buttonText = verbs.items().get(i).getButtonText();
            String code = "" + i;
            Label widget = new Label(
                    buttonText);

            this.setWidget(row, column, widget);
            widget.addMouseMoveHandler(
                    new VerbMouseOverHandler(
                            bus, sentenceText,
                            code, i));
            widget.addClickHandler(
                    new ImageMouseClickHandler(
                            bus, null));
            
            DOM.setStyleAttribute(
                    this.getElement(), "color",
                    "Purple");
        }
    }

}
