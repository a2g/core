package com.github.a2g.core;


import com.github.a2g.core.mouse.ChoiceMouseClickHandler;
import com.github.a2g.core.mouse.ChoiceMouseOutHandler;
import com.github.a2g.core.mouse.ChoiceMouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;


public class ChoicesPanel extends Grid {

    ChoicesPanel() {
        super(4, 1);
        for (int i = 0; i < getRowCount(); i++) {
            Label widget = new Label("");

            this.setWidget(i, 0, widget);
        }
    }

    public void update(Choices choices, final EventBus bus) {
        // destroy old
        for (int i = 0; i < getRowCount(); i++) {
            this.setWidget(i, 0, null);
        }

        for (int i = 0; i < getRowCount()
                && i
                        < choices.getPlaces().size(); i++) {
            int place = choices.getPlaces().get(i).intValue();
            String buttonText = choices.getTexts().get(
                    i);
            Label label = new Label(buttonText);

            this.setWidget(i, 0, label);

            label.addMouseOverHandler(
                    new ChoiceMouseOverHandler(
                            label));
            label.addMouseOutHandler(
                    new ChoiceMouseOutHandler(
                            label));
            label.addClickHandler(
                    new ChoiceMouseClickHandler(
                            bus, label, place));

        }	
    }
}
