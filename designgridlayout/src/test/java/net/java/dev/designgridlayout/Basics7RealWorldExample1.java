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

package net.java.dev.designgridlayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Basics7RealWorldExample1 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Basics7RealWorldExample1 example = new Basics7RealWorldExample1();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		JRadioButton searchOnQueryButton = new JRadioButton("Search on Query");
		JRadioButton filterOnQueryButton = new JRadioButton("Filter on Query");
		JTextField nameField = new JTextField("abcdefghijklmopqrstuvwxyz");
		JTextField typeField = new JTextField("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		JTextField vendorField = new JTextField("0123456789");
		JTextField nodeField = new JTextField("~!@#$%^&*()_+}{|[]");
		JProgressBar progressBar = new JProgressBar(0, 9);
		progressBar.setValue(5);

		JLabel locationLabel = new JLabel("schema:/usr/dist");

		JCheckBox currentCheckBox = new JCheckBox("Current Library");
		JCheckBox localCheckBox = new JCheckBox("Local Machine");
		JCheckBox referencedCheckBox = new JCheckBox("Referenced Libraries");
		JCheckBox networkCheckBox = new JCheckBox("Network Storage");
		JCheckBox additionalCheckBox = new JCheckBox("Additional Libraries");
		JCheckBox unlicensedCheckBox = new JCheckBox("Unlicensed Components");

		JButton searchButton = new JButton("Search");
		JButton clearButton = new JButton("Clear");

		layout.row().grid(label("Mode:")).add(searchOnQueryButton).add(filterOnQueryButton);
		layout.row().grid(label("Name:")).add(nameField);
		layout.row().grid(label("Type:")).add(typeField);
		layout.row().grid(label("Vendor:")).add(vendorField);
		layout.row().grid(label("Note:")).add(nodeField);
		layout.emptyRow();
		layout.row().grid(label("Progress:")).add(progressBar).add(locationLabel);
		layout.emptyRow();
		layout.row().grid(label("Scope:")).add(currentCheckBox).add(localCheckBox);
		layout.row().grid().add(referencedCheckBox).add(networkCheckBox);
		layout.row().grid().add(additionalCheckBox).add(unlicensedCheckBox);
		layout.emptyRow();
		layout.row().center().add(searchButton).add(clearButton);
	}
	// CSON: MagicNumber
}
