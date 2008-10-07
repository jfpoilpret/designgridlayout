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
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import org.jdesktop.layout.LayoutStyle;

final class GridRow extends AbstractRow
{
	GridRow(Container parent, HeightGrowPolicy heightTester)
	{
		super(parent, heightTester);
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

		for (RowItem item: items())
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
		List<RowItem> items = items();

		// Account for gap between label and first component
		if (hasLabel() && items.size() > 0)
		{
			JComponent left = label();
			JComponent right = items.get(0).component();
			int gap = layoutStyle.getPreferredGap(
				left, right, LayoutStyle.RELATED, SwingConstants.EAST, _parent);
			hgap = Math.max(hgap, gap);
		}

		for (int nth = 0; nth < (items.size() - 1); nth++)
		{
			JComponent left = items.get(nth).component();
			JComponent right = items.get(nth + 1).component();
			int gap = layoutStyle.getPreferredGap(
				left, right, LayoutStyle.RELATED, SwingConstants.EAST, _parent);
			hgap = Math.max(hgap, gap);
		}
		return hgap;
	}

	// CSOFF: ParameterAssignment
	@Override void layoutRow(LayoutHelper helper, int x, int y, 
		int hgap, int rowWidth, int labelWidth)
	{
		// Account for label column
		if (labelWidth > 0)
		{
			int width = labelWidth;
			if (hasLabel())
			{
				JComponent component = label();
				helper.setSizeLocation(component, x, y, width, height(), baseline());
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

			Iterator<RowItem> i = items().iterator();
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
				helper.setSizeLocation(component, x, y, width, height(), baseline());
				x += width + hgap;
			}
		}
	}
	// CSON: ParameterAssignment
}
