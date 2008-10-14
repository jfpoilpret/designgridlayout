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
import javax.swing.JLabel;

/**
 * Any row created by {@link DesignGridLayout#row()} implements this
 * interface. Through this interface, you can add components to the current row;
 * position and size of components will use the canonical grid calculated for
 * the whole layout.
 * 
 * @author Jean-Francois Poilpret
 */
public interface IGridRow extends IRow
{
	/**
	 * Adds a label to the row. This label is always the first component in the
	 * row (whatever the order in which this method is called).
	 * <p/>
	 * Labels have a special treatment: they are not a part of the canonical
	 * grid but use a fixed-width on the left of the canonical grid. Labels
	 * are automatically right-aligned.
	 * <p/>
	 * Only one label is allowed in a row, thus if this method is called several
	 * times, only the last call will set the actual label.
	 * 
	 * @param label the label to add to this row
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow label(JLabel label);
	
	/*
	 * (non-Javadoc)
	 * @see IRow#add(javax.swing.JComponent[])
	 */
	public abstract IGridRow add(JComponent... children);
	
	/**
	 * Adds one component to this row and allows it to span several columns of
	 * the canonical grid.
	 * <p/>
	 * The order of calls match the order in which the components will
	 * row. Components are added left to right, in the same order as they appear
	 * in the arguments list.
	 * 
	 * @param child component to add to this row; it is added to the right of
	 * the component that was added by the nearest previous call to an add
	 * method.
	 * @param span the number of columns to span (must be &gt; 0)
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow add(JComponent child, int span);

	/**
	 * Adds an empty column to the current row.
	 * 
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow empty();

	/**
	 * Adds one or more empty columns to the current row.
	 * 
	 * @param span the number of columns to span (must be &gt; 0)
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow empty(int span);

	/*
	 * (non-Javadoc)
	 * @see IRow#addMulti(javax.swing.JComponent[])
	 */
	public abstract IGridRow addMulti(JComponent... children);
	
	/**
	 * Adds components to this row; all components are "assembled" as one
	 * global component and span a given number of columns in the row.
	 * <p/>
	 * Note that the width of each individual component will never grow bigger
	 * than its preferred width.
	 * 
	 * @param span the number of columns to span (must be &gt; 0)
	 * @param children components to assemble and add to this row
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow addMulti(int span, JComponent... children);
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IRow#growWeight(double)
	 */
	public abstract IGridRow growWeight(double weight);
}
