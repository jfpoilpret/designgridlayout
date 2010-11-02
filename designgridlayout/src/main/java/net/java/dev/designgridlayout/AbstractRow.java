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

import java.awt.Container;
import java.util.List;

import javax.swing.JComponent;

abstract class AbstractRow
{
	// Called by DesignGridLayout immediately after instanciation
	final void init(
		Container parent, HeightGrowPolicy heightTester, OrientationPolicy orientation)
	{
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
	
	final void setUnrelatedGap()
	{
		_unrelatedGap = true;
	}
	
	final boolean hasUnrelatedGap()
	{
		return _unrelatedGap;
	}

	final void vgap(int vgap)
	{
		_vgap = vgap;
	}

	final int vgap()
	{
		return _vgap;
	}

	final void init()
	{
		_maxWidth = ComponentHelper.maxValues(items(), PrefWidthExtractor.INSTANCE);
		_height = ComponentHelper.maxValues(allItems(), PrefHeightExtractor.INSTANCE);
		_baseline = ComponentHelper.maxValues(allItems(), BaselineExtractor.INSTANCE);
		boolean fixedHeight = ComponentHelper.isFixedHeight(_heightTester, items());
		if (fixedHeight || _growWeight == -1.0)
		{
			_growWeight = (fixedHeight ? 0.0 : 1.0);
		}
	}
	
	void setLabelAlignment(LabelAlignment align)
	{
	}

	final protected int baseline()
	{
		return _baseline;
	}

	final protected int maxWidth()
	{
		return _maxWidth;
	}

	int height()
	{
		return _height;
	}
	
	void actualHeight(int height)
	{
		_actualHeight = height;
	}

	int actualHeight()
	{
		return _actualHeight;
	}

	final void growWeight(double weight)
	{
		if (weight >= 0.0)
		{
			_growWeight = weight;
		}
	}

	final double growWeight()
	{
		return _growWeight;
	}

	int numGrids()
	{
		return 0;
	}
	
	void totalGrids(int totalGrids)
	{
	}
	
	int gridspan(int grid)
	{
		return 1;
	}
		
	int gridColumns(int grid)
	{
		return 0;
	}

	int labelWidth(int grid)
	{
		return 0;
	}

	int maxColumnWidth(int grid, int maxColumns, IExtractor extractor)
	{
		return 0;
	}

	int totalNonGridWidth(int hgap, int unrelhgap)
	{
		return 0;
	}

	int componentNonGridWidth()
	{
		return 0;
	}

	void forceComponentNonGridWidth(int width)
	{
	}

	int hgap()
	{
		return ComponentHelper.hgap(allItems(), parent());
	}

	int unrelhgap()
	{
		return ComponentHelper.unrelhgap(allItems(), parent());
	}

	int gridgap()
	{
		return 0;
	}
	
	boolean isEmpty()
	{
		return allItems().isEmpty();
	}

	JComponent leftComponent()
	{
		return (allItems().isEmpty() ? null : allItems().get(0).component());
	}
	
	JComponent rightComponent()
	{
		return (allItems().isEmpty() ? null 
			: allItems().get(allItems().size() - 1).component());
	}
	
	void checkSpanRows()
	{
	}

	abstract List<? extends IRowItem> items();

	// Returns all items including potential labels
	List<? extends IRowItem> allItems()
	{
		return items();
	}
	
	// Returns the actual extra height allocated to the row
	//CSOFF: ParameterNumber
	abstract int layoutRow(LayoutHelper helper, int left, int hgap, int gridgap, 
		int unrelhgap, int rowWidth, int gridsWidth, List<Integer> labelsWidth);
	//CSON: ParameterNumber

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
}
