package com.github.a2g.core;


import com.github.a2g.core.mouse.ChoiceMouseClickHandler;
import com.github.a2g.core.mouse.ChoiceMouseOutHandler;
import com.github.a2g.core.mouse.ChoiceMouseOverHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;


public class DialogTreePanel extends Grid {

    DialogTreePanel() {
        super(4, 1);
        for (int i = 0; i < getRowCount(); i++) {
            Label widget = new Label("");

            this.setWidget(i, 0, widget);
        }
    }

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
                    new ChoiceMouseOverHandler(
                            label));
            label.addMouseOutHandler(
                    new ChoiceMouseOutHandler(
                            label));
            label.addClickHandler(
                    new ChoiceMouseClickHandler(
                            bus, label, subBranchId));

        }	
    }
}
