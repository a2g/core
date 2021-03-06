package com.github.a2g.core.action.performer;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromTalkPerformer;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromTalkPerformer;
import com.github.a2g.core.primitive.LinesAndMaxWidth.LineAndPos;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.SpeechBubble;
import com.github.a2g.core.primitive.RectF;

public class TalkPerformer {
    public static String SCENE_TALKER = "SCENE_TALKER";// use animation of scene
    public static String SCENE_DIALOG_THEM = "SCENE_DIALOG_US";
    public static String SCENE_DIALOG_US = "SCENE_DIALOG_THEM";

	private ArrayList<SpeechBubble> pages;

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

	   private static final Logger SET_STATE_OF_POPUP = Logger.getLogger(LogNames.SET_STATE_OF_POPUP.toString());

	public enum NonIncrementing {
		True, False, FromAPI
	}

	public TalkPerformer(String atid, String fullSpeech) {
		this.numberOfFramesTotal = 0;
		this.nonIncrementing = NonIncrementing.False;
		this.ocode = -1;
		this.atid = atid;
		this.fullSpeech = fullSpeech;

		this.totalDurationInSeconds = 0;
	}

	public TalkPerformer(short ocode, String fullSpeech) {
		this.numberOfFramesTotal = 0;
		this.nonIncrementing = NonIncrementing.False;
		this.ocode = ocode;
		this.atid = "";
		this.fullSpeech = fullSpeech;
		pages = new ArrayList<SpeechBubble>();

		this.totalDurationInSeconds = 0;
	}

	int getAdjustedNumberOfFrames(String speech, double approxDuration, int animFramesCount,
			double durationOfSingleAnimation) {
		// but if we need an animation, we find out how long it takes
		// to play a single play of the animation to play whilst talking.

		// ... then we find how many times the animation should repeat
		// so that it fills up the totalDuration.
		double numberOfTimesAnimRepeats = approxDuration / durationOfSingleAnimation;

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

		// 1. first split based on authored breaks
		String[] splitByNewline = fullSpeech.split("\n");

		ArrayList<String> speech = new ArrayList<String>();
		for (int i = 0; i < splitByNewline.length; i++) {
			String page = splitByNewline[i];
			speech.add(page);
		}

		// 2. get atid using contingengies
		if (ocode != -1) {
			String otid = scene.getOtidByCode(ocode);
			atid = scene.getAtidOfCurrentAnimationByOtid(otid);
			// odd choice, but shouldn't make a difference - we just need any
			// animation
			// from that object, since api.setSpeechRectangleIndex sets all animations.
		} else if (atid == SCENE_TALKER) {
			atid = scene.getAtidOfSceneTalker();
		} else if (atid == SCENE_DIALOG_US) {
			atid = scene.getAtidOfSceneDialogUs();
		} else if (atid == SCENE_DIALOG_THEM) {
			atid = scene.getAtidOfSceneDialogThem();
		}

		// 3. get speech rectangle using contingencies
		// ..and that's a lot of contingencies
		RectF headRectF = scene.getHeadRectangleUsingContingencies(atid);
		RectI headRectI = translateRect(headRectF);
		PointI downwardTailOffset = scene.getSpeechBubbleOffsetForDownwardTail(atid);
		PointI upwardTailOffset = scene.getSpeechBubbleOffsetForUpwardTail(atid);
		pages = SpeechBubble.calculateWordWrappedPages(new PointI(scene.getSceneGuiWidth(), scene.getSceneGuiHeight()),
				splitByNewline, scene, headRectI, downwardTailOffset, upwardTailOffset); 
		
 
        // get total duration
		totalDurationInSeconds = 0;//sanity
        for (int i = 0; i < pages.size(); i++) {
            SpeechBubble page = pages.get(i);
            for (int j = 0; j < page.lines.lines.size(); j++) {
                LineAndPos line = page.lines.lines.get(j);
                totalDurationInSeconds += getSecondsForLine(line.lineText);
            }
        }
        
		// set ceilings (for easy calculation)
		double rollingStartingTimeForLine = 0;
		for (int i = 0; i < pages.size(); i++) {
			SpeechBubble page = pages.get(i);
			page.startingTime = rollingStartingTimeForLine / totalDurationInSeconds;
			for (int j = 0; j < page.lines.lines.size(); j++) {
			    LineAndPos line = page.lines.lines.get(j);
				line.startingProgress = rollingStartingTimeForLine / totalDurationInSeconds;
				rollingStartingTimeForLine += getSecondsForLine(line.lineText);
			}
			page.atid = atid;
		}

		if (this.nonIncrementing == TalkPerformer.NonIncrementing.FromAPI) {
			this.nonIncrementing = master.isSayNonIncrementing() ? NonIncrementing.True : NonIncrementing.False;
		}

		// only now do
		if (atid == "") {
			// if theres no animation then we just wait for the
			// totalDuration;
			double framesPerSecond = 40;
			numberOfFramesTotal = (int) ((totalDurationInSeconds) * framesPerSecond);
		} else {
			numberOfFramesTotal = getAdjustedNumberOfFrames(speech.get(0), totalDurationInSeconds,
					scene.getNumberOfFramesByAtid(atid), scene.getDurationByAtid(atid));
		}

		if (numberOfFramesTotal < 1) {
			// titleCard.displayTitleCard("error!! id=<" + atid
			// + "> numberOfFramesTotal=" + numberOfFramesTotal);
			assert (false);
		}

		// always make the speaker visible
		if (atid != "") {
			otid = scene.getOtidByAtid(atid);
			if (otid != "") {
				// stash old state
				this.frameOfWhatItWasBeforeTalking = scene.getCurrentFrameByOtid(otid);
				this.atidOfWhatItWasBeforeTalking = scene.getAtidOfCurrentAnimationByOtid(otid);

				// set new state
				scene.setCurrentAnimationByAtid(atid);
				scene.setVisibleByOtid(otid, true);
			}
		}

		boolean visible = true;
		scene.setStateOfPopup(visible, pages.get(0), this);
		return totalDurationInSeconds;
	}

	public void onUpdate(double progress) {

		if (!otid.isEmpty()) {
			if (atid != "" && otid != "") {
				scene.setVisibleByOtid(otid, true);
			}

			// update text in bubble
			for (int i = pages.size() - 1; i >= 0; i--) {
				SpeechBubble page = pages.get(i);
				if (progress > page.startingTime) {
				    SET_STATE_OF_POPUP.fine("SETSTATEOFPOPUP " + (progress)+" " + page.toString());
					scene.setStateOfPopup(true, page, this);
					break;
				}
				/*
				 * for(int j= pages.get(i).lines.lines.size()-1; j>=0;j--){ //
				 * go backwards thru the loop to find text that should be valid
				 * if (progress > pages.get(i).lines.lines.get(j).startingTime)
				 * { scene.setStateOfPopup(true, pages.get(i), null); break; } }
				 */
			}

			// if theres an associated animation, then use it
			if (this.atid != "" && nonIncrementing == NonIncrementing.False) {
				int numberOfFramesSoFar = (int) (progress * numberOfFramesTotal);
				int frame = numberOfFramesSoFar % scene.getNumberOfFramesByAtid(atid);

				// all frames of the animation should be shown
				this.scene.setCurrentFrameByOtid(otid, frame);
			}
		}
	}

	RectI translateRect(RectF r) {

		RectI rectInPixels = new RectI(
				(int) (r.getLeft() * scene.getSceneGuiWidth()),
				(int) (r.getTop() * scene.getSceneGuiHeight()), 
				(int) (r.getWidth() * scene.getSceneGuiWidth()),
				(int) (r.getHeight() * scene.getSceneGuiHeight()));
		return rectInPixels;
	}

	public boolean onComplete() {

		if (this.otid != "") {
			scene.setCurrentAnimationByAtid(atidOfWhatItWasBeforeTalking);
			if (this.nonIncrementing != NonIncrementing.True) {
				scene.setCurrentFrameByOtid(otid, frameOfWhatItWasBeforeTalking);
			}
		}

		scene.setStateOfPopup(false, null, null);
		return false;
	}

	double getSecondsForLine(String speech) {
		double popupDisplayDuration = master.getPopupDisplayDuration();
		int numberOfSpaces = 0;
		for (int i = 0; i < speech.length(); i++) {
			numberOfSpaces += (speech.charAt(i) == ' ') ? 1 : 0;
		}
		// int delay = how;
		// int duration = (speech.length() * (2 + delay)) * 40;
		return popupDisplayDuration + numberOfSpaces * 0;
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
