/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;


import com.google.gwt.user.client.ui.Grid;


public class InventoryPanel extends Grid {

    public InventoryPanel() {
        super(1, 1);
    }

    public void updateInventory(Inventory inventory) {
        double ratioOfWidthToHeight = 2;
        int count = inventory.items().getCount();
        double halfCount = (count
                / ratioOfWidthToHeight);
        double height = Math.sqrt(halfCount);
        double width = height
                * ratioOfWidthToHeight; // make it square

        this.resize((int) (height + .5),
                (int) (width + .5));

        int j = 0;

        for (int i = 0; i
                < inventory.items().getCount()
                        && j < count; i++) {
            InventoryItem item = inventory.items().at(
                    i);

            if (item.isVisible()) {
                int row = j / getColumnCount();
                int column = j
                        % getColumnCount();

                if (row < getRowCount()
                        && column
                                < getColumnCount()) {
                    item.getDisplayName();

                    this.setWidget(row, column,
                            item.getImage());
                }
                j++;
            }
        }
    }

}
    
