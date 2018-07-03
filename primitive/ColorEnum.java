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

package com.github.a2g.core.primitive;

public enum ColorEnum {
	// the names below must be html named constants
	// get them from here: https://en.wikipedia.org/wiki/Web_colors
	Black(0, 0, 0),
	Gray(128, 128, 128),
	Navy(0, 0, 128),
	Blue(0, 0, 255),
	Lime(0, 255, 0),
	Teal(0, 128, 128),
	Aqua(0, 255, 255),
	Maroon(128, 0, 0),
	Red(255, 0, 0),
	Purple(128, 0, 128),
	Fuchsia(255, 0, 255),
	Olive(128, 128, 0),
	Yellow(255, 255, 0)	,
	Silver(192, 192, 192),
	White(255, 255, 255), 
	MediumVioletRed(199,  21, 133),
	PaleVioletRed(219, 112, 147),
	SteelBlue(70, 130, 180)
	;

	public final int r;
	public final int g;
	public final int b;

	ColorEnum(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
};
