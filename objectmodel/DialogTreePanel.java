package com.github.a2g.core.objectmodel;


import com.github.a2g.core.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.mouse.DialogTreeMouseOverHandler;
import com.google.gwt.event.shared.EventBus;
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
}
