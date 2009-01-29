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

import java.awt.Dimension;
import java.awt.Insets;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SyncLayoutEngine implements ILayoutEngine
{
	void add(DesignGridLayout layout)
	{
		ILayoutEngine source = layout.getLayoutEngine();
		_engines.add(source);
		ILayoutEngine engine = (ILayoutEngine) Proxy.newProxyInstance(
			Thread.currentThread().getContextClassLoader(), 
			new Class<?>[]{ILayoutEngine.class}, 
			new SyncLayoutInvocationHandler(this, source));
		layout.setLayoutEngine(engine);
	}
	void alignGrids()
	{
		_alignGrids = true;
	}
	
	void alignRows()
	{
		_alignRows = true;
	}
	
	// Implementation of ILayoutEngine
	//---------------------------------
	
	public void forceConsistentVGaps()
	{
		_current.forceConsistentVGaps();
	}

	public void margins(double top, double left, double bottom, double right)
	{
		_current.margins(top, left, bottom, right);
	}

	public void reset()
	{
		_preferredSize = null;
		_minimumSize = null;
		_numGrids = 0;
		_margins = new Insets(0, 0, 0, 0);
		for (ILayoutEngine engine: _engines)
		{
			engine.reset();
		}
	}

	public int getNumGrids()
	{
		return _current.getNumGrids();
	}

	public List<Integer> getLabelWidths()
    {
		preInit();
		return _current.getLabelWidths();
    }
	
	public Insets getMargins()
	{
		preInit();
		return _current.getMargins();
	}
	
	public int hgap()
	{
		preInit();
		return _current.hgap();
	}
	
	public void hgap(int hgap)
	{
		for (ILayoutEngine engine: _engines)
		{
			engine.hgap(hgap);
		}
	}

	public List<AbstractRow> rows()
	{
		preInit();
		return _current.rows();
	}

	public void setMapRowsPosition(Map<String, Integer> rowsPosition)
	{
		_rowsPosition = rowsPosition;
	}
	
	public Dimension getMinimumSize()
	{
		initialize();
		Dimension min = _current.getMinimumSize();
		return computeDimension(min, _minimumSize);
	}
	
	public Dimension getPreferredSize()
	{
		initialize();
		Dimension pref = _current.getPreferredSize();
		return computeDimension(pref, _preferredSize);
	}
	
	public void layoutContainer()
	{
		initialize();
		//TODO this is where the whole complex stuff begins!
		if (_alignRows)
		{
			//TODO later
			// Align the first row always
			
			// Then best effort for aligning other rows, based on their heights
			
		}
		_current.layoutContainer();
	}

	// Package methods (used by SyncLayoutEngine)
	//--------------------------------------------
	void setCurrentDelegate(ILayoutEngine engine)
	{
		// Note that all methods are supposed to be called in EDT, hence no
		// threads contention is expected, so we have a very simple 
		// implementation here
		//TODO make it thread-safe (lots of developers call Swing out of EDT...)
		_current = engine;
	}
	
	// Private methods
	//-----------------
	
	private void preInit()
	{
		if (_preferredSize != null)
		{
			return;
		}

		if (_alignGrids)
		{
			countGrids();
			computeLabelWidths();
			computeHorizontalMargins();
			computeHorizontalGutters();
		}
		if (_alignRows)
		{
			computeRowsAlignment();
			computeVerticalMargins();
		}
	}
	
	private void countGrids()
	{
		// Calculate common number of grids
		for (ILayoutEngine engine: _engines)
		{
			int grids = engine.getNumGrids();
			if (_numGrids == 0)
			{
				_numGrids = grids;
			}
			else if (grids != _numGrids)
			{
				_numGrids = 1;
				break;
			}
		}
	}

	private void computeLabelWidths()
	{
		// Then compute label widths
		_labelWidths.clear();
		for (int i = 0; i < _numGrids; i++)
		{
			_labelWidths.add(0);
		}
		for (ILayoutEngine engine: _engines)
		{
			List<Integer> labels = engine.getLabelWidths();
			for (int i = 0; i < _numGrids; i++)
			{
				int width = Math.max(labels.get(i), _labelWidths.get(i));
				_labelWidths.set(i, width);
			}
		}
		// Make sure all LayoutEngines have the correct label widths
		for (ILayoutEngine engine: _engines)
		{
			List<Integer> labels = engine.getLabelWidths();
			for (int i = 0; i < _numGrids; i++)
			{
				labels.set(i, _labelWidths.get(i));
			}
		}
	}
	
	private void computeHorizontalMargins()
	{
		for (ILayoutEngine engine: _engines)
		{
			Insets margins = engine.getMargins();
			_margins.left = Math.max(_margins.left, margins.left);
			_margins.right = Math.max(_margins.right, margins.right);
		}
		// Make sure margins are the same for all engines
		for (ILayoutEngine engine: _engines)
		{
			Insets margins = engine.getMargins();
			margins.left = _margins.left;
			margins.right = _margins.right;
		}
	}
	
	private void computeVerticalMargins()
	{
		for (ILayoutEngine engine: _engines)
		{
			Insets margins = engine.getMargins();
			_margins.top = Math.max(_margins.top, margins.top);
			_margins.bottom = Math.max(_margins.bottom, margins.bottom);
		}
		// Make sure margins are the same for all engines
		for (ILayoutEngine engine: _engines)
		{
			Insets margins = engine.getMargins();
			margins.top = _margins.top;
			margins.bottom = _margins.bottom;
		}
	}
	
	private void computeHorizontalGutters()
	{
		int hgap = 0;
		for (ILayoutEngine engine: _engines)
		{
			hgap = Math.max(hgap, engine.hgap());
		}
		for (ILayoutEngine engine: _engines)
		{
			engine.hgap(hgap);
		}
	}
	
	private void computeRowsAlignment()
	{
		boolean hasName = false;
		// Check if at least one row is named
		for (ILayoutEngine engine: _engines)
		{
			for (AbstractRow row: engine.rows())
			{
				if (row.name() != null)
				{
					hasName = true;
					break;
				}
			}
		}
		if (!hasName)
		{
			computeFirstRowBaseline();
		}
	}
	
	private void computeFirstRowBaseline()
	{
		int baseline = 0;
		for (ILayoutEngine engine: _engines)
		{
			for (AbstractRow row: engine.rows())
			{
				baseline = Math.max(baseline, row.baseline());
				break;
			}
		}
		// Make sure baseline is consistent across engines
		for (ILayoutEngine engine: _engines)
		{
			for (AbstractRow row: engine.rows())
			{
				row.baseline(baseline);
				break;
			}
		}
	}
	
	private void initialize()
    {
		preInit();

		if (_preferredSize != null)
		{
			return;
		}

		// Calculate potential (ie considering that layouts are synchronized
		// vertically and horizontally) min and pref sizes. Real dimensions will
		// be calculated when required
		_minimumSize = new Dimension(0, 0);
		_preferredSize = new Dimension(0, 0);
		for (ILayoutEngine engine: _engines)
		{
			Dimension min = engine.getMinimumSize();
			_minimumSize.width = Math.max(_minimumSize.width, min.width);
			_minimumSize.height = Math.max(_minimumSize.height, min.height);
			Dimension pref = engine.getPreferredSize();
			_preferredSize.width = Math.max(_preferredSize.width, pref.width);
			_preferredSize.height = Math.max(_preferredSize.height, pref.height);
		}
		//TODO other early initialization there?
    }
	
	private Dimension computeDimension(Dimension source, Dimension destination)
	{
		if (_alignGrids)
		{
			// Consistent width
			source.width = Math.max(source.width, destination.width);
		}
		if (_alignRows)
		{
			// Consistent height
			source.height = Math.max(source.height, destination.height);
		}
		return source;
	}

	private final List<ILayoutEngine> _engines = new ArrayList<ILayoutEngine>();
//	private final Map<ILayoutEngine, SyncRowMapping> _engines = 
//		new LinkedHashMap<ILayoutEngine, SyncRowMapping>();
	private ILayoutEngine _current = null;

	// Synchronization settings
	private boolean _alignGrids = false;
	private boolean _alignRows = false;

	// Synchronization computed properties
	private int _numGrids = 0;
	private List<Integer> _labelWidths = new ArrayList<Integer>();
	private Insets _margins = null;
	private Dimension _minimumSize = null;
	private Dimension _preferredSize = null;
	//TODO remove initialization?
	private Map<String, Integer> _rowsPosition = null;
}
