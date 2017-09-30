package com.github.a2g.core.action.performer;

import java.util.ArrayList;
import java.util.List;

import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.primitive.Point;

public class WalkMultiPerformer {
	//private static final  Logger MULTIWALKER = Logger.getLogger(LogNames.MULTIWALKER);

	double startX;
	double startY;
	double endX;
	double endY;
	double startScale;
	double endScale;
	private ArrayList<WalkSinglePerformer> singleWalks;
	private Double[] progressPercentageForStartOfEachSingleWalk;

	private IScenePresenterFromActions scene;  

	private short ocode; 
	private String otid;
	private boolean isSetToInitialAtEnd;
	private boolean isCancelNeededDueToGateOrNoGoZone; 
	private boolean isToUseSwitchForChildren;

	public enum NonIncrementing {
		True, False, FromAPI
	}

	public WalkMultiPerformer(short ocode, boolean isToUseSwitchForChildren) {
		this.endX = Double.NaN;
		this.endY = Double.NaN;
		this.startX = Double.NaN;
		this.startY = Double.NaN;
		this.startScale = 1.0;
		this.endScale = 1.0;
		this.isCancelNeededDueToGateOrNoGoZone = false;
		this.isToUseSwitchForChildren = isToUseSwitchForChildren;
		this.ocode = ocode; 
		this.otid = "";
		this.singleWalks = new ArrayList<WalkSinglePerformer>();
		this.isSetToInitialAtEnd = false;
		// this is used to tell whether findPath returned null, and we didn't initalize
		this.progressPercentageForStartOfEachSingleWalk = null;
	}


	public void setEndX(double endX) {
		this.endX = endX;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}



	public double getRunningDuration() 
	{
		otid =  scene.getOtidByCode(ocode);

		startX = scene.getBaseMiddleXByOtid(otid);
		startY = scene.getBaseMiddleYByOtid(otid); 

		if (endX == Double.NaN)
			endX = startX;
		if (endY == Double.NaN)
			endY = startY;
		List<Point> list = scene.findPath(new Point(startX,startY), new Point(endX,endY));
		if(list== null)
			return 0;
		progressPercentageForStartOfEachSingleWalk =  new Double[list.size()];
		progressPercentageForStartOfEachSingleWalk[0]=0.0;//first startin percentage is zero. 
		Point startPoint = list.get(0);
		double totalDuration = 0;
		for (int i =1;i<list.size();i++) {
			Point endPoint = list.get(i);

			MovePerformer m = new MovePerformer(ocode);
			WalkPerformer w = new WalkPerformer(ocode);
			SwitchPerformer sw = isToUseSwitchForChildren? new SwitchPerformer(ocode) : null;
			ScalePerformer sc = new ScalePerformer(ocode);
			sc.setStartScaleForScaler(startScale);
			sc.setStartScaleForScaler(endScale);
			WalkSinglePerformer k = new WalkSinglePerformer( m,w,sw,sc);
			k.setScene(scene);
			k.setStartX(startPoint.getX());
			k.setStartY(startPoint.getY());
			k.setEndX(endPoint.getX());
			k.setEndY(endPoint.getY());
			k.setStartScale(startScale);
			k.setEndScale(endScale);
			k.setToInitialAtEnd(false);
			double duration = k.getRunningDuration();
			totalDuration+=duration;
			if(i==list.size()-1)
			{
				k.setToInitialAtEnd(isSetToInitialAtEnd);
			}
			singleWalks.add(k);
			progressPercentageForStartOfEachSingleWalk[i] = duration;
			startPoint = endPoint;
		}

		//normalize the lengths so we know when, in the progress, to jump in.
		double rollingStartingTime = 0;
		for(int i=0; i<progressPercentageForStartOfEachSingleWalk.length;i++)
		{
			double duration = progressPercentageForStartOfEachSingleWalk[i];
			rollingStartingTime+=duration;
			progressPercentageForStartOfEachSingleWalk[i] = rollingStartingTime/totalDuration;
		}

		return totalDuration;
	}

	public void onUpdateGameAction(double progress) {

		if(progressPercentageForStartOfEachSingleWalk!=null)
		{
			for (int i = singleWalks.size() - 1; i >= 0; i--) {
				// go backwards thru the loop to find text that should be valid
				double startPercentageForWalk = progressPercentageForStartOfEachSingleWalk[i];
				double endPercentageForWalk = progressPercentageForStartOfEachSingleWalk[i+1];

				if (progress > startPercentageForWalk) {
					double howFarInsideThisSegmentAreWe = progress -startPercentageForWalk;
					double fullSegmentLength = endPercentageForWalk-startPercentageForWalk;
					double percent = howFarInsideThisSegmentAreWe/fullSegmentLength;
					singleWalks.get(i).onUpdateGameAction(percent);
					if(singleWalks.get(i).isCancelNeededDueToGateOrNoGoZone())
						isCancelNeededDueToGateOrNoGoZone = true;
					singleWalks.get(i).mover.getStartPtForMover();
					singleWalks.get(i).mover.getEndPtForMover();
					//MULTIWALKER.log( Level.ALL, "{0} walk to-mid  from {1} {2} to {3} {4} {5}", new Object[]{i, from.getX(), from.getY(),to.getX(), to.getY(), percent} );
					break;
				}
			}
		}

	}

	public boolean onCompleteActionAndCheckForGateExit() {
		
		boolean isExited = false;
		if(progressPercentageForStartOfEachSingleWalk!=null)
		{
			isExited = singleWalks.get(singleWalks.size()-1).onCompleteActionAndCheckForGateExit();
		}
		return isExited;
	}

	public void setScene(IScenePresenterFromActions scene) {
		this.scene = scene;
	}

	public void setToInitialAtEnd(boolean b) {
		isSetToInitialAtEnd = b;
	}

	public boolean isCancelNeededDueToGateOrNoGoZone() {
		return isCancelNeededDueToGateOrNoGoZone;
	}


	public void setEndScale(double endScale) {
		this.endScale = endScale;

	}

	public void setStartScale(double startScale) {
		this.startScale = startScale;
	}
}
