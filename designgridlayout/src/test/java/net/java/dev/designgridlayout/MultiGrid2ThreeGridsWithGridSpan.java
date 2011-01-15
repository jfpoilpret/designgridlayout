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

// Shows issue 13 example: multiple labels
public class MultiGrid2ThreeGridsWithGridSpan extends AbstractMultiGrid
{
	public static void main(String[] args) throws Exception
	{
		MultiGrid2ThreeGridsWithGridSpan example = new MultiGrid2ThreeGridsWithGridSpan();
		example.go(true);
	}
	
	// CSOFF: LineLength
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("label1"))	.add(field("XX1"))	.grid(label("lbl2"))	.add(field("XX2"))	.grid(label("label3"))	.add(field("XX3"));
		layout.row().grid(label("L4"))		.add(field("XX4"))	.grid(label("L5"))		.add(field("XX5"))	.grid(label("L6"))		.add(field("XX6"));
		layout.row().grid()					.add(field("XXa"))	.grid()					.add(field("XXb"))	.grid()					.add(field("XXc"));
		layout.row().grid()										.grid(label("WWWWWW"))	.add(field("XX8"))	.grid(label("LLLL"))	.add(field("XXX"));

		layout.row().grid(label("Lbl7"),2)	.add(field("XXXXX7"))											.grid(label("LBL8"))	.add(field("XX8"));
		layout.row().grid(label("Lbl9"))	.add(field("XX9"))	.grid(label("LBL10"))	.add(field("XXXX10"));
		layout.row().grid(label("Lbl11"))	.add(field("XXXXXXX11"));
		layout.row().grid(label("Lbl12"),1)	.add(field("X12"))	.grid()										.grid(label("LBL13"))	.add(field("X13"));
		layout.emptyRow();
		layout.row().center().add(button("OK"), button("Cancel"));
	}
	// CSON: LineLength
}
