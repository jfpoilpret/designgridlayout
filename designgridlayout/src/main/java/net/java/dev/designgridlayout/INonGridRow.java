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

/**
 * Any row created by one of {@link DesignGridLayout#leftRow()}, 
 * {@link DesignGridLayout#centerRow()} or {@link DesignGridLayout#rightRow()} 
 * implements this interface. Through this interface, you can add components to 
 * the current row.
 * <p/>
 * All added components share the same width (the maximum of all components
 * preferred widths) and are aligned on the left, center or right depending on
 * which {@code DesignGridLayout} method was called to create this row.
 * 
 * @author Jean-Francois Poilpret
 */
public interface INonGridRow extends IRow
{
	/*
	 * (non-Javadoc)
	 * @see IRow#add(javax.swing.JComponent[])
	 */
	public abstract INonGridRow add(JComponent... children);
	
	/*
	 * (non-Javadoc)
	 * @see IRow#addMulti(javax.swing.JComponent[])
	 */
	public abstract INonGridRow addMulti(JComponent... children);
	
	/**
	 * Sets the "extreme" component(s) of this row to fill the whole space
	 * towards the container border. Extreme components are defined as follows:
	 * <ul>
	 * <li>For a left-aligned row: the rightmost component</li>
	 * <li>For a centered row: the leftmost and rightmost components</li>
	 * <li>For a right-aligned row: the leftmost component</li>
	 * </ul>
	 * This proves useful for implementing "group separators" as advised by
	 * Karsten Lentzsch (JLabel + JSeparator).
	 * 
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract INonGridRow fill();
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IRow#growWeight(double)
	 */
	public abstract INonGridRow growWeight(double weight);
}
