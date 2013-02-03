//  Copyright 2005-2013 Jason Aaron Osgood, Jean-Francois Poilpret
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

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class ShowHideRowsRealWorldExample1 extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		ShowHideRowsRealWorldExample1 example = new ShowHideRowsRealWorldExample1();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		addGroup(layout, "Identity");
		layout.row().grid(label("First name:")).add(field("Jean-Francois")).empty();
		layout.row().grid(label("Surname:")).add(field("Poilpret")).empty();
		layout.row().grid(label("Sex")).add(check("Male"), check("Female")).empty(2);

		List<IHideable> groupRows = new ArrayList<IHideable>();
		IRow row;
		addGroup(layout, "Address", groupRows);
		row = layout.row().grid(label("Address 1")).add(field(""));
		groupRows.add(row);
		row = layout.row().grid(label("Address 2")).add(field(""));
		groupRows.add(row);
		row = layout.row().grid(label("Zip")).add(field("")).empty(3);
		groupRows.add(row);
		row = layout.row().grid(label("City")).add(field(""));
		groupRows.add(row);
		
		groupRows = new ArrayList<IHideable>();
		addGroup(layout, "Preferences", groupRows);
		row = layout.row().grid(label("Guitars")).add(list());
		groupRows.add(row);

		layout.emptyRow();
		layout.row().bar().add(button("OK"), Tag.OK).add(button("Cancel"), Tag.CANCEL);
	}
	
	private void addGroup(DesignGridLayout layout, String name, List<IHideable> groupRows)
	{
		JCheckBox group = new JCheckBox(name);
		group.setName(name);
		group.setForeground(Color.BLUE);
		group.setSelected(true);
		group.addItemListener(new ShowHideAction(groupRows));
		layout.emptyRow();
		layout.row().left().add(group, new JSeparator()).fill();
	}
	
	private void addGroup(DesignGridLayout layout, String name)
	{
		JLabel group = new JLabel(name);
		group.setForeground(Color.BLUE);
		layout.emptyRow();
		layout.row().left().add(group, new JSeparator()).fill();
	}
	
	private JCheckBox check(String text)
	{
		return new JCheckBox(text);
	}
	
	private class ShowHideAction implements ItemListener
	{
		public ShowHideAction(List<IHideable> rows)
		{
			_rows = rows;
		}
		
		@Override public void itemStateChanged(ItemEvent event)
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				for (IHideable row: _rows)
				{
					row.show();
				}
			}
			else
			{
				for (IHideable row: _rows)
				{
					row.hide();
				}
			}
			frame().pack();
		}
		
		final private List<IHideable> _rows;
	}
}
