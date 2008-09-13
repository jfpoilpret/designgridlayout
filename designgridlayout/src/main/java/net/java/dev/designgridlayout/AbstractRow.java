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

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jdesktop.layout.Baseline;

abstract class AbstractRow implements IGridRow, INonGridRow
{
	static final private int HEIGHT_DEFAULT = -1;

	final protected Container _parent;
	final protected List<RowItem> _items = new ArrayList<RowItem>();

	private JLabel _label = null;

	private boolean _inited = false;
	private boolean _fill = false;
	private int _baseline;
	private int _height;
	private int _explicitHeight = HEIGHT_DEFAULT;

	private int _maxWidth;
	private int _vgap;

	protected AbstractRow(Container parent)
	{
		_parent = parent;
	}

	/* (non-Javadoc)
	 * @see IGridRow#label(javax.swing.JLabel)
	 */
	public AbstractRow label(JLabel label)
	{
		if (label != null)
		{
			_label = label;
			_parent.add(_label);
			_label.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see IGridRow#add(javax.swing.JComponent)
	 */
	public AbstractRow add(JComponent... children)
	{
		for (JComponent component: children)
		{
			add(component, 1);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see IGridRow#add(javax.swing.JComponent, int)
	 */
	public AbstractRow add(JComponent child, int span)
	{
		RowItem item = new RowItem(span, child);
		_items.add(item);
		_parent.add(child);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see INonGridRow#fill()
	 */
	public AbstractRow fill()
	{
		_fill = true;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see IGridRow#addMulti(javax.swing.JComponent[])
	 */
	public AbstractRow addMulti(JComponent... children)
	{
		return addMulti(1, children);
	}

	/*
	 * (non-Javadoc)
	 * @see IGridRow#addMulti(int, javax.swing.JComponent[])
	 */
	public AbstractRow addMulti(int span, JComponent... children)
	{
		return add(new MultiComponent(children), span);
	}

	/*
	 * (non-Javadoc)
	 * @see IGridRow#empty()
	 */
	public AbstractRow empty()
	{
		return empty(1);
	}

	/*
	 * (non-Javadoc)
	 * @see IGridRow#empty(int)
	 */
	public AbstractRow empty(int span)
	{
		return add(new JPanel(), span);
	}

	void height(int height)
	{
		_explicitHeight = height;
	}

	protected boolean isFill()
	{
		return _fill;
	}

	int baseline()
	{
		maximize();
		return _baseline;
	}

	int gridColumns()
	{
		return 0;
	}

	boolean hasLabel()
	{
		return _label != null;
	}

	int height()
	{
		if (_explicitHeight != HEIGHT_DEFAULT)
		{
			return _explicitHeight;
		}

		maximize();
		return _height;
	}

	List<RowItem> items()
	{
		return _items;
	}

	JLabel label()
	{
		return _label;
	}

	private void maximize()
	{
		if (!_inited )
		{
			for (RowItem item: _items)
			{
				JComponent component = item.component();
				Dimension d = component.getPreferredSize();
				_maxWidth = Math.max(_maxWidth, d.width);
				_height = Math.max(_height, d.height);
				_baseline = Math.max(_baseline, Baseline.getBaseline(component));
			}
			_inited = true;
		}
	}
	
	int labelWidth()
	{
		return (_label != null ? _label.getPreferredSize().width : 0);
	}

	int maxWidth()
	{
		maximize();
		return _maxWidth;
	}

	void reset()
	{
		_inited = false;
	}

	int vgap()
	{
		return _vgap;
	}

	protected void vgap(int vgap)
	{
		_vgap = vgap;
	}
	
	int maxColumnWidth(int maxColumns)
	{
		return 0;
	}

	int totalWidth(int hgap)
	{
		return 0;
	}
	
	int hgap()
	{
		return 0;
	}

	abstract void layoutRow(int x, int y, int hgap, int rowWidth, int labelWidth);
}
