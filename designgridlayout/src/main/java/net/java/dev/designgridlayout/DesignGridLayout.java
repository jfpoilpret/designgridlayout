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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.layout.LayoutStyle;

/**
 * Swing LayoutManager that implements "Canonical Grids" as used by graphic 
 * artists to design magazines, posters, forms...
 * <p/>
 * Canonical grids are useful for, but not limited to, all kinds of forms mostly 
 * used in UI dialogs.
 * <p/>
 * DesignGridLayout provides a convenient API to have automatically well-balanced
 * canonical grids panels. With this API, there is no need for any graphical
 * designer.
 * <p/>
 * Typically, DesignGridLayout will be used as follows:
 * <pre>
 * public class MyPanel extends JPanel {
 *     public MyPanel() {
 *         DesignGridLayout layout = new DesignGridLayout(this);
 *         //...
 *         layout.row().grid(labelA).add(fieldA);
 *         layout.row().grid(labelB).add(fieldB);
 *         //...
 *         layout.row().center().add(okButton, cancelButton);
 *         }
 *         //...
 * </pre>
 * 
 * @author Jason Aaron Osgood
 * @author Jean-Francois Poilpret
 */
public class DesignGridLayout implements LayoutManager
{
	/**
	 * Builds a DesignGridLayout instance attached to a {@link Container}.
	 * This instance should be then used to add rows and components to the parent
	 * container.
	 * <p/>
	 * Note that this constructor auomatically calls {@code parent.setLayout(this)}
	 * so you don't need to call it yourself.
	 * <p/>
	 * In no way should the {@link Container#add} and {@link Container#remove}
	 * ever be used with {@code parent}.
	 * 
	 * @param parent the container for which we want to use DesignGridLayout; 
	 * cannot be {@code null}.
	 */
	public DesignGridLayout(Container parent)
	{
		if (parent == null)
		{
			throw new NullPointerException("parent cannot be null");
		}
		_parent = parent;
		_parent.setLayout(this);
		_orientation = new OrientationPolicy(parent);
	}

	/**
	 * Define special margins ratios for the parent container. You normally
	 * won't use this method because DesignGridLayout uses the best margin values 
	 * for the current used Look and Feel. However, it may be useful when you use
	 * DesignGridLayout in special containers such as views used in a 
	 * docking-based application, in which case you would rather avoid wasting
	 * too much space as margins for each single docked view.
	 * 
	 * @param top top margin ratio that will be applied to the standard top
	 * margin for the current platform
	 * @param left left margin ratio that will be applied to the standard left
	 * margin for the current platform
	 * @param bottom bottom margin ratio that will be applied to the standard 
	 * bottom margin for the current platform
	 * @param right right margin ratio that will be applied to the standard 
	 * right margin for the current platform
	 * @return this instance of DesignGridLayout, allowing for chained calls
	 * to other methods (also known as "fluent API")
	 */
	public DesignGridLayout margins(double top, double left, double bottom, double right)
	{
		_topWeight = (top < 0.0 ? 0.0 : top);
		_leftWeight = (left < 0.0 ? 0.0 : left);
		_bottomWeight = (bottom < 0.0 ? 0.0 : bottom);
		_rightWeight = (right < 0.0 ? 0.0 : right);
		return this;
	}
	
	/**
	 * Define a special margins ratio for the parent container. You normally
	 * won't use this method because DesignGridLayout uses the best margin values 
	 * for the current used Look and Feel. However, it may be useful when you use
	 * DesignGridLayout in special containers such as views used in a 
	 * docking-based application, in which case you would rather avoid wasting
	 * too much space as margins for each single docked view.
	 * 
	 * @param ratio the ratio to apply to each standard margin for the current 
	 * platform
	 * @return this instance of DesignGridLayout, allowing for chained calls
	 * to other methods (also known as "fluent API")
	 */
	public DesignGridLayout margins(double ratio)
	{
		return margins(ratio, ratio, ratio, ratio);
	}

	/**
	 * Creates a new row. The type of the row is not determined yet, but will
	 * be determined by the chained call performed on the returned 
	 * {@link IRowCreator}.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * <p/>
	 * Note that this method has no effect on the layout if there's no chained
	 * call on the returned {@link IRowCreator} (ie when you have code like
	 * {@code layout.row();}).
	 * 
	 * @return a new {@code IRowCreator} that must be used to set the actual 
	 * type of the created row.
	 */
	public IRowCreator row()
	{
		return new RowCreator(-1.0);
	}
	
	/**
	 * Creates a new row. The type of the row is not determined yet, but will
	 * be determined by the chained call performed on the returned 
	 * {@link IRowCreator}.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * <p/>
	 * This method explicitly sets the vertical growth weight for this row; this 
	 * is applicable only if this row contains at least one component that can 
	 * vary in height (eg JScrollPane, vertical JSlider); if this row has no such
	 * component, then calling this method will have no impact, ie this row will
	 * always have a fixed height.
	 * <p/>
	 * Note that this method has no effect on the layout if there's no chained
	 * call on the returned {@link IRowCreator} (ie when you have code like
	 * {@code layout.row();}).
	 * <p/>
	 * <b>Important!</b> Note that it is generally useless to use this method:
	 * DesignGridLayout will by default assign a weight of 1.0 to all rows that 
	 * should have a variable height, hence during resize, extra height will be
	 * equally split to all such rows. Use this method only if you want a row to
	 * get more ({@code verticalWeight > 1.0}) or less 
	 * ({@code verticalWeight < 1.0}) extra height than other rows.
	 * 
	 * @param verticalWeight the weight given to this row when DesignGridLayout
	 * distributes extra height during resize actions; must be {@code >= 0.0} or
	 * the value will be ignored.
	 * @return a new {@code IRowCreator} that must be used to set the actual 
	 * type of the created row.
	 * @see #row()
	 */
	public IRowCreator row(double verticalWeight)
	{
		return new RowCreator(verticalWeight);
	}
	
	/**
	 * Adds a new empty row to the container. This row will never contain any 
	 * component and is used only for introducing vertical space between rows or 
	 * groups of rows. The height of that row is automatically calculated based
	 * on the fact that the previously-added row and the next-added row contain
	 * unrelated components.
	 */
	public void emptyRow()
	{
		if (!_rowList.isEmpty())
		{
			AbstractRow current = _rowList.get(_rowList.size() - 1);
			current.vgap(-1);
		}
	}
	
	private <T extends AbstractRow> T addRow(T row, double verticalWeight)
	{
		_rowList.add(row);
		row.init(_parent, _heightTester, _orientation);
		row.growWeight(verticalWeight);
		return row;
	}

	/**
	 * Do not add components via the parent container's {@link Container#add(Component)}
	 * method, use directly DesignGridLayout API instead.
	 */
	public void addLayoutComponent(String constraint, Component component)
	{
		throw new IllegalArgumentException("Do not use this method");
	}

	/**
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
		if (_rowList.size() < 1)
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
			LayoutHelper helper = new LayoutHelper(_heightTester, parentWidth, rtl);
			for (AbstractRow row: _rowList)
			{
				helper.setY(y);
				int extraHeight = (int) (row.growWeight() * totalExtraHeight); 
				helper.setRowAvailableHeight(extraHeight + row.height());
				row.layoutRow(helper, x, _hgap, _gridgap, rowWidth, 
					gridsWidth, _labelWidths);
				y += row.height() + extraHeight + row.vgap();
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		checkParent(parent);
		//TODO check if the next line is really useful... Remove it if not
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
		//TODO check if the next line is really useful... Remove it if not
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
		return _parent.getInsets().top + (int)(_topWeight * _top);
	}

	private int left()
	{
		return _parent.getInsets().left + (int)(_leftWeight * _left);
	}

	private int bottom()
	{
		return _parent.getInsets().bottom + (int)(_bottomWeight * _bottom);
	}

	private int right()
	{
		return _parent.getInsets().right + (int)(_rightWeight * _right);
	}

	private int getContainerGap(JComponent component, int position)
	{
		LayoutStyle layoutStyle = LayoutStyle.getSharedInstance();
		return layoutStyle.getContainerGap(component, position, _parent);
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
	//FIXME vgaps calculation
	// To be absolutely correct, each component's actual layout position should
	// be determined by factoring component heights, component baselines, and 
	// each row's maximum height.
	// Problem is that this would have to be re-calculated every time... Maybe
	// it's better not to try to improve vgaps
	private void computeGutters()
	{
		LayoutStyle layoutStyle = LayoutStyle.getSharedInstance();

		// Handle horizontal gaps
		_hgap = 0;
		_gridgap = 0;
		for (AbstractRow row: _rowList)
		{
			_hgap = Math.max(_hgap, row.hgap());
			_gridgap = Math.max(_gridgap, row.gridgap());
		}

		// Vertical gaps (per row)
		for (int nthRow = 0; nthRow < (_rowList.size() - 1); nthRow++)
		{
			int maxComboHeight = 0;
			int rowGap = 0;

			AbstractRow row = _rowList.get(nthRow);
			List<JComponent> items1 = row.components();
			List<JComponent> items2 = _rowList.get(nthRow + 1).components();
			int style = (row.vgap() == -1 ? LayoutStyle.UNRELATED : LayoutStyle.RELATED);

			for (int nthItems1 = 0; nthItems1 < items1.size(); nthItems1++)
			{
				JComponent upper = items1.get(nthItems1);
				int aboveHeight = upper.getPreferredSize().height;

				for (int nthItems2 = 0; nthItems2 < items2.size(); nthItems2++)
				{
					JComponent lower = items2.get(nthItems2);
					int belowHeight = lower.getPreferredSize().height;

					int gap = layoutStyle.getPreferredGap(
						upper, lower, style, SwingConstants.SOUTH, _parent);
					int comboHeight = aboveHeight + gap + belowHeight;
					if (comboHeight > maxComboHeight)
					{
						maxComboHeight = comboHeight;
						rowGap = gap;
					}
				}
			}
			row.vgap(rowGap);
		}
	}

	private void initialize()
	{
		if (_preferredSize != null)
		{
			return;
		}

		// Make sure there's something to do
		if (_rowList.size() < 1)
		{
			_preferredSize = new Dimension(0, 0);
			_minimumSize = new Dimension(0, 0);
			return;
		}

		// Calculate margins and gutters
		computeMargins();
		computeGutters();

		// Now, initialize each row and count the number of grids & columns 
		// in the grids
		for (AbstractRow row: _rowList)
		{
			row.init();
		}
		countGrids();

		// Second, calculate labels width
		computeLabelWidths();

		// Compute preferred & minimum widths for each sub-grid (without labels), 
		// use largest width for all grids
		int preferredWidth = computeGridWidth(PrefWidthExtractor.INSTANCE);
		int minimumWidth = computeGridWidth(MinWidthExtractor.INSTANCE);

		// Total height
		int preferredHeight = totalHeight() + top() + bottom() + 1;

		// Calculate total height growth factor of all variable height rows
		_totalWeight = 0.0;
		for (AbstractRow row: _rowList)
		{
			_totalWeight += row.growWeight();
		}

		_preferredSize = new Dimension(preferredWidth, preferredHeight);
		_minimumSize = new Dimension(minimumWidth, preferredHeight);
	}
	
	private void countGrids()
	{
		// Calculate the actual number of sub-grids
		_maxGrids = 0;
		for (AbstractRow row: _rowList)
		{
			_maxGrids = Math.max(_maxGrids, row.numGrids());
		}
		// Inform each row about the total number of sub-grids
		for (AbstractRow row: _rowList)
		{
			row.totalGrids(_maxGrids);
		}
	}

	private int countGridColumns(int grid)
	{
		int maxColumns = 0;
		for (AbstractRow row: _rowList)
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
			for (AbstractRow row: _rowList)
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
		for (AbstractRow row: _rowList)
		{
			totalHeight += row.height() + row.vgap();
		}
		return totalHeight;
	}
	
	private int computeGridWidth(IExtractor extractor)
	{
		// Compute preferred width for each sub-grid (without labels), 
		// use largest width for all grids
		//FIXME: need to care for grid-span in calculation!
		int width = 0;
		for (int grid = 0; grid < _maxGrids; grid++)
		{
			int maxColumns = countGridColumns(grid);
	
			// Third, compute width to use for columns of grid
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
		for (AbstractRow row: _rowList)
		{
			maxWidth = Math.max(
				maxWidth, row.maxColumnWidth(grid, maxColumns, extractor));
		}
		return maxWidth;
	}

	private int totalNonGridWidth(IExtractor extractor)
	{
		int maxWidth = 0;
		for (AbstractRow row: _rowList)
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
		if (!_rowList.isEmpty())
		{
			AbstractRow topRow = _rowList.get(0);
			for (JComponent component: topRow.components())
			{
				int gap = getContainerGap(component, SwingConstants.NORTH);
				_top = Math.max(_top, gap);
			}
		}
	}

	private void computeBottomMargin()
	{
		_bottom = 0;
		if (!_rowList.isEmpty())
		{
			int maxComboHeight = 0;
			int bottomGap = 0;
			AbstractRow bottomRow = _rowList.get(_rowList.size() - 1);
			for (JComponent component: bottomRow.components())
			{
				int height = component.getPreferredSize().height;
				int gap = getContainerGap(component, SwingConstants.SOUTH);
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
	
	private void computeLeftRightMargins()
	{
		_left = 0;
		_right = 0;
		for (AbstractRow row: _rowList)
		{
			List<JComponent> components = row.components();
			if (components.size() > 0)
			{
				JComponent left = components.get(0);
				_left = Math.max(_left, getContainerGap(left, SwingConstants.WEST));

				JComponent right = components.get(components.size() - 1);
				_right = Math.max(_right, getContainerGap(right, SwingConstants.EAST));
			}
		}
	}
	
	private void reset()
	{
		_preferredSize = null;
	}

	// Returned by row()
	private class RowCreator implements IRowCreator
	{
		RowCreator(double weight)
		{
			_weight = weight;
		}
		
		public INonGridRow center()
        {
			return addRow(new CenterRow(), _weight);
        }

		public INonGridRow left()
        {
			return addRow(new LeftRow(), _weight);
        }

		public INonGridRow right()
        {
			return addRow(new RightRow(), _weight);
        }

		public IGridRow grid(JLabel label)
        {
			return addRow(new GridRow(), _weight).grid(label);
        }

		public IGridRow grid(JLabel label, int gridspan)
        {
			return addRow(new GridRow(), _weight).grid(label, gridspan);
        }

		public IGridRow grid()
        {
			return addRow(new GridRow(), _weight).grid();
        }

		public IGridRow grid(int gridspan)
        {
			return addRow(new GridRow(), _weight).grid(gridspan);
        }

		private final double _weight;
	}

	static private HeightGrowPolicy _defaultHeightTester = new DefaultGrowPolicy();

	private HeightGrowPolicy _heightTester = _defaultHeightTester;
	
	final private Container _parent;
	final private OrientationPolicy _orientation;

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
	
	final private List<AbstractRow> _rowList = new ArrayList<AbstractRow>();
	final private List<Integer> _labelWidths = new ArrayList<Integer>();
	private int _totalLabelWidth;
	private int _maxGrids;
}
