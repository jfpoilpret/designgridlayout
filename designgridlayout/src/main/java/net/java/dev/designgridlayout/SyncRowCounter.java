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

final class SyncRowCounter
{
	SyncRowCounter(List<AbstractRow> rows)
	{
		_rows = rows;
	}

	int getCurrentHeight()
	{
		return getRowHeight(_start);
	}
	
	int computeFittingRowsHeight(int height)
	{
		int usedHeight = 0;
		_weight = 0.0;
		for (_end = _start; _end < _rows.size(); _end++)
		{
			int rowHeight = getRowHeight(_end);
			if (usedHeight + rowHeight > height)
			{
				break;
			}
			usedHeight += rowHeight;
			_weight += _rows.get(_end).growWeight();
		}
		return usedHeight;
	}
	
	boolean canFittingRowsGrow()
	{
		return _weight > 0.0;
	}
	
	int getFirstNonFittingRowHeight()
	{
		return getRowHeight(_end);
	}
	
	boolean canFirstNonFittingRowGrow()
	{
		if (_end < _rows.size())
		{
			return _rows.get(_end).growWeight() > 0.0;
		}
		else
		{
			return false;
		}
	}
	
	void dispatchHeightToFittingRows(int height)
	{
		// First check that there ares till rows to layout
		if (_start >= _rows.size())
		{
			return;
		}

		// Recheck number of rows fitting in height
		int usedHeight = computeFittingRowsHeight(height);
		int diff = height - usedHeight;
		if (diff > 0)
		{
			// Dispatch height in rows _start to _end
			if (_weight > 0.0)
			{
				// Dispatch extra height only to rows with variable height
				for (int i = _start; i < _end; i++)
				{
					AbstractRow row = _rows.get(i);
					int extra = (int) (row.growWeight() / _weight * diff);
					//TODO
				}
			}
			else
			{
				// Dispatch extra height equally between all rows
				int extra = diff / (_end - _start);
				int fudge = diff % (_end - _start);
				for (int i = _start; i < _end; i++)
				{
					// TODO
				}
			}
		}
		
		// Prepare for next round
		_start = _end;
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
	private double _weight = 0.0;
}
