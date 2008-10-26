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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import org.jdesktop.layout.LayoutStyle;

final class HorizontalLayout implements LayoutManager
{
	HorizontalLayout(
		Container parent, HeightGrowPolicy heightTester, OrientationPolicy orientation)
	{
		_parent = parent;
		_heightTester = heightTester;
		_orientation = orientation;
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
	
	public int getBaseline()
	{
		computeAll();
		return _baseline;
	}
	
	public void layoutContainer(Container parent)
	{
		if (parent != _parent)
		{
			throw new IllegalArgumentException(
				"must use HorizontalLayout instance with original parent container");
		}
		
		synchronized(parent.getTreeLock())
		{
			computeAll();

			// Check layout orientation
			boolean rtl = _orientation.isRightToLeft();

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
			LayoutHelper helper = new LayoutHelper(_heightTester, parentWidth, rtl);
			helper.setY(0);
			for (JComponent child: _children)
			{
				helper.setRowAvailableHeight(_parent.getHeight());
				// Apply reduction ratio to component width
				int width = (int) (child.getPreferredSize().width * ratio);
				helper.setSizeLocation(child, x, width, _height, _baseline);
				x += width + _gaps[nth];
				nth++;
			}
		}
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		initSizeCalculation(parent);
		return new Dimension(_minWidth, _height);
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		initSizeCalculation(parent);
		return new Dimension(_prefWidth, _height);
	}
	
	private void initSizeCalculation(Container parent)
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
			_baseline = ComponentHelper.baseline(_children);
			_minWidth = ComponentHelper.sumMinimumWidth(_children);
			_prefWidth = ComponentHelper.sumPreferredWidth(_children);
			_height = ComponentHelper.preferredHeight(_children);

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

	private final Container _parent;
	private final HeightGrowPolicy _heightTester;
	private final OrientationPolicy _orientation;
	private final List<JComponent> _children = new ArrayList<JComponent>();
	private boolean _inited = false;
	private int _baseline = 0;
	private int _height = 0;
	private int _minWidth = 0;
	private int _prefWidth = 0;
	private int[] _gaps = null;
	private int _gap = 0;
}
