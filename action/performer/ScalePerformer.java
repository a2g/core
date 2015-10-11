/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.action.performer;

import com.github.a2g.core.interfaces.internal.IScenePresenterFromScalePerformer;
import com.github.a2g.core.interfaces.performer.IScalePerformer;


public class ScalePerformer implements IScalePerformer
{	
	private IScenePresenterFromScalePerformer scene;

	private short ocode;// set in constructor

	private double endScale;// set via setters
	private double startScale;// set via setters
	
	public ScalePerformer(short ocode) 
	{
		this.ocode = ocode;
		this.endScale = 1.0;
		this.startScale = 1.0;
	}

	@Override
	public void setStartScaleForScaler(double startScale) {
		this.startScale = startScale;
	}

	@Override
	public void setEndScaleForScaler(double endScale) {
		this.endScale = endScale;
	}

	@Override
	public void runForScaler() {
		
	}

	
	@Override
	public void onUpdateForScaler(double progress) {
		String otid = scene.getOtidByCode(ocode);

		scene.setScaleOnCurrentFrameByOtid(otid, progress*endScale + (1.0-progress)*startScale);
	}

	@Override
	public void onCompleteForScaler() {
		String otid = scene.getOtidByCode(ocode);

		scene.setScaleOnCurrentFrameByOtid(otid, endScale);
	}

	@Override
	public void setSceneForScaler(IScenePresenterFromScalePerformer scene) {
		this.scene = scene;
	}



}
