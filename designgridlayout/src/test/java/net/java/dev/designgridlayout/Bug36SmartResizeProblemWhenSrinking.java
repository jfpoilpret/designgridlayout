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

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.UIManager;

public class Bug36SmartResizeProblemWhenSrinking extends AbstractDesignGridExample
{
	public static void main(String[] args) throws Exception
	{
		// Change LAF?
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame.setDefaultLookAndFeelDecorated(true);
		Bug36SmartResizeProblemWhenSrinking example = 
			new Bug36SmartResizeProblemWhenSrinking();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().left().fill().add(label("Group1"), new JSeparator());
		layout.row().grid(label("Label1")).add(field("field1")).grid(label("Label2")).add(field("field2"));
		layout.row().grid(label("Label1")).add(field("field1")).grid(label("Label2")).add(field("field2"));
		layout.row().grid(label("Label1")).add(field("field1")).grid(label("Label2")).add(field("field2"));
		layout.emptyRow();
		layout.row().center().add(button("Button1"), button("Button2"));
		layout.emptyRow();
		layout.row().left().fill().add(label("Group2"), new JSeparator());
		layout.row().center().fill().add(table());
		layout.row().center().add(button("Button3"));
//		Left/fill/ [group]
//		Grid/grid
//		Grid/grid
//		Grid/grid
//		Empty
//		Center [buttons]
//		Empty
//		Left/fill/ [group]
//		Center/fill [table]
//		Center [button] 
	}
	
}
