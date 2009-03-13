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

package net.java.dev.designgridlayout.internal.sync;

import java.awt.Insets;
import java.util.List;

import net.java.dev.designgridlayout.internal.engine.ILayoutEngine;
import net.java.dev.designgridlayout.internal.row.AbstractRow;
import net.java.dev.designgridlayout.internal.util.ConsistentBaselineSpacingHelper;

import static net.java.dev.designgridlayout.internal.util.RowIterator.each;

abstract class AbstractLayoutSyncPolicy implements ILayoutRowSyncPolicy
{
	public int preferredHeight(List<ILayoutEngine> engines)
	{
		synchronize(engines, false);
		int maxHeight = 0;
		for (ILayoutEngine engine: engines)
		{
			maxHeight = Math.max(maxHeight, engineHeight(engine));
		}
		return maxHeight;
	}

	public int availableHeight(
		int height, List<ILayoutEngine> engines, ILayoutEngine current)
	{
		return height;
	}

	public void synchronize(List<ILayoutEngine> engines)
	{
		synchronize(engines, true);
	}

	protected int engineHeight(ILayoutEngine engine)
	{
		Insets margins = engine.getMargins();
		int layoutHeight = margins.top + margins.bottom;
		for (AbstractRow row: each(engine.rows()))
		{
			layoutHeight += row.height() + row.extraHeight() + row.vgap();
		}
		return layoutHeight;
	}
	
	protected void makeBaselinesDistanceConsistent(
		List<ILayoutEngine> engines, boolean respectHeight)
	{
		ConsistentBaselineSpacingHelper.fixEnginesBaselinesDistance(
			engines, respectHeight);
	}
	
	abstract protected void synchronize(
		List<ILayoutEngine> engines, boolean respectHeight);
}
