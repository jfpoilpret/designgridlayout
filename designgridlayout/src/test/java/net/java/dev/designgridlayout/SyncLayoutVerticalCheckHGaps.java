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

public class SyncLayoutVerticalCheckHGaps extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		SyncLayoutVerticalCheckHGaps example = new SyncLayoutVerticalCheckHGaps();
		example.go(true);
	}
	
	public SyncLayoutVerticalCheckHGaps()
	{
		super(true);
	}

	@Override protected void build()
	{
		DesignGridLayout layout1 = createSubPanel();
		layout1.row().grid(label("lbl1")).add(radio("radio1"), radio("radio2"), radio("radio3"));
		layout1.row().grid(label("lbl2")).add(checkbox("check1"), checkbox("check2"), checkbox("check3"));

		DesignGridLayout layout2 = createSubPanel();
		layout2.row().grid(label("label1")).add(field("field1"), field("field2"), field("field3"));

		Synchronizer.synchronize(layout1, layout2).alignGrids();
	}
}
