//  Copyright 2005-2011 Jason Aaron Osgood, Jean-Francois Poilpret
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
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

abstract public class AbstractExample implements IExample
{
	protected JFrame _frame = null;

	@Override public JFrame frame()
	{
		return _frame;
	}

	@Override public String name()
	{
		return getClass().getSimpleName();
	}

	protected static void setTableHeight(JTable table, int rows)
	{
	//		int width = table.getColumnModel().getTotalColumnWidth();
			int width = 200;
			int height = rows * table.getRowHeight();
			table.setPreferredScrollableViewportSize(new Dimension(width, height));
		}

	protected JButton button()
	{
		return button("Button");
	}

	protected JButton button(String text)
	{
		return new JButton(text);
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

	private static final Object[] COLUMNS_PLAYERS = {"First name", "Surname", "Band"};

	private static final Object[][] CONTENTS_PLAYERS = {
			{"Eric", "Clapton", "The Yardbirds"},
			{"Jimmy", "Page", "Led Zeppelin"},
			{"Jimi", "Hendrix", "The Jimi Hendrix Experience"},
			{"Mark", "Knopfler", "Dire Straits"},
			{"", "The Edge", "U2"},
			{"Gary", "Moore", "Thin Lizzy"},
		};
	private static final Object[] GUITARS = {
			"Fender Telecaster",
			"Fender Stratocaster",
			"Gibson Les Paul",
		};
}
