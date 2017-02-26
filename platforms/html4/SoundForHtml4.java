package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.internal.ISound;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;

public class SoundForHtml4
implements ISound{
	Audio audio;
	String location;
	public SoundForHtml4(String location)
	{
		this.location = location;
		audio = Audio.createIfSupported();
		if(audio!=null)
		{
			audio.setSrc(location);
			audio.load();
		}
	}
	@Override
	public void play() {
		audio.play();
	}

	@Override
	public double getDuration() {
		return audio.getDuration();
	}
	@Override
	public void stop() {
		if(audio.getReadyState()!=MediaElement.HAVE_NOTHING)
		audio.setCurrentTime(0.0);
		 
	}
	@Override
	public String getLocation() {
		return location;
	}

}
