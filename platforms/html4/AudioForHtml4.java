package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.IAudio;
import com.google.gwt.media.client.Audio;

public class AudioForHtml4 
implements IAudio{
	Audio audio;
	public AudioForHtml4(String location)
	{
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

}
