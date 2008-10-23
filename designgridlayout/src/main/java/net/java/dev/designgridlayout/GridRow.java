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
		_current.add(child, span);
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
		_current = new SubGrid(parent(), label, 1);
		_grids.add(_current);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.ISubGridStarter#label()
	 */
	public IGridRow label()
    {
	    return label(null);
    }

	@Override int labelWidth(int grid)
	{
		return findGrid(grid).labelWidth();
	}

	@Override int numGrids()
	{
		return _grids.size();
	}

	@Override int gridColumns(int grid)
	{
		return findGrid(grid).gridColumns();
	}

	@Override int maxColumnWidth(int grid, int maxColumns)
	{
		return findGrid(grid).maxColumnWidth(maxColumns);
	}

	@Override int hgap()
	{
		int hgap = 0;
		for (ISubGrid grid : _grids)
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

	// CSOFF: ParameterAssignment
	@Override int layoutRow(LayoutHelper helper, int x, int y, int hgap, 
		int gridgap, int rowWidth, int gridWidth, List<Integer> labelsWidth)
	{
		int actualHeight = 0;
		for (int i = 0; i < _grids.size(); i++)
		{
			ISubGrid grid = _grids.get(i);
			// Calculate the actual width for this sub-grid (depends on spans)
			int width = gridWidth;
			for (int j = 1; j < grid.gridspan(); j++)
			{
				width += hgap + labelsWidth.get(i + j) + hgap;
			}
			
			int labelWidth = labelsWidth.get(i);
			int height = grid.layoutRow(
				helper, x, y, height(), baseline(), hgap, width, labelWidth);
			actualHeight = Math.max(actualHeight, height);
			// Position origin to the next grid
			// Note: we don't account for the current gridspan and actual grid
			// width because this will be accounted on next loop iteration through
			// the use of EmptySubGrid fakes
			x += labelWidth + hgap + gridWidth + gridgap;
		}
		return actualHeight;
	}
	// CSON: ParameterAssignment

	@Override List<JComponent> components()
	{
		return _components;
	}
	
	private ISubGrid findGrid(int index)
	{
		int i = 0;
		for (ISubGrid grid: _grids)
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
			for (ISubGrid grid : _grids)
			{
				if (index < current + grid.items().size())
				{
					return grid.items().get(index - current).component();
				}
				current += grid.items().size();
			}
			// We should normally never come there
			//FIXME clearer message just in case we pass here
			throw new IndexOutOfBoundsException();
		}

		@Override public int size()
		{
			int size = 0;
			for (ISubGrid grid : _grids)
			{
				size += grid.items().size();
			}
			return size;
		}
	}

	private SubGrid _current = null;
	final private List<ISubGrid> _grids = new ArrayList<ISubGrid>();
	final private ComponentList _components = new ComponentList();
	static final private ISubGrid NULL_GRID = new EmptySubGrid();
}
