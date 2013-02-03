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

import javax.swing.JTextField;

// Shows issue 13 example: multiple labels
public class MultiGrid3ThreeComplexGrids extends AbstractMultiGrid
{
	public static void main(String[] args) throws Exception
	{
		MultiGrid3ThreeComplexGrids example = new MultiGrid3ThreeComplexGrids();
		example.go(true);
	}
	
	// CSOFF: LineLength
	@Override public void build(DesignGridLayout layout)
	{
		// Simple cases, multi-columns on sub-grid with 1-gridspan
		layout.row().grid(label("label1"))	.add(field("XX1a"), field("XX1b"), field("XX1c"))
					.grid(label("lbl2"))	.add(field("XX2a"), field("XX2b"), field("XX2c"))
					.grid(label("label3"))	.add(field("XX3a"), field("XX3b"), field("XX3c"));
		layout.row().grid(label("L4"))		.add(field("XX4a"), field("XX4b"))	
					.grid(label("L5"))		.add(field("XX5a"), field("XX5b"))	
					.grid(label("L6"))		.add(field("XX6a"), field("XX6b"));
		layout.row().grid()					.add(field("XXa"))	
					.grid()					.add(field("XXb"))	
					.grid()					.add(field("XXc"));

		layout.row().grid(label("Lbl7"),2)	.add(field("XXXX7a"), field("XXXX7b"));
		layout.row().grid(label("Lbl8"), 2)	.add(field("XX8a"), field("XX8b"), field("XX8c"));
		layout.row().grid(label("Lbl9"), 2)	.add(field("XX9a"), field("XX9b"), field("XX9c"), field("XX9d"));

		layout.row().grid(label("Lbl10"))	.add(field("XXX10a"), field("XXX10b"));
		layout.row().grid(label("Lbl11"))	.add(field("XX11a"), field("XX11b"), field("XX11c"));
		layout.row().grid(label("Lbl12"))	.add(field("XX12a"), field("XX12b"), field("XX12c"), field("XX12d"));
		layout.row().grid(label("Lbl13"))	.add(field("XX13a"), field("XX13b"), field("XX13c"), field("XX13d"), field("XX13e"));

		layout.emptyRow();
		layout.row().center().add(button("OK"), button("Cancel"));
	}
	// CSON: LineLength

	@Override protected JTextField field(String text)
	{
		JTextField field = super.field(text);
		field.setColumns(5);
		return field;
	}
}
