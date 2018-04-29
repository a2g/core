package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Font;
import java.awt.Graphics2D;

import com.github.a2g.core.interfaces.internal.IFontMethods;
import com.google.gwt.touch.client.Point;

public class FontCallsSwing implements IFontMethods
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
/*
g.fillRoundRect(
		0,
		0,
		speechRect.getWidth(),
		speechRect.getHeight(),
		10,
		10);
g.setColor(Color.black);

Graphics g2 = bufferedImage.getGraphics();
for(int i=0;i<all.lines.lines.size();i++)
{
	LineAndPos s = all.lines.lines.get(i);
	g2.drawString(s.line, s.x, s.y);
}


g2.drawRect(0+1, 0+1, speechRect.getWidth()-3, speechRect.getHeight()-3);
g2.drawRect(0, 0, speechRect.getWidth()-1, speechRect.getHeight()-1);*/