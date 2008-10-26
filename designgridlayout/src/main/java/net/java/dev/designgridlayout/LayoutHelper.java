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

import java.awt.Dimension;

import javax.swing.JComponent;

import org.jdesktop.layout.Baseline;

final class LayoutHelper
{
	LayoutHelper(HeightGrowPolicy tester, int parentWidth, boolean rtl)
	{
		_tester = tester;
		_parentWidth = parentWidth;
		_rtl = rtl;
	}
	
	void setRowAvailableHeight(int availableHeight)
	{
		_availableHeight = availableHeight;
	}
	
	void setY(int y)
	{
		_y = y;
	}
	
	// Returns the actual extra height used by this component
	int setSizeLocation(JComponent component, int x, int width, 
		int maxHeight, int maxBaseline)
	{
		Dimension d = component.getPreferredSize();
		int usedExtraHeight;
		if (_tester.canGrowHeight(component))
		{
			// Checks how much extra height this component can really use
			usedExtraHeight = 
				_tester.computeExtraHeight(component, _availableHeight - d.height);
			component.setSize(width, d.height + usedExtraHeight);
		}
		else
		{
			usedExtraHeight = 0;
			component.setSize(width, d.height);
		}

		int baseline = Baseline.getBaseline(component);
		int yy = 0;
		if (baseline > 0)
		{
			yy = maxBaseline - baseline;
		}
		else
		{
			yy = (maxHeight - d.height) / 2;
		}
		component.setLocation((_rtl ? _parentWidth - x - width : x), _y + yy);
		return usedExtraHeight;
	}
	
	private final HeightGrowPolicy _tester;
	private final int _parentWidth;
	private final boolean _rtl;
	private int _availableHeight;
	private int _y;
}
