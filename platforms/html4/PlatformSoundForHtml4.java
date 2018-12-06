package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformSound;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;
/*
 * how to fix the getDuration whilst asynchronous loading problem when update to 2.8.2
 * http://www.gwtproject.org/javadoc/latest/com/google/gwt/media/client/MediaBase.html
 * https://www.programcreek.com/java-api-examples/index.php?api=com.google.gwt.media.client.Audio
 * private void loadDiceAudio() {
  diceAudio = Audio.createIfSupported();
  if (diceAudio != null) {
    diceAudio.addSource(gameSounds.diceRollMp3().getSafeUri().asString(), AudioElement.TYPE_MP3);
    diceAudio.addSource(gameSounds.diceRollOgg().getSafeUri().asString(), AudioElement.TYPE_OGG);
    diceAudio.addSource(gameSounds.diceRollWav().getSafeUri().asString(), AudioElement.TYPE_WAV);
    diceAudio.load();
  }
}

 */
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
		    audio.setPreload(MediaElement.PRELOAD_AUTO);
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
