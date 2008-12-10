//  Copyright 2008 Jason Aaron Osgood, Jean-Francois Poilpret
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

public class Issue10 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Issue10 example = new Issue10();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("lbl1")).add(list()).add(field("field1"));
		layout.row().grid().spanRow().add(field("field3"));
		layout.row().grid(label("lbl4")).add(field("field4"));
	}

	static private JScrollPane list()
	{
		JList list = new JList(guitars);
		list.setVisibleRowCount(2);
		return new JScrollPane(list);
	}

	static private final Object[] guitars =
	{
		"Fender Telecaster",
		"Fender Stratocaster",
		"Gibson Les Paul",
	};
}
