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

import java.awt.Dimension;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jdesktop.layout.LayoutStyle;

final class GridRow extends AbstractRow implements IGridRow
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent,
	 * int)
	 */
	public IGridRow add(JComponent child, int span)
	{
		RowItem item = new RowItem(span, child);
		_items.add(item);
		parent().add(child);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent[])
	 */
	public IGridRow add(JComponent... children)
	{
		for (JComponent component: children)
		{
			add(component, 1);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(int,
	 * javax.swing.JComponent[])
	 */
	public IGridRow addMulti(int span, JComponent... children)
	{
		return add(new MultiComponent(growPolicy(), orientation(), children), span);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(javax.swing.JComponent[])
	 */
	public IGridRow addMulti(JComponent... children)
	{
		return addMulti(1, children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.IGridRow#empty()
	 */
	public IGridRow empty()
	{
		return empty(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.IGridRow#empty(int)
	 */
	public IGridRow empty(int span)
	{
		return add(new JPanel(), span);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.ISubGridStarter#label(javax.swing.JLabel)
	 */
	public IGridRow label(JLabel label)
	{
		if (label != null)
		{
			_label = label;
			parent().add(_label);
			_label.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISubGridStarter#label()
	 */
	public IGridRow label()
    {
	    // TODO Later on, when working actively on issue #13
	    return this;
    }

	@Override int labelWidth()
	{
		return (_label != null ? _label.getPreferredSize().width : 0);
	}

	@Override int gridColumns()
	{
		int columns = 0;
		for (RowItem item: _items)
		{
			columns += item.span();
		}
		return columns;
	}

	@Override int maxColumnWidth(int maxColumns)
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

	@Override int hgap()
	{
		LayoutStyle layoutStyle = LayoutStyle.getSharedInstance();

		int hgap = 0;
		List<RowItem> items = _items;

		// Account for gap between label and first component
		if (_label != null && items.size() > 0)
		{
			JComponent left = _label;
			JComponent right = items.get(0).component();
			int gap =
			    layoutStyle.getPreferredGap(
			        left, right, LayoutStyle.RELATED, SwingConstants.EAST, parent());
			hgap = Math.max(hgap, gap);
		}

		for (int nth = 0; nth < (items.size() - 1); nth++)
		{
			JComponent left = items.get(nth).component();
			JComponent right = items.get(nth + 1).component();
			int gap =
			    layoutStyle.getPreferredGap(
			        left, right, LayoutStyle.RELATED, SwingConstants.EAST, parent());
			hgap = Math.max(hgap, gap);
		}
		return hgap;
	}

	// CSOFF: ParameterAssignment
	@Override int layoutRow(
	    LayoutHelper helper, int x, int y, int hgap, int rowWidth, int labelWidth)
	{
		int actualHeight = 0;
		// Account for label column
		if (labelWidth > 0)
		{
			int width = labelWidth;
			if (_label != null)
			{
				actualHeight = Math.max(0, helper.setSizeLocation(
					_label, x, y, width, height(), baseline()));
			}
			x += width + hgap;
			rowWidth -= (width + hgap);
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
					component, x, y, width, height(), baseline()));
				x += width + hgap;
			}
		}
		return actualHeight;
	}
	// CSON: ParameterAssignment

	@Override protected List<JComponent> components()
	{
		return _components;
	}

	private class ComponentList extends AbstractList<JComponent>
	{
		@Override public JComponent get(int index)
		{
			return _items.get(index).component();
		}

		@Override public int size()
		{
			return _items.size();
		}
	}

	final private List<RowItem> _items = new ArrayList<RowItem>();
	final private ComponentList _components = new ComponentList();
	private JLabel _label = null;
}
