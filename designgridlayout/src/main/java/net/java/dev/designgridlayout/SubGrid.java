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

import java.awt.Container;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.layout.LayoutStyle;

final class SubGrid implements ISubGrid
{
	SubGrid(Container parent, JLabel label, int gridspan)
	{
		_parent = parent;
		_label = label;
		_gridspan = (gridspan <= 0 ? 0 : gridspan);
		if (_label != null)
		{
			_parent.add(_label);
			_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
	}

	void add(JComponent child, int span)
	{
		RowItem item = new RowItem(span, child);
		_parent.add(child);
		_items.add(item);
	}
	
	public int gridspan()
	{
		return _gridspan;
	}
	
	public void gridspan(int span)
	{
		if (_gridspan == 0)
		{
			_gridspan = span;
		}
	}

	public int labelWidth()
	{
		return (_label != null ? _label.getPreferredSize().width : 0);
	}

	public int gridColumns()
	{
		int columns = 0;
		for (RowItem item: _items)
		{
			columns += item.span();
		}
		return columns;
	}

	public int maxColumnWidth(int maxColumns, IExtractor extractor)
	{
		int maxWidth = 0;
		// Note columns (sum item spans), not the count of components
		int columns = gridColumns();
		float divisions = (float) columns / (float) maxColumns;

		for (RowItem item: _items)
		{
			int width = extractor.value(item.component());

			// Ignores remainder (fudge), which is incorrect if remainder
			// is greater than horizontal gap (hopefully rarely)
			width = (int) ((width * divisions) / item.span());
			maxWidth = Math.max(maxWidth, width);
		}
		return maxWidth;
	}

	public int hgap()
	{
		return ComponentHelper.hgap(_components, _parent);
	}

	public int layoutRow(LayoutHelper helper, int left, int height, int baseline, 
		int hgap, int rowWidth, int labelWidth)
	{
		int x = left;
		int actualHeight = 0;
		// Account for label column
		if (labelWidth > 0)
		{
			if (_label != null)
			{
				actualHeight = Math.max(0, helper.setSizeLocation(
					_label, x, labelWidth, height, baseline));
			}
			x += labelWidth + hgap;
		}

		int columns = gridColumns();
		if (columns > 0)
		{
			// pre-subtract gaps
			int gridWidth = rowWidth - ((columns - 1) * hgap);
			int columnWidth = gridWidth / columns;

			// fudge is whatever pixels are left over
			int fudge = gridWidth % columns;

			Iterator<RowItem> i = _items.iterator();
			while (i.hasNext())
			{
				RowItem item = i.next();
				int span = item.span();
				int width = columnWidth * span + ((span - 1) * hgap);
				// Apply the fudge to the last component/column
				if (!i.hasNext())
				{
					width += fudge;
				}
				JComponent component = item.component();
				actualHeight = Math.max(0, helper.setSizeLocation(
					component, x, width, height, baseline));
				x += width + hgap;
			}
		}
		return actualHeight;
	}

	public List<RowItem> items()
	{
		return _items;
	}
	
	private class ComponentsList extends AbstractList<JComponent>
	{
		@Override public JComponent get(int index)
		{
			if (_label != null)
			{
				if (index == 0)
				{
					return _label;
				}
				else
				{
					return _items.get(index - 1).component();
				}
			}
			else
			{
				return _items.get(index).component();
			}
		}

		@Override public int size()
		{
			int size = _items.size();
			if (_label != null)
			{
				size++;
			}
			return size;
		}
	}

	final private List<RowItem> _items = new ArrayList<RowItem>();
	final private ComponentsList _components = new ComponentsList();
	final private Container _parent;
	final private JLabel _label;
	// 0 means auto span until the right-most edge
	private int _gridspan;
}
