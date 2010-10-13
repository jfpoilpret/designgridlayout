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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JComponent;
import javax.swing.LayoutStyle.ComponentPlacement;

import static net.java.dev.designgridlayout.RowIterator.each;

class DesignGridLayoutManager implements LayoutManager
{
	DesignGridLayoutManager(
		Container parent, List<AbstractRow> rows, OrientationPolicy orientation)
	{
		_parent = parent;
		_rows = rows;
		_orientation = orientation;
	}

	void margins(double top, double left, double bottom, double right)
	{
		_topWeight = (top < 0.0 ? 0.0 : top);
		_leftWeight = (left < 0.0 ? 0.0 : left);
		_bottomWeight = (bottom < 0.0 ? 0.0 : bottom);
		_rightWeight = (right < 0.0 ? 0.0 : right);
	}
	
	void forceConsistentBaselinesDistance()
	{
		_consistentBaselineDistance = true;
	}

	void setHeightTester(HeightGrowPolicy heightTester)
	{
		_heightTester = heightTester;
	}

	/*
	 * Do not add components via the parent container's {@link Container#add(Component)}
	 * method, use directly DesignGridLayout API instead.
	 */
	public void addLayoutComponent(String constraint, Component component)
	{
		throw new IllegalArgumentException("Do not use this method");
	}

	/*
	 * Do not remove components via the parent container's 
	 * {@link Container#remove(Component)} method; removing components from a 
	 * DesignGridLayout-managed container is not supported.
	 */
	public void removeLayoutComponent(Component parent)
	{
		throw new IllegalArgumentException("Do not use this method");
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer
	 */
	public void layoutContainer(Container parent)
	{
		checkParent(parent);

		// Make sure there's something to do
		if (_rows.isEmpty())
		{
			return;
		}

		synchronized(parent.getTreeLock())
		{
			// Always calculate the size of our contents
			initialize();

			// Calculate extra height to split between variable height rows
			double totalExtraHeight = 0.0;
			if (_totalWeight > 0.0)
			{
				totalExtraHeight = Math.max(
					0, (parent.getHeight() - _preferredSize.height) / _totalWeight);
			}
			
			// Check layout orientation
			boolean rtl = _orientation.isRightToLeft();

			int x = left();
			int y = top();
			int parentWidth = parent.getWidth();
			// Never layout components smaller than the minimu size
			parentWidth = Math.max(parentWidth, _minimumSize.width);

			int rowWidth = parentWidth - left() - right();
			// Calculate the total width assigned exclusively to sub-grids components
			int gridsWidth = rowWidth;
			// Exclude labels (and their gaps) from available space
			if (_totalLabelWidth > 0)
			{
				gridsWidth -= _totalLabelWidth + _maxGrids * _hgap;
			}
			// Exclude inter-grid gaps
			gridsWidth -= (_maxGrids - 1) * _gridgap;
			
			// Start laying out every single row (all components but row-span ones)
			LayoutHelper helper = new LayoutHelper(_heightTester, parentWidth, rtl, _rows);
			for (AbstractRow row: _rows)
			{
				// Issue #30 - check that row is not empty
				if (!row.isEmpty())
				{
					helper.setY(y);
					int extraHeight = (int) (row.growWeight() * totalExtraHeight); 
					helper.setRowAvailableHeight(extraHeight + row.height());
					row.layoutRow(helper, x, _hgap, _gridgap, rowWidth, 
						gridsWidth, _labelWidths);
					row.actualHeight(row.height() + extraHeight);
					y += row.actualHeight() + row.vgap();
				}
			}
			
			// Second pass: all row-span components
			int rowIndex = 0;
			for (AbstractRow row: _rows)
			{
				for (IRowItem item: row.items())
				{
					if (item.isFirstSpanRow() && !item.isLastSpanRow())
					{
						// Calculate size based on number of spanned rows
						helper.setHeight(rowIndex, item.component(), item.rowSpan());
					}
				}
				rowIndex++;
			}

		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		checkParent(parent);
		reset();
		initialize();
		// Note: Dimension instances can be mutated by an outsider
		return new Dimension(_minimumSize);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent)
	{
		checkParent(parent);
		reset();
		initialize();
		// Note: Dimension instances can be mutated by an outsider
		return new Dimension(_preferredSize);
	}
	
	private void checkParent(Container parent)
	{
		if (parent != _parent)
		{
			throw new IllegalArgumentException(
				"must use DesignGridLayout instance with original parent container");
		}
	}

	private int top()
	{
		return _parent.getInsets().top + (int) (_topWeight * _top);
	}

	private int left()
	{
		return _parent.getInsets().left + (int) (_leftWeight * _left);
	}

	private int bottom()
	{
		return _parent.getInsets().bottom + (int) (_bottomWeight * _bottom);
	}

	private int right()
	{
		return _parent.getInsets().right + (int) (_rightWeight * _right);
	}

	/*
	 * Horizontal gaps are easy.
	 * Since canonical grids are "balanced", just use the biggest intra-component 
	 * gap in the grid for all intra-component gaps.
	 * Vertical gaps are tougher.
	 * The vertical gaps between each component of the upper row and each 
	 * component of the lower row are compared. The heights of each component is 
	 * factored in, which seems to work well. 
	 */
	private void computeGutters()
	{
		// Handle horizontal gaps
		computeHorizontalGutters();

		// Vertical gaps (per row)
		computeVerticalGutters();
	}
	
	private void computeHorizontalGutters()
	{
		_hgap = 0;
		_gridgap = 0;
		for (AbstractRow row: _rows)
		{
			_hgap = Math.max(_hgap, row.hgap());
			_gridgap = Math.max(_gridgap, row.gridgap());
		}
	}

	//FIXME vgaps calculation
	// To be absolutely correct, each component's actual layout position should
	// be determined by factoring component heights, component baselines, and 
	// each row's maximum height.
	// Problem is that this would have to be re-calculated every time... Maybe
	// it's better not to try to improve vgaps
	private void computeVerticalGutters()
	{
		ComponentGapsHelper helper = ComponentGapsHelper.instance();
		int nthRow = 0;
		for (AbstractRow row: _rows)
		{
			nthRow++;
			if (row.isEmpty())
			{
				// Current row has no component: nothing to compute!
				continue;
			}
			AbstractRow next = nextNonEmptyRow(nthRow);
			if (next == null)
			{
				// Current row is the last row with components: 
				// computation is finished
				break;
			}

			int maxComboHeight = 0;
			int rowGap = 0;

			List<? extends IRowItem> items1 = row.allItems();
			List<? extends IRowItem> items2 = next.allItems();
			ComponentPlacement style;
			if (row.hasUnrelatedGap())
			{
				style = ComponentPlacement.UNRELATED;
			}
			else
			{
				style = ComponentPlacement.RELATED;
			}

			for (IRowItem item1: items1)
			{
				if (item1.isLastSpanRow())
				{
					JComponent upper = item1.component();
					int aboveHeight = upper.getPreferredSize().height;

					for (IRowItem item2: items2)
					{
						if (item2.isFirstSpanRow())
						{
							JComponent lower = item2.component();
							int belowHeight = lower.getPreferredSize().height;
							int gap = helper.getVerticalGap(upper, lower, style, _parent);
							int comboHeight = aboveHeight + gap + belowHeight;
							if (comboHeight > maxComboHeight)
							{
								maxComboHeight = comboHeight;
								rowGap = gap;
							}
						}
					}
				}
			}
			row.vgap(rowGap);
		}
	}
	
	private void computeConsistentBaselineDistance()
	{
		Iterator<AbstractRow> iter = each(_rows).iterator();
		if (!iter.hasNext())
		{
			return;
		}
		// Baseline distances
		int relatedBaselineDistance = 0;
		int unrelatedBaselineDistance = 0;
		AbstractRow current;
		AbstractRow next = iter.next();
		while (iter.hasNext())
		{
			current = next;
			next = iter.next();
			if (current.growWeight() == 0.0)
			{
				int distance = current.height() + current.vgap() - current.baseline() +
					next.baseline();
				if (current.hasUnrelatedGap())
				{
					unrelatedBaselineDistance = 
						Math.max(unrelatedBaselineDistance, distance);
				}
				else
				{
					relatedBaselineDistance = 
						Math.max(relatedBaselineDistance, distance);
				}
			}
		}
		iter = each(_rows).iterator();
		next = iter.next();
		while (iter.hasNext())
		{
			current = next;
			next = iter.next();
			if (current.growWeight() == 0.0)
			{
				int vgap = (current.hasUnrelatedGap()
					? unrelatedBaselineDistance : relatedBaselineDistance);
				vgap += current.baseline() - current.height() - next.baseline();
				current.vgap(vgap);
			}
		}
	}

	private void initialize()
	{
		if (_preferredSize != null)
		{
			return;
		}

		// Make sure there's something to do
		if (_rows.isEmpty())
		{
			_preferredSize = new Dimension(0, 0);
			_minimumSize = new Dimension(0, 0);
			return;
		}
		
		// First of all count number of canonical grids in the whole panel
		countGrids();

		// Check all spanRow() calls are correct on all GridRows
		// (show colored placeholder in all locations where a problem occurs)
		for (AbstractRow row: _rows)
		{
			row.checkSpanRows();
		}
		
		// Calculate margins and gutters
		computeMargins();
		computeGutters();
		
		// Initialize the list of all rowspan items across the layout
		initRowSpanItems();

		// Initialize each row (compute width, height, baseline)
		for (AbstractRow row: _rows)
		{
			row.init();
		}

		// Compute consistent baselines
		if (_consistentBaselineDistance)
		{
			computeConsistentBaselineDistance();
		}

		// Calculate labels width for all grids
		computeLabelWidths();

		// Compute preferred & minimum widths for each sub-grid (without labels), 
		// use largest width for all grids
		int preferredWidth = computeGridWidth(PrefWidthExtractor.INSTANCE);
		int minimumWidth = computeGridWidth(MinWidthExtractor.INSTANCE);

		// Total height
		int preferredHeight = totalHeight() + top() + bottom() + 1;

		// Calculate total height growth factor of all variable height rows
		_totalWeight = 0.0;
		for (AbstractRow row: _rows)
		{
			_totalWeight += row.growWeight();
		}

		_preferredSize = new Dimension(preferredWidth, preferredHeight);
		_minimumSize = new Dimension(minimumWidth, preferredHeight);
	}

	private void initRowSpanItems()
	{
		// Perform pre-calculation of preferred height per row for each spanned item
		int rowIndex = 0;
		for (AbstractRow row: _rows)
		{
			for (IRowItem item: row.items())
			{
				if (item.isFirstSpanRow())
				{
					List<AbstractRow> spannedRows = 
						_rows.subList(rowIndex, rowIndex + item.rowSpan());
					item.setSpannedRows(spannedRows);
				}
			}
			rowIndex++;
		}
	}
	
	private void countGrids()
	{
		// Calculate the actual number of sub-grids
		_maxGrids = 0;
		for (AbstractRow row: _rows)
		{
			_maxGrids = Math.max(_maxGrids, row.numGrids());
		}
		// Inform each row about the total number of sub-grids
		for (AbstractRow row: _rows)
		{
			row.totalGrids(_maxGrids);
		}
	}

	private int countGridColumns(int grid)
	{
		int maxColumns = 0;
		for (AbstractRow row: _rows)
		{
			// Note columns (sum item spans), not the count of components
			maxColumns = Math.max(maxColumns, row.gridColumns(grid));
		}
		return maxColumns;
	}
	
	private void computeLabelWidths()
	{
		_labelWidths.clear();
		_totalLabelWidth = 0;
		for (int i = 0; i < _maxGrids; i++)
		{
			int width = 0;
			for (AbstractRow row: _rows)
			{
				// Label width first
				width = Math.max(width, row.labelWidth(i));
			}
			_labelWidths.add(width);
			_totalLabelWidth += width;
		}
	}

	private int totalHeight()
	{
		int totalHeight = 0;
		for (AbstractRow row: _rows)
		{
			totalHeight += row.height() + row.vgap();
		}
		return totalHeight;
	}
	
	private int computeGridWidth(IExtractor extractor)
	{
		// Compute preferred width for each sub-grid (without labels), 
		// use largest width for all grids
		int width = 0;
		for (int grid = 0; grid < _maxGrids; grid++)
		{
			int maxColumns = countGridColumns(grid);
	
			// Compute width to use for columns of grid
			int maxWidth = maxGridRowsColumnWidth(grid, maxColumns, extractor);
	
			// Then, calculate the preferred width for that grid
			int gridWidth = maxWidth * maxColumns + (_hgap * (maxColumns - 1)) + 1;
	
			width = Math.max(width, gridWidth);
		}
		// Now use preferred width for each subgrid
		width *= _maxGrids;
		
		// Account for labels
		if (_totalLabelWidth > 0)
		{
			width += _totalLabelWidth + (_maxGrids * _hgap);
		}

		// Add gaps between grids
		// first hgap is correct (label to 1st comp), 2nd gap should be bigger
		width += (_maxGrids - 1) * _gridgap;
		
		// Add left and right margins
		width += left() + right();

		// Don't forget to account for the minimum width of non grid rows
		width = Math.max(width, totalNonGridWidth(extractor));
		
		return width;
	}
	
	private int maxGridRowsColumnWidth(int grid, int maxColumns, IExtractor extractor)
	{
		int maxWidth = 0;
		for (AbstractRow row: _rows)
		{
			int width = row.maxColumnWidth(grid, maxColumns, extractor);
			// If current grid spans several sub-grids, then colum width must
			// be reduced (by removing extra labels) and divide result by number
			// of spanned sub-grids
			int span = row.gridspan(grid);
			if (span > 1)
			{
				for (int i = 1; i < span; i++)
				{
					width -= _gridgap + _labelWidths.get(grid + i) + _hgap;
				}
				int fudge = width % span;
				width /= span;
				if (fudge > 0)
				{
					width += 1;
				}
			}
			maxWidth = Math.max(maxWidth, width);
		}
		return maxWidth;
	}

	private int totalNonGridWidth(IExtractor extractor)
	{
		int maxWidth = 0;
		for (AbstractRow row: _rows)
		{
			maxWidth = Math.max(maxWidth, row.totalNonGridWidth(_hgap, extractor));
		}
		return maxWidth + left() + right() + 1;
	}
	
	private void computeMargins()
	{
		// Handle top row
		computeTopMargin();
	
		// Handle bottom row
		computeBottomMargin();
	
		// Handle left-most and right-most columns
		computeLeftRightMargins();
	}
	
	private void computeTopMargin()
	{
		_top = 0;
		// Issue #30 - find the first non empty row
		AbstractRow topRow = firstNonEmptyRow();
		if (topRow != null)
		{
			ComponentGapsHelper helper = ComponentGapsHelper.instance();
			for (IRowItem item: topRow.allItems())
			{
				int gap = helper.getNorthContainerGap(item.component(), _parent);
				_top = Math.max(_top, gap);
			}
		}
	}

	private void computeBottomMargin()
	{
		_bottom = 0;
		int maxComboHeight = 0;
		int bottomGap = 0;
		// Issue #30 - find the last non empty row
		AbstractRow bottomRow = lastNonEmptyRow();
		if (bottomRow != null)
		{
			ComponentGapsHelper helper = ComponentGapsHelper.instance();
			for (IRowItem item: bottomRow.allItems())
			{
				int height = item.preferredHeight();
				int gap = helper.getSouthContainerGap(item.component(), _parent);
				int comboHeight = height + gap;
				if (comboHeight > maxComboHeight)
				{
					maxComboHeight = comboHeight;
					bottomGap = gap;
				}
			}
			_bottom = Math.max(_bottom, bottomGap);
		}
	}
	
	private AbstractRow firstNonEmptyRow()
	{
		for (AbstractRow row: _rows)
		{
			if (!row.isEmpty())
			{
				return row;
			}
		}
		return null;
	}
	
	private AbstractRow nextNonEmptyRow(int index)
	{
		for (int i = index; i < _rows.size(); i++)
		{
			AbstractRow row = _rows.get(i);
			if (!row.isEmpty())
			{
				return row;
			}
		}
		return null;
	}
	
	private AbstractRow lastNonEmptyRow()
	{
		ListIterator<AbstractRow> i = _rows.listIterator(_rows.size());
		while (i.hasPrevious())
		{
			AbstractRow row = i.previous();
			if (!row.isEmpty())
			{
				return row;
			}
		}
		return null;
	}

	private void computeLeftRightMargins()
	{
		_left = 0;
		_right = 0;
		ComponentGapsHelper helper = ComponentGapsHelper.instance();
		for (AbstractRow row: _rows)
		{
			JComponent left = row.leftComponent();
			if (left != null)
			{
				_left = Math.max(_left, helper.getWestContainerGap(left, _parent));
			}

			JComponent right = row.rightComponent();
			if (right != null)
			{
				_right = Math.max(_right, helper.getEastContainerGap(right, _parent));
			}
		}
	}
	
	private void reset()
	{
		_preferredSize = null;
	}

	final private Container _parent;
	final private OrientationPolicy _orientation;

	private HeightGrowPolicy _heightTester;
	
	private Dimension _preferredSize = null;
	private Dimension _minimumSize = null;

	private int _top;
	private int _left;
	private int _bottom;
	private int _right;

	private int _hgap;
	private int _gridgap;

	private double _totalWeight;
	
	private double _topWeight = 1.0;
	private double _leftWeight = 1.0;
	private double _bottomWeight = 1.0;
	private double _rightWeight = 1.0;
	
	private boolean _consistentBaselineDistance = false;

	final private List<AbstractRow> _rows;
	final private List<Integer> _labelWidths = new ArrayList<Integer>();
	private int _totalLabelWidth;
	private int _maxGrids;
}
