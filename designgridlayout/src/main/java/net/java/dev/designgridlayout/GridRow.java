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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jdesktop.layout.LayoutStyle;

final class GridRow extends AbstractRow implements ISpannableGridRow
{
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISpannableGridRow#spanRow()
	 */
	public ISpannableGridRow spanRow()
	{
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
		_current.add(child, span);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent[])
	 */
	public ISpannableGridRow add(JComponent... children)
	{
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
		return add(new MultiComponent(growPolicy(), orientation(), children), span);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(javax.swing.JComponent[])
	 */
	public ISpannableGridRow addMulti(JComponent... children)
	{
		return addMulti(1, children);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#empty()
	 */
	public ISpannableGridRow empty()
	{
		return empty(1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#empty(int)
	 */
	public ISpannableGridRow empty(int span)
	{
		return add(new JPanel(), span);
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
		// Fix the span of the previous sub-grid (if it was in auto-span mode)
		if (_current != null)
		{
			_current.gridspan(1);
		}
		_current = new SubGrid(parent(), label, gridspan);
		_grids.add(_current);
		return this;
	}

	@Override int gridspan(int grid)
	{
		return findGrid(grid).gridspan();
	}

	@Override int labelWidth(int grid)
	{
		return findGrid(grid).labelWidth();
	}

	@Override int numGrids()
	{
		int numGrids = 0;
		for (SubGrid grid: _grids)
		{
			numGrids += (grid.gridspan() > 0 ? grid.gridspan() : 1);
		}
		return numGrids;
	}
	
	@Override void totalGrids(int totalGrids)
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
		
	@Override int gridColumns(int grid)
	{
		return findGrid(grid).gridColumns();
	}

	@Override int maxColumnWidth(int grid, int maxColumns, IExtractor extractor)
	{
		return findGrid(grid).maxColumnWidth(maxColumns, extractor);
	}

	@Override int hgap()
	{
		int hgap = 0;
		for (SubGrid grid : _grids)
		{
			hgap = Math.max(hgap, grid.hgap());
		}
		return hgap;
	}
	
	@Override int gridgap()
	{
		LayoutStyle layoutStyle = LayoutStyle.getSharedInstance();
		int gridgap = 0;
		for (int i = 0; i < _grids.size() - 1; i++)
		{
			List<RowItem> leftGrid = _grids.get(i).items();
			List<RowItem> rightGrid = _grids.get(i + 1).items();
			if (!(leftGrid.isEmpty() || rightGrid.isEmpty()))
			{
				JComponent left = leftGrid.get(leftGrid.size() - 1).component();
				JComponent right = rightGrid.get(0).component();
				int gap = layoutStyle.getPreferredGap(
					left, right, LayoutStyle.UNRELATED, SwingConstants.EAST, parent());
				gridgap = Math.max(gridgap, gap);
			}
		}
		return gridgap;
	}

	@Override int layoutRow(LayoutHelper helper, int left, int hgap, int gridgap, 
		int rowWidth, int gridsWidth, List<Integer> labelsWidth)
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

	@Override List<JComponent> components()
	{
		return _components;
	}
	
	private ISubGrid findGrid(int index)
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
		return NULL_GRID;
	}

	private class ComponentList extends AbstractList<JComponent>
	{
		@Override public JComponent get(int index)
		{
			int current = 0;
			for (SubGrid grid : _grids)
			{
				if (index < current + grid.items().size())
				{
					return grid.items().get(index - current).component();
				}
				current += grid.items().size();
			}
			// We should normally never come there
			throw new IndexOutOfBoundsException(
				"Index: " + index + ", Size = " + size());
		}

		@Override public int size()
		{
			int size = 0;
			for (SubGrid grid : _grids)
			{
				size += grid.items().size();
			}
			return size;
		}
	}

	private SubGrid _current = null;
	private int _totalGrids = 0;
	final private List<SubGrid> _grids = new ArrayList<SubGrid>();
	final private ComponentList _components = new ComponentList();

	static final private ISubGrid NULL_GRID = new EmptySubGrid();
}
