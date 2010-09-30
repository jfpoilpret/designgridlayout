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

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Bug37SmartResizeWithJInternalFrame
{
	public static void main(String[] args) throws Exception
	{
		final Bug37SmartResizeWithJInternalFrame example = 
			new Bug37SmartResizeWithJInternalFrame();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				example.go(true);
			}
		});
	}
	
	public void go(boolean exitOnClose)
	{
		_frame = new JFrame();
		_frame.setName(getClass().getSimpleName());
		_frame.setBounds(30, 30, 800, 600);
		_frame.setDefaultCloseOperation(exitOnClose
			? JFrame.EXIT_ON_CLOSE
			: WindowConstants.DISPOSE_ON_CLOSE);
		JDesktopPane desktop = new JDesktopPane();
		_frame.setContentPane(desktop);
		_frame.setVisible(true);
		
		JInternalFrame frame = new JInternalFrame("", true, true, true, true);
		frame.setName("INTERNAL");
		frame.setLocation(30, 30);
		
		JPanel top = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(top);
		top.setName("TOP");
		build(layout);
		frame.setContentPane(top);

		// Order of calling desktop.add() is important wrt frame.pack() call!
		desktop.add(frame);
		frame.pack();
		frame.setVisible(true);
		try
		{
			frame.setSelected(true);
		}
		catch (java.beans.PropertyVetoException e)
		{
		}
	}

	protected void build(DesignGridLayout layout)
	{
		layout.row().center().fill().add(table());
		layout.row().center().add(new JButton("OK"));
	}
	
	protected JScrollPane table()
	{
		JTable table = new JTable(CONTENTS_PLAYERS, COLUMNS_PLAYERS);
		setTableHeight(table, 3);
		return new JScrollPane(table);
	}
	
	static protected void setTableHeight(JTable table, int rows)
	{
		int width = table.getColumnModel().getTotalColumnWidth();
//		int width = 600;
		int height = rows * table.getRowHeight();
		table.setPreferredScrollableViewportSize(new Dimension(width, height));
	}
	
	static private final Object[] COLUMNS_PLAYERS = {"First name", "Surname", "Band"};
	static private final Object[][] CONTENTS_PLAYERS =
	{
		{"Eric", "Clapton", "The Yardbirds"},
		{"Jimmy", "Page", "Led Zeppelin"},
		{"Jimi", "Hendrix", "The Jimi Hendrix Experience"},
		{"Mark", "Knopfler", "Dire Straits"},
		{"", "The Edge", "U2"},
		{"Gary", "Moore", "Thin Lizzy"},
	};
	
	protected JFrame _frame = null;
}
