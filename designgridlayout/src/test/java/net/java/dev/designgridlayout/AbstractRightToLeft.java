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

import java.awt.ComponentOrientation;

abstract public class AbstractRightToLeft extends AbstractDesignGridExample
{
	protected AbstractRightToLeft(ComponentOrientation orientation)
	{
		_orientation = orientation;
	}
	
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("L1")).add(field("Field11")).add(field("F12"));
		layout.row().grid(label("Label2")).add(field("2nd field"));
		layout.row().grid(label("Lbl3")).add(field("F3"), 2);
		layout.row().grid(label("L4")).empty().add(field("Field4"), 2).empty();
		layout.row().grid().add(field("F5"), 4);
		layout.row().center().add(button("B1"), button("Button2"), button("3"));
		layout.row().left().add(button("B1"), button("Button2"), button("3"));
		layout.row().right().add(button("B1"), button("Button2"), button("3"));
		layout.row().center().add(button("B1"), button("Button2"), button("3")).fill();
		layout.row().left().fill().add(button("B1"), button("Button2"), button("3"));
		layout.row().right().add(button("B1"), button("Button2"), button("3")).fill();
	}

	@Override protected void prePack()
	{
		frame().applyComponentOrientation(_orientation);
	}
	
	final private ComponentOrientation _orientation;
}
