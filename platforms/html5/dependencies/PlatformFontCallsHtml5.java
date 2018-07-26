package com.github.a2g.core.platforms.html5.dependencies;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformFontCalls;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.touch.client.Point;

public class PlatformFontCallsHtml5 implements IPlatformFontCalls{

	Context2d context;
	int fontHeight;
	
	public PlatformFontCallsHtml5(Context2d context)
	{
		this.context = context;
		this.fontHeight = 10;
	}
	
	@Override
	public Point measureTextWidthAndHeight(String text) {
		return new Point(context.measureText(text).getWidth(), fontHeight);
	}

	@Override
	public void setFontNameAndHeight(String fontName, int fontHeight) {
		this.fontHeight = fontHeight;
		context.setFont(""+fontHeight+"px \""+fontName+"\"");
	}

}
