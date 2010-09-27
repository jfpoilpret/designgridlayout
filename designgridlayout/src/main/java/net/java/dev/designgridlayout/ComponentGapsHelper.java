//  Copyright 2009 Jason Aaron Osgood, Jean-Francois Poilpret
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

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

final class ComponentGapsHelper
{
	static public ComponentGapsHelper instance()
	{
		return new ComponentGapsHelper();
	}
	
	public int getVerticalGap(JComponent component1, JComponent component2, 
		ComponentPlacement type, Container parent)
	{
		if (component1 instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component1;
			// Take the maximum gap for all children in component1
			int gap = 0;
			for (JComponent child: multi.getChildren())
			{
				gap = Math.max(gap, getVerticalGap(child, component2, type, parent));
			}
			return gap;
		}
		else if (component2 instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component2;
			// Take the maximum gap for all children in component2
			int gap = 0;
			for (JComponent child: multi.getChildren())
			{
				gap = Math.max(gap, getVerticalGap(component1, child, type, parent));
			}
			return gap;
		}
		else
		{
			return _style.getPreferredGap(
				component1, component2, type, SwingConstants.SOUTH, parent);
		}
	}
	
	public int getHorizontalGap(JComponent component1, JComponent component2, 
		ComponentPlacement type, Container parent)
	{
		if (component1 instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component1;
			int size = multi.getChildren().length;
			// Take the gap for the last child (east-side) in component1
			return (size == 0 ? 0 : getHorizontalGap(
				multi.getChildren()[size - 1], component2, type, parent));
		}
		else if (component2 instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component2;
			int size = multi.getChildren().length;
			// Take the gap for the first child (west-side) in component2
			return (size == 0 ? 0 : getHorizontalGap(
				component1, multi.getChildren()[0], type, parent));
		}
		else
		{
			return _style.getPreferredGap(
				component1, component2, type, SwingConstants.EAST, parent);
		}
	}
    
	public int getNorthContainerGap(JComponent component, Container parent)
	{
		if (component instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component;
			// Take the maximum gap for all children in component
			int gap = 0;
			for (JComponent child: multi.getChildren())
			{
				gap = Math.max(gap, getNorthContainerGap(child, parent));
			}
			return gap;
		}
		else
		{
			return _style.getContainerGap(component, SwingConstants.NORTH, parent);
		}
	}

	public int getSouthContainerGap(JComponent component, Container parent)
	{
		if (component instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component;
			// Take the maximum gap for all children in component
			int gap = 0;
			for (JComponent child: multi.getChildren())
			{
				gap = Math.max(gap, getSouthContainerGap(child, parent));
			}
			return gap;
		}
		else
		{
			return _style.getContainerGap(component, SwingConstants.SOUTH, parent);
		}
	}

	public int getWestContainerGap(JComponent component, Container parent)
	{
		if (component instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component;
			if (multi.getChildren().length > 0)
			{
				// Take the gap for the first child (west-side) in component
				return getWestContainerGap(multi.getChildren()[0], parent);
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return _style.getContainerGap(component, SwingConstants.WEST, parent);
		}
	}

	public int getEastContainerGap(JComponent component, Container parent)
	{
		if (component instanceof MultiComponent)
		{
			MultiComponent multi = (MultiComponent) component;
			int size = multi.getChildren().length;
			if (size > 0)
			{
				// Take the gap for the last child (east-side) in component
				return getEastContainerGap(multi.getChildren()[size - 1], parent);
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return _style.getContainerGap(component, SwingConstants.EAST, parent);
		}
	}

	private final LayoutStyle _style = LayoutStyle.getInstance(); 
}
