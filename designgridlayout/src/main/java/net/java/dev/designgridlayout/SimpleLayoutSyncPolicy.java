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

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

// This very simple policy for synchronizing rows of various layouts works as 
// follows:
// - align all rows of all layouts one to one (1-1, 2-2...) independently of
// the height of each row
//
// This policy is simple because it does not take into account:
// - the fact that a row in a layout is higher than the matching row in other
// layouts and thus could map to several rows of the other layouts
// One result of applying this policy is to have a largely increased preferred
// height of all synchronized layouts.
// One advantage of this policy is that it is simple and thus more performant
// than any other -more beautiful but ore complex- policy.
class SimpleLayoutSyncPolicy extends AbstractLayoutSyncPolicy
{
	public int preferredHeight(List<ILayoutEngine> engines)
	{
		synchronize(engines, false);
		int maxHeight = 0;
		for (ILayoutEngine engine: engines)
		{
			Insets margins = engine.getMargins();
			int layoutHeight = margins.top + margins.bottom;
			for (AbstractRow row: engine.rows())
			{
				layoutHeight += row.height() + row.extraHeight() + row.vgap();
			}
			maxHeight = Math.max(maxHeight, layoutHeight);
		}
		return maxHeight;
	}
	
	public int availableHeight(
		int height, List<ILayoutEngine> engines, ILayoutEngine current)
	{
		// First calculate the max height
		return height;
	}
	
	public void synchronize(List<ILayoutEngine> engines)
	{
		synchronize(engines, true);
	}
	
	static private void synchronize(List<ILayoutEngine> engines, boolean respectHeight)
	{
		List<SyncRowCounter> counters = initCounters(engines, respectHeight);
		// Loop until all rows of all layouts have been processed
		while (computeMaxHeightAndAlignBaselines(counters))
		{
		}
	}
	
	static private List<SyncRowCounter> initCounters(
		List<ILayoutEngine> engines, boolean respectHeight)
	{
		// Initialize row counters (one per layout)
		List<SyncRowCounter> counters = new ArrayList<SyncRowCounter>();
		for (ILayoutEngine engine: engines)
		{
			counters.add(new SyncRowCounter(engine.rows(), respectHeight));
		}
		return counters;
	}
	
	static private boolean computeMaxHeightAndAlignBaselines(List<SyncRowCounter> counters)
	{
		// Find max height of current row across all layouts
		int maxHeight = 0;
		int maxBaseline = 0;
		for (SyncRowCounter counter: counters)
		{
			maxHeight = Math.max(maxHeight, counter.height());
			maxBaseline = Math.max(maxBaseline, counter.baseline());
		}
		for (SyncRowCounter counter: counters)
		{
			counter.baseline(maxBaseline);
			counter.height(maxHeight);
			counter.next();
		}
		return (maxHeight != 0);
	}
	
	static final private class SyncRowCounter
	{
		SyncRowCounter(List<AbstractRow> rows, boolean respectHeight)
		{
			_rows = rows;
			_respectHeight = respectHeight;
		}
		
		int baseline()
		{
			return (_index < _rows.size() ? _rows.get(_index).baseline() : 0);
		}
		
		int height()
		{
			if (_index < _rows.size())
			{
				AbstractRow row = _rows.get(_index);
				int rowHeight = _respectHeight ? row.actualHeight() : row.height();
				return rowHeight + row.vgap();
			}
			else
			{
				return 0;
			}
		}
		
		void baseline(int baseline)
		{
			if (_index < _rows.size())
			{
				_rows.get(_index).baseline(baseline);
			}
		}
		
		void height(int height)
		{
			if (_index < _rows.size())
			{
				AbstractRow row = _rows.get(_index);
				int rowHeight = _respectHeight ? row.actualHeight() : row.height();
				row.extraHeight(height - rowHeight - row.vgap());
			}
		}

		void next()
		{
			_index++;
		}
		
		private final List<AbstractRow> _rows;
		private final boolean _respectHeight;
		private int _index = 0;
	}
}
