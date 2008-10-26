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

import javax.swing.JComponent;

import org.jdesktop.layout.Baseline;

final class ComponentHelper
{
	private ComponentHelper()
	{
	}
	
	static int baseline(Iterable<JComponent> components)
	{
		int baseline = 0;
		for (JComponent component: components)
		{
			baseline = Math.max(baseline, Baseline.getBaseline(component));
		}
		return baseline;
	}

	static int preferredHeight(Iterable<JComponent> components)
	{
		int height = 0;
		for (JComponent component: components)
		{
			height = Math.max(height, component.getPreferredSize().height);
		}
		return height;
	}

	static int preferredWidth(Iterable<JComponent> components)
	{
		int width = 0;
		for (JComponent component: components)
		{
			width = Math.max(width, component.getPreferredSize().width);
		}
		return width;
	}

	static int minimumWidth(Iterable<JComponent> components)
	{
		int width = 0;
		for (JComponent component: components)
		{
			width = Math.max(width, component.getMinimumSize().width);
		}
		return width;
	}

	static int sumPreferredWidth(Iterable<JComponent> components)
	{
		int width = 0;
		for (JComponent component: components)
		{
			width += component.getPreferredSize().width;
		}
		return width;
	}

	static int sumMinimumWidth(Iterable<JComponent> components)
	{
		int width = 0;
		for (JComponent component: components)
		{
			width += component.getMinimumSize().width;
		}
		return width;
	}
	
	static boolean isFixedHeight(
		HeightGrowPolicy policy, Iterable<JComponent> components)
	{
		for (JComponent component: components)
		{
			if (policy.canGrowHeight(component))
			{
				return false;
			}
		}
		return true;
	}
}
