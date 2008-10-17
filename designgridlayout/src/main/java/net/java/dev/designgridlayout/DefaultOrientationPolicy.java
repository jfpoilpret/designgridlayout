//  Copyright 2008 Jason Aaron Osgood, Jean-Francois Poilpret
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

import java.awt.ComponentOrientation;
import java.awt.Container;

class DefaultOrientationPolicy implements OrientationPolicy
{
	public DefaultOrientationPolicy(Container parent)
	{
		_parent = parent;
	}
	
	public boolean isRightToLeft()
	{
		if (_rtl != null)
		{
			return _rtl;
		}
		else
		{
			// Check layout orientation
			ComponentOrientation orientation = _parent.getComponentOrientation();
			return orientation.isHorizontal() && !orientation.isLeftToRight();
		}
	}
	
	void setRightToLeft(boolean rtl)
	{
		_rtl = rtl;
	}

	private final Container _parent;
	private Boolean _rtl = null;
}
