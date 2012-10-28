package com.github.a2g.core.swing.factory;

import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.primitive.Point;

public class SwingImage extends Image
{
	private final java.awt.Image image;
	private String objectId;
	public SwingImage(final java.awt.Image image,
	String objectId, ImagePanelAPI panel, Point offset) 
	{
		super(panel,offset);
		this.image = image;
		this.objectId = objectId;
		panel.setImageVisible(this, false);
	}
	
	public java.awt.Image getNativeImage()
	{
		return image;
	}
	
	public String getObjectId() 
	{
		return objectId;
	}
}
