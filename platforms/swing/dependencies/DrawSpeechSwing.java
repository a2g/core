package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.github.a2g.core.action.performer.dependencies.CommonDraw;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectAndLeaderLine; 

public class DrawSpeechSwing
{
	// to use BufferedImage bufferedImage = new BufferedImage(	resolution.getX(),	resolution.getY(),	BufferedImage.TYPE_INT_RGB);

	public static BufferedImage draw(RectAndLeaderLine all, ColorEnum speechColor, PointI resolution) 
	{
		BufferedImage img = new BufferedImage(resolution.getX(), resolution.getY(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();

		// draw resolution rect
		g2d.setColor(new Color(0,255,0));
		g2d.drawRect(0, 0, resolution.getX(), resolution.getY());

		SwingCanvasExposingIDrawSpeech adapter = new SwingCanvasExposingIDrawSpeech(g2d);
		CommonDraw.drawSpeech(adapter, all, speechColor);

		g2d.dispose();
		return img;

	}
}
