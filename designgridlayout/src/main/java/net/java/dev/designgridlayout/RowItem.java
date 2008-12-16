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
class RowItem implements IRowItem
{
	// Used to create an item holding a real component (that may span several
	// rows below or not)
	public RowItem(int span, JComponent component)
	{
		_component = component;
		_span = span;
	}

	// Used to create a placeholder for a component spanning a row
	public RowItem(RowItem original)
	{
		_spanPrevious = original;
		original._spanNext = this;
	}
	
	public JComponent component()
	{
		return (_spanPrevious != null ? _spanPrevious.component() : _component);
	}
	
	public int preferredHeight()
	{
		return (_spanPrevious != null ? _spanPrevious.preferredHeight() : calculateHeight());
	}
	
	private int calculateHeight()
	{
		if (_heightPerRow == -1)
		{
			int height = _component.getPreferredSize().height;
			int rows = rowSpan();
			_heightPerRow = Math.max(0, (height / rows) + (height % rows) - _usableVgap);
		}
		return _heightPerRow;
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
		_usableVgap = vgap;
		_heightPerRow = -1;
	}
	
	public boolean isFirstSpanRow()
	{
		return _spanPrevious == null;
	}

	public boolean isLastSpanRow()
	{
		return _spanNext == null;
	}

	public int rowSpan()
	{
		if (_spanPrevious == null)
		{
			int rows = 1;
			RowItem next = _spanNext;
			while (next != null)
			{
				rows++;
				next = next._spanNext;
			}
			return rows;
		}
		else
		{
			return _spanPrevious.rowSpan();
		}
	}
	
	// Used to replace a rowspan placeholder with a real component (error marker)
	public void replace(JComponent component)
	{
		_component = component;
		_heightPerRow = -1;
		_span = _spanPrevious.span();
		_spanPrevious._spanNext = null;
		_spanPrevious = null;
	}
	
	public int span()
	{
		return (_spanPrevious != null ? _spanPrevious.span() : _span);
	}
	
	private JComponent _component = null;
	private int _span = 1;
	private int _usableVgap = 0;
	private int _heightPerRow = -1;
	private RowItem _spanPrevious = null;
	private RowItem _spanNext = null;
}
