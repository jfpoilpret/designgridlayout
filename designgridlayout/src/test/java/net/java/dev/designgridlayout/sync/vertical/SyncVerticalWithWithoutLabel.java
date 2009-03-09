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

package net.java.dev.designgridlayout.sync.vertical;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.Synchronizer;
import net.java.dev.designgridlayout.sync.AbstractSyncLayoutExample;

public class SyncVerticalWithWithoutLabel extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		SyncVerticalWithWithoutLabel example = 
			new SyncVerticalWithWithoutLabel();
		example.go(true);
	}
	
	public SyncVerticalWithWithoutLabel()
	{
		super(true);
	}

	@Override protected void build()
	{
		DesignGridLayout layout1 = createSubPanel();
		layout1.row().grid().add(field("field1"));

		DesignGridLayout layout2 = createSubPanel();
		layout2.row().grid(label("label1")).add(field("field1"));

		Synchronizer.synchronize(layout1, layout2).alignGrids();
	}
}
