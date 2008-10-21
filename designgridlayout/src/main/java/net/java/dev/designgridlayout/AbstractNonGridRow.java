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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

abstract class AbstractNonGridRow extends AbstractRow implements INonGridRow
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.INonGridRow#add(javax.swing.JComponent[])
	 */
	public INonGridRow add(JComponent... children)
	{
		for (JComponent component: children)
		{
			_components.add(component);
			parent().add(component);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.INonGridRow#addMulti(javax.swing.JComponent[])
	 */
	public INonGridRow addMulti(JComponent... children)
	{
		return add(new MultiComponent(growPolicy(), orientation(), children));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.INonGridRow#fill()
	 */
	public INonGridRow fill()
	{
		_fill = true;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.dev.designgridlayout.INonGridRow#growWeight(double)
	 */
	public INonGridRow growWeight(double weight)
	{
		setGrowWeight(weight);
		return this;
	}

	@Override protected List<JComponent> components()
	{
		return _components;
	}

	@Override int totalNonGridWidth(int hgap)
	{
		int maxWidth = 0;
		int compWidth = ComponentHelper.preferredWidth(_components);
		int count = _components.size();
		int width = compWidth * count + (hgap * (count - 1));
		maxWidth = Math.max(maxWidth, width);
		return maxWidth;
	}

	// CSOFF: ParameterAssignment
	@Override int layoutRow(
	    LayoutHelper helper, int x, int y, int hgap, int rowWidth, int labelWidth)
	{
		// Calculate various needed widths & origin
		int count = _components.size();
		int width = maxWidth();
		int availableWidth = (rowWidth - ((count - 1) * hgap));
		width = Math.min(width, availableWidth / count);

		int usedWidth;
		int leftFiller = width;
		int rightFiller = width;
		if (!_fill)
		{
			usedWidth = width * count + ((count - 1) * hgap);
			x += xOffset(rowWidth, usedWidth);
		}
		else
		{
			usedWidth = availableWidth;
			leftFiller = leftFiller(count, width, availableWidth);
			rightFiller = rightFiller(count, width, availableWidth);
		}

		return layoutRow(helper, x, y, hgap, width, leftFiller, rightFiller);
	}
	// CSON: ParameterAssignment

	// CSOFF: ParameterAssignment
	protected int layoutRow(
	    LayoutHelper helper, int x, int y, int hgap, int width, int leftFiller, int rightFiller)
	{
		int count = _components.size();
		int i = 0;
		int actualHeight = 0;
		for (JComponent component: _components)
		{
			int compWidth;
			if (i == 0)
			{
				compWidth = leftFiller;
			}
			else if (i == count - 1)
			{
				compWidth = rightFiller;
			}
			else
			{
				compWidth = width;
			}
			actualHeight =
			    Math.max(actualHeight, helper.setSizeLocation(
			        component, x, y, compWidth, height(), baseline()));
			x += compWidth + hgap;
			i++;
		}
		return actualHeight;
	}
	// CSON: ParameterAssignment

	abstract protected int xOffset(int rowWidth, int usedWidth);

	abstract protected int leftFiller(int count, int width, int availableWidth);

	abstract protected int rightFiller(int count, int width, int availableWidth);

	private final List<JComponent> _components = new ArrayList<JComponent>();
	private boolean _fill = false;
}
