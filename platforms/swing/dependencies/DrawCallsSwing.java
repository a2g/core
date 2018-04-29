package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.github.a2g.core.interfaces.performer.ISpeechCommonExpandedSet;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.touch.client.Point;

public class DrawCallsSwing implements ISpeechCommonExpandedSet
{ 
	Graphics2D graphics; 
	
	public DrawCallsSwing(Graphics2D g)
	{
		this.graphics = g;
	}

	@Override
	public void setFontNameAndHeight(String name, int pixelHeight) {
		graphics.setFont(new Font(name, Font.BOLD, pixelHeight));
		
	}

	@Override
	public void fillRect(int x, int y, int width, int height, ColorEnum fillColor) {
		graphics.setColor(new Color(fillColor.r, fillColor.g, fillColor.b));
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void drawSinglePixelRect(int x, int y, int width, int height, ColorEnum fillColor) {
	    graphics.setColor(new Color(fillColor.r, fillColor.g, fillColor.b));
		graphics.drawRect(x, y, width, height);
	}

	@Override
	public void drawText(double x, double y, String line, ColorEnum textColor) {
		graphics.setColor(new Color(textColor.r, textColor.g, textColor.b));
		graphics.drawString(line, (int)x, (int)y);
		
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, ColorEnum fillColor, ColorEnum lineColor) {
		graphics.setColor(new Color(fillColor.r, fillColor.g, fillColor.b));
		graphics.fillPolygon(xPoints, yPoints, nPoints);
		graphics.setColor(new Color(lineColor.r, lineColor.g, lineColor.b));
		graphics.setStroke(new BasicStroke(3F));
		graphics.drawPolygon(xPoints, yPoints, nPoints);		
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