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

// Class used during layout for special handling of row-span components.
// Each item is able to layout itself, based on all necessary information
// collected during the first pass of DesignGridLayout layout.
final class RowSpanItem
{
	RowSpanItem(int rowIndex, JComponent component, int spannedRows)
	{
		_rowIndex = rowIndex;
		_component = component;
		_rows = spannedRows;
		int height = _component.getPreferredSize().height;
		_heightPerRow = (height / _rows) + (height % _rows);
//		int vgap = 0;
//		for (AbstractRow row: spannedRows.subList(0, spannedRows.size() - 1))
//		{
//			vgap += row.vgap();
//		}
//		Dimension prefSize = _component.getPreferredSize();
//		int height = prefSize.height;
//		height = (height / _rows.size()) + (height % _rows.size()) - vgap;
//		setPreferredSize(new Dimension(prefSize.width, height));
	}
	
	JComponent component()
	{
		return _component;
	}
	
	int firstRow()
	{
		return _rowIndex;
	}
	
	int spannedRows()
	{
		return _rows;
	}
	
	boolean isRowSpanned(int row)
	{
		return (row >= _rowIndex) && (row < _rowIndex + _rows);
	}
	
	int heightPerRow()
	{
		return _heightPerRow;
	}
	
	void initUsableVgap(int usableVgap)
    {
		_heightPerRow -= usableVgap;
    }

	final private int _rowIndex;
	final private JComponent _component;
	final private int _rows;
	private int _heightPerRow = 0;
}
