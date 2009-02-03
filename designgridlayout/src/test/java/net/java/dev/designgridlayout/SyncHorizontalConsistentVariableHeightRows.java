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

import javax.swing.JList;
import javax.swing.JScrollPane;

public class SyncHorizontalConsistentVariableHeightRows 
	extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		SyncHorizontalConsistentVariableHeightRows example = 
			new SyncHorizontalConsistentVariableHeightRows();
		example.go(true);
	}
	
	public SyncHorizontalConsistentVariableHeightRows()
	{
		super(false);
	}

	@Override protected void build()
	{
		DesignGridLayout layout1 = createSubPanel();
		layout1.row().grid(label("lbl1")).add(list());
		layout1.row().grid(label("lbl2")).add(field("field2"));

		DesignGridLayout layout2 = createSubPanel();
		layout2.row().grid(label("label1")).add(list());
		layout2.row().grid(label("lbl2")).add(radio("radio2"));

		Synchronizer.synchronize(layout1, layout2).alignRows();
	}
	
	@Override protected JScrollPane list()
	{
		JScrollPane scroller = super.list();
		((JList) scroller.getViewport().getView()).setVisibleRowCount(3);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scroller;
	}
}
