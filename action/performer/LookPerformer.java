package com.github.a2g.core.action.performer;

import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromLookPerformer;

public class LookPerformer {
	private double totalDurationInSeconds;
	private IScenePresenterFromLookPerformer scene;
	private String atidOfWhatItWasBeforeLooking;
	private int frameOfWhatItWasBeforeLooking;
	
	private String atid;
	private String otid;

	public LookPerformer(String atid, double secs) {
		this.atid = atid;
		this.otid = "";
		this.totalDurationInSeconds = secs;
	}
 
	public double run() {
		if (atid != "")
		{
			otid = scene.getOtidByAtid(atid);
			if(otid != "") 
			{
				// stash old state
				this.frameOfWhatItWasBeforeLooking = scene.getCurrentFrameByOtid(otid);
				this.atidOfWhatItWasBeforeLooking = scene.getAtidOfCurrentAnimationByOtid(otid);
				
				// set new state
				scene.setCurrentAnimationByAtid(atid);
				//scene.setVisibleByOtid(otid, true);
			}
		}
		return this.totalDurationInSeconds;
	}
	

	public void onUpdate(double progress) {
		scene.setCurrentAnimationByAtid(atid);
	}

	public boolean onComplete() {

		if (this.otid != "") {
			scene.setCurrentAnimationByAtid(atidOfWhatItWasBeforeLooking);
			scene.setCurrentFrameByOtid(otid, frameOfWhatItWasBeforeLooking);
		}

		return false;
	}


	public IScenePresenterFromLookPerformer getscene() {
		return scene;
	}

	public void setScene(IScenePresenterFromLookPerformer scene) {
		this.scene = scene;
	}

}
