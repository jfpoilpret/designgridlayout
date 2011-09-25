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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Basics8RealWorldExample2 extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Basics8RealWorldExample2 example = new Basics8RealWorldExample2();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		JTextField nameField = new JTextField("abcdefghijklmopqrstuvwxyz");
		JTextField streetField = new JTextField("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

		JRadioButton homeButton = new JRadioButton("Home");
		JRadioButton officeButton = new JRadioButton("Office");
		JRadioButton alternateButton = new JRadioButton("Alternate");

		JTextField cityField = new JTextField("Smallville");
		JTextField stateField = new JTextField("XX");
		JTextField zipField = new JTextField("12345");
		JTextField phoneField = new JTextField("888-555-1212");
		JTextField faxField = new JTextField("888-555-1212");

		JTextField emailField = new JTextField("mailbox@domain.com");

		JCheckBox compilerCheckBox = new JCheckBox("Compilers");
		JCheckBox databaseCheckBox = new JCheckBox("Database");
		JCheckBox productivityCheckBox = new JCheckBox("Productivity");
		JCheckBox prototypingCheckBox = new JCheckBox("Prototyping");
		JCheckBox teamwareCheckBox = new JCheckBox("Teamware");
		JCheckBox networkingCheckBox = new JCheckBox("Networking");

		applyButton = new JButton("Apply");
		JButton resetButton = new JButton("Reset");

		layout.row().grid(label("Name:")).add(nameField);
		layout.row().grid(label("Address:"))
			.add(homeButton).add(officeButton).add(alternateButton);
		layout.row().grid(label("Street:")).add(streetField);
		layout.row().grid(label("City/State:")).add(cityField, 2).add(stateField);
		layout.row().grid(label("Zip:")).add(zipField).empty(2);
		layout.row().grid(label("Phone/FAX:")).add(phoneField).add(faxField).empty();
		layout.row().grid(label("E-mail:")).add(emailField, 2).empty();
		layout.emptyRow();
		layout.row().grid(label("Interests:"))
			.add(compilerCheckBox).add(databaseCheckBox).add(productivityCheckBox);
		layout.row().grid().add(prototypingCheckBox).add(teamwareCheckBox).add(networkingCheckBox);
		layout.emptyRow();
		layout.row().center().add(applyButton).add(resetButton);
	}
	// CSON: MagicNumber

	private JButton applyButton;
}
