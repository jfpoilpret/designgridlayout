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

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Issue3 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Issue3 example = new Issue3();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		// You can add components one line at a time
		layout.row().label(label("One")).add(new JTextField("1"));
//		layout.row().label(label("Two")).add(table);
		layout.row().label(label("Two")).add(new JScrollPane(_table));
//		layout.row().center().add(button(), button());
	}
	// CSON: MagicNumber

	@Override protected void preDisplay()
    {
		_table.requestFocusInWindow();
    }

	private final JTable _table = new JTable(10, 2);
}
