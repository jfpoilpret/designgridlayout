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

import javax.swing.JSlider;

// Shows issue 5 with lack of variable height rows
public class SmartVerticalResize5SameWeight extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		SmartVerticalResize5SameWeight example = new SmartVerticalResize5SameWeight();
		example.go(true);
	}
	
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label2")).add(table());
		layout.row().grid(label("L3")).add(field("Field31")).empty();
		layout.row().grid(label("Lbl4")).add(table()).add(table());
		layout.row().grid(label("L5")).add(field("F5")).add(slider(JSlider.HORIZONTAL));
		layout.row().grid(label("L6")).add(list()).add(textarea("Wonderful sound"));
		layout.row().grid(label("Power")).add(slider(JSlider.VERTICAL));
		layout.row().center().add(button(), button(), button());
	}
}
