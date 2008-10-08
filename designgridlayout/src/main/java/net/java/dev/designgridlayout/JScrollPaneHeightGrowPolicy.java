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

import javax.swing.JScrollPane;

class JScrollPaneHeightGrowPolicy 
	extends AbstractClassBasedHeightGrowPolicy<JScrollPane>
{
	public JScrollPaneHeightGrowPolicy()
	{
		super(JScrollPane.class);
	}

	@Override protected boolean componentCanGrowHeight(JScrollPane component)
	{
		//FIXME do we need to check getUnitIncrement() > 0
		return true;
	}

	@Override protected int componentComputeExtraHeight(JScrollPane component, int extraHeight)
	{
		int unit = component.getVerticalScrollBar().getUnitIncrement(+1);
		if (unit <= 0)
		{
			return extraHeight;
		}
		else
		{
			// Return an integral number of units pixels
			return (extraHeight / unit) * unit;
		}
	}
}
