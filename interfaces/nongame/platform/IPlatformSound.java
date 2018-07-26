package com.github.a2g.core.interfaces.nongame.platform;

/*
 * An abstraction of the sound object on both GWT and Java AWT
 */
public interface IPlatformSound 
{
	String getLocation();
	double getDuration();
	void play();
	void stop();
}
