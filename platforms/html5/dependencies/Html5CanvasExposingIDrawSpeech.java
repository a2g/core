package com.github.a2g.core.platforms.html5.dependencies;

import com.github.a2g.core.interfaces.performer.IDrawSpeech;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class Html5CanvasExposingIDrawSpeech implements IDrawSpeech{


	Context2d contextB; 

	public Html5CanvasExposingIDrawSpeech(Context2d g)
	{
		this.contextB = g;
	}
	@Override
	public void fillText(double x, double y, String line, ColorEnum textColor, ColorEnum fillColor) {
		contextB.setStrokeStyle(CssColor.make(textColor.r, textColor.g, textColor.b));
		contextB.fillText(line, x, y);
	}

	@Override
	public void fillRect(int left, int top, int width, int height, ColorEnum fillColor) {
		contextB.setFillStyle(CssColor.make(fillColor.r, fillColor.g, fillColor.b));
		contextB.fillRect(left, top, width, height);
	}

	@Override
	public void drawSinglePixelRect(int x, int y, int w, int h, ColorEnum lineColor) {
		contextB.setStrokeStyle(CssColor.make(lineColor.r, lineColor.g, lineColor.b));
		contextB.rect(x, y, w, h);
	}
	
	@Override
	public void setFontNameAndHeight(String fontName, int fontHeight) {
 
		contextB.setFont(""+fontHeight+"px \""+fontName+"\"");
	}
	 
}
