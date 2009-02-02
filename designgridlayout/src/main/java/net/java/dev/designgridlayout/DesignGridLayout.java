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

package net.java.dev.designgridlayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

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
		_orientation = new OrientationPolicy(parent);
		_engine = new LayoutEngine(
			_locker, _parent, _rows, _orientation, _heightTester);
		_parent.setLayout(this);
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
	 * @return {@code this} instance of DesignGridLayout, allowing for chained 
	 * calls to other methods (also known as "fluent API")
	 * @throws IllegalStateException if this layout has been locked (which happens
	 * automatically the first its container frame is packed or displayed)
	 */
	public DesignGridLayout margins(double top, double left, double bottom, double right)
		throws IllegalStateException
	{
		_locker.checkUnlocked();
		_engine.margins(top, left, bottom, right);
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
	 * @return {@code this} instance of DesignGridLayout, allowing for chained 
	 * calls to other methods (also known as "fluent API")
	 * @throws IllegalStateException if this layout has been locked (which happens
	 * automatically the first its container frame is packed or displayed)
	 */
	public DesignGridLayout margins(double ratio) throws IllegalStateException
	{
		return margins(ratio, ratio, ratio, ratio);
	}
	
	/**
	 * Disable DesignGridLayout "smart vertical resize" feature. This means that
	 * all variable height components in {@code this} layout will have their
	 * height take every single available pixel, possibly showing partial
	 * information (e.g. a {@code JTable} would show its last row truncated).
	 * <p/>
	 * <b>IMPORTANT NOTE!</b> This method should be called before adding any
	 * row to {@code this} layout, otherwise results are unpredictable. This
	 * will not be considered a bug and, as such, will not be fixed in future
	 * versions (or just as a side effect of potential future refactoring).
	 * <p/>
	 * <b>WARNING!</b> You should avoid using this method at all costs since it
	 * gives your application a poor user experience. It was added as a special
	 * request (issue #34) from one DesignGridLayout user.
	 * 
	 * @return {@code this} instance of DesignGridLayout, allowing for chained 
	 * calls to other methods (also known as "fluent API")
	 */
	public DesignGridLayout disableSmartVerticalResize()
	{
		_locker.checkUnlocked();
		if (!(_heightTester.getDelegate() instanceof UnitHeightGrowPolicy))
		{
			_heightTester.setDelegate(
				new UnitHeightGrowPolicy(_heightTester.getDelegate()));
		}
		return this;
	}

	/**
	 * Requires to use consistent vertical gaps between rows (ie the vertical gap
	 * between rows will be same for every pair of 2 consecutive rows, provided
	 * that there is no {@link #emptyRow()} call between this pair of rows.
	 * <p/>
	 * Forcing consistent gaps changes the global balance of forms: on one hand,
	 * it seems to make all consecutive labels equidistant, on the other hand,
	 * it seems to introduce too much space between actual components (fields,
	 * checkboxes...) Hence, this is only provided as an option. DesignGridLayout
	 * defaults to using the actual vertical gap between each pair of rows.
	 * 
	 * @return {@code this} instance of DesignGridLayout, allowing for chained 
	 * calls to other methods (also known as "fluent API")
	 * @throws IllegalStateException if this layout has been locked (which happens
	 * automatically the first its container frame is packed or displayed)
	 */
	public DesignGridLayout forceConsistentVGaps() throws IllegalStateException
	{
		_locker.checkUnlocked();
		_engine.forceConsistentVGaps();
		return this;
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
	 * @throws IllegalStateException if this layout has been locked (which happens
	 * automatically the first its container frame is packed or displayed)
	 */
	public IRowCreator row() throws IllegalStateException
	{
		_locker.checkUnlocked();
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
	 * @throws IllegalStateException if this layout has been locked (which happens
	 * automatically the first its container frame is packed or displayed)
	 * @see #row()
	 */
	public IRowCreator row(double verticalWeight) throws IllegalStateException
	{
		_locker.checkUnlocked();
		return new RowCreator(verticalWeight);
	}
	
	/**
	 * Adds a new empty row to the container. This row will never contain any 
	 * component and is used only for introducing vertical space between rows or 
	 * groups of rows. The height of that row is automatically calculated based
	 * on the fact that the previously-added row and the next-added row contain
	 * unrelated components.
	 * 
	 * @throws IllegalStateException if this layout has been locked (which happens
	 * automatically the first its container frame is packed or displayed)
	 */
	public void emptyRow() throws IllegalStateException
	{
		_locker.checkUnlocked();
		if (_current != null)
		{
			_current.setUnrelatedGap();
		}
	}
	
	private <T extends AbstractRow> T addRow(T row, double verticalWeight)
		throws IllegalStateException
	{
		_locker.checkUnlocked();
		_current = row;
		_rows.add(row);
		row.init(_locker, _parent, _heightTester, _orientation);
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
		_engine.layoutContainer(_parent.getWidth(), _parent.getHeight());
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		checkParent(parent);
		return _engine.getMinimumSize();
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent)
	{
		checkParent(parent);
		return _engine.getPreferredSize();
	}
	
	private void checkParent(Container parent)
	{
		if (parent != _parent)
		{
			throw new IllegalArgumentException(
				"must use DesignGridLayout instance with original parent container");
		}
		if (!_parent.isValid())
		{
			_engine.reset();
		}
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

		public ISpannableGridRow grid(JLabel label)
		{
			return addRow(newGridRow(), _weight).grid(label);
		}

		public IGridRow grid(JLabel label, int gridspan)
		{
			return addRow(newGridRow(), _weight).grid(label, gridspan);
		}

		public ISpannableGridRow grid()
		{
			return addRow(newGridRow(), _weight).grid();
		}

		public IGridRow grid(int gridspan)
		{
			return addRow(newGridRow(), _weight).grid(gridspan);
		}
		
		private GridRow newGridRow()
		{
			if (!_rows.isEmpty())
			{
				AbstractRow previous = _rows.get(_rows.size() - 1);
				if (previous instanceof GridRow)
				{
					return new GridRow((GridRow) previous);
				}
			}
			return new GridRow(null);
		}

		private final double _weight;
	}
	
	ILayoutEngine getLayoutEngine()
	{
		return _engine;
	}

	void setLayoutEngine(ILayoutEngine engine)
	{
		_engine = engine;
	}
	
	static private HeightGrowPolicy _defaultHeightTester = new DefaultGrowPolicy();

	private HeightGrowPolicyProxy _heightTester = 
		new HeightGrowPolicyProxy(_defaultHeightTester);
	
	final private Container _parent;
	final private OrientationPolicy _orientation;
	final private LayoutLocker _locker = new LayoutLocker();
	private ILayoutEngine _engine;
	private AbstractRow _current = null;
	final private List<AbstractRow> _rows = new ArrayList<AbstractRow>();
}
