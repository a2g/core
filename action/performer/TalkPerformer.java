package com.github.a2g.core.action.performer;

import java.util.ArrayList;

import com.github.a2g.core.action.performer.dependencies.SpeechCalculatorOuterForAll;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromTalkPerformer;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromTalkPerformer;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine;
import com.github.a2g.core.primitive.RectF;

public class TalkPerformer {
	private ArrayList<RectAndLeaderLine> returnObjects;
	
	private ArrayList<Double> startingTimeForEachLine;
	private double totalDurationInSeconds;
	private IScenePresenterFromTalkPerformer scene;
	private IMasterPresenterFromTalkPerformer master;
	private int numberOfFramesTotal;
	private NonIncrementing nonIncrementing;
	private String atidOfWhatItWasBeforeTalking;
	private int frameOfWhatItWasBeforeTalking;
	
	private String atid;
	private String otid;
	private String fullSpeech;
	private short ocode;
	public static String SCENE_TALKER = "SCENE_TALKER";// use animation of scene talker
	public static String SCENE_DIALOG_THEM = "SCENE_DIALOG_US";
	public static String SCENE_DIALOG_US = "SCENE_DIALOG_THEM";
	public enum NonIncrementing {
		True, False, FromAPI
	}

	public TalkPerformer(String atid, String fullSpeech) {
		this.numberOfFramesTotal = 0;
		this.nonIncrementing = NonIncrementing.False;
		this.ocode = -1;
		this.atid = atid;
		this.otid = "";
		this.fullSpeech = fullSpeech;
		startingTimeForEachLine = new ArrayList<Double>();
		
		this.totalDurationInSeconds = 0;
	}
 
	public TalkPerformer(short ocode, String fullSpeech) {
		this.numberOfFramesTotal = 0;
		this.nonIncrementing = NonIncrementing.False;
		this.ocode = ocode;
		this.atid = "";
		this.otid = "";
		this.fullSpeech = fullSpeech;
		returnObjects = new ArrayList<RectAndLeaderLine>();
		startingTimeForEachLine = new ArrayList<Double>();
		
		this.totalDurationInSeconds = 0;
	}

	int getAdjustedNumberOfFrames(String speech, double approxDuration,
			int animFramesCount, double durationOfSingleAnimation) {
		// but if we need an animation, we find out how long it takes
		// to play a single play of the animation to play whilst talking.
		
		// ... then we find how many times the animation should repeat
		// so that it fills up the totalDuration.
		double numberOfTimesAnimRepeats = approxDuration
				/ durationOfSingleAnimation;

		// ... and the number of frames that occurs during that
		// many plays of the animation.
		int numberOfFramesTotal = (int) (numberOfTimesAnimRepeats * animFramesCount);
		if (numberOfFramesTotal == 0)
			numberOfFramesTotal = animFramesCount;
		// The effect of this is that there is a little bit of 'over-play'
		// where the amount of time it takes to 'talk' 
		// when there is a talking animation, is usually a
		// little bit more than the time calculated from the speech.
		// This is to ensure that the animation always ends whilst
		// the head is down, mouth is shut. The smaller the number of
		// frames in a talking animation the less over-play.
		return numberOfFramesTotal;
	}

	public double run() {
		
	
		// first split based on authored breaks
		String[] lines = fullSpeech.split("\n");
		

		ArrayList<String> speech = new ArrayList<String>();
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			speech.add(line);
			double secondsForLine = getSecondsForLine(line);
			totalDurationInSeconds = totalDurationInSeconds + secondsForLine;
		}
		
		RectF maxRectF = scene.getSpeechRectUsingContingencies(atid);
		Rect maxRectI = translateRect(maxRectF);
		PointI mouth = scene.GetMouthLocationByOtid(otid);
		returnObjects = SpeechCalculatorOuterForAll.calculate(lines, maxRectI, 30, mouth, 38, 3, scene);
		//SpeechCalculatorOuterForAll calc = new SpeechCalculatorOuterForAll(speech, maxBalloonRect, 30, mouth, 38, 3,
		//	canvas);
		
		// set ceilings (for easy calcluation)
		double rollingStartingTimeForLine = 0;
		for (int i = 0; i < lines.length; i++) {
			startingTimeForEachLine.add(new Double(rollingStartingTimeForLine
					/ totalDurationInSeconds));
			String line = lines[i];
			rollingStartingTimeForLine += getSecondsForLine(line);
		}
		
		if(this.nonIncrementing==TalkPerformer.NonIncrementing.FromAPI)
		{
			this.nonIncrementing = master.isSayNonIncrementing()? NonIncrementing.True : NonIncrementing.False;
		}
		
		if(ocode!=-1)
		{
			String otid = scene.getOtidByCode(ocode);
			atid = scene.getAtidOfCurrentAnimationByOtid(otid);
			// odd choice, but shouldn't make a difference - we just need any animation
			// from that object, since api.setSpeechRect sets all animations.
		}
		else if (atid == SCENE_TALKER) {
			atid = scene.getAtidOfSceneTalker();
		}else if(atid == SCENE_DIALOG_US){
			atid = scene.getAtidOfSceneDialogUs();
		}else if(atid == SCENE_DIALOG_THEM){
			atid = scene.getAtidOfSceneDialogThem();
		}
		
		// only now do
		if (atid == "") {
			// if theres no animation then we just wait for the
			// totalDuration;
			double framesPerSecond = 40;
			numberOfFramesTotal = (int) ((totalDurationInSeconds) * framesPerSecond);
		} else {
			numberOfFramesTotal = getAdjustedNumberOfFrames(speech.get(0),
					totalDurationInSeconds,
					scene.getNumberOfFramesByAtid(atid),
					scene.getDurationByAtid(atid));
		}

		if (numberOfFramesTotal < 1) {
			//titleCard.displayTitleCard("error!! id=<" + atid
			//		+ "> numberOfFramesTotal=" + numberOfFramesTotal);
			assert (false);
		}

		// always make the speaker visible
		if (atid != "")
		{
			otid = scene.getOtidByAtid(atid);
			if(otid != "") 
			{
				// stash old state
				this.frameOfWhatItWasBeforeTalking = scene.getCurrentFrameByOtid(otid);
				this.atidOfWhatItWasBeforeTalking = scene.getAtidOfCurrentAnimationByOtid(otid);
				
				// set new state
				scene.setCurrentAnimationByAtid(atid);
				scene.setVisibleByOtid(otid, true);
			}
		}

		
		
		boolean visible = true;
		scene.setStateOfPopup(visible, returnObjects.get(0), this);
		return totalDurationInSeconds;
	}

	public void onUpdate(double progress) {

		if (!otid.isEmpty()) {
			if (atid != "" && otid != "") {
				scene.setVisibleByOtid(otid, true);
			}

			// update text in bubble
			for (int i = startingTimeForEachLine.size() - 1; i >= 0; i--) {
				// go backwards thru the loop to find text that should be valid
				if (progress > startingTimeForEachLine.get(i)) {
					scene.setStateOfPopup(true, returnObjects.get(i), null);
					break;
				}
			}

			// if theres an associated animation, then use it
			if (this.atid != "" && nonIncrementing == NonIncrementing.False) {
				int numberOfFramesSoFar = (int) (progress * numberOfFramesTotal);
				int frame = numberOfFramesSoFar
						% scene.getNumberOfFramesByAtid(atid);

				// all frames of the animation should be shown
				this.scene.setCurrentFrameByOtid(otid, frame);
			}
		}
	}

	Rect translateRect(RectF r)
	{

		Rect rectInPixels = new Rect((int) (r.getLeft() * scene.getSceneGuiWidth()),
				(int) (r.getTop() * scene.getSceneGuiHeight()),
				(int) (r.getWidth() * scene.getSceneGuiWidth()),
				(int) (r.getHeight() * scene.getSceneGuiHeight()));
		return rectInPixels;
	}
	public boolean onComplete() {

		if (this.otid != "") {
			scene.setCurrentAnimationByAtid(atidOfWhatItWasBeforeTalking);
			if(this.nonIncrementing != NonIncrementing.True)
			{
				scene.setCurrentFrameByOtid(otid, frameOfWhatItWasBeforeTalking);
			}
		}

		scene.setStateOfPopup(false, null, null);
		return false;
	}

	double getSecondsForLine(String speech) {
		double popupDisplayDuration = master.getPopupDisplayDuration();
		int numberOfSpaces = 0;
		for(int i=0;i<speech.length();i++)
		{
			numberOfSpaces+=(speech.charAt(i)==' ')?1:0;
		}
		// int delay = how;
		// int duration = (speech.length() * (2 + delay)) * 40;
		return  popupDisplayDuration+numberOfSpaces*0;
	}

 
	public void setNonIncrementing(NonIncrementing nonIncrementing) {
		this.nonIncrementing = nonIncrementing;
	}

	public IScenePresenterFromTalkPerformer getscene() {
		return scene;
	}

	public void setScene(IScenePresenterFromTalkPerformer scene) {
		this.scene = scene;
	}

	public void setMaster(IMasterPresenterFromTalkPerformer sayActionTest) {
		this.master = sayActionTest;
		
	}
}
