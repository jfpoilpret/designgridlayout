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

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

public class ShowHideRowsRealWorldExample3 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		ShowHideRowsRealWorldExample3 example = new ShowHideRowsRealWorldExample3();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		addGroup(layout, "Identity");
		layout.row().grid(label("First name:")).add(field("Jean-Francois")).empty();
		layout.row().grid(label("Surname:")).add(field("Poilpret")).empty();
		layout.row().grid(label("Sex")).add(check("Male"), check("Female")).empty(2);

		RowGroup group = new RowGroup();
		addGroup(layout, "Address", group);
		layout.row().group(group).grid(label("Address 1")).add(field(""));
		layout.row().group(group).grid(label("Address 2")).add(field(""));
		layout.row().group(group).grid(label("Zip")).add(field("")).empty(3);
		layout.row().group(group).grid(label("City")).add(field(""));
		
		group = new RowGroup();
		addGroup(layout, "Preferences", group);
		layout.row().group(group).grid(label("Guitars")).add(list());

		layout.emptyRow();
		layout.row().bar().add(button("OK"), Tag.OK).add(button("Cancel"), Tag.CANCEL);
	}
	
	private void addGroup(DesignGridLayout layout, String name, RowGroup group)
	{
		JLabel groupName = new JLabel(name);
		groupName.setForeground(Color.BLUE);
		JToggleButton groupBox = new JToggleButton();
		groupBox.setName(name);
		// The following line is mandatory to get the right baseline for the icon!
		groupBox.setText(" ");
		// Since we already add pixels due to the text, we want to reduce any extra space
		groupBox.setIconTextGap(0);
		// Setting an empty border is necessary to reduce the size to just the icon
		// The down side is that it also removes the focus border
		// Could use 1 pixel instead but:
		// - focus border would not render right on HiDPI monitor anyway
		// - focus border clearly shows there is a space after the icon (the " " text)
		groupBox.setBorder(BorderFactory.createEmptyBorder());
		groupBox.setContentAreaFilled(false);
		groupBox.setFocusPainted(false);
		groupBox.setRolloverEnabled(true);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		groupBox.setIcon(new ImageIcon(loader.getResource("expand-group.png")));
		groupBox.setSelectedIcon(new ImageIcon(loader.getResource("collapse-group.png")));
//		groupBox.setRolloverSelectedIcon(
//			new ImageIcon(loader.getResource("expand-group.png")));
//		groupBox.setRolloverIcon(
//			new ImageIcon(loader.getResource("collapse-group.png")));
		groupBox.setSelected(true);
		groupBox.addItemListener(new ShowHideAction(group));
		layout.emptyRow();
		layout.row().left().addMulti(groupName, groupBox).add(new JSeparator()).fill();
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
		public ShowHideAction(RowGroup group)
		{
			_group = group;
		}
		
		@Override public void itemStateChanged(ItemEvent event)
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				_group.show();
			}
			else
			{
				_group.hide();
			}
			frame().pack();
		}
		
		final private RowGroup _group;
	}
}
