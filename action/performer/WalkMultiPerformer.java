package com.github.a2g.core.action.performer;

import java.util.ArrayList;
import java.util.List;

import com.github.a2g.core.interfaces.internal.IMasterPresenterFromTalkPerformer;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromTalkPerformer;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromWalkMultiPerformer;
import com.github.a2g.core.primitive.PointF;

public class WalkMultiPerformer {
	double startX;
	double startY;
	double endX;
	double endY;
	private ArrayList<WalkSinglePerformer> walkSingles;
	private ArrayList<Double> startingTimeForEachLine;
	
	private double totalDurationInSeconds;
	private IScenePresenterFromWalkMultiPerformer scene;  
 
	private short ocode; 
	private String otid; 
	
	public enum NonIncrementing {
		True, False, FromAPI
	}

	public WalkMultiPerformer(short ocode) {
		this.endX = Double.NaN;
		this.endY = Double.NaN;
		this.startX = 0.0;
		this.startY = 0.0;

		this.ocode = ocode; 
		this.otid = "";

		walkSingles = new ArrayList<WalkSinglePerformer>();
		startingTimeForEachLine = new ArrayList<Double>();

		this.totalDurationInSeconds = 0;
	}

 
	public void setEndXForMover(double endX) {
		this.endX = endX;
	}

	public void setEndYForMover(double endY) {
		this.endY = endY;
	}

	 

	public double run() {
 
		otid =  scene.getOtidByCode(ocode);

		startX = scene.getBaseMiddleXByOtid(otid);
		startY = scene.getBaseMiddleYByOtid(otid);
		double screenCoordsPerSecond = scene.getScreenCoordsPerSecondByOtid(otid);

		if (endX == Double.NaN)
			endX = startX;
		if (endY == Double.NaN)
			endY = startY;
		List<PointF> list = scene.findPath(new PointF(startX,startY), new PointF(endX,endY));

		PointF firstPoint = list.get(0);
		double rollingStartingTimeForLine = 0;
		for (int i = 1; i < list.size(); i++) {
			PointF secondPoint = list.get(i);
			WalkSinglePerformer k = new WalkSinglePerformer(ocode);
			k.setEndX(secondPoint.getX());
			k.setEndX(secondPoint.getY());
			k.setToInitialAtEnd(false);
			walkSingles.add(k);

			double dist = Math.hypot(secondPoint.getX()-firstPoint.getX(), secondPoint.getY()-firstPoint.getY());				
			double duration = dist / screenCoordsPerSecond;
			rollingStartingTimeForLine+=duration;
			// set ceilings (for easy calcluation)
			startingTimeForEachLine.add(new Double(duration));

			firstPoint = secondPoint;
		}



		return rollingStartingTimeForLine;
	}

	public void onUpdate(double progress) {

		// update text in bubble
		for (int i = startingTimeForEachLine.size() - 1; i >= 0; i--) {
			// go backwards thru the loop to find text that should be valid
			if (progress > startingTimeForEachLine.get(i)) {
				double segmentProgress = startingTimeForEachLine.get(i)-progress;
				double segmentFullLength = startingTimeForEachLine.get(i+1);
				walkSingles.get(i).onUpdateGameAction(segmentProgress/segmentFullLength);
				break;
			}
			else
			{
			//walkSingles.get(i).onCompleteGameAction();
			}
		}
 
	}

	public boolean onComplete() {
		walkSingles.get(walkSingles.size()-1).onCompleteGameAction();
		 
		return false;
	}
 
	public void setScene(IScenePresenterFromWalkMultiPerformer scene) {
		this.scene = scene;
	}


}
