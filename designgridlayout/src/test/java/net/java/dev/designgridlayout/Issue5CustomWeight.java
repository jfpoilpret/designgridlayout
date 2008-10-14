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

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;

// Shows issue 5 with lack of variable height rows
public class Issue5CustomWeight extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Issue5CustomWeight example = new Issue5CustomWeight();
		example.go(true);
	}
	
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().label(label("Label2")).add(table());
		layout.row().growWeight(1.0).label(label("L3")).add(field("Field31")).empty();
		layout.row().growWeight(2.0).label(label("Lbl4")).add(table()).add(table());
		layout.row().label(label("L5")).add(field("F5")).add(slider(JSlider.HORIZONTAL));
		layout.row().label(label("L6")).add(list()).add(textarea("Wonderful sound"));
		layout.row().growWeight(0.0).label(label("Power")).add(slider(JSlider.VERTICAL));
		layout.centerRow().add(button(), button(), button());
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
}
