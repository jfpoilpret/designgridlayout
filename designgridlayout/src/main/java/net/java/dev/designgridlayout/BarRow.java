//  Copyright 2005-2010 Jason Aaron Osgood, Jean-Francois Poilpret
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
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

final class BarRow extends AbstractRow implements IBarRow
{
	@Override public IBarRow add(JComponent child, Tag tag)
	{
		if (child != null)
		{
			_items.add(new BarRowItem(child, (tag == null ? Tag.OTHER : tag)));
			parent().add(child);
		}
		return this;
	}

	@Override public IBarRow addCenter(JComponent... children)
	{
		return add(Tag.OTHER, children);
	}

	@Override public IBarRow addLeft(JComponent... children)
	{
		return add(Tag.LEFT, children);
	}

	@Override public IBarRow addRight(JComponent... children)
	{
		return add(Tag.RIGHT, children);
	}
	
	private IBarRow add(Tag tag, JComponent... children)
	{
		for (JComponent child: children)
		{
			add(child, tag);
		}
		return this;
	}
	
	@Override public IBarRow gap()
	{
		_items.add(null);
		return this;
	}

	@Override public IBarRow withOwnRowWidth()
	{
		_ownRowWidth = true;
		return this;
	}

	@Override List<BarRowItem> items()
	{
		if (_leftItems == null)
		{
			// First of all split into 3 lists: left, center and right
			_leftItems = PlatformHelper.extractLeftItems(_items);
			_centerItems = PlatformHelper.extractCenterItems(_items);
			_rightItems = PlatformHelper.extractRightItems(_items);
			// Then rebuild _items to only include all but non-null items
			_items.clear();
			_items.addAll(_leftItems);
			_items.addAll(_centerItems);
			_items.addAll(_rightItems);
			// Calculate number of extra gaps
			_numUnrelatedGaps =	(_leftItems.isEmpty() ? 0 : 1) +
								(_centerItems.isEmpty() ? 0 : 1) +
								(_rightItems.isEmpty() ? 0 : 1);
			if (_numUnrelatedGaps > 0)
			{
				_numUnrelatedGaps--;
			}
			Iterator<BarRowItem> i = _items.iterator();
			while (i.hasNext())
			{
				if (i.next() == null)
				{
					_numUnrelatedGaps++;
					i.remove();
				}
			}
		}
		return _items;
	}

	@Override int totalNonGridWidth(int hgap, int unrelhgap)
	{
		int count = _items.size();
		int counthgap = count - 1 - _numUnrelatedGaps;
		assert counthgap >= 0;
		int totalWidth = 
			_compWidth * count + (hgap * counthgap) + (unrelhgap * _numUnrelatedGaps);
		return totalWidth;
	}

	@Override int componentNonGridWidth()
	{
		return (_ownRowWidth ? 0 : actualComponentNonGridWidth());
	}
	
	private int actualComponentNonGridWidth()
	{
		return ComponentHelper.maxValues(_items, PrefWidthExtractor.INSTANCE);
	}

	@Override void forceComponentNonGridWidth(int width)
	{
		_compWidth = ((width > 0 && !_ownRowWidth) ? width : actualComponentNonGridWidth());
	}

	@Override int layoutRow(LayoutHelper helper, int left, int hgap, int gridgap, 
		int unrelhgap, int rowWidth, int gridsWidth, List<Integer> labelsWidth)
	{
		// Calculate various needed widths & origin
		int x = left;
		int count = _items.size();
		int counthgap = count - 1 - _numUnrelatedGaps;

		//FIXME X should not be calculated this way, but that way instead:
		// - for left part, x = left OK
		// - for center part, x = left + (rowWidth + centerWidth)/2
		// - for right part, x = left + (rowWidth - rightWidth)
		int usedWidth = 
			_compWidth * count + (hgap * counthgap) + (unrelhgap * _numUnrelatedGaps);
		int extraWidth = rowWidth - usedWidth;
		int interGap = extraWidth / 2;
		int fudge = extraWidth % 2;
		
		// Now layout each part of the row
		int actualHeight = 0;
		LayoutResult result = layoutOnePart(helper, x, hgap, unrelhgap, _leftItems);
		actualHeight = Math.max(actualHeight, result.height());

		x = result.x() + interGap;
		result = layoutOnePart(helper, x, hgap, unrelhgap, _centerItems);
		actualHeight = Math.max(actualHeight, result.height());

		x = result.x() + interGap + fudge;
		result = layoutOnePart(helper, x, hgap, unrelhgap, _rightItems);
		actualHeight = Math.max(actualHeight, result.height());

		return actualHeight;
	}

	private LayoutResult layoutOnePart(
		LayoutHelper helper, int xOrigin, int hgap, int unrelhgap, List<BarRowItem> items)
	{
		int x = xOrigin;
		int actualHeight = 0;
		for (BarRowItem item: items)
		{
			if (item != null)
			{
				actualHeight = Math.max(actualHeight, helper.setSizeLocation(
					item.component(), x, _compWidth, height(), baseline()));
				x += _compWidth + hgap;
			}
			else
			{
				x += unrelhgap - hgap;
			}
		}
		if (!items.isEmpty())
		{
			// If this part does have items, then add the necessary gap after it
			x += unrelhgap - hgap;
		}
		return new LayoutResult(x, actualHeight);
	}
	
	static private class LayoutResult
	{
		LayoutResult(int x, int height)
		{
			_x = x;
			_height = height;
		}
		
		int x()
		{
			return _x;
		}
		
		int height()
		{
			return _height;
		}
		
		final private int _x;
		final private int _height;
	}
	
	private final List<BarRowItem> _items = new ArrayList<BarRowItem>();
	private List<BarRowItem> _leftItems = null;
	private List<BarRowItem> _centerItems = null;
	private List<BarRowItem> _rightItems = null;
	private int _numUnrelatedGaps = 0;
	private boolean _ownRowWidth = false;
	private int _compWidth = 0;
}
