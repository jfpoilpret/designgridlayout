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

import java.util.ArrayList;
import java.util.List;

// This simple policy for synchronizing rows of various layouts works as follows:
// - search of the highest first row among all layouts
// - for other layouts, all rows fitting in max height get aligned together
//	(recursively) and they are resized to fit max height
// - process loops with the next -unprocessed- rows on all layouts
//
// This policy is simple because it does not take into account:
// - the fact that some rows have variable height (they could be favored to grow)
// - the difference of the height of rows fitting in max height, and max height
// itself (if the difference is big, it might be preferrable to extend the row
// with max height)
// One result of applying this policy is to have a largely increased preferred
// height of all synchronized layouts.
// One advantage of this policy is that it is simple and thus more performant
// than any other -more beautiful but ore complex- policy.
class SimpleLayoutSyncPolicy implements ILayoutRowSyncPolicy
{
	public void synchronize(List<ILayoutEngine> engines)
	{
		initCounters(engines);
		synchronizeRows(_counters);
	}
	
	private void synchronizeRows(List<SyncRowCounter> counters)
	{
		// Loop until all rows of all layouts have been processed
		while (true)
		{
			// Find max height of current row across all layouts
			int maxHeight = computeMaxHeightAndAlignBaselines(counters);
			if (maxHeight == 0)
			{
				// No more rows to align in layouts
				return;
			}

			// Now dispatch this maximum height among several rows across 
			// all layouts
			List<SyncRowCounter> remaining = new ArrayList<SyncRowCounter>();
			for (SyncRowCounter counter: counters)
			{
				switch (counter.countFittingRows(maxHeight))
				{
					case 0:
					break;
					
					case 1:
					counter.height(maxHeight);
					break;
					
					default:
					remaining.add(counter);
					break;
				}
				counter.next();
			}
			if (!remaining.isEmpty())
			{
				synchronizeRows(remaining);
			}
		}
	}
	
	private void initCounters(List<ILayoutEngine> engines)
	{
		// Initialize row counters (one per layout)
		_counters = new ArrayList<SyncRowCounter>();
		for (ILayoutEngine engine: engines)
		{
			_counters.add(new SyncRowCounter(engine.rows()));
		}
	}
	
	private int computeMaxHeightAndAlignBaselines(List<SyncRowCounter> counters)
	{
		// Find max height of current row across all layouts
		int maxHeight = 0;
		int maxBaseline = 0;
		for (SyncRowCounter counter: counters)
		{
			maxHeight = Math.max(maxHeight, counter.getCurrentHeight());
			maxBaseline = Math.max(maxBaseline, counter.baseline());
		}
		for (SyncRowCounter counter: counters)
		{
			counter.baseline(maxBaseline);
		}
		return maxHeight;
	}
	
	static final private class SyncRowCounter
	{
		SyncRowCounter(List<AbstractRow> rows)
		{
			_rows = rows;
		}
		
		int baseline()
		{
			if (_start < _rows.size())
			{
				return _rows.get(_start).baseline();
			}
			else
			{
				return 0;
			}
		}
		
		void baseline(int baseline)
		{
			if (_start < _rows.size())
			{
				_rows.get(_start).baseline(baseline);
			}
		}
		
		void height(int height)
		{
			if (_start < _rows.size())
			{
				AbstractRow row = _rows.get(_start);
				row.extraHeight(height - row.height() - row.vgap());
			}
		}

		int getCurrentHeight()
		{
			return getRowHeight(_start);
		}
		
		int countFittingRows(int height)
		{
			int usedHeight = 0;
			for (_end = _start; _end < _rows.size(); _end++)
			{
				int rowHeight = getRowHeight(_end);
				if (usedHeight + rowHeight > height)
				{
					break;
				}
				usedHeight += rowHeight;
			}
			return _end - _start;
		}
		
		void next()
		{
			_start++;
		}
		
		private int getRowHeight(int index)
		{
			if (index < _rows.size())
			{
				AbstractRow row = _rows.get(index);
				return row.height() + row.vgap();
			}
			else
			{
				return 0;
			}
		}
		
		private final List<AbstractRow> _rows;
		private int _start = 0;
		private int _end = 0;
	}

	private List<SyncRowCounter> _counters; 
}
