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

import java.util.List;

import javax.swing.JComponent;

final class LayoutHelper
{
	LayoutHelper(HeightGrowPolicy tester, int parentWidth, boolean rtl)
	{
		this(tester, parentWidth, rtl, null);
	}
	
	LayoutHelper(
		HeightGrowPolicy tester, int parentWidth, boolean rtl, List<AbstractRow> rows)
	{
		_tester = tester;
		_parentWidth = parentWidth;
		_rtl = rtl;
		_rows = rows;
	}
	
	void setRowAvailableHeight(int availableHeight)
	{
		_availableHeight = availableHeight;
	}
	
	void setY(int y)
	{
		_y = y;
	}
	
	// Called for mutiple row-span components
	void setHeight(int rowIndex, JComponent component, int spannedRows)
	{
		// Calculate the total available height
		int availableHeight = 0;
		for (int i = 0; i < spannedRows; i++)
		{
			AbstractRow row = _rows.get(rowIndex + i);
			availableHeight += row.actualHeight();
			if (i + 1 < spannedRows)
			{
				availableHeight += row.vgap();
			}
		}
		// Keep smart vertical resize behavior in any case
		int height = component.getPreferredSize().height;
		int usedExtraHeight = 0;
		if (MarkerHelper.isMarker(component))
		{
			// spanRow() error markers are special, they must take all available height
			usedExtraHeight = availableHeight - height;
		}
		else if (_tester.canGrowHeight(component))
		{
			// Checks how much extra height this component can really use
			usedExtraHeight = 
				_tester.computeExtraHeight(component, availableHeight - height);
		}
		component.setSize(component.getWidth(), height + usedExtraHeight);
	}
	
	// Returns the actual extra height used by this component
	int setSizeLocation(JComponent component, int x, int width, 
		int maxHeight, int maxBaseline)
	{
		int height = component.getPreferredSize().height;
		int usedExtraHeight = 0;
		if (_tester.canGrowHeight(component))
		{
			// Checks how much extra height this component can really use
			usedExtraHeight = 
				_tester.computeExtraHeight(component, _availableHeight - height);
		}
		component.setSize(width, height + usedExtraHeight);

		int baseline = BaselineHelper.getBaseline(component);
		int yy = 0;
		if (baseline > 0)
		{
			yy = maxBaseline - baseline;
		}
		else
		{
			yy = (maxHeight - height) / 2;
		}
		component.setLocation((_rtl ? _parentWidth - x - width : x), _y + yy);
		return usedExtraHeight;
	}
	
	private final HeightGrowPolicy _tester;
	private final int _parentWidth;
	private final boolean _rtl;
	private final List<AbstractRow> _rows;
	private int _availableHeight;
	private int _y;
}
