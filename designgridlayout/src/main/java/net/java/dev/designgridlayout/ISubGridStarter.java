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

import javax.swing.JLabel;

/**
 * Any row created by {@link DesignGridLayout#row()} implements this
 * interface. This interface is used to start a new sub-grid in the current
 * row.
 * <p/>
 * Using one of the {@code label()} methods of this interface is mandatory
 * before adding any further component to the current row.
 * <p/>
 * Every time {@code label()} is called for the current row, a new sub-grid is
 * started, with its own specific label column, and additional gap from the
 * previous sub-grid.
 * 
 * @author Jean-Francois Poilpret
 */
public interface ISubGridStarter
{
	/**
	 * Starts a new sub-grid in the row, starting with a label.
	 * <p/>
	 * Labels have a special treatment: they are not a part of the canonical
	 * grid but use a fixed-width on the left of the canonical grid. Labels
	 * are automatically right-aligned.
	 * <p/>
	 * The new sub-grid initiated by this call will span all space on its right
	 * unless another call to a {@code label()} method occurs in the same row. 
	 * 
	 * @param label the label to add to this row
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow label(JLabel label);
	
	/**
	 * Starts a new sub-grid in the row, starting with a label.
	 * <p/>
	 * Labels have a special treatment: they are not a part of the canonical
	 * grid but use a fixed-width on the left of the canonical grid. Labels
	 * are automatically right-aligned.
	 * <p/>
	 * The new sub-grid initiated by this call will span a certain number of
	 * sub-grids on its right, as determined by {@code gridspan}.
	 * 
	 * @param label the label to add to this row
	 * @param gridspan the number of sub-grids this new sub-grid should span;
	 * if {@code <= 0}, then the behavior is the same as {@link #label(JLabel)}.
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow label(JLabel label, int gridspan);

	/**
	 * Starts a new sub-grid in the row, starting with an empty label.
	 * <p/>
	 * Labels (empty or not) have a special treatment: they are not a part of 
	 * the canonical grid but use a fixed-width on the left of the canonical 
	 * grid. Labels are automatically right-aligned.
	 * <p/>
	 * The new sub-grid initiated by this call will span all space on its right
	 * unless another call to a {@code label()} method occurs in the same row. 
	 * 
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow label();

	/**
	 * Starts a new sub-grid in the row, starting with an empty label.
	 * <p/>
	 * Labels (empty or not) have a special treatment: they are not a part of 
	 * the canonical grid but use a fixed-width on the left of the canonical 
	 * grid. Labels are automatically right-aligned.
	 * <p/>
	 * The new sub-grid initiated by this call will span a certain number of
	 * sub-grids on its right, as determined by {@code gridspan}.
	 * 
	 * @param gridspan the number of sub-grids this new sub-grid should span;
	 * if {@code <= 0}, then the behavior is the same as {@link #label()}.
	 * @return {@code this} row (to allow chaining other methods for the current 
	 * row)
	 */
	public abstract IGridRow label(int gridspan);
}
