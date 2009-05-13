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

package net.java.dev.designgridlayout.internal.sync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.java.dev.designgridlayout.internal.engine.ILayoutEngine;
import net.java.dev.designgridlayout.internal.row.AbstractRow;
import net.java.dev.designgridlayout.internal.util.LayoutEngineProxy;

import static net.java.dev.designgridlayout.internal.util.RowHelper.each;

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
public class SimpleLayoutSyncPolicy extends AbstractLayoutSyncPolicy
{
	// CSOFF: EmptyBlockCheck
	@Override protected void synchronize(
		List<ILayoutEngine> engines, boolean respectHeight)
	{
		List<SyncRowCounter> counters = initCounters(engines, respectHeight);
		// Loop until all rows of all layouts have been processed
		while (alignCurrentRowsBaselines(counters))
		{
		}

		//FIXME problem with variable height layouts, maybe useless actually...
//		if (!respectHeight)
		{
			makeBaselinesDistanceConsistent(engines, respectHeight);
		}
	}
	// CSON: EmptyBlockCheck
	
	@Override public int availableHeight(
		int height, List<ILayoutEngine> engines, ILayoutEngine current)
	{
		// Limit the height given to the engine according to its preferred size
		//FIXME should normally NOT give the entire difference but should also
		// account for all other engines!
		int globalPreferredHeight = 0;
		for (ILayoutEngine engine: engines)
		{
			globalPreferredHeight = Math.max(
				globalPreferredHeight, engine.getPreferredSize().height);
		}
		int currentPreferredHeight = current.getPreferredSize().height;
		return height + currentPreferredHeight - globalPreferredHeight;
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
	
	static private boolean alignCurrentRowsBaselines(List<SyncRowCounter> counters)
	{
		// Find max height of current row across all layouts
		int maxHeight = 0;
		int maxBaseline = 0;
		for (SyncRowCounter counter: counters)
		{
			maxHeight = Math.max(maxHeight, counter.height());
			maxBaseline = Math.max(maxBaseline, counter.baseline());
		}
		// Now align all rows on their baselines
		for (SyncRowCounter counter: counters)
		{
			counter.align(maxBaseline, maxHeight);
			counter.next();
		}
		return (maxHeight != 0);
	}
	
	static final private class SyncRowCounter
	{
		SyncRowCounter(List<AbstractRow> rows, boolean respectHeight)
		{
			_iterator = each(rows).iterator();
			_respectHeight = respectHeight;
			next();
		}
		
		int baseline()
		{
			return (_current != null ? _current.baseline() : 0);
		}
		
		int height()
		{
			if (_current != null)
			{
				int height = _respectHeight ? _current.actualHeight() : _current.height();
				return height + _current.vgap();
			}
			else
			{
				return 0;
			}
		}
		
		void align(int baseline, int height)
		{
			if (_current != null)
			{
				_current.baseline(baseline);
				int rowHeight = _respectHeight ? _current.actualHeight() : _current.height();
				// Account for changes made to natural baseline of the row!
//				rowHeight -= baseline - _current.naturalBaseline();
				_current.extraHeight(height - rowHeight - _current.vgap());
			}
		}

		void next()
		{
			_current = (_iterator.hasNext() ? _iterator.next() : null);
		}
		
		private final Iterator<AbstractRow> _iterator;
		private final boolean _respectHeight;
		private AbstractRow _current;
	}
}
