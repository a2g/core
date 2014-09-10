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

package com.github.a2g.core.interfaces;


public interface OnFillLoadListAPI
{
	void addEssential(LoadAPI blah);
	void setScenePixelSize(int width, int height);
	void kickStartLoading();
	void setInventoryPixelSize(int width, int height);
	SceneAPI getSceneByName(String string);
	void setValue(Object string, int value);
	void clearAllLoadedLoads();
	void setContinueAfterLoad(boolean b);
}
