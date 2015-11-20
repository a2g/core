package com.github.a2g.core.action.performer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.PointF;

public class WalkMultiPerformer {
	private static final  Logger MULTIWALKER = Logger.getLogger(LogNames.MULTIWALKER);
	
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
		List<PointF> list = scene.findPath(new PointF(startX,startY), new PointF(endX,endY));

		progressPercentageForStartOfEachSingleWalk =  new Double[list.size()];
		progressPercentageForStartOfEachSingleWalk[0]=0.0;//first startin percentage is zero. 
		PointF startPoint = list.get(0);
		double totalDuration = 0;
		for (int i =1;i<list.size();i++) {
			PointF endPoint = list.get(i);
			
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

		// update text in bubble
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
				PointF from = singleWalks.get(i).mover.getStartPtForMover();
				PointF to = singleWalks.get(i).mover.getEndPtForMover();
				MULTIWALKER.log( Level.ALL, "{0} walk to-mid  from {1} {2} to {3} {4} {5}", new Object[]{i, from.getX(), from.getY(),to.getX(), to.getY(), percent} );
				break;
			}
			else
			{
			//walkSingles.get(i).onCompleteGameAction();
			}
		}
 
	}

	public boolean onCompleteActionAndCheckForGateExit() {
		boolean isExited = singleWalks.get(singleWalks.size()-1).onCompleteActionAndCheckForGateExit();
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
