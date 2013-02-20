/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
 
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
