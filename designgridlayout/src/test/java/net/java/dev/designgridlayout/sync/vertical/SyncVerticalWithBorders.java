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

import javax.swing.border.TitledBorder;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.Synchronizer;
import net.java.dev.designgridlayout.sync.AbstractSyncLayoutExample;

public class SyncVerticalWithBorders extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		SyncVerticalWithBorders example = 
			new SyncVerticalWithBorders();
		example.go(true);
	}
	
	public SyncVerticalWithBorders()
	{
		super(true);
	}

	@Override protected void build()
	{
		DesignGridLayout layout1 = createSubPanel(new TitledBorder("Border"));
		layout1.row().grid(label("lbl1")).add(field("field1"));
		layout1.row().grid(label("lbl2")).add(field("field2")).grid(label("lbl3")).add(field("field3"));

		DesignGridLayout layout2 = createSubPanel();
		layout2.row().grid(label("label1")).add(field("field1"));
		layout2.row().grid(label("lbl2")).add(field("field2")).grid(label("label3")).add(field("field3"));

		Synchronizer.synchronize(layout1, layout2).alignAllGrids();
	}
}
