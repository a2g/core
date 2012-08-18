package com.github.a2g.core.bridge;

import com.google.gwt.core.client.RunAsyncCallback;

public class GWT 
{
	public static <T> T create(Class<?> classLiteral)
	{
		return com.google.gwt.core.client.GWT.create(classLiteral);
	}
	
	public static void runAsync(RunAsyncCallback callback) 
	{
		com.google.gwt.core.client.GWT.runAsync(callback);
	}
};