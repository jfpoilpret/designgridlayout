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

import java.awt.Dimension;
import java.awt.Insets;
import java.util.List;

// The interface implemented by all layout engines, those which actually
// perform all the work
interface ILayoutEngine
{
	// Calls to DesignGridLayout, directly forwarded to ILayoutEngine
	public void margins(double top, double left, double bottom, double right);
	public void forceConsistentBaselinesDistance();

	public void reset();

	// Used for synchronization
	public boolean mustForceConsistentBaselinesDistance();
	public int getNumGrids();
	public List<Integer> getLabelWidths();
	public Insets getMargins();
	public int hgap();
	public void hgap(int hgap);
	public double getTotalWeight();
	public List<AbstractRow> rows();

	// Called by DesignGridLayout for actual LayoutManager stuff
	public Dimension getMinimumSize();
	public Dimension getPreferredSize();
	public int computeRowsActualHeight(int height);
	public void layoutContainer(int width, int height);
}
