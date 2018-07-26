package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.BasicStroke;
import java.awt.Color; 
import java.awt.Graphics2D;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformDrawCalls;
import com.github.a2g.core.primitive.ColorEnum;
 

public class PlatformDrawCallsSwing implements IPlatformDrawCalls
{ 
	Graphics2D graphics; 
	
	public PlatformDrawCallsSwing(Graphics2D g)
	{
		this.graphics = g;
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
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, ColorEnum fillColor, ColorEnum lineColor, int brushWidth) {
		graphics.setColor(new Color(fillColor.r, fillColor.g, fillColor.b));
		graphics.fillPolygon(xPoints, yPoints, nPoints);
		graphics.setColor(new Color(lineColor.r, lineColor.g, lineColor.b));
		graphics.setStroke(new BasicStroke(3F));
		graphics.drawPolygon(xPoints, yPoints, nPoints);		
	}

	
}
