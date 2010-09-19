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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.layout.Baseline;

// This helper solves an issue with baselines in Windows XP LAF on Java 5
final class BaselineHelper
{
	private BaselineHelper()
	{
	}

	static public int getBaseline(Component comp)
	{
		Dimension size = comp.getPreferredSize();
		return getBaseline(comp, size.width, size.height);
	}

	// CSOFF: ReturnCountCheck
	static private int getBaseline(Component comp, int width, int height)
	{
		if (!(comp instanceof JComponent))
		{
			// Heavyweight AWT Component is not supported
			return -1;
		}
		if (_isJava6)
		{
			// No required workaround for Java >= 6
			return Baseline.getBaseline((JComponent) comp, width, height);
		}
		// Handle special cases that swing-layout does not address well in Java5
		if (comp instanceof JScrollPane)
		{
			return getScrollPaneBaseline((JScrollPane) comp, width, height);
		}
		else if (comp instanceof JTableHeader)
		{
			return getTableHeaderBaseline((JTableHeader) comp, width, height);
		}
		else if (comp instanceof MultiComponent)
		{
			Dimension size = comp.getPreferredSize();
			return ((MultiComponent) comp).getBaseline(size.width, size.height);
		}
		else
		{
			return Baseline.getBaseline((JComponent) comp, width, height);
		}
	}
	// CSON: ReturnCountCheck

	private static int getTableHeaderBaseline(JTableHeader header, int width, int height)
	{
		TableColumnModel columnModel = header.getColumnModel();
		if (columnModel.getColumnCount() == 0)
		{
			return -1;
		}
		TableColumn column = columnModel.getColumn(0);
		TableCellRenderer renderer = column.getHeaderRenderer();
		if (renderer == null)
		{
			renderer = header.getDefaultRenderer();
		}
		Component comp = renderer.getTableCellRendererComponent(
			header.getTable(), column.getHeaderValue(), false, false, -1, 0);
		Dimension pref = comp.getPreferredSize();
		return getBaseline(comp, pref.width, height);
	}

	// CSOFF: ParameterAssignmentCheck
	static private int getScrollPaneBaseline(JScrollPane sp, int width, int height)
	{
		// Remove insets from calculation
		Insets spInsets = sp.getInsets();
		int y = spInsets.top;
		width -= spInsets.left + spInsets.right;
		height -= spInsets.top + spInsets.bottom;

		// First use baseline of column header if any
		JViewport columnHeader = sp.getColumnHeader();
		if (columnHeader != null && columnHeader.isVisible())
		{
			Component header = columnHeader.getView();
			if (header != null && header.isVisible())
			{
				// Header is always given its preferred size.
				int baseline = getBaseline(header);
				if (baseline >= 0)
				{
					return y + baseline;
				}
			}
			Dimension columnPref = columnHeader.getPreferredSize();
			height -= columnPref.height;
			y += columnPref.height;
		}

		// Else use baseline for view if any
		int baseline = getViewportBaseline(sp, width, height);
		if (baseline >= 0)
		{
			return y + baseline;
		}
		return -1;
	}
	// CSON: ParameterAssignmentCheck
	
	// CSOFF: ParameterAssignmentCheck
	static private int getViewportBaseline(JScrollPane sp, int width, int height)
	{
		JViewport viewport = sp.getViewport();
		Component view = (viewport == null) ? null : viewport.getView();
		if (view != null && view.isVisible())
		{
			int y = 0;
			Border viewportBorder = sp.getViewportBorder();
			if (viewportBorder != null)
			{
				Insets vpbInsets = viewportBorder.getBorderInsets(sp);
				y = vpbInsets.top;
				width -= vpbInsets.left + vpbInsets.right;
				height -= vpbInsets.top + vpbInsets.bottom;
			}
			if (view.getWidth() > 0 && view.getHeight() > 0)
			{
				Dimension min = view.getMinimumSize();
				width = Math.max(min.width, view.getWidth());
				height = Math.max(min.height, view.getHeight());
			}
			if (width > 0 && height > 0)
			{
				int baseline = getBaseline(view);
				if (baseline > 0)
				{
					return y + baseline;
				}
			}
		}
		return -1;
	}
	// CSON: ParameterAssignmentCheck
	
	static private final boolean _isJava6;
	
	// CSOFF: IllegalCatchCheck
	static
	{
		boolean isJava6;
		try
		{
			Component.class.getMethod("getBaseline", int.class, int.class);
			isJava6 = true;
		}
		catch (Exception e)
		{
			isJava6 = false;
		}
		_isJava6 = isJava6;
	}
	// CSON: IllegalCatchCheck
}
