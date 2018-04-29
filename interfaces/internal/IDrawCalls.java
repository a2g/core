package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.primitive.ColorEnum;

public interface IDrawCalls 
{
	public void fillRect(int x, int y, int width, int height, ColorEnum fillColor);
	public void drawSinglePixelRect(int x, int y, int w, int h, ColorEnum lineColor);
	public void drawText(double x, double y, String line, ColorEnum fillColor );
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, ColorEnum lineColor, ColorEnum fillColor);
}
