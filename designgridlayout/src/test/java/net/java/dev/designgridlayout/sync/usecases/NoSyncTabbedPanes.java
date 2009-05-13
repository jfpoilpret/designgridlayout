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

package net.java.dev.designgridlayout.sync.usecases;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.sync.AbstractSyncLayoutExample;

public class NoSyncTabbedPanes extends AbstractSyncLayoutExample
{
	public static void main(String[] args)
	{
		NoSyncTabbedPanes example = new NoSyncTabbedPanes();
		example.go(true);
	}
	
	@Override protected JPanel createTopPanel()
	{
		JPanel top = new JPanel();
		_tabs.setName("TABS");
		top.add(_tabs);
		return top;
	}

	@Override protected void build()
	{
		// Create first tab with general information
		DesignGridLayout layout1 = createSubPanel("Contact");
		layout1.row().grid(label("First name:")).add(field("Jean-Francois")).grid(label("Surname:")).add(field("Poilpret"));
		layout1.row().grid(label("Display:")).add(field("Mr Jean-Francois Poilpret"));
		layout1.row().grid(label("Email 1:")).add(field("jfpoilpret@yahoo.fr"));
		layout1.row().grid(label("Email 2:")).add(field(""));
		layout1.row().grid(label("Mobile:"), 1).add(field(""));
		layout1.forceConsistentBaselinesDistance();
		
		// Create second tab with other information
		DesignGridLayout layout2 = createSubPanel("Address");
		layout2.row().grid(label("Type:"), 1).add(_cboAddressType);
		layout2.row().grid(label("Street 1:")).add(field(""));
		layout2.row().grid(label("Street 2:")).add(field(""));
		layout2.row().grid(label("ZIP:")).add(field("")).grid(label("City:")).add(field("Ho Chi Minh city"));
		layout2.row().grid(label("Country:")).add(_cboCountries);
		layout2.forceConsistentBaselinesDistance();
		
		postBuild(layout1, layout2);
	}
	
	protected void postBuild(DesignGridLayout layout1, DesignGridLayout layout2)
	{
	}

	@Override protected DesignGridLayout createSubPanel(String title)
	{
		JPanel panel = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(panel);
		_tabs.addTab(title, panel);
		return layout;
	}
	
	@Override protected JTextField field(String text)
	{
		JTextField field = new JTextField(text);
		return field;
	}

	final private JTabbedPane _tabs = new JTabbedPane(); 
	final private JComboBox _cboAddressType = 
		new JComboBox(new Object[]{"Personal", "Professional"});
	final private JComboBox _cboCountries = 
		new JComboBox(new Object[]{"Vietnam"});
}
