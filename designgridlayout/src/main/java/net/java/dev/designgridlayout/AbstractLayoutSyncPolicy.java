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
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

abstract class AbstractLayoutSyncPolicy implements ILayoutRowSyncPolicy
{
	public int preferredHeight(List<ILayoutEngine> engines)
	{
		synchronize(engines);
		int maxHeight = 0;
		for (ILayoutEngine engine: engines)
		{
			maxHeight = Math.max(maxHeight, engineHeight(engine));
		}
		return maxHeight;
	}

	static protected int engineHeight(ILayoutEngine engine)
	{
		Insets margins = engine.getMargins();
		int layoutHeight = margins.top + margins.bottom;
		for (AbstractRow row: each(engine.rows()))
		{
			layoutHeight += row.height() + row.extraHeight() + row.vgap();
		}
		return layoutHeight;
	}
	
	public int availableHeight(
		int height, List<ILayoutEngine> engines, ILayoutEngine current)
	{
		return height;
	}
	
	static protected Iterable<AbstractRow> each(final List<AbstractRow> rows)
	{
		return new Iterable<AbstractRow>()
		{
			public Iterator<AbstractRow> iterator()
			{
				return new RowIterator(rows);
			}
		};
	}
	
	static private class RowIterator implements Iterator<AbstractRow>
	{
		public RowIterator(List<AbstractRow> rows)
		{
			_rows = rows;
		}
		
		public AbstractRow next()
		{
			int index = findNext();
			if (index != -1)
			{
				_index++;
				return _rows.get(index);
			}
			else
			{
				throw new NoSuchElementException();
			}
		}
		
		public boolean hasNext()
		{
			return findNext() != -1;
		}
		
		public void remove()
		{
			// Empty operation
		}
		
		private int findNext()
		{
			for (int i = _index; i < _rows.size(); i++)
			{
				AbstractRow row = _rows.get(i);
				if (!row.isEmpty())
				{
					return i;
				}
			}
			return -1;
		}
		
		private final List<AbstractRow> _rows;
		private int _index = 0;
	}
}
