/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.Point;
import com.github.a2g.core.authoredroom.Rect;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class Image {
    private final AbsolutePanel panel;
    private final com.google.gwt.user.client.ui.Image image;
    private final Point offset;

    public Image(final com.google.gwt.user.client.ui.Image image, AbsolutePanel panel, Point offset) {
        this.offset = offset;

        this.image = image;
        this.panel = panel;

        image.setVisible(false);
    }

    public void addImageToPanel(int before) {
        if (before == -1) {
            panel.add(image, this.offset.getX(),
            		this.offset.getY());
        } else {
            panel.insert(image, this.offset.getX(),
            		this.offset.getY(), before);
        }
    }

    public void removeImageFromPanel() {
        panel.remove(image);
    }

    public void setTopLeft(Point topLeft) {

        update(topLeft);
    }

    public void setVisible(boolean visible, Point position) {
        image.setVisible(visible);
        update(position);
    }

    public Rect getBoundingRect() {
        return new Rect(this.offset.getX(),
        		this.offset.getY(),
                image.getWidth(),
                image.getHeight());
    }

    private void update(Point topLeft) {
        // if(image.getParent() != panel)
        // {
        // panel.add(image, x+this.finalX, y+this.finalY);
        // }
        panel.getElement().getStyle().setPosition(
                Position.RELATIVE);
        int x = this.offset.getX();
        int y = this.offset.getY();
        panel.setWidgetPosition(image,
                x + topLeft.getX(), y + topLeft.getY());
    }

}
