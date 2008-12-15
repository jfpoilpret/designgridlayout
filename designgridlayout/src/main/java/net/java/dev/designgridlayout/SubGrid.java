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

import java.awt.Color;
import java.awt.Container;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

final class SubGrid implements ISubGrid
{
	SubGrid(SubGrid previous, Container parent, JLabel label, int gridspan)
	{
		_previous = previous;
		_parent = parent;
		_label = label;
		_gridspan = (gridspan <= 0 ? 0 : gridspan);
		if (_label != null)
		{
			_parent.add(_label);
			_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
	}

	void spanRow()
	{
		if (_previous == null)
		{
			// Bad use of DesignGridLayout, use a maker component
			add(createMarker(1, NO_PREVIOUS_SUBGRID), 1);
		}
		else
		{
			// Find the RowItem, in the above subgrid, matching the current
			// column position.
			RowItem previous = _previous.findItem(_column);
			if (previous == null)
			{
				// Bad use of DesignGridLayout, use a maker component
				add(createMarker(1, NO_MATCHING_COMPONENT), 1);
			}
			else
			{
				// It is impossible to say now if this call can succeed, this can
				// only be checked later (checkSpanRows()), for now we consider
				// that it works
				_spanRow = true;
				_items.add(new RowItem(previous));
			}
		}
	}
	
	void add(JComponent child, int span)
	{
		RowItem item = new RowItem(span, child);
		_column += span;
		_parent.add(child);
		_items.add(item);
	}
	
	void checkSpanRows()
	{
		// If there is no remaining spanRow() call to check, then we're done
		if (!_spanRow)
		{
			return;
		}

		// Check that the number of columns in this sub-grid matches the 
		// previous sub-grid. If not, then we have to replace all spanning
		// RowItems with marker components
		if (_previous.gridColumns() != gridColumns())
		{
			for (RowItem item: _items)
			{
				if (!item.isRealComponent())
				{
					JComponent marker = createMarker(item.span(), UNMATCHED_COLUMNS_SUBGRIDS);
					item.replace(marker);
					_parent.add(marker);
				}
			}
		}
	}
	
	List<RowSpanItem> initRowSpanItems(int rowIndex)
	{
		List<RowSpanItem> allSpanItems =  new ArrayList<RowSpanItem>();
		for (RowItem item: _items)
		{
			if (item.isSpanComponent())
			{
				allSpanItems.add(
					new RowSpanItem(rowIndex, item.component(), item.rowSpan()));
			}
		}
		return allSpanItems;
	}

	public int gridspan()
	{
		return _gridspan;
	}
	
	public void gridspan(int span)
	{
		if (_previous != null && _spanRow)
		{
			_gridspan = _previous.gridspan();
		}
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
			int width = extractor.value(item);

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
				if (item.isRealComponent())
				{
					JComponent component = item.component();
					actualHeight = Math.max(0, helper.setSizeLocation(
						component, x, width, height, baseline));
				}
				x += width + hgap;
			}
		}
		return actualHeight;
	}

	public List<RowItem> items()
	{
		return _items;
	}
	
	private JComponent createMarker(int span, String tooltip)
	{
		JLabel marker = new JLabel(MARKER_LABEL);
		marker.setBackground(Color.RED);
		marker.setToolTipText(tooltip);
		return marker;
	}
	
	private RowItem findItem(int column)
	{
		int i = 0;
		for (RowItem item: _items)
		{
			if (i == column)
			{
				return item;
			}
			i += item.span();
			if (i > column)
			{
				return null;
			}
		}
		return null;
	}
	
	//#### Can't we do without this terrible class (just to use ComponentHelper.hgap())?
	private class AllItemsList extends AbstractList<RowItem>
	{
		@Override public RowItem get(int index)
		{
			if (_label != null)
			{
				if (index == 0)
				{
					return new RowItem(1, _label);
				}
				else
				{
					return _items.get(index - 1);
				}
			}
			else
			{
				return _items.get(index);
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

	static final private String MARKER_LABEL = "spanRow()"; 
	static final private String NO_PREVIOUS_SUBGRID = 
		"spanRow() cannot work on a grid-row with no grid-row immediately above, " +
		"or with no matching sub-grid (same column position) in the above grid-row";
	static final private String NO_MATCHING_COMPONENT = 
		"spanRow() cannot work when there is no component, on the above grid-row, " +
		"with a matching column location";
	static final private String UNMATCHED_COLUMNS_SUBGRIDS = 
		"spanRow() cannot work on a sub-grid where the number of columns is different " +
		"from the above sub-grid";
	final private List<RowItem> _items = new ArrayList<RowItem>();
	final private AllItemsList _components = new AllItemsList();
	final private SubGrid _previous;
	final private Container _parent;
	final private JLabel _label;
	// 0 means auto span until the right-most edge
	private int _gridspan;
	private boolean _spanRow;
	private int _column = 0;
}
