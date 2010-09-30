//  Copyright 2005-2010 Jason Aaron Osgood, Jean-Francois Poilpret
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

import javax.swing.JTextField;

// Issue 38: too small vgap between multicomponents 
// NB: does not happen on Metal/WinXP. Happens on Aqua (Macintosh).
public class Bug38WrongVGapsWithAddMulti extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Bug38WrongVGapsWithAddMulti example = new Bug38WrongVGapsWithAddMulti();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label 1")).add(field("F1"));
		layout.row().grid(label("Label 2")).add(field("F2"));
		layout.row().grid(label("Label 3")).addMulti(field("F3"));
		layout.row().grid(label("Label 4")).addMulti(field("F4"));
	}

	@Override protected JTextField field(String text)
	{
		JTextField field = new JTextField(text);
		field.setColumns(10);
		return field;
	}
}
