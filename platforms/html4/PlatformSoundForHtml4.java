package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.platform.IPlatformSound;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;

public class PlatformSoundForHtml4
implements IPlatformSound{
	Audio audio;
	String location;
	public PlatformSoundForHtml4(String location)
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
