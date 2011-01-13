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

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to group several rows from a layout, in order to show or
 * hide them all in a single call.
 * <p/>
 * This allows to handle dynamic layouts where some rows appear or disappear based
 * on some user settings or actions.
 * <p/>
 * Rows can be added at the time they are defined by calling {@link IRowCreator#group}.
 * <p/>
 * The following snippet shows {@code RowGroup} usage:
 * <pre>
 * // Set the layout
 * ...
 * RowGroup addressGroup = new RowGroup();
 * layout.row().group(group).grid(label("Address 1")).add(field(""));
 * layout.row().group(group).grid(label("Address 2")).add(field(""));
 * layout.row().group(group).grid(label("Zip")).add(field("")).empty(3);
 * layout.row().group(group).grid(label("City")).add(field(""));
 * ...
 * // Later on, depending on some user action or some other setting
 * addressGroup.hide();
 * </pre>
 *
 * @author Jean-Francois Poilpret
 */
public final class RowGroup
{
	//TODO should we only keep weak references to rows?
	void add(IHideable row)
	{
		if (!_rows.contains(row))
		{
			_rows.add(row);
		}
	}

	/**
	 * Hides all rows in this {@code RowGroup}, by calling {@link IHideable#hide()} 
	 * on each of them.
	 */
	public void hide()
	{
		for (IHideable row: _rows)
		{
			row.hide();
		}
	}

	/**
	 * Restores all rows to their initial visible state, by calling 
	 * {@link IHideable#show()} for each of them.
	 */
	public void show()
	{
		for (IHideable row: _rows)
		{
			row.show();
		}
	}

	final private List<IHideable> _rows = new ArrayList<IHideable>();
}
