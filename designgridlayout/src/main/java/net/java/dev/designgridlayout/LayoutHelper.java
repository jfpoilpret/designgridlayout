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
	
	void setRowExtraHeight(int extraHeight)
	{
		_extraHeight = extraHeight;
	}
	
	// CSOFF: ParameterAssignment
	void setSizeLocation(JComponent component, int x, int y, int width, 
		int maxHeight, int maxBaseline)
	{
		Dimension d = component.getPreferredSize();
		if (_tester.canGrowHeight(component))
		{
			component.setSize(width, maxHeight + _extraHeight);
		}
		else
		{
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
		if (_rtl)
		{
			x = _parentWidth - x - width;
		}
		component.setLocation(x, y + yy);
	}
	// CSON: ParameterAssignment
	
	private final HeightGrowPolicy _tester;
	private final int _parentWidth;
	private final boolean _rtl;
	private int _extraHeight;
}
