/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.Rect;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class Image {
    private final AbsolutePanel panel;
    private final com.google.gwt.user.client.ui.Image image;
    private final int finalX;
    private final int finalY;

    public Image(final com.google.gwt.user.client.ui.Image image, AbsolutePanel panel, int finalX, int finalY) {
        this.finalX = finalX;
        this.finalY = finalY;

        this.image = image;
        this.panel = panel;

        image.setVisible(false);
    }

    public void addImageToPanel(int x, int y, int before) {
        if (before == -1) {
            panel.add(image, this.finalX,
                    this.finalY);
        } else {
            panel.insert(image, this.finalX,
                    this.finalY, before);
        }
    }

    public void removeImageFromPanel() {
        panel.remove(image);
    }

    public void setXY(int x, int y) {

        update(x, y);
    }

    public void setVisible(boolean visible, int x, int y) {
        image.setVisible(visible);
        update(x, y);
    }

    public Rect getBoundingRect() {
        return new Rect(finalX, finalY,
                image.getWidth(),
                image.getHeight());
    }

    private void update(int x, int y) {
        // if(image.getParent() != panel)
        // {
        // panel.add(image, x+this.finalX, y+this.finalY);
        // }
        panel.getElement().getStyle().setPosition(
                Position.RELATIVE);
        panel.setWidgetPosition(image,
                x + finalX, y + finalY);
    }

}
