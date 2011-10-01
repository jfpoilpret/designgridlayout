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

import javax.swing.JComponent;

import net.java.dev.designgridlayout.Componentizer.Width;

public class Componentizer7WithinDesignGrid extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Componentizer7WithinDesignGrid example = new Componentizer7WithinDesignGrid();
		example.go(true);
	}
	
	@Override protected void build(DesignGridLayout layout)
	{
		JComponent multiComponent1 = Componentizer.create()
			.add(Width.MIN_TO_PREF, field("Select a file for upload"))
			.add(Width.PREF_FIXED, button("Select..."))
			.component();
		JComponent multiComponent2 = Componentizer.create()
			.addFixed(list())
			.component();
		layout.row().grid(label("Label 1")).add(field("ABCDEF"), field("GHIJKLMNOP"));
		layout.row().grid(label("L2")).add(multiComponent1, field("QRSTUVWXYZ"));
//		layout.row().grid(label("L3")).add(multiComponent2, field("12345"));
	}
}
