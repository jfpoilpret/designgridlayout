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

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public abstract class AbstractBaseExample
{
	protected JFrame _frame = null;
	protected JButton _lastCreatedButton = null;

	public JFrame frame()
	{
		return _frame;
	}

	public void go(boolean exitOnClose)
	{
		_frame = new JFrame(name());
		_frame.setName(getClass().getSimpleName());

		_frame.setDefaultCloseOperation(exitOnClose
			? JFrame.EXIT_ON_CLOSE
			: WindowConstants.DISPOSE_ON_CLOSE);
		JPanel top = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(top);
		top.setName("TOP");

		build(layout);

		_frame.add(top);
		prePack();
		_frame.pack();
		preDisplay();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	protected void prePack()
	{
	}

	protected void preDisplay()
	{
		if (_lastCreatedButton != null)
		{
			_lastCreatedButton.requestFocusInWindow();
		}
	}

	protected JButton button()
	{
		return button("Button");
	}
	
	protected JButton button(String text)
	{
		_lastCreatedButton = new JButton(text);
		return _lastCreatedButton;
	}
	
	protected JLabel label(int num)
	{
		return label("Row " + num);
	}

	protected JLabel label(String label)
	{
		return new JLabel(label);
	}

	protected JTextField field(String text)
	{
		JTextField field = new JTextField(text);
		return field;
	}
	
	protected JSlider slider(int orientation)
	{
		JSlider slider = new JSlider(orientation, 0, 100, 50);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		return slider;
	}
	
	protected JScrollPane textarea(String content)
	{
		JTextArea area = new JTextArea(3, 10);
		area.setText(content);
		return new JScrollPane(area);
	}
	
	protected JScrollPane list()
	{
		JList list = new JList(GUITARS);
		list.setVisibleRowCount(2);
		return new JScrollPane(list);
	}

	protected JScrollPane table()
	{
		JTable table = new JTable(CONTENTS_PLAYERS, COLUMNS_PLAYERS);
		setTableHeight(table, 4);
		return new JScrollPane(table);
	}
	
	static protected void setTableHeight(JTable table, int rows)
	{
//		int width = table.getColumnModel().getTotalColumnWidth();
		int width = 200;
		int height = rows * table.getRowHeight();
		table.setPreferredScrollableViewportSize(new Dimension(width, height));
	}
	
	protected abstract void build(DesignGridLayout layout);

	public String name()
	{
		return getClass().getSimpleName();
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
	
	static private final Object[] GUITARS =
	{
		"Fender Telecaster",
		"Fender Stratocaster",
		"Gibson Les Paul",
	};
}
