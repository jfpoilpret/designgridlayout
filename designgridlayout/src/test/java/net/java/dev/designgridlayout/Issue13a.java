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

// Shows issue 13 example: multiple labels
public class Issue13a extends AbstractIssue13
{
	public static void main(String[] args) throws Exception
	{
		Issue13a example = new Issue13a();
		example.go(true);
	}
	
	// CSOFF: LineLength
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().label(label("label1"))	.add(field("XX1"))	.label(label("lbl2"))	.add(field("XX2"))	.label(label("label3"))	.add(field("XX3"));
		layout.row().label(label("L4"))		.add(field("XX4"))	.label(label("L5"))		.add(field("XX5"))	.label(label("L6"))		.add(field("XX6"));
		layout.row().label()				.add(field("XXa"))	.label()				.add(field("XXb"))	.label()				.add(field("XXc"));
		layout.row().label()									.label(label("WWWWWW"))	.add(field("XX8"))	.label(label("LLLL"))	.add(field("XXX"));

		layout.row().label(label("Lbl7"),2)	.add(field("XXXXX7"))											.label(label("LBL8"))	.add(field("XX8"));
		layout.row().label(label("Lbl9"))	.add(field("XX9"))	.label(label("LBL10"))	.add(field("XXXX10"));
		layout.row().label(label("Lbl11"))	.add(field("XXXXXXX11"));
		layout.row().label(label("Lbl12"),1).add(field("X12"))	.label()									.label(label("LBL13"))	.add(field("X13"));
		layout.emptyRow();
		layout.centerRow().add(button("OK"), button("Cancel"));
	}
	// CSON: LineLength
}
