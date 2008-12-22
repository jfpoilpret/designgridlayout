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
		//TODO: improve margins calculations for List
		listPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		JPanel addressPanel = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(addressPanel);
		build(layout);

		JPanel top = new JPanel();
		top.setName("TOP");
		top.setLayout(new BorderLayout());
		top.add(listPanel, BorderLayout.LINE_START);
		top.add(addressPanel, BorderLayout.CENTER);
		_frame.add(top);
		prePack();
		_frame.pack();
		preDisplay();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}
	// CSON: MagicNumber

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

		layout.row().grid(label("Last Name"))	.add(lastNameField)	.grid(label("First Name"))	.add(firstNameField);
		layout.row().grid(label("Phone"))		.add(phoneField)	.grid(label("Email"))		.add(emailField);
		layout.row().grid(label("Address 1"))	.add(address1Field);
		layout.row().grid(label("Address 2"))	.add(address2Field);
		layout.row().grid(label("City"), 1)		.add(cityField);
		layout.row().grid(label("State"))		.add(stateField)	.grid(label("Postal Code"))	.add(postalField);
		layout.row().grid(label("Country"), 1)	.add(countryField);
		layout.emptyRow();
		layout.row().center().add(newButton).add(deleteButton).add(editButton).add(saveButton).add(cancelButton);
	}

	@Override public String name()
	{
		return "Address Book Demo";
	}
}
