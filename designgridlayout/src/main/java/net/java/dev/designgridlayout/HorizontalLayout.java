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

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import org.jdesktop.layout.Baseline;
import org.jdesktop.layout.LayoutStyle;

final class HorizontalLayout implements LayoutManager
{
	private final Container _parent;
	private final List<JComponent> _children = new ArrayList<JComponent>();
	private boolean _inited = false;
	private int _baseline = 0;
	private int _height = 0;
	private int _minWidth = 0;
	private int _prefWidth = 0;
	private int[] _gaps = null;
	private int _gap = 0;
	
	HorizontalLayout(Container parent)
	{
		_parent = parent;
	}
	
	public HorizontalLayout add(JComponent... children)
	{
		for (JComponent child: children)
		{
			_children.add(child);
			_parent.add(child);
		}
		return this;
	}
	
	public void layoutContainer(Container parent)
	{
		if (parent != _parent)
		{
			throw new IllegalArgumentException(
				"must use HorizontalLayout instance with original parent container");
		}
		
		computeAll();

		synchronized(parent.getTreeLock())
		{
			// Check layout orientation
			ComponentOrientation orientation = parent.getComponentOrientation();
			boolean rtl = orientation.isHorizontal() && !orientation.isLeftToRight();

			// Compute ratio actual size / preferred size (only if actual size smaller)
			int parentWidth = parent.getSize().width;
			int availableWidth = parentWidth - _gap;
			int prefWidth = _prefWidth - _gap;
			float ratio = 1.0f;
			if (availableWidth < prefWidth)
			{
				ratio = (float) availableWidth / prefWidth;
			}
			int nth = 0;
			int x = 0;
			int y = 0;
			LayoutHelper helper = new LayoutHelper(_tester, parentWidth, rtl);
			for (JComponent child: _children)
			{
				// Apply reduction ratio to component width
				int width = (int) (child.getPreferredSize().width * ratio);
				helper.setSizeLocation(child, x, y, width, _height, _baseline);
				x += width + _gaps[nth];
				nth++;
			}
		}
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		initSizeCalculattion(parent);
		return new Dimension(_minWidth, _height);
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		initSizeCalculattion(parent);
		return new Dimension(_prefWidth, _height);
	}
	
	private void initSizeCalculattion(Container parent)
	{
		if (parent != _parent)
		{
			throw new IllegalArgumentException(
				"must use HorizontalLayout instance with original parent container");
		}

		computeAll();
	}

	public void addLayoutComponent(String name, Component comp)
	{
		throw new IllegalArgumentException("do not use this method");
	}

	public void removeLayoutComponent(Component comp)
	{
		throw new IllegalArgumentException("do not use this method");
	}
	
	private void computeAll()
	{
		if (!_inited )
		{
			for (JComponent child: _children)
			{
				_baseline = Math.max(_baseline, Baseline.getBaseline(child));
				_minWidth += child.getMinimumSize().width;
				Dimension d = child.getPreferredSize();
				_height = Math.max(_height, d.height);
				_prefWidth += d.width;
			}

			LayoutStyle layoutStyle = LayoutStyle.getSharedInstance();
			_gaps = new int[_children.size()];
			for (int nth = 0; nth < _children.size() - 1; nth++)
			{
				JComponent left = _children.get(nth);
				JComponent right = _children.get(nth + 1);
				int gap = layoutStyle.getPreferredGap(
					left, right, LayoutStyle.RELATED, SwingConstants.EAST, _parent);
				_gaps[nth] = gap;
				_gap += gap;
			}
			_minWidth += _gap;
			_prefWidth += _gap;
			_gaps[_children.size() - 1] = 0;

			_inited = true;
		}
	}
	
	//FIXME probably wrong if layout contains growable component
	static private final HeightGrowPolicy _tester = new HeightGrowPolicy()
	{
		public boolean canGrowHeight(Component component)
        {
	        return false;
        }
	};
}
