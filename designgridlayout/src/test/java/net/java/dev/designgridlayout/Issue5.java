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
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Shows issue 5 with lack of variable height rows
public class Issue5 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Issue5 example = new Issue5();
		example.go(true);
	}
	
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().label(label("Look & Feel")).add(_plafs);
		layout.row().label(label("Label2")).add(table());
		layout.row().label(label("L3")).add(field("Field31")).empty();
		layout.row().label(label("Lbl4")).add(table()).add(table());
		layout.row().label(label("L5")).add(field("F5")).add(slider(JSlider.HORIZONTAL));
		layout.row().label(label("L6")).add(list()).add(textarea("Wonderful sound"));
		layout.row().label(label("Power")).add(slider(JSlider.VERTICAL));
		layout.centerRow().add(systemLafSetter(), javaLafSetter(), lafSetter());
	}
	
	static private JSlider slider(int orientation)
	{
		JSlider slider = new JSlider(orientation, 0, 100, 50);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		return slider;
	}
	
	static private JScrollPane textarea(String content)
	{
		JTextArea area = new JTextArea(3, 10);
		area.setText(content);
		return new JScrollPane(area);
	}
	
	static private JScrollPane table()
	{
		JTable table = new JTable(content_players, columns_players);
		setTableHeight(table, 4);
		return new JScrollPane(table);
	}
	
	static private JScrollPane list()
	{
		JList list = new JList(guitars);
		list.setVisibleRowCount(2);
		return new JScrollPane(list);
	}
	
	static private void setTableHeight(JTable table, int rows)
	{
//		int width = table.getColumnModel().getTotalColumnWidth();
		int width = 200;
		int height = rows * table.getRowHeight();
		table.setPreferredScrollableViewportSize(new Dimension(width, height));
	}
	
	private JButton lafSetter()
	{
		Action action = new AbstractAction("Combo-selected LAF")
		{
			public void actionPerformed(ActionEvent e)
			{
				String laf = (String) _plafs.getSelectedItem();
				if (laf != null && !laf.equals(""))
				{
					setLaf((String) _plafs.getSelectedItem());
				}
			}
		};
		JButton button = new JButton(action);
		button.setName("ComboLAF");
		return button;
	}
	
	private JButton systemLafSetter()
	{
		Action action = new AbstractAction("System LAF")
		{
			public void actionPerformed(ActionEvent e)
			{
				setLaf(UIManager.getSystemLookAndFeelClassName());
			}
		};
		JButton button = new JButton(action);
		button.setName("SystemLAF");
		return button;
	}
	
	private JButton javaLafSetter()
	{
		Action action = new AbstractAction("Java LAF")
		{
			public void actionPerformed(ActionEvent e)
			{
				setLaf(UIManager.getCrossPlatformLookAndFeelClassName());
			}
		};
		JButton button = new JButton(action);
		button.setName("JavaLAF");
		return button;
	}
	
	private void setLaf(String lafClassName)
	{
		// CSOFF: IllegalCatch
		try
		{
			_helper.setPlaf(lafClassName);
			SwingUtilities.updateComponentTreeUI(frame());
			frame().pack();
			frame().repaint();
		}
		catch (Exception e1)
		{
			// CSOFF: GenericIllegalRegexp
			e1.printStackTrace();
			// CSON: GenericIllegalRegexp
		}
		// CSON: IllegalCatch
	}
	
	static private final Object[] columns_players = {"First name", "Surname", "Band"};
	static private final Object[][] content_players =
	{
		{"Eric", "Clapton", "The Yardbirds"},
		{"Jimmy", "Page", "Led Zeppelin"},
		{"Jimi", "Hendrix", "The Jimi Hendrix Experience"},
		{"Mark", "Knopfler", "Dire Straits"},
		{"", "The Edge", "U2"},
		{"Gary", "Moore", "Thin Lizzy"},
	};
	
	static private final Object[] guitars =
	{
		"Fender Telecaster",
		"Fender Stratocaster",
		"Gibson Les Paul",
	};

	private final LafHelper _helper = new LafHelper();
	private final JComboBox _plafs = new JComboBox(_helper.getPlafs());
}
