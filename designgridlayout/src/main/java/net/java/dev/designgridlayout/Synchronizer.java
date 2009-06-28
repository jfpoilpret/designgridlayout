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

import net.java.dev.designgridlayout.internal.engine.SyncLayoutEngine;

/**
 * Utility class providing synchronization of alignments (vertical and/or
 * horizontal) between several {@link DesignGridLayout} instances.
 * <p/>
 * {@code Synchronizer} is particularly useful for layouts that are used in
 * wizard dialogs or in {@link javax.swing.JTabbedPane}s.
 * <p/>
 * {@code Synchronizer} uses a fluent API for setting up synchronization of
 * several {@link DesignGridLayout}s:
 * <pre>
 * Synchronizer.synchronize(layout1, layout2).alignGrids().alignRows();
 * </pre>
 * It is possible to align more than 2 layouts through the same
 * {@code Synchronizer} instance (useful for wizards and tabs):
 * <pre>
 * Synchronizer.synchronize(layout1, layout2).with(layout3).alignGrids().alignRows();
 * </pre>
 * It is also possible to synchronize the same layout horizontally with a
 * second layout, and vertically with a thrid layout, but in this case, 2
 * {@code Synchronizer}s must be used:
 * <pre>
 * Synchronizer.synchronize(layout1, layout2).alignRows();
 * Synchronizer.synchronize(layout1, layout3).alignGrids();
 * </pre>
 * {@code Synchronizer is supports:
 * <ul>
 * <li>alignment of grids x origin</li>
 * <li>alignment of rows baselines</li>
 * </ul>
 * TODO more details about policies for both kinds of alignements!
 * 
 * @author Jean-Francois Poilpret
 */
final public class Synchronizer
{
	/**
	 * TODO
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	static public Synchronizer synchronize(
		DesignGridLayout source, DesignGridLayout destination)
	{
		return new Synchronizer().with(source).with(destination);
	}
	
	public Synchronizer with(DesignGridLayout layout)
	{
		_engine.add(layout.getLayoutEngine());
		return this;
	}
	
	public Synchronizer alignAllGrids()
	{
		_engine.alignGrids(true);
		return this;
	}
	
	public Synchronizer alignFirstGrid()
	{
		_engine.alignGrids(false);
		return this;
	}
	
	//TODO several align methods
	// alignFirstRowOnly()
	// alignFirstFixedHeightRows()
	// alignAllRowsOneToOne()
	// alignBestEffort()?
	// ...?
	public Synchronizer alignRows()
	{
		_engine.alignRows(null);
		return this;
	}
	
	private Synchronizer()
	{
	}
	
	private final SyncLayoutEngine _engine = new SyncLayoutEngine();
}
