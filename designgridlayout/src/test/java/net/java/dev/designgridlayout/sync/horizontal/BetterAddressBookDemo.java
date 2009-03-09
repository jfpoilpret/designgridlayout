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

package net.java.dev.designgridlayout.sync.horizontal;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.Synchronizer;
import net.java.dev.designgridlayout.sync.AbstractSyncLayoutExample;

/**
 * Created in response to John O'Conner's "Layout Manager Showdown",
 * http://weblogs.java.net/blog/joconner/archive/2006/10/layout_manager.html
 * 
 * @author jasonosgood
 */
public class BetterAddressBookDemo extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		BetterAddressBookDemo example = new BetterAddressBookDemo();
		example.go(true);
	}

	@Override protected JComponent createTopPanel()
	{
    	JPanel top = new JPanel();
    	top.setLayout(new BorderLayout());
		return top;
	}
	
	@Override protected void build()
	{
		// Use DGL for the list on the left (fixed width)
		DesignGridLayout layout1 = createSubPanel(BorderLayout.LINE_START);
		layout1.row().center().fill().add(scroller);
		
		// Use DGL for the details on the right (variable width)
		DesignGridLayout layout2 = createSubPanel(BorderLayout.CENTER);
		layout2.row().grid(label("Last Name"))	.add(lastNameField)	.grid(label("First Name"))	.add(firstNameField);
		layout2.row().grid(label("Phone"))		.add(phoneField)	.grid(label("Email"))		.add(emailField);
		layout2.row().grid(label("Address 1"))	.add(address1Field);
		layout2.row().grid(label("Address 2"))	.add(address2Field);
		layout2.row().grid(label("City"), 1)		.add(cityField);
		layout2.row().grid(label("State"))		.add(stateField)	.grid(label("Postal Code"))	.add(postalField);
		layout2.row().grid(label("Country"), 1)	.add(countryField);
		layout2.emptyRow();
		layout2.row().center().add(newButton).add(deleteButton).add(editButton).add(saveButton).add(cancelButton);
		
		Synchronizer.synchronize(layout1, layout2).alignRows();
	}

	@Override public String name()
	{
		return "Address Book Demo - Improved";
	}

	static private final String[] ITEMS =
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
	
	final private JList list = new JList(ITEMS);
	final private JScrollPane scroller = new JScrollPane(
		list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	final private JTextField lastNameField = new JTextField("Martian");
	final private JTextField firstNameField = new JTextField("Marvin");
	final private JTextField phoneField = new JTextField("805-123-4567");
	final private JTextField emailField = new JTextField("marvin@wb.com");
	final private JTextField address1Field = new JTextField("1001001010101 Martian Way");
	final private JTextField address2Field = new JTextField("Suite 10111011");
	final private JTextField cityField = new JTextField("Ventura");
	final private JTextField stateField = new JTextField("CA");
	final private JTextField postalField = new JTextField("93001");
	final private JTextField countryField = new JTextField("USA");

	final private JButton newButton = new JButton("New");
	final private JButton deleteButton = new JButton("Delete");
	final private JButton editButton = new JButton("Edit");
	final private JButton saveButton = new JButton("Save");
	final private JButton cancelButton = new JButton("Cancel");
}
