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
 *         layout.row().label(labelA).add(fieldA);
 *         layout.row().label(labelB).add(fieldB);
 *         //...
 *         layout.centerRow().add(okButton, cancelButton);
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
	 * Use this constant with {@link #margins(int, int, int, int)} method if you 
	 * want to keep the calculated margins for one or more of top, left, bottom 
	 * or right margins values.
	 */
	static final public int MARGIN_DEFAULT = -1;

	/**
	 * Builds a DesignGridLayout instance attached to a {@link Container}.
	 * This instance should be then used to add rows and components to the parent
	 * container.
	 * <p/>
	 * Note that this constructor auomatically calls {@code parent.setLayout(this}
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
	 * Define special margins (in pixels) for the parent container. You normally
	 * won't use this method because DesignGridLayout uses the best margin values 
	 * for the current used Look and Feel. However, it may be useful when you use
	 * DesignGridLayout in special containers such as views used in a 
	 * docking-based application, in which case you would rather avoid wasting
	 * too much space as margins for each single docked view.
	 * 
	 * @param top top margin in pixels
	 * @param left left margin in pixels
	 * @param bottom bottom margin in pixels
	 * @param right right margin in pixels
	 * @return this instance of DesignGridLayout, allowing for chained calls
	 * to other methods (also known as "fluent API")
	 */
	public DesignGridLayout margins(int top, int left, int bottom, int right)
	{
		_explicitTop = top;
		_explicitLeft = left;
		_explicitBottom = bottom;
		_explicitRight = right;
		return this;
	}

	/**
	 * Adds a left-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several LayoutManager for one
	 * single dialog.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new left-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 */
	public INonGridRow leftRow()
	{
		return leftRow(-1.0);
	}

	/**
	 * Adds a left-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several LayoutManager for one
	 * single dialog.
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
	 * <b>Important!</b> Note that it is generally useless to use this method:
	 * DesignGridLayout will by default assign a weight of 1.0 to all rows that 
	 * should have a variable height, hence during resize, extra height will be
	 * equally split to all such rows. Use this method only if you want a row to
	 * get more ({@code weight > 1.0}) or less ({@code weight < 1.0}) extra 
	 * height than other rows.
	 * 
	 * @param verticalWeight the weight given to this row when DesignGridLayout
	 * distributes extra height during resize actions; must be {@code >= 0.0} or
	 * the value will be ignored.
	 * @return a new left-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 * @see #leftRow()
	 */
	public INonGridRow leftRow(double verticalWeight)
	{
		return addRow(new LeftRow(), verticalWeight);
	}

	/**
	 * Adds a right-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several LayoutManager for one
	 * single dialog.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new right-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 */
	public INonGridRow rightRow()
	{
		return rightRow(-1.0);
	}

	/**
	 * Adds a right-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several LayoutManager for one
	 * single dialog.
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
	 * <b>Important!</b> Note that it is generally useless to use this method:
	 * DesignGridLayout will by default assign a weight of 1.0 to all rows that 
	 * should have a variable height, hence during resize, extra height will be
	 * equally split to all such rows. Use this method only if you want a row to
	 * get more ({@code weight > 1.0}) or less ({@code weight < 1.0}) extra 
	 * height than other rows.
	 * 
	 * @param verticalWeight the weight given to this row when DesignGridLayout
	 * distributes extra height during resize actions; must be {@code >= 0.0} or
	 * the value will be ignored.
	 * @return a new right-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 * @see #rightRow()
	 */
	public INonGridRow rightRow(double verticalWeight)
	{
		return addRow(new RightRow(), verticalWeight);
	}

	/**
	 * Adds a center-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several LayoutManager for one
	 * single dialog.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new center-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 */
	public INonGridRow centerRow()
	{
		return centerRow(-1.0);
	}

	/**
	 * Adds a center-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several LayoutManager for one
	 * single dialog.
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
	 * <b>Important!</b> Note that it is generally useless to use this method:
	 * DesignGridLayout will by default assign a weight of 1.0 to all rows that 
	 * should have a variable height, hence during resize, extra height will be
	 * equally split to all such rows. Use this method only if you want a row to
	 * get more ({@code weight > 1.0}) or less ({@code weight < 1.0}) extra 
	 * height than other rows.
	 * 
	 * @param verticalWeight the weight given to this row when DesignGridLayout
	 * distributes extra height during resize actions; must be {@code >= 0.0} or
	 * the value will be ignored.
	 * @return a new center-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 * @see #centerRow()
	 */
	public INonGridRow centerRow(double verticalWeight)
	{
		return addRow(new CenterRow(), verticalWeight);
	}

	/**
	 * Adds a grid-aligned row. This row is using a canonical grid for 
	 * determining size and location of components in the row.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new grid-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 */
	public ISubGridStarter row()
	{
		return row(-1.0);
	}

	/**
	 * Adds a grid-aligned row. This row is using a canonical grid for 
	 * determining size and location of components in the row.
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
	 * <b>Important!</b> Note that it is generally useless to use this method:
	 * DesignGridLayout will by default assign a weight of 1.0 to all rows that 
	 * should have a variable height, hence during resize, extra height will be
	 * equally split to all such rows. Use this method only if you want a row to
	 * get more ({@code weight > 1.0}) or less ({@code weight < 1.0}) extra 
	 * height than other rows.
	 * 
	 * @param verticalWeight the weight given to this row when DesignGridLayout
	 * distributes extra height during resize actions; must be {@code >= 0.0} or
	 * the value will be ignored.
	 * @return a new grid-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 * @see #row()
	 */
	public ISubGridStarter row(double verticalWeight)
	{
		return addRow(new GridRow(), verticalWeight);
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
		return (_explicitTop <= MARGIN_DEFAULT ? _top : _explicitTop);
	}

	private int left()
	{
		return (_explicitLeft <= MARGIN_DEFAULT ? _left : _explicitLeft);
	}

	private int bottom()
	{
		return (_explicitBottom <= MARGIN_DEFAULT ? _bottom : _explicitBottom);
	}

	private int right()
	{
		return (_explicitRight <= MARGIN_DEFAULT ? _right : _explicitRight);
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
			return;
		}

		// Prior to computation start, reset everything and
		// calculate margins and gutters
		reset();
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

		// Compute preferred width for each sub-grid (without labels), 
		// use largest width for all grids
		//FIXME: need to care for grid-span in calculation!
		int preferredWidth = 0;
		for (int grid = 0; grid < _maxGrids; grid++)
		{
			int maxColumns = countGridColumns(grid);
	
			// Third, compute width to use for columns of grid
			int maxWidth = maxGridRowsColumnWidth(grid, maxColumns);
	
			// Then, calculate the preferred width for that grid
			int gridWidth = maxWidth * maxColumns + (_hgap * (maxColumns - 1)) + 1;
	
			preferredWidth = Math.max(preferredWidth, gridWidth);
		}
		// Now use preferred width for each subgrid
		preferredWidth *= _maxGrids;
		
		// Account for labels
		if (_totalLabelWidth > 0)
		{
			preferredWidth += _totalLabelWidth + (_maxGrids * _hgap);
		}

		// Add gaps between grids
		// first hgap is correct (label to 1st comp), 2nd gap should be bigger
		preferredWidth += (_maxGrids - 1) * _gridgap;
		
		// Add left and right margins
		preferredWidth += left() + right();

		// Don't forget to account for the minimum width of non grid rows
		preferredWidth = Math.max(preferredWidth, totalNonGridWidth());

		// Total height
		int preferredHeight = totalHeight() + top() + bottom() + 1;

		// Calculate total height growth factor of all variable height rows
		_totalWeight = 0.0;
		for (AbstractRow row: _rowList)
		{
			_totalWeight += row.growWeight();
		}

		_preferredSize = new Dimension(preferredWidth, preferredHeight);
		//TODO: to be improved for Issue #18
		_minimumSize = _preferredSize;
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
	
	private int maxGridRowsColumnWidth(int grid, int maxColumns)
	{
		int maxWidth = 0;
		for (AbstractRow row: _rowList)
		{
			maxWidth = Math.max(maxWidth, row.maxColumnWidth(grid, maxColumns));
		}
		return maxWidth;
	}

	private int totalNonGridWidth()
	{
		int maxWidth = 0;
		for (AbstractRow row: _rowList)
		{
			maxWidth = Math.max(maxWidth, row.totalNonGridWidth(_hgap));
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
	
	private int _explicitTop = MARGIN_DEFAULT;
	private int _explicitLeft = MARGIN_DEFAULT;
	private int _explicitBottom = MARGIN_DEFAULT;
	private int _explicitRight = MARGIN_DEFAULT;
	
	final private List<AbstractRow> _rowList = new ArrayList<AbstractRow>();
	final private List<Integer> _labelWidths = new ArrayList<Integer>();
	private int _totalLabelWidth;
	private int _maxGrids;
}
