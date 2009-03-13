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

//TODO Javadoc
// Primary API to synchronize DesignGridLayout instances
final public class Synchronizer
{
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
	
	public Synchronizer alignGrids()
	{
		_engine.alignGrids();
		return this;
	}
	
	public Synchronizer alignRows()
	{
		_engine.alignRows();
		return this;
	}
	
	private Synchronizer()
	{
	}
	
	private final SyncLayoutEngine _engine = new SyncLayoutEngine();
}
