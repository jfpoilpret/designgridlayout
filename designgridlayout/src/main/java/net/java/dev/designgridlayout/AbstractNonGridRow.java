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

import javax.swing.JComponent;

abstract class AbstractNonGridRow extends AbstractRow
{
	protected AbstractNonGridRow(Container parent, HeightGrowPolicy heightTester)
	{
		super(parent, heightTester);
	}

	@Override int totalWidth(int hgap)
	{
		int maxWidth = 0;
		int compWidth = 0;
		for (RowItem item: items())
		{
			JComponent component = item.component();
			Dimension d = component.getPreferredSize();
			compWidth = Math.max(compWidth, d.width);
		}
		int count = items().size();
		int width = compWidth * count + (hgap * (count - 1));
		maxWidth = Math.max(maxWidth, width);
		return maxWidth;
	}
	
	// CSOFF: ParameterAssignment
	@Override void layoutRow(LayoutHelper helper, int x, int y, 
		int hgap, int rowWidth, int labelWidth)
	{
		// Calculate various needed widths & origin
		int count = items().size();
		int width = maxWidth();
		int availableWidth = (rowWidth - ((count - 1) * hgap));
		width = Math.min(width, availableWidth / count);

		int usedWidth;
		int leftFiller = width;
		int rightFiller = width;
		if (!isFill())
		{
			usedWidth = width * count + ((count - 1) * hgap);
			x += xOffset(rowWidth, usedWidth);
		}
		else
		{
			usedWidth = availableWidth;
			leftFiller = leftFiller(count, width, availableWidth);
			rightFiller = rightFiller(count, width, availableWidth);
		}

		layoutRow(helper, x, y, hgap, width, leftFiller, rightFiller);
	}
	// CSON: ParameterAssignment
	
	// CSOFF: ParameterAssignment
	protected void layoutRow(LayoutHelper helper, int x, int y, 
		int hgap, int width, int leftFiller, int rightFiller)
	{
		int count = items().size();
		int i = 0;
		for (RowItem item: items())
		{
			int compWidth;
			if (i == 0)
			{
				compWidth = leftFiller;
			}
			else if (i == count - 1)
			{
				compWidth = rightFiller;
			}
			else
			{
				compWidth = width;
			}
			JComponent component = item.component();
			helper.setSizeLocation(component, x, y, compWidth, height(), baseline());
			x += compWidth + hgap;
			i++;
		}
	}
	// CSON: ParameterAssignment
	
	abstract protected int xOffset(int rowWidth, int usedWidth);
	abstract protected int leftFiller(int count, int width, int availableWidth);
	abstract protected int rightFiller(int count, int width, int availableWidth);
}
