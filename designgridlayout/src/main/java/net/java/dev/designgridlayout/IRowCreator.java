//  Copyright 2009 Jason Aaron Osgood, Jean-Francois Poilpret
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
	 * Gives a logical name to the row to be created. This name will be used
	 * by {@link Synchronizer} to align rows from different panels if they
	 * have the same name. 
	 * <p/>
	 * Giving a name is optional. If a row has no name, then it won't be aligned
	 * if its layout is synchronized with another layout.
	 * <p/>
	 * The name given to the row must be unique in a given {@link DesignGridLayout}
	 * instance.
	 * 
	 * @param name logical name of the new row, used when synchronizing layouts
	 * @return {@code this} instance of IRowCreator, allowing for chained 
	 * calls to other methods (also known as "fluent API")
	 * @throws IllegalStateException if this layout has been locked (which 
	 * happens automatically the first its container frame is packed or displayed)
	 * @throws IllegalArgumentException if name is null or not unique in the
	 * current DesignGridLayout
	 */
	public abstract IRowCreator sync(String name) 
		throws IllegalStateException, IllegalArgumentException;
	
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
	 * @throws IllegalStateException if this layout has been locked (which 
	 * happens automatically the first its container frame is packed or displayed)
	 */
	public abstract INonGridRow center() throws IllegalStateException;

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
	 * @throws IllegalStateException if this layout has been locked (which 
	 * happens automatically the first its container frame is packed or displayed)
	 */
	public abstract INonGridRow left() throws IllegalStateException;

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
	 * @throws IllegalStateException if this layout has been locked (which 
	 * happens automatically the first its container frame is packed or displayed)
	 */
	public abstract INonGridRow right() throws IllegalStateException;
}
