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

// Used for all components added to a SubGrid, real or spanned
// Instances are mutable but only under some conditions
class NonGridRowItem implements ISpannableRowItem
{
	// Used to create an item holding a real component (that may span several
	// rows below or not)
	public NonGridRowItem(JComponent component)
	{
		_component = component;
	}

	public JComponent component()
	{
		return _component;
	}
	
	public int preferredHeight()
	{
		return component().getPreferredSize().height;
	}

	public int minimumWidth()
	{
		return component().getMinimumSize().width;
	}
	
	public int preferredWidth()
	{
		return component().getPreferredSize().width;
	}
	
	public int baseline()
	{
		return Baseline.getBaseline(component());
	}

	public void initUsableVgap(int vgap)
    {
    }

	public boolean isFirstSpanRow()
	{
		return true;
	}

	public boolean isLastSpanRow()
	{
		return true;
	}

	public int rowSpan()
    {
	    return 1;
    }

	final private JComponent _component;
}
