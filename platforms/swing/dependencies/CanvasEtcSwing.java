package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
 
import com.github.a2g.core.objectmodel.SpeechCommon;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectAndLeaderLine;

public class CanvasEtcSwing  
{
	BufferedImage img;
	Graphics2D g2d;
	public CanvasEtcSwing()
	{

	}

	public void setScenePixelSize(int width, int height) {
		this.img = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
	}	 

	public  BufferedImage draw(RectAndLeaderLine rectEtc, ColorEnum speechColor, PointI resolution) 
	{
		// draw resolution rect
		g2d.setBackground(new Color(0, 0, 0, 0));
		g2d.clearRect(0, 0, img.getWidth(), img.getHeight());
		g2d.setColor(new Color(0,255,0));
		g2d.drawRect(0, 0, resolution.getX(), resolution.getY());

		DrawCallsSwing adapter = new DrawCallsSwing(g2d);
		SpeechCommon.mainDraw(adapter, rectEtc, speechColor);

		g2d.dispose();
		return img;
	}

	public BufferedImage getImage() {
		return this.img;
	}

	public Graphics2D getGraphics() {
		return g2d;
	}

}
