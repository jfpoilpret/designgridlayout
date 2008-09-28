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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * Created in response to John O'Conner's "Layout Manager Showdown",
 * http://weblogs.java.net/blog/joconner/archive/2006/10/layout_manager.html
 * 
 * @author jasonosgood
 */
public class AddressBookDemo extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		AddressBookDemo example = new AddressBookDemo();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void go(boolean exitOnClose)
	{
		_frame = new JFrame(name());
		_frame.setName(getClass().getSimpleName());

		_frame.setDefaultCloseOperation(exitOnClose
		    ? JFrame.EXIT_ON_CLOSE
		    : WindowConstants.DISPOSE_ON_CLOSE);

		String[] items =
		{
			"Bunny, Bugs",
			"Cat, Sylvester",
			"Coyote, Wile E.",
			"Devil, Tasmanian",
			"Duck, Daffy",
			"Fudd, Elmer",
			"Le Pew, Pepe",
			"Martian, Marvin"
		};
		JList list = new JList(items);
		JScrollPane scroller =
		    new JScrollPane(
		        list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
		        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.add(scroller, BorderLayout.CENTER);
		listPanel.setPreferredSize(new Dimension(150, 1));
		listPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 0));

		JPanel addressPanel = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(addressPanel);
		build(layout);

		JPanel top = new JPanel();
		top.setName("TOP");
		top.setLayout(new BorderLayout());
		top.add(listPanel, BorderLayout.WEST);
		top.add(addressPanel, BorderLayout.CENTER);
		_frame.add(top);
		_frame.pack();
		_frame.setVisible(true);
	}
	// CSON: MagicNumber

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		JTextField lastNameField = new JTextField("Martian");
		JTextField firstNameField = new JTextField("Marvin");
		JTextField phoneField = new JTextField("805-123-4567");
		JTextField emailField = new JTextField("marvin@wb.com");
		JTextField address1Field = new JTextField("1001001010101 Martian Way");
		JTextField address2Field = new JTextField("Suite 10111011");
		JTextField cityField = new JTextField("Ventura");
		JTextField stateField = new JTextField("CA");
		JTextField postalField = new JTextField("93001");
		JTextField countryField = new JTextField("USA");

		JButton newButton = new JButton("New");
		JButton deleteButton = new JButton("Delete");
		JButton editButton = new JButton("Edit");
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");

		layout.row().label(label("Last Name"))
			.add(lastNameField, 2).add(label("First Name")).add(firstNameField, 2);
		layout.row().label(label("Phone"))
			.add(phoneField, 2).add(label("Email")).add(emailField, 2);
		layout.row().label(label("Address 1")).add(address1Field);
		layout.row().label(label("Address 2")).add(address2Field);
		layout.row().label(label("City")).add(cityField, 2);
		layout.row().label(label("State"))
			.add(stateField, 2).add(label("Postal")).add(postalField, 2);
		layout.row().label(label("Country")).add(countryField, 2).empty(3);
		layout.emptyRow(18);
		layout.centerRow().add(newButton).add(deleteButton).add(editButton)
			.add(saveButton).add(cancelButton);
	}
	// CSON: MagicNumber

	static public JLabel label(String text)
	{
		JLabel label = AbstractBaseExample.label(text);
		label.setHorizontalAlignment(JLabel.RIGHT);
		return label;
	}
	
	@Override public String name()
	{
		return "Address Book Demo";
	}
}
