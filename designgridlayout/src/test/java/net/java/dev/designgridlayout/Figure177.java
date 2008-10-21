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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Figure177 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Figure177 example = new Figure177();
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

		layout.row().label(label("Mode:")).add(searchOnQueryButton).add(filterOnQueryButton);
		layout.row().label(label("Name:")).add(nameField);
		layout.row().label(label("Type:")).add(typeField);
		layout.row().label(label("Vendor:")).add(vendorField);
		layout.row().label(label("Note:")).add(nodeField);
		layout.emptyRow(14);
		layout.row().label(label("Progress:")).add(progressBar).add(locationLabel);
		layout.emptyRow(14);
		layout.row().label(label("Scope:")).add(currentCheckBox).add(localCheckBox);
		layout.row().label().add(referencedCheckBox).add(networkCheckBox);
		layout.row().label().add(additionalCheckBox).add(unlicensedCheckBox);
		layout.emptyRow(20);
		layout.centerRow().add(searchButton).add(clearButton);
	}
	// CSON: MagicNumber
}
