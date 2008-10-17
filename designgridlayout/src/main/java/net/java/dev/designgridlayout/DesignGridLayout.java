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

	static private HeightGrowPolicy _defaultHeightTester = new DefaultGrowPolicy();

	private HeightGrowPolicy _heightTester = _defaultHeightTester;
	
	final private Container _parent;
	final private DefaultOrientationPolicy _orientation;

	private Dimension _layoutSize = null;

	private int _top = 0;
	private int _left = 0;
	private int _bottom = 0;
	private int _right = 0;

	private int _hgap = 0;
	private int _labelWidth = 0;

	private double _totalWeight = 0.0;
	
	private int _explicitTop = MARGIN_DEFAULT;
	private int _explicitLeft = MARGIN_DEFAULT;
	private int _explicitBottom = MARGIN_DEFAULT;
	private int _explicitRight = MARGIN_DEFAULT;
	
	final private List<AbstractRow> _rowList = new ArrayList<AbstractRow>();

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
		_orientation = new DefaultOrientationPolicy(parent);
		reset();
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
	 * Forces the orientation of components inside this layout to be from left
	 * to right, whatever the orientation defined by the current
	 * {@code java.util.Locale}.
	 * <p/>
	 * <b>Note 1</b>: you normally never need to call this method because
	 * {@code DesignGridLayout} automatically detects and uses the orientation 
	 * defined by the current {@code java.util.Locale}. Calling this method is
	 * useful only if you want to override the default orientation.
	 * <b>Note 2</b>: calling this method will not affect the orientation of
	 * text inside components themselves!
	 * 
	 * @return this instance of DesignGridLayout, allowing for chained calls
	 * to other methods (also known as "fluent API")
	 */
	public DesignGridLayout leftToRight()
	{
		_orientation.setRightToLeft(false);
		return this;
	}
	
	/**
	 * Forces the orientation of components inside this layout to be from right
	 * to left, whatever the orientation defined by the current
	 * {@code java.util.Locale}.
	 * <p/>
	 * <b>Note 1</b>: you normally never need to call this method because
	 * {@code DesignGridLayout} automatically detects and uses the orientation 
	 * defined by the current {@code java.util.Locale}. Calling this method is
	 * useful only if you want to override the default orientation.
	 * <b>Note 2</b>: calling this method will not affect the orientation of
	 * text inside components themselves!
	 * 
	 * @return this instance of DesignGridLayout, allowing for chained calls
	 * to other methods (also known as "fluent API")
	 */
	public DesignGridLayout rightToLeft()
	{
		_orientation.setRightToLeft(true);
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
		return addRow(new LeftRow(_parent, _heightTester, _orientation));
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
		return addRow(new RightRow(_parent, _heightTester, _orientation));
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
		return addRow(new CenterRow(_parent, _heightTester, _orientation));
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
	public IGridRow row()
	{
		return addRow(new GridRow(_parent, _heightTester, _orientation));
	}

	/**
	 * Adds a new, fixed-height, empty row to the container. This row will never 
	 * contain any component and is used only for introducing vertical space 
	 * between rows or groups of rows.
	 * 
	 * @param height the height in pixels of the new created empty row.
	 */
	public void emptyRow(int height)
	{
		addRow(new GridRow(_parent, _heightTester, _orientation)).height(height);
	}
	
	private AbstractRow addRow(AbstractRow row)
	{
		_rowList.add(row);
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
					0, (parent.getHeight() - _layoutSize.height) / _totalWeight);
			}
			
			// Check layout orientation
			boolean rtl = _orientation.isRightToLeft();

			int x = left();
			int y = top();
			int parentWidth = parent.getWidth();
			int rowWidth = parentWidth - left() - right();
			LayoutHelper helper = new LayoutHelper(_heightTester, parentWidth, rtl);
			for (AbstractRow row: _rowList)
			{
				int extraHeight = (int) (row.heightGrowth() * totalExtraHeight); 
				if (row.items().size() > 0)
				{
					helper.setRowAvailableHeight(extraHeight + row.height());
					row.layoutRow(helper, x, y, _hgap, rowWidth, _labelWidth);
				}
				y += row.height() + extraHeight + row.vgap();
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		return preferredLayoutSize(parent);
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
		return _layoutSize;
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
		for (AbstractRow row: _rowList)
		{
			_hgap = Math.max(_hgap, row.hgap());
		}

		// Vertical gaps (per row)
		for (int nthRow = 0; nthRow < (_rowList.size() - 1); nthRow++)
		{
			int maxComboHeight = 0;
			int rowGap = 0;

			AbstractRow row = _rowList.get(nthRow);
			List<RowItem> items1 = row.items();
			List<RowItem> items2 = _rowList.get(nthRow + 1).items();

			for (int nthItems1 = 0; nthItems1 < items1.size(); nthItems1++)
			{
				JComponent upper = items1.get(nthItems1).component();
				int aboveHeight = upper.getPreferredSize().height;

				for (int nthItems2 = 0; nthItems2 < items2.size(); nthItems2++)
				{
					JComponent lower = items2.get(nthItems2).component();
					int belowHeight = lower.getPreferredSize().height;

					int gap = layoutStyle.getPreferredGap(
						upper, lower, LayoutStyle.RELATED, SwingConstants.SOUTH, _parent);
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
		if (_layoutSize != null)
		{
			return;
		}

		// Make sure there's something to do
		if (_rowList.size() < 1)
		{
			_layoutSize = new Dimension(0, 0);
			return;
		}

		// Prior to computation start, reset everything and
		// calculate margins and gutters
		reset();
		computeMargins();
		computeGutters();

		// First, reset each row and count the number of columns in the grid
		int maxColumns = countGridColumns();

		// Second, calculate label width
		computeLabelWidth();
		
		// Third, compute width to use for columns of grid
		int maxWidth = maxGridRowsColumnWidth(maxColumns);

		// Then, calculate the preferred total width
		int preferredWidth =
			maxWidth * maxColumns + left() + right() + (_hgap * (maxColumns - 1)) + 1;

		// Account for labels
		if (_labelWidth > 0)
		{
			preferredWidth += _labelWidth;
			// Be sure to add hgap if needed
			if (maxColumns > 0)
			{
				preferredWidth += _hgap;
			}
		}
		
		// Don't forget to account for the minimum width of non grid rows
		preferredWidth = Math.max(preferredWidth, totalNonGridWidth());

		// Total height
		int preferredHeight = totalHeight() + top() + bottom() + 1;

		// Calculate total height growth factor of all variable height rows
		_totalWeight = 0.0;
		for (AbstractRow row: _rowList)
		{
			_totalWeight += row.heightGrowth();
		}

		_layoutSize = new Dimension(preferredWidth, preferredHeight);
	}
	
	private int countGridColumns()
	{
		int maxColumns = 0;
		for (AbstractRow row: _rowList)
		{
			row.reset();
			// Note columns (sum item spans), not the count of components
			maxColumns = Math.max(maxColumns, row.gridColumns());
		}
		return maxColumns;
	}
	
	private void computeLabelWidth()
	{
		_labelWidth = 0;
		for (AbstractRow row: _rowList)
		{
			// Label width first
			_labelWidth = Math.max(_labelWidth, row.labelWidth());
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
	
	private int maxGridRowsColumnWidth(int maxColumns)
	{
		int maxWidth = 0;
		for (AbstractRow row: _rowList)
		{
			maxWidth = Math.max(maxWidth, row.maxColumnWidth(maxColumns));
		}
		return maxWidth;
	}

	private int totalNonGridWidth()
	{
		int maxWidth = 0;
		for (AbstractRow row: _rowList)
		{
			maxWidth = Math.max(maxWidth, row.totalWidth(_hgap));
		}
		return maxWidth + left() + right() + 1;
	}
	
	private void computeMargins()
	{
		// Make sure there's something to do
		if (_rowList.size() > 0)
		{
			// Handle top row
			computeTopMargin();
		
			// Handle bottom row
			computeBottomMargin();
		
			// Handle left-most and right-most columns
			computeLeftRightMargins();
		}
	}
	
	private void computeTopMargin()
	{
		AbstractRow topRow = _rowList.get(0);
		for (RowItem item: topRow.items())
		{
			int gap = getContainerGap(item.component(), SwingConstants.NORTH);
			_top = Math.max(_top, gap);
		}
	}

	private void computeBottomMargin()
	{
		int maxComboHeight = 0;
		int bottomGap = 0;
		AbstractRow bottomRow = _rowList.get(_rowList.size() - 1);
		for (RowItem item: bottomRow.items())
		{
			JComponent c = item.component();
			int height = c.getPreferredSize().height;
			int gap = getContainerGap(c, SwingConstants.SOUTH);
			int comboHeight = height + gap;
			if (comboHeight > maxComboHeight)
			{
				maxComboHeight = comboHeight;
				bottomGap = gap;
			}
		}
		_bottom = Math.max(_bottom, bottomGap);
	}
	
	private void computeLeftRightMargins()
	{
		for (AbstractRow row: _rowList)
		{
			List<RowItem> items = row.items();
			if (items.size() > 0)
			{
				JComponent left = items.get(0).component();
				_left = Math.max(_left, getContainerGap(left, SwingConstants.WEST));

				JComponent right = items.get(items.size() - 1).component();
				_right = Math.max(_right, getContainerGap(right, SwingConstants.EAST));
			}
		}
	}
	
	private void reset()
	{
		_layoutSize = null;
		_top = 0;
		_left = 0;
		_bottom = 0;
		_right = 0;
		_hgap = 0;
		_labelWidth = 0;
		_totalWeight = 0.0;
	}
}
