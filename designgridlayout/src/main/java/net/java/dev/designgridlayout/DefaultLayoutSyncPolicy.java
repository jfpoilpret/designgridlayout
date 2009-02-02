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

import java.util.List;

import static net.java.dev.designgridlayout.RowIterator.each;

// This very simple policy for synchronizing rows of various layouts simply
// algns the first row of all layouts.
class DefaultLayoutSyncPolicy extends AbstractLayoutSyncPolicy
{
	public void synchronize(List<ILayoutEngine> engines)
	{
		// Compute max baseline of first row for all layouts
		int maxBaseline = 0;
		for (ILayoutEngine engine: engines)
		{
			int rowBaseline = 0;
			for (AbstractRow row: each(engine.rows()))
			{
				rowBaseline = row.baseline();
				break;
			}
			maxBaseline = Math.max(maxBaseline, rowBaseline);
		}
		// Force consistent baseline for first row of all layouts
		for (ILayoutEngine engine: engines)
		{
			for (AbstractRow row: each(engine.rows()))
			{
				row.baseline(maxBaseline);
				break;
			}
		}
	}
}
