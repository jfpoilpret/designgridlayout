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
import java.awt.Dimension;
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
		_gridspan = (gridspan < 0 ? 0 : gridspan);
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

	public int maxColumnWidth(int maxColumns)
	{
		int maxWidth = 0;
		// Note columns (sum item spans), not the count of components
		int columns = gridColumns();
		float divisions = (float) columns / (float) maxColumns;

		for (RowItem item: _items)
		{
			JComponent component = item.component();
			Dimension d = component.getPreferredSize();

			// Ignores remainder (fudge), which is incorrect if remainder
			// is greater than horizontal gap (hopefully rarely)
			int width = (int) ((d.getWidth() * divisions) / item.span());
			maxWidth = Math.max(maxWidth, width);
		}
		return maxWidth;
	}

	public int hgap()
	{
		LayoutStyle layoutStyle = LayoutStyle.getSharedInstance();

		int hgap = 0;

		// Account for gap between label and first component
		if (_label != null && _items.size() > 0)
		{
			JComponent left = _label;
			JComponent right = _items.get(0).component();
			int gap = layoutStyle.getPreferredGap(
				left, right, LayoutStyle.RELATED, SwingConstants.EAST, _parent);
			hgap = Math.max(hgap, gap);
		}

		for (int nth = 0; nth < (_items.size() - 1); nth++)
		{
			JComponent left = _items.get(nth).component();
			JComponent right = _items.get(nth + 1).component();
			int gap = layoutStyle.getPreferredGap(
				left, right, LayoutStyle.RELATED, SwingConstants.EAST, _parent);
			hgap = Math.max(hgap, gap);
		}
		return hgap;
	}

	// CSOFF: ParameterAssignment
	public int layoutRow(LayoutHelper helper, int x, int y, 
		int height, int baseline, int hgap, int rowWidth, int labelWidth)
	{
		int actualHeight = 0;
		// Account for label column
		if (labelWidth > 0)
		{
			if (_label != null)
			{
				actualHeight = Math.max(0, helper.setSizeLocation(
					_label, x, y, labelWidth, height, baseline));
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
					component, x, y, width, height, baseline));
				x += width + hgap;
			}
		}
		return actualHeight;
	}
	// CSON: ParameterAssignment

	public List<RowItem> items()
	{
		return _items;
	}

	final private List<RowItem> _items = new ArrayList<RowItem>();
	final private Container _parent;
	final private JLabel _label;
	// 0 means auto span until the right-most edge
	private int _gridspan;
}
