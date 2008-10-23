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
public class Issue13b extends AbstractIssue13
{
	public void setPanelIndex(int panel)
	{
		_panel = panel;
	}
	
	// CSOFF: LineLength
	@Override public void build(DesignGridLayout layout)
	{
		switch (_panel)
		{
			case 0:
			layout.row().label(label("label1"))	.add(field("XX1"));
			layout.row().label(label("L4"))		.add(field("XX4"));
			layout.row().label()				.add(field("XXa"));
			break;
			
			case 1:
			layout.row().label(label("lbl2"))	.add(field("XX2"));
			layout.row().label(label("L5"))		.add(field("XX5"));
			layout.row().label()				.add(field("XXb"));
			break;
				
			case 2:
			layout.row().label(label("label3"))	.add(field("XX3"));
			layout.row().label(label("L6"))		.add(field("XX6"));
			layout.row().label()				.add(field("XXc"));
			break;
		}
	}
	// CSON: LineLength
	
	private int _panel = 0;
}
