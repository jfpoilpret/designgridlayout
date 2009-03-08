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

import static net.java.dev.designgridlayout.RowIterator.each;
import static net.java.dev.designgridlayout.RowIterator.forEachConsecutive;

final class ConsistentBaselineSpacingHelper
{
	private ConsistentBaselineSpacingHelper()
	{
	}
	
	static protected ILayoutEngine findConsistentBaselinesEngine(
		List<ILayoutEngine> engines)
	{
		for (ILayoutEngine engine: engines)
		{
			if (engine.mustForceConsistentBaselinesDistance())
			{
				return engine;
			}
		}
		return null;
	}
	
	static public void fixRowsBaselinesDistance(List<AbstractRow> rows)
	{
		// Calculate extra height needed to make  consistent baseline 
		// spacing for the concerned engine
		List<Integer> extras = computeExtraHeight(rows, false);

		// Finally we can add this additional extra to the engine's rows
		addExtraHeight(rows, extras);
	}
	
	static public void fixEnginesBaselinesDistance(
		List<ILayoutEngine> engines, boolean respectHeight)
	{
		// First check if any engine requires consistent baselines spacing
		ILayoutEngine consistentBaselinesEngine = findConsistentBaselinesEngine(engines);
		if (consistentBaselinesEngine == null)
		{
			return;
		}
		
		// Calculate extra height needed to make  consistent baseline 
		// spacing for the concerned engine
		List<Integer> extras = computeExtraHeight(
			consistentBaselinesEngine.rows(), respectHeight);
		
		// Finally we can add this additional extra to all engines
		for (ILayoutEngine engine: engines)
		{
			addExtraHeight(engine.rows(), extras);
		}
	}
	
	static protected void addExtraHeight(List<AbstractRow> rows, List<Integer> extras)
	{
		int index = 0;
		for (AbstractRow row: each(rows))
		{
			if (index >= extras.size())
			{
				break;
			}
			row.extraHeight(row.extraHeight() + extras.get(index));
			index++;
		}
	}

	static protected List<Integer> computeExtraHeight(
		List<AbstractRow> rows, final boolean respectHeight)
	{
		// Calculate max distances inter-rows (fixed height only)
		ConsistentBaselineCalculator calculator = 
			new ConsistentBaselineCalculator(respectHeight);
		forEachConsecutive(rows, calculator);
		final int relatedBaselineDistance = calculator._relatedBaselineDistance;
		final int unrelatedBaselineDistance = calculator._unrelatedBaselineDistance;
		
		// Now calculate extra height needede to make  consistent baseline 
		// spacing for the concerned engine
		final List<Integer> extras = new ArrayList<Integer>();
		forEachConsecutive(rows, new RowIterator.RowPairWorker()
		{
			public void perform(AbstractRow current, AbstractRow next)
			{
				int extra = 0;
				if (current.growWeight() == 0.0)
				{
					int height = (respectHeight ? current.actualHeight() : current.height());
					height += current.extraHeight();
					extra = (current.hasUnrelatedGap() 
						? unrelatedBaselineDistance : relatedBaselineDistance);
					extra -= height + current.vgap();
					extra += current.baseline() - next.baseline();
				}
				extras.add(extra);
			}
		});
		return extras;
	}
	
	static final private class ConsistentBaselineCalculator 
		implements RowIterator.RowPairWorker
	{
		public ConsistentBaselineCalculator(boolean respectHeight)
		{
			_respectHeight = respectHeight;
		}
		
		public void perform(AbstractRow current, AbstractRow next)
		{
			if (current.growWeight() == 0.0)
			{
				int height = (_respectHeight ? current.actualHeight() : current.height());
				int distance = height + current.extraHeight();
				distance += current.vgap() - current.baseline() + next.baseline();
				if (current.hasUnrelatedGap())
				{
					_unrelatedBaselineDistance = 
						Math.max(_unrelatedBaselineDistance, distance);
				}
				else
				{
					_relatedBaselineDistance = 
						Math.max(_relatedBaselineDistance, distance);
				}
			}
		}
		
		final private boolean _respectHeight;
		private int _relatedBaselineDistance = 0;
		private int _unrelatedBaselineDistance = 0;
	}
}
