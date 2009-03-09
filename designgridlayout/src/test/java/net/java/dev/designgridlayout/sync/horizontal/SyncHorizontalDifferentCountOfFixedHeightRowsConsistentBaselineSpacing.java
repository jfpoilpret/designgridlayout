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

package net.java.dev.designgridlayout.sync.horizontal;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.Synchronizer;
import net.java.dev.designgridlayout.sync.AbstractSyncLayoutExample;

public class SyncHorizontalDifferentCountOfFixedHeightRowsConsistentBaselineSpacing 
	extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		SyncHorizontalDifferentCountOfFixedHeightRowsConsistentBaselineSpacing example = 
			new SyncHorizontalDifferentCountOfFixedHeightRowsConsistentBaselineSpacing();
		example.go(true);
	}
	
	public SyncHorizontalDifferentCountOfFixedHeightRowsConsistentBaselineSpacing()
	{
		super(false);
	}

	@Override protected void build()
	{
		DesignGridLayout layout1 = createSubPanel();
		layout1.forceConsistentBaselinesDistance();
		layout1.row().grid(label("lbl1")).add(field("field1"));
		layout1.row().grid(label("lbl2")).add(field("field2"));
		layout1.row().grid(label("lbl3")).add(field("field3"));
		layout1.row().grid(label("lbl4")).add(field("field4"));
		layout1.row().grid(label("lbl5")).add(field("field5"));

		DesignGridLayout layout2 = createSubPanel();
		layout2.forceConsistentBaselinesDistance();
		layout2.row().grid().empty();
		layout2.row().grid(label("lbl1")).add(combobox());
		layout2.row().grid(label("lbl2")).add(button("push me"));
		layout2.row().grid(label("lbl3")).add(checkbox("check3"));

		Synchronizer.synchronize(layout1, layout2).alignRows();
	}
}
