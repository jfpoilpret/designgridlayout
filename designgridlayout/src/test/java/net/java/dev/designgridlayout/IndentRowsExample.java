//  Copyright 2005-2011 Jason Aaron Osgood, Jean-Francois Poilpret
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

//import java.awt.ComponentOrientation;

//import javax.swing.UIManager;

public class IndentRowsExample extends AbstractDesignGridExample
{
	public static void main(String[] args) throws Exception
	{
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		IndentRowsExample example = new IndentRowsExample();
		example.go(true);
	}

//	@Override protected void prePack()
//	{
//		frame().applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.labelAlignment(LabelAlignment.LEFT);

		layout.row().grid(label("No Indent")).indent(0).add(field("Something"));
		layout.row().grid(label("Indent #1")).indent(1).add(field("Something long"));
		layout.row().grid(label("Indent #2")).indent(2).add(field("Something longer"));
		layout.row().grid(label("Indent #3")).indent(3).add(field("Something much longer"));
		layout.emptyRow();
		
		layout.row().left().indent(0).add(field(" Left row indent #0"));
		layout.row().left().indent(1).add(field(" Left row indent #1"));
		layout.row().left().indent(2).add(field(" Left row indent #2"));
		layout.row().left().indent(3).add(field(" Left row indent #3"));
		layout.emptyRow();

		layout.row().center().indent(0).add(field(" Center row indent #0"));
		layout.row().center().indent(1).add(field(" Center row indent #1"));
		layout.row().center().indent(2).add(field(" Center row indent #2"));
		layout.row().center().indent(3).add(field(" Center row indent #3"));
		layout.emptyRow();

		layout.row().right().indent(0).add(field(" Right row indent #0"));
		layout.row().right().indent(1).add(field(" Right row indent #1"));
		layout.row().right().indent(2).add(field(" Right row indent #2"));
		layout.row().right().indent(3).add(field(" Right row indent #3"));
	}
}
