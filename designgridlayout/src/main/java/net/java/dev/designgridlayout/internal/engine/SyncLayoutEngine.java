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

package net.java.dev.designgridlayout.internal.engine;

import java.awt.Dimension;
import java.awt.Insets;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.java.dev.designgridlayout.internal.row.AbstractRow;
import net.java.dev.designgridlayout.internal.sync.DefaultLayoutSyncPolicy;
import net.java.dev.designgridlayout.internal.sync.ILayoutRowSyncPolicy;
import net.java.dev.designgridlayout.internal.sync.SimpleLayoutSyncPolicy;
import net.java.dev.designgridlayout.internal.sync.SyncLayoutInvocationHandler;
import net.java.dev.designgridlayout.internal.util.LayoutEngineProxy;

import static net.java.dev.designgridlayout.internal.util.RowHelper.each;

public class SyncLayoutEngine implements ILayoutEngine
{
	public void add(ILayoutEngine proxy)
	{
		ILayoutEngine source = LayoutEngineProxy.getDelegate(proxy);
		_engines.add(source);
		ILayoutEngine engine = (ILayoutEngine) Proxy.newProxyInstance(
			Thread.currentThread().getContextClassLoader(), 
			new Class<?>[]{ILayoutEngine.class}, 
			new SyncLayoutInvocationHandler(this, source));
		LayoutEngineProxy.setDelegate(proxy, engine);
	}

	public void alignGrids()
	{
		_alignGrids = true;
	}
	
	public void alignRows()
	{
		_alignRows = true;
	}
	
	// Implementation of ILayoutEngine
	//---------------------------------
	
	public void forceConsistentBaselinesDistance()
	{
		_current.forceConsistentBaselinesDistance();
	}
	
	public boolean mustForceConsistentBaselinesDistance()
	{
		return _current.mustForceConsistentBaselinesDistance();
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

	public double getTotalWeight()
	{
		preInit();
		return _current.getTotalWeight();
	}

	public List<AbstractRow> rows()
	{
		preInit();
		return _current.rows();
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
	
	public int computeRowsActualHeight(int height)
	{
		initialize();
		// Pass correct height to _current engine
		if (_alignRows)
		{
			//TODO Do we need to loop on every engine, can't we just use _current?
			int availableHeight = height;
			for (ILayoutEngine engine: _engines)
			{
//				int currentHeight = 
//					syncPolicy().availableHeight(height, _engines, _current);
				int currentHeight = 
					syncPolicy().availableHeight(height, _engines, engine);
				currentHeight = engine.computeRowsActualHeight(currentHeight);
				if (engine == _current)
				{
					availableHeight = currentHeight;
				}
			}
//			int currentHeight = 
//				syncPolicy().availableHeight(height, _engines, _current);
//			availableHeight = _current.computeRowsActualHeight(currentHeight);
			return availableHeight;
		}
		else
		{
			return _current.computeRowsActualHeight(height);
		}
	}
	
	public void layoutContainer(int width, int height)
	{
		int availableHeight = computeRowsActualHeight(height);
		if (_alignRows)
		{
			syncPolicy().synchronize(_engines);
		}
//		int availableHeight = computeRowsActualHeight(height);
		int referenceWidth = width;
		if (_alignGrids)
		{
			// Make sure that width is the same for all engines when it is
			// smaller than the minimum width for all of them
			referenceWidth = Math.max(width, _minimumSize.width);
		}
		_current.layoutContainer(referenceWidth, availableHeight);
	}

	// Package methods (used by SyncLayoutEngine)
	//--------------------------------------------
	public void setCurrentDelegate(ILayoutEngine engine)
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
		if (_alignRows)
		{
			_preferredSize.height = syncPolicy().preferredHeight(_engines);
			_minimumSize.height = _preferredSize.height;
		}
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
			source.height = destination.height;
		}
		return source;
	}
	
	private ILayoutRowSyncPolicy syncPolicy()
	{
		if (_policy == null)
		{
			// Check if all layouts have row weights mapping 1 to 1
			if (checkOneToOneRowMapping())
			{
				// Align all rows 1 to 1
				_policy = new SimpleLayoutSyncPolicy();
			}
			else
			{
				// Align only on first row baseline
				_policy = new DefaultLayoutSyncPolicy();
			}
		}
		return _policy;
	}
	
	private boolean checkOneToOneRowMapping()
	{
		// First check if no layout has variable height
		boolean allWeightsZero = true;
		List<RowWeightChecker> checkers = new ArrayList<RowWeightChecker>();
		for (ILayoutEngine engine: _engines)
		{
			RowWeightChecker checker = new RowWeightChecker(engine);
			checkers.add(checker);
			if (!checker.isFixedHeight())
			{
				allWeightsZero = false;
			}
		}
		if (allWeightsZero)
		{
			return true;
		}

		// At least one layout has variable height
		// We have to check if individual rows have similar weights
		double refWeight;
		do
		{
			refWeight = -1.0;
			for (RowWeightChecker checker: checkers)
			{
				double weight = checker.getWeight();
				if (weight != -1.0)
				{
					if (refWeight == -1.0)
					{
						refWeight = weight;
					}
					else if (weight != refWeight) 
					{
						if (	weight == 0.0
							||	refWeight == 0.0
							||	Math.abs((weight - refWeight) / refWeight) < SIMILAR_WEIGHTS)
						{
							return false;
						}
					}
				}
			}
		}
		while (refWeight != -1.0);

		return true;
	}
	
	static private class RowWeightChecker
	{
		public RowWeightChecker(ILayoutEngine engine)
		{
			_weight = engine.getTotalWeight();
			_next = each(engine.rows()).iterator();
		}
		
		public boolean isFixedHeight()
		{
			return _weight == 0.0;
		}
		
		public double getWeight()
		{
			if (_next.hasNext())
			{
				return _next.next().growWeight();
			}
			else
			{
				return -1.0;
			}
		}
		
		private final double _weight;
		private final Iterator<AbstractRow> _next;
	}
	
	static private final double SIMILAR_WEIGHTS = 0.1;

	private final List<ILayoutEngine> _engines = new ArrayList<ILayoutEngine>();
	private ILayoutRowSyncPolicy _policy = null;
	private ILayoutEngine _current = null;

	// Synchronization settings
	private boolean _alignGrids = false;
	private boolean _alignRows = false;

	// Synchronization computed properties
	private int _numGrids = 0;
	private List<Integer> _labelWidths = new ArrayList<Integer>();
	//TODO replace _margins with local variables!
	private Insets _margins = null;
	private Dimension _minimumSize = null;
	private Dimension _preferredSize = null;
}
