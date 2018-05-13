package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Font;
import java.awt.Graphics2D;

import com.github.a2g.core.interfaces.internal.IFontCalls;
import com.google.gwt.touch.client.Point;

public class FontCallsSwing 
implements IFontCalls
{ 
	Graphics2D graphics; 

	public FontCallsSwing(Graphics2D g)
	{
		this.graphics = g;
	}

	@Override
	public void setFontNameAndHeight(String name, int pixelHeight) {
		graphics.setFont(new Font(name, Font.BOLD, pixelHeight));
	}

	@Override
	public Point measureTextWidthAndHeight(String text) {
		double d = graphics.getFontMetrics().stringWidth(text);
		return new Point(d,graphics.getFontMetrics().getHeight());
	}
}
