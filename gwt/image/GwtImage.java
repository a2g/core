package com.github.a2g.core.gwt.image;

import com.github.a2g.core.authoredscene.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.primitive.Point;

public class GwtImage extends Image
{
	private final com.google.gwt.user.client.ui.Image image;
	
	public GwtImage(final com.google.gwt.user.client.ui.Image image,
	ImagePanelAPI panel, Point offset) 
	{
		super(panel,offset);
		this.image = image;
		panel.setImageVisible(this, false);
	}
	
	public com.google.gwt.user.client.ui.Image getNativeImage()
	{
		return image;
	}
}
