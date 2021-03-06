package com.github.a2g.core.platforms.swing;

import java.io.File;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformSound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer; 

public class PlatformSoundForSwing
implements IPlatformSound
{

	double durationInSeconds;
	String location;
	Media file;
	 MediaPlayer mediaPlayer ;
	 boolean hasLoaded;
	 boolean wasPlayedButNotLoaded;
	 
	public PlatformSoundForSwing(String location)
	{
		hasLoaded = false;
		 wasPlayedButNotLoaded = false;
		this.location = location;
		File filestring = new File(location); 
		
		
	    file = new Media(filestring.toURI().toString());  

	   mediaPlayer = new MediaPlayer(file);

	    mediaPlayer.setOnReady(new Runnable() {

	        @Override
	        public void run() {
	        	hasLoaded = true;
	            durationInSeconds = file.getDuration().toSeconds();

	            // play if you want
	            if(wasPlayedButNotLoaded)
	            	mediaPlayer.play();
	        }
	    });
	}

	@Override
	public void play() {

		if(hasLoaded)
			mediaPlayer.play();
		else
			wasPlayedButNotLoaded = true;
	}

	@Override
	public double getDuration()
	{
		return durationInSeconds;
	}

	@Override
	public void stop() {
		if(mediaPlayer!=null)
			mediaPlayer.stop();
	}

	@Override
	public String getLocation() {
		return location;
	}

}
