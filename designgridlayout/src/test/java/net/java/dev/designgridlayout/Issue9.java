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

import java.awt.ComponentOrientation;

public class Issue9 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Issue9 example = new Issue9();
		example.setOrientation(ComponentOrientationHelper.getOrientation(args));
		example.go(false);
	}
	
	public void setOrientation(ComponentOrientation orientation)
	{
		_orientation = orientation;
		ComponentOrientationHelper.debugOrientation(orientation);
	}
	
	public void setForceOrientation(boolean rtl)
	{
		_rtl = rtl;
	}

	@Override public void build(DesignGridLayout layout)
	{
		if (_rtl != null)
		{
			if (_rtl)
			{
				layout.rightToLeft();
			}
			else
			{
				layout.leftToRight();
			}
		}
		layout.row().label(label("L1")).add(field("Field11")).add(field("F12"));
		layout.row().label(label("Label2")).add(field("2nd field"));
		layout.row().label(label("Lbl3")).add(field("F3"), 2);
		layout.row().label(label("L4")).empty().add(field("Field4"), 2).empty();
		layout.row().add(field("F5"), 4);
		layout.centerRow().add(button("B1"), button("Button2"), button("3"));
		layout.leftRow().add(button("B1"), button("Button2"), button("3"));
		layout.rightRow().add(button("B1"), button("Button2"), button("3"));
		layout.centerRow().add(button("B1"), button("Button2"), button("3")).fill();
		layout.leftRow().fill().add(button("B1"), button("Button2"), button("3"));
		layout.rightRow().add(button("B1"), button("Button2"), button("3")).fill();
	}

	@Override protected void prePack()
    {
		frame().applyComponentOrientation(_orientation);
    }
	
	private ComponentOrientation _orientation;
	private Boolean _rtl = null;
}
