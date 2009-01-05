//  Copyright 2009 Jason Aaron Osgood, Jean-Francois Poilpret
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package net.java.dev.designgridlayout;

import java.awt.Component;

// Class used to show an image that is always scaled to fill all the
// available space
// This implementation is over-simplistic and should not be used outside that
// test. It is absolutely not coded with performance in mind.
public class PictureJava6 extends Picture
{
	public java.awt.Component.BaselineResizeBehavior getBaselineResizeBehavior()
	{
		return java.awt.Component.BaselineResizeBehavior.CONSTANT_ASCENT;
	}
}
