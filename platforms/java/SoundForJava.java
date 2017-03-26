package com.github.a2g.core.platforms.java;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.github.a2g.core.interfaces.internal.ISound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer; 

public class SoundForJava
implements ISound
{

	double durationInSeconds;
	String location;
	Media file;
	 MediaPlayer mediaPlayer ;
	 boolean hasLoaded;
	 boolean wasPlayedButNotLoaded;
	 
	SoundForJava(String location)
	{
		hasLoaded = false;
		 wasPlayedButNotLoaded = false;
		this.location = location;
		File filestring = new File(location);
		long audioFileLength = filestring.length();
		
		
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
