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

package com.github.a2g.core.platforms.java;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.a2g.core.interfaces.IPackagedImage;

public class PackagedImageForJava
implements IPackagedImage
{
	String imagePath;
	public PackagedImageForJava(String imagePath)
	{
		this.imagePath = imagePath;
	}
	public String getPath()
	{
		return imagePath;
	}

	public java.awt.Image unpack()
	{

		java.awt.Image img = null;
		try
		{
			String path = this.getPath();
			img=ImageIO.read(new File(path));
		}
		catch(IOException e)
		{
			try
			{
				img=ImageIO.read(new File("E:/Conan/Swing/bin/com/github/a2g/core/res/leftArrow.png"));
			}
			catch(IOException f)
			{
				System.out.println("couldn't find 'com/github/a2g/core/leftArrow.png', so exiting.");
				System.exit(0);
			}
		}
		return img;
	}
}