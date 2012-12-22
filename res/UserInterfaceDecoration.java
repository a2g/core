package com.github.a2g.core.res;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class UserInterfaceDecoration 
{
    public interface MyRes extends ClientBundle    
    {
        public static final UserInterfaceDecoration .MyRes RESOURCE =  GWT.create(UserInterfaceDecoration.MyRes.class);
        
        @Source("com/github/a2g/core/res/leftArrow.png")
        abstract ImageResource leftArrow();
        @Source("com/github/a2g/core/res/rightArrow.png")
        abstract ImageResource rightArrow();
    }
    
    public static ImageResource getLeftArrow()
    {
        final MyRes res = MyRes.RESOURCE;
        return res.leftArrow();
    }
    public static ImageResource getRightArrow()
    {
        final MyRes res = MyRes.RESOURCE;
        return res.rightArrow();
    }
}
