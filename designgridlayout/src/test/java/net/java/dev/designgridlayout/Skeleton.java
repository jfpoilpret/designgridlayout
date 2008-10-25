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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Skeleton extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Skeleton example = new Skeleton();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		layout.leftRow().add(projectLabel);
		layout.row().label(idLabel).add(id).empty(2);
		layout.emptyRow();

		layout.leftRow().add(mfgLabel);
		layout.row().label(companyLabel).add(company);
		layout.row().label(contactLabel).add(contact);
		layout.row().label(orderLabel).add(order).empty(2);
		layout.emptyRow();

		layout.leftRow().add(inspectorLabel);
		layout.row().label(nameLabel).add(name);
		layout.row().label(referenceLabel).add(reference).empty(2);
		layout.row().label(statusLabel).add(status).empty(2);
		layout.emptyRow();

		layout.leftRow().add(shipLabel);
		layout.row().label(shipyardLabel).add(shipyard);
		layout.row().label(registerNoLabel).add(registerNo).empty(2);
		layout.row().label(hullNumsLabel).add(hullNums).empty(2);
		layout.row().label(projectTypeLabel).add(projectType).empty(2);
	}
	// CSON: MagicNumber

	@Override public String name()
	{
		return "Skeleton";
	}

	// CSOFF: MemberName
	final private JLabel projectLabel = new JLabel("Project");
	final private JLabel idLabel = new JLabel("Identifier", SwingConstants.RIGHT);
	final private JTextField id = new JTextField("Sample Project");
	final private JLabel mfgLabel = new JLabel("Manufacturer");
	final private JLabel companyLabel = new JLabel("Company", SwingConstants.RIGHT);
	final private JTextField company = new JTextField("Hapag Lloyd");
	final private JLabel contactLabel = new JLabel("Contact", SwingConstants.RIGHT);
	final private JTextField contact = new JTextField("Buzz Lightyear");
	final private JLabel orderLabel = new JLabel("Order No", SwingConstants.RIGHT);
	final private JTextField order = new JTextField("583-992/20002");
	final private JLabel inspectorLabel = new JLabel("Inspector");
	final private JLabel nameLabel = new JLabel("Name", SwingConstants.RIGHT);
	final private JTextField name = new JTextField("Clouseau");
	final private JLabel referenceLabel = new JLabel("Reference No", SwingConstants.RIGHT);
	final private JTextField reference = new JTextField("32098");
	final private JLabel statusLabel = new JLabel("Status", SwingConstants.RIGHT);
	final private JComboBox status = new JComboBox(new String[]{"In Progress", "Overdue"});
	final private JLabel shipLabel = new JLabel("Ship");
	final private JLabel shipyardLabel = new JLabel("Shipyard", SwingConstants.RIGHT);
	final private JTextField shipyard = new JTextField("HDW");
	final private JLabel registerNoLabel = new JLabel("Register No", SwingConstants.RIGHT);
	final private JTextField registerNo = new JTextField("22067");
	final private JLabel hullNumsLabel = new JLabel("Hull Numbers", SwingConstants.RIGHT);
	final private JTextField hullNums = new JTextField("472");
	final private JLabel projectTypeLabel = new JLabel("Project Type", SwingConstants.RIGHT);
	final private JComboBox projectType = new JComboBox(
		new String[]{"New Building", "Overhaul"});
	// CSON: MemberName
}
