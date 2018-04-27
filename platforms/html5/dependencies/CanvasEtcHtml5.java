package com.github.a2g.core.platforms.html5.dependencies;

import com.github.a2g.core.action.performer.dependencies.CommonDraw;
import com.github.a2g.core.interfaces.internal.IContext2d;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.RectAndLeaderLine;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class CanvasEtcHtml5 
implements IContext2d 
{

	private Canvas canvasA;
	private Canvas canvasB;
	private Context2d contextA;
	private Context2d contextB;

	public CanvasEtcHtml5(String styleNameMustMatchDivTag)
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

		canvasA.setStyleName(styleNameMustMatchDivTag);     // *** must match the div tag in CanvasExample.html ***
		canvasB.setStyleName(styleNameMustMatchDivTag);
	}

	

	@Override
	public Point measureTextWidthAndHeight(String text) {
		double d = contextB.measureText(text).getWidth();
		return new Point(d,10);
	}

	public void addItselfToPanel(RootPanel rootPanel) {
		rootPanel.add(canvasA);
		rootPanel.add(canvasB);
		contextA = canvasA.getContext2d();
		contextB = canvasB.getContext2d();
	}

 
	public void drawSinglePixelRect(int left, int top, int w , int h )
	{
		contextB.setLineWidth(1);
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
 

	public int getCoordinateSpaceWidth() {
		return canvasA.getCoordinateSpaceWidth();
	}

	public int getCoordinateSpaceHeight() {
		return canvasA.getCoordinateSpaceHeight();
	}

	public void drawSpeech(RectAndLeaderLine speechRectAndLeaderLine, ColorEnum speechColor) 
	{
		CommonDraw.drawSpeech(new Html5CanvasExposingIDrawSpeech(contextB), speechRectAndLeaderLine, speechColor);
	}
	
	public void setFontNameAndHeightUsedInHtml4(String fontName, int fontHeight) {
		 
		contextB.setFont(""+fontHeight+"px \""+fontName+"\"");
	}



	public Context2d getContextB() {// used for test harness, otherwise not used.
		return contextB;
	}

}
