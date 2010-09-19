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

package net.java.dev.designgridlayout.internal.row;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.java.dev.designgridlayout.IGridRow;
import net.java.dev.designgridlayout.ISpannableGridRow;
import net.java.dev.designgridlayout.internal.util.ComponentGapsHelper;
import net.java.dev.designgridlayout.internal.util.IExtractor;
import net.java.dev.designgridlayout.internal.util.LayoutHelper;
import net.java.dev.designgridlayout.internal.util.SubList;

public final class GridRow extends AbstractRow implements ISpannableGridRow
{
	public GridRow(GridRow previous)
	{
		_previous = previous;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISpannableGridRow#spanRow()
	 */
	public ISpannableGridRow spanRow()
	{
		checkUnlocked();
		_current.spanRow();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent,
	 * int)
	 */
	public ISpannableGridRow add(JComponent child, int span)
	{
		checkUnlocked();
		_current.add(child, span);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent[])
	 */
	public ISpannableGridRow add(JComponent... children)
	{
		checkUnlocked();
		for (JComponent component: children)
		{
			add(component, 1);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(int,
	 * javax.swing.JComponent[])
	 */
	public ISpannableGridRow addMulti(int span, JComponent... children)
	{
		checkUnlocked();
		return add(new MultiComponent(growPolicy(), orientation(), children), span);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(javax.swing.JComponent[])
	 */
	public ISpannableGridRow addMulti(JComponent... children)
	{
		checkUnlocked();
		return addMulti(1, children);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#empty()
	 */
	public ISpannableGridRow empty()
	{
		checkUnlocked();
		return empty(1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#empty(int)
	 */
	public ISpannableGridRow empty(int span)
	{
		checkUnlocked();
		return add(null, span);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISubGridStarter#grid(javax.swing.JLabel)
	 */
	public ISpannableGridRow grid(JLabel label)
	{
		return newGrid(label, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISubGridStarter#grid()
	 */
	public ISpannableGridRow grid()
	{
		return newGrid(null, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISubGridStarter#grid(int)
	 */
	public IGridRow grid(int gridspan)
	{
		return newGrid(null, gridspan);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISubGridStarter#grid(javax.swing.JLabel, int)
	 */
	public IGridRow grid(JLabel label, int gridspan)
	{
		return newGrid(label, gridspan);
	}

	private ISpannableGridRow newGrid(JLabel label, int gridspan)
	{
		checkUnlocked();
		// Fix the span of the previous sub-grid (if it was in auto-span mode)
		if (_current != null)
		{
			_current.gridspan(1);
			_gridIndex += _current.gridspan();
		}
		// Find the matching subgrid (if any) of the previous row
		SubGrid previous = (_previous != null ? _previous.findGrid(_gridIndex) : null);
		// Initialize the sublist of _items to be used by the new subgrid
		_current = new SubGrid(new SubList(_items), previous, parent(), label, gridspan);
		_grids.add(_current);
		return this;
	}

	@Override public void checkSpanRows()
	{
		for (SubGrid grid: _grids)
		{
			grid.checkSpanRows();
		}
	}

	@Override public int gridspan(int grid)
	{
		return findNonNulGrid(grid).gridspan();
	}

	@Override public int labelWidth(int grid)
	{
		return findNonNulGrid(grid).labelWidth();
	}

	@Override public int numGrids()
	{
		int numGrids = 0;
		for (SubGrid grid: _grids)
		{
			numGrids += (grid.gridspan() > 0 ? grid.gridspan() : 1);
		}
		return numGrids;
	}
	
	@Override public void totalGrids(int totalGrids)
	{
		// Set the correct grid-span for the last sub-grid if needed
		int span = totalGrids;
		Iterator<SubGrid> i = _grids.iterator();
		while (i.hasNext())
		{
			SubGrid grid = i.next();
			if (i.hasNext())
			{
				span -= grid.gridspan();
			}
			else
			{
				grid.gridspan(span);
			}
		}
		// Add as many fake grids as necessary to the sub-grids list
		_totalGrids = totalGrids;
	}
		
	@Override public int gridColumns(int grid)
	{
		return findNonNulGrid(grid).gridColumns();
	}

	@Override public int maxColumnWidth(int grid, int maxColumns, IExtractor extractor)
	{
		return findNonNulGrid(grid).maxColumnWidth(maxColumns, extractor);
	}

	@Override public int hgap()
	{
		int hgap = 0;
		for (SubGrid grid: _grids)
		{
			hgap = Math.max(hgap, grid.hgap());
		}
		return hgap;
	}
	
	@Override public int gridgap()
	{
		ComponentGapsHelper helper = ComponentGapsHelper.instance();
		int gridgap = 0;
		for (int i = 0; i < _grids.size() - 1; i++)
		{
			List<RowItem> leftGrid = _grids.get(i).items();
			List<RowItem> rightGrid = _grids.get(i + 1).items();
			if (!(leftGrid.isEmpty() || rightGrid.isEmpty()))
			{
				JComponent left = leftGrid.get(leftGrid.size() - 1).component();
				JComponent right = rightGrid.get(0).component();
				int gap = helper.getHorizontalGap(
					left, right, ComponentPlacement.UNRELATED, parent());
				gridgap = Math.max(gridgap, gap);
			}
		}
		return gridgap;
	}

	@Override public boolean isEmpty()
	{
		for (SubGrid grid: _grids)
		{
			if (grid.leftComponent() != null)
			{
				return false;
			}
		}
		return true;
	}
	
	@Override public JComponent leftComponent()
	{
		return (_grids.isEmpty() ? null : _grids.get(0).leftComponent());
	}
	
	@Override public List<RowItem> items()
	{
		return _items;
	}
	
	@Override public List<RowItem> allItems()
	{
		if (_allItems == null)
		{
			_allItems = new ArrayList<RowItem>(_items.size() + _grids.size());
			for (SubGrid grid: _grids)
			{
				// Add label if any
				if (grid.label() != null)
				{
					_allItems.add(new RowItem(1, grid.label()));
				}
				_allItems.addAll(grid.items());
			}
		}
		return _allItems;
	}
	
	@Override public int layoutRow(LayoutHelper helper, int left, int hgap, 
		int gridgap, int rowWidth, int gridsWidth, List<Integer> labelsWidth)
	{
		int x = left;
		int actualHeight = 0;
		int gridWidth = gridsWidth / _totalGrids;
		int gridIndex = 0;
		Iterator<Integer> label = labelsWidth.iterator();
		for (SubGrid grid: _grids)
		{
			// Find the label for the current sub-grid
			int labelWidth = label.next();

			// Calculate the actual width for this sub-grid (depends on spans)
			int width = gridWidth;
			for (int i = 1; i < grid.gridspan(); i++)
			{
				width += gridgap + label.next() + hgap + gridWidth;
			}
			gridIndex += grid.gridspan();
			if (gridIndex == _totalGrids)
			{
				// Add fudge to the true last grid 
				// Note that this is not necessarily the last grid of _grids!
				int fudge = gridsWidth % _totalGrids;
				width += fudge;
			}
			
			// Layout the sub-grid
			int height = grid.layoutRow(
				helper, x, height(), baseline(), hgap, width, labelWidth);
			actualHeight = Math.max(actualHeight, height);

			// Position origin to the next grid
			x += labelWidth + hgap + width + gridgap;
		}
		return actualHeight;
	}

	SubGrid findGrid(int index)
	{
		int i = 0;
		for (SubGrid grid: _grids)
		{
			if (i == index)
			{
				return grid;
			}
			i += grid.gridspan();
			if (i > index)
			{
				break;
			}
		}
		return null;
	}

	private ISubGrid findNonNulGrid(int index)
	{
		SubGrid grid = findGrid(index);
		return (grid != null ? grid : NULL_GRID);
	}
	
	private SubGrid _current = null;
	private int _gridIndex = 0;
	private int _totalGrids = 0;
	final private GridRow _previous;
	final private List<SubGrid> _grids = new ArrayList<SubGrid>();
	final private List<RowItem> _items = new ArrayList<RowItem>();
	private List<RowItem> _allItems = null;

	static final private ISubGrid NULL_GRID = new EmptySubGrid();
}
