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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class Rfe29ExactVGaps extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Rfe29ExactVGaps example = new Rfe29ExactVGaps();
		example.go(true);
	}

	// Show as many kinds of components as possible
	@Override protected void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label1")).add(field("field1"));
		layout.row().grid(label("Label2")).add(textarea("something"));
		layout.row().grid(label("Label3")).add(field("field3"));
		layout.row().grid(label("Label4")).add(new JCheckBox("check"));
		layout.row().grid(label("Label5")).add(field("field5"));
		layout.row().grid(label("Label6")).add(new JRadioButton("radio"));
		layout.row().grid(label("Label7")).add(field("field7"));
		layout.row().grid(label("Label8")).add(new JComboBox(COMBO_CONTENT));
		layout.row().grid(label("Label9")).add(field("field9"));
		layout.emptyRow();
		layout.row().grid(label("Label10")).add(field("field10"));
	}
	
	static final private String[] COMBO_CONTENT =
	{
		"First option",
		"Second option",
		"Third option",
		"Other option",
		"No option at all",
	};
}
