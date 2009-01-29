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
import java.util.Map;

// The interface implemented by all layout engines, those which actually
// perform all the work
interface ILayoutEngine
{
	public void margins(double top, double left, double bottom, double right);
	public void forceConsistentVGaps();

	public void reset();
	
	public int getNumGrids();
	public List<Integer> getLabelWidths();
	public Insets getMargins();
	public int hgap();
	public void hgap(int hgap);
	public List<AbstractRow> rows();
	public void setMapRowsPosition(Map<String, Integer> rowsPosition);

	public Dimension getMinimumSize();
	public Dimension getPreferredSize();
	public void layoutContainer();
}
