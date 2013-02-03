//  Copyright 2005-2013 Jason Aaron Osgood, Jean-Francois Poilpret
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

import javax.swing.JSeparator;

// Demonstrate bad calls to spanRow()
public class RowSpan4ErrorMarkers extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		RowSpan4ErrorMarkers example = new RowSpan4ErrorMarkers();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		// spanrow() called in first row
		layout.row().grid(label("lbl0")).spanRow();
		layout.row().center().fill().add(new JSeparator());

		// spanrow() called on a subgrid with different number of columns
		layout.row().grid(label("lbl1")).add(field("field1"), field("field1"), field("field1"));
		layout.row().grid(label("lbl2")).add(field("field2")).spanRow();
		layout.row().center().fill().add(new JSeparator());
		
		// spanrow() called with no matching item
		layout.row().grid(label("lbl1")).add(field("field1"), 2);
		layout.row().grid(label("lbl2")).add(field("field2")).spanRow();
		layout.row().center().fill().add(new JSeparator());
		
		// spanrow() called with no matching item
		layout.row().grid(label("lbl1")).add(field("field1"), 2);
		layout.row().grid(label("lbl2")).spanRow().add(field("field2"));
		layout.row().center().fill().add(new JSeparator());

		// spanrow() called with no matching item
		layout.row().grid(label("lbl1")).add(field("field1"), 2).add(field("field1"));
		layout.row().grid(label("lbl2")).spanRow();
		layout.row().center().fill().add(new JSeparator());

		// spanrow() called on non matching subgrids
		layout.row().grid(label("lbl11"), 2).add(field("field1")).grid(label("lbl13")).add(field("field1"));
		layout.row().grid(label("lbl21"), 1).add(field("field2")).grid(label("lbl22")).spanRow();
		layout.row().center().fill().add(new JSeparator());
		
		// spanrow() called while the previous row is not a gridrow
		layout.row().left().add(button());
		layout.row().grid(label("lbl1")).spanRow();
		layout.row().center().fill().add(new JSeparator());
		
		// multiple spanrow() in consecutive gridrows, the first being bad
		// Note: the red marker must span rows 2 & 3
		layout.row().grid(label("lbl1")).add(field("field1"), field("field1"), field("field1"));
		layout.row().grid(label("lbl2")).add(field("field2")).spanRow();
		layout.row().grid(label("lbl3")).add(field("field3")).spanRow();
	}
}
