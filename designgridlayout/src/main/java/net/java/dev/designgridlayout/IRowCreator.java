//  Copyright 2005-2010 Jason Aaron Osgood, Jean-Francois Poilpret
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

/**
 * Interface returned by {@link DesignGridLayout#row()} in order to specify the
 * type of row to be actually added to the current layout (grid, centered, left-
 * or right-aligned).
 * 
 * @author Jean-Francois Poilpret
 */
public interface IRowCreator extends ISubGridStarter
{
	/**
	 * Creates a center-aligned row. Center-aligned rows are NOT canonical grids 
	 * but avoid the otherwise mandatory use of several {@code LayoutManager}s 
	 * for one single dialog.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new center-aligned row which API is used in a chained-way 
	 * (fluent API) to add components to the row.
	 */
	public abstract INonGridRow center();

	/**
	 * Creates a left-aligned row. Left-aligned rows are NOT canonical grids but
	 * avoid the otherwise mandatory use of several {@code LayoutManager}s for 
	 * one single dialog.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new left-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 */
	public abstract INonGridRow left();

	/**
	 * Creates a right-aligned row. Right-aligned rows are NOT canonical grids 
	 * but avoid the otherwise mandatory use of several {@code LayoutManager} 
	 * for one single dialog.
	 * <p/>
	 * The new row is located under the previously created row, which means that
	 * each line of code using this method creates a new row and all lines can 
	 * be read as defining the visual UI of the container.
	 * 
	 * @return a new right-aligned row which API is used in a chained-way (fluent 
	 * API) to add components to the row.
	 */
	public abstract INonGridRow right();
}
