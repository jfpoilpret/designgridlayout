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

import java.awt.Container;
import java.util.List;

import javax.swing.JComponent;

import net.java.dev.designgridlayout.internal.util.BaselineExtractor;
import net.java.dev.designgridlayout.internal.util.ComponentHelper;
import net.java.dev.designgridlayout.internal.util.IExtractor;
import net.java.dev.designgridlayout.internal.util.LayoutHelper;
import net.java.dev.designgridlayout.internal.util.LayoutLocker;
import net.java.dev.designgridlayout.internal.util.OrientationPolicy;
import net.java.dev.designgridlayout.internal.util.PrefHeightExtractor;
import net.java.dev.designgridlayout.internal.util.PrefWidthExtractor;
import net.java.dev.designgridlayout.policy.HeightGrowPolicy;

abstract public class AbstractRow
{
	// Called by DesignGridLayout immediately after instanciation
	public final void init(LayoutLocker locker, Container parent, 
		HeightGrowPolicy heightTester, OrientationPolicy orientation)
	{
		_locker = locker;
		_parent = parent;
		_heightTester = heightTester;
		_orientation = orientation;
	}
	
	// Used by children
	final protected Container parent()
	{
		return _parent;
	}

	// Used by children
	final protected HeightGrowPolicy growPolicy()
	{
		return _heightTester;
	}

	// Used by children
	final protected OrientationPolicy orientation()
	{
		return _orientation;
	}
	
	public final void setUnrelatedGap()
	{
		_unrelatedGap = true;
	}
	
	public final boolean hasUnrelatedGap()
	{
		return _unrelatedGap;
	}
	
	public final void extraHeight(int extraHeight)
	{
		_extraHeight = extraHeight;
	}
	
	public final int extraHeight()
	{
		return _extraHeight;
	}

	public final void vgap(int vgap)
	{
		_vgap = vgap;
	}

	public final int vgap()
	{
		return _vgap;
	}

	public final void init()
	{
		_extraHeight = 0;
		_maxWidth = ComponentHelper.maxValues(items(), PrefWidthExtractor.INSTANCE);
		_height = ComponentHelper.maxValues(allItems(), PrefHeightExtractor.INSTANCE);
		_baseline = ComponentHelper.maxValues(allItems(), BaselineExtractor.INSTANCE);
		boolean fixedHeight = ComponentHelper.isFixedHeight(_heightTester, items());
		if (fixedHeight || _growWeight == -1.0)
		{
			_growWeight = (fixedHeight ? 0.0 : 1.0);
		}
	}
	
	final protected void checkUnlocked() throws IllegalStateException
	{
		_locker.checkUnlocked();
	}

	public final int baseline()
	{
		return _baseline;
	}

	public final void baseline(int baseline)
	{
		_baseline = baseline;
	}

	final protected int maxWidth()
	{
		return _maxWidth;
	}

	public int height()
	{
		return _height;
	}
	
	public void actualHeight(int height)
	{
		_actualHeight = height;
	}

	public int actualHeight()
	{
		return _actualHeight;
	}

	public final void growWeight(double weight)
	{
		if (weight >= 0.0)
		{
			_growWeight = weight;
		}
	}

	public final double growWeight()
	{
		return _growWeight;
	}

	public int numGrids()
	{
		return 0;
	}
	
	public void totalGrids(int totalGrids)
	{
	}
	
	public int gridspan(int grid)
	{
		return 1;
	}
		
	public int gridColumns(int grid)
	{
		return 0;
	}

	public int labelWidth(int grid)
	{
		return 0;
	}

	public int maxColumnWidth(int grid, int maxColumns, IExtractor extractor)
	{
		return 0;
	}

	public int totalNonGridWidth(int hgap, IExtractor extractor)
	{
		return 0;
	}

	public int hgap()
	{
		return ComponentHelper.hgap(allItems(), parent());
	}

	public int gridgap()
	{
		return 0;
	}
	
	public boolean isEmpty()
	{
		return allItems().isEmpty();
	}

	public JComponent leftComponent()
	{
		return (allItems().isEmpty() ? null : allItems().get(0).component());
	}
	
	public JComponent rightComponent()
	{
		return (allItems().isEmpty() ? null 
			: allItems().get(allItems().size() - 1).component());
	}
	
	public abstract void checkSpanRows();

	public abstract List<? extends IRowItem> items();

	// Returns all items including potential labels
	public List<? extends IRowItem> allItems()
	{
		return items();
	}
	
	// Returns the actual extra height allocated to the row
	public abstract int layoutRow(LayoutHelper helper, int left, int hgap, int gridgap, 
		int rowWidth, int gridsWidth, List<Integer> labelsWidth);

	private LayoutLocker _locker;
	private Container _parent;
	private HeightGrowPolicy _heightTester;
	private OrientationPolicy _orientation;
	private boolean _unrelatedGap = false;
	private int _vgap = 0;
	private int _baseline;
	private int _height;
	private double _growWeight = -1.0;
	private int _maxWidth;
	private int _actualHeight;
	private int _extraHeight;
}
