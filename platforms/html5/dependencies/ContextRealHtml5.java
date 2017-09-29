package com.github.a2g.core.platforms.html5.dependencies;

import com.github.a2g.core.interfaces.internal.IContext2d;
import com.github.a2g.core.platforms.html5.mouse.SceneMouseClickHandler;
import com.github.a2g.core.platforms.html5.mouse.SceneMouseOverHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextRealHtml5 
implements IContext2d
{
	
	private Canvas canvasA;
	private Canvas canvasB;
	private Context2d contextA;
	private Context2d contextB;
	
	public ContextRealHtml5(String styleNameMustMAtchDivTag)
	{
		   canvasA = Canvas.createIfSupported();
		   if (canvasA == null) {
	              RootPanel.get().add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
	              return;
	        }
			canvasB = Canvas.createIfSupported();
	        if (canvasB == null) {
	              RootPanel.get().add(new Label("Sorry, your browser doesn't support two Canvas elements"));
	              return;
	        }
	         
	        canvasA.setStyleName(styleNameMustMAtchDivTag);     // *** must match the div tag in CanvasExample.html ***
	}

	@Override
	public void setFont(String font) {
		contextB.setFont(font);
	}

	@Override
	public double measureTextWidth(String text) {
		double d = contextB.measureText(text).getWidth();
		return d;
	}

	public void addItselfToPanel(RootPanel rootPanel) {
		rootPanel.add(canvasA);
		rootPanel.add(canvasB);
		contextA = canvasA.getContext2d();
		contextB = canvasB.getContext2d();
	}
	
	public void setFillStyle(String color)
	{
		contextB.setFillStyle(color);
	}
	public void fillRect(int left, int top, int width, int height)
	{
		contextB.fillRect(left, top, width, height);
	}
	public void fillText(String text, double x, double y)
	{
		contextB.fillText(text,  x, y);
	}
	public void setLineWidth(double width)
	{
		contextB.setLineWidth(width);
	}
	public void setStrokeStyle(String s)
	{
		contextB.setStrokeStyle(s);
	}
	public void strokeRect(int left, int top, int w , int h )
	{
		contextB.strokeRect(left, top, w, h);
	}
	
	public void setScenePixelSize(int width, int height, Panel w) {
		w.remove(canvasA);

		
		canvasA.setSize("" + width + "px", "" + height + "px");
		canvasA.setCoordinateSpaceWidth(width);
		canvasA.setCoordinateSpaceHeight(height);
		canvasB.setSize("" + width + "px", "" + height + "px");
		canvasB.setCoordinateSpaceWidth(width);
		canvasB.setCoordinateSpaceHeight(height);

		w.add(canvasA);

		contextA = canvasA.getContext2d();
		contextB = canvasB.getContext2d();
		
	}
	public void addMouseMoveHandler(MouseMoveHandler handler) {
		canvasA.addMouseMoveHandler(handler);
	}
	
	public void addClickHandler(ClickHandler handler)
	{
		canvasA.addClickHandler(handler);
	}

	public void translateAndDraw(int x, int y, ImageElement imageElement) 
	{
		contextB.save();
		contextB.translate(x, y);
		contextB.drawImage(imageElement, 0, 0);
		contextB.restore();
	}
	public Element getElement()
	{
		return canvasA.getElement();
	}
	
	public double getOffsetHeight(){ return this.canvasA.getOffsetHeight(); }
	public double getOffsetWidth(){ return this.canvasA.getOffsetWidth(); }

	public void copyBackBufferToFront() {
		contextA.drawImage(contextB.getCanvas(), 0, 0);
	}

	public void fillText(String text, int x, int y, int width) {
		contextB.fillText(text, x, y, width);
	}

	public int getCoordinateSpaceWidth() {
		return canvasA.getCoordinateSpaceWidth();
	}
	
	public int getCoordinateSpaceHeight() {
		return canvasA.getCoordinateSpaceHeight();
	}
}
