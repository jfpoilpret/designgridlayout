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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Shows issue 5 with lack of variable height rows
public class SmartVerticalResize2AllComponents extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		if (args.length > 1)
		{
			_loadPlafs = Boolean.parseBoolean(args[1]);
		}
		SmartVerticalResize2AllComponents example = new SmartVerticalResize2AllComponents();
		example.go(true);
	}
	
	public SmartVerticalResize2AllComponents()
	{
		_helper = (_loadPlafs ? new LafHelper() : null);
		_plafs = (_loadPlafs ? new JComboBox(_helper.getPlafs()) : new JComboBox());
	}
	
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Look & Feel")).add(_plafs);
		layout.row().grid(label("Label2")).add(table());
		layout.row().grid(label("L3")).add(field("Field31")).empty();
		layout.row().grid(label("Lbl4")).add(table()).add(table());
		layout.row().grid(label("L5")).add(field("F5")).add(slider(JSlider.HORIZONTAL));
		layout.row().grid(label("L6")).add(list()).add(textarea("Wonderful sound"));
		layout.row().grid(label("Power")).add(slider(JSlider.VERTICAL));
		layout.row().center().add(systemLafSetter(), javaLafSetter(), lafSetter());
	}
	
	@SuppressWarnings("serial")
	private JButton lafSetter()
	{
		Action action = new AbstractAction("Combo-selected LAF")
		{
			public void actionPerformed(ActionEvent e)
			{
				String laf = (String) _plafs.getSelectedItem();
				if (laf != null && !laf.equals(""))
				{
					setLaf((String) _plafs.getSelectedItem());
				}
			}
		};
		JButton button = new JButton(action);
		button.setName("ComboLAF");
		return button;
	}
	
	@SuppressWarnings("serial")
	private JButton systemLafSetter()
	{
		Action action = new AbstractAction("System LAF")
		{
			public void actionPerformed(ActionEvent e)
			{
				setLaf(UIManager.getSystemLookAndFeelClassName());
			}
		};
		JButton button = new JButton(action);
		button.setName("SystemLAF");
		return button;
	}
	
	@SuppressWarnings("serial")
	private JButton javaLafSetter()
	{
		Action action = new AbstractAction("Java LAF")
		{
			public void actionPerformed(ActionEvent e)
			{
				setLaf(UIManager.getCrossPlatformLookAndFeelClassName());
			}
		};
		JButton button = new JButton(action);
		button.setName("JavaLAF");
		return button;
	}
	
	private void setLaf(String lafClassName)
	{
		// CSOFF: IllegalCatch
		try
		{
			_helper.setPlaf(lafClassName);
			SwingUtilities.updateComponentTreeUI(frame());
			frame().pack();
			frame().repaint();
		}
		catch (Exception e1)
		{
			// CSOFF: GenericIllegalRegexp
			e1.printStackTrace();
			// CSON: GenericIllegalRegexp
		}
		// CSON: IllegalCatch
	}
	
	static private boolean _loadPlafs = true;

	private final LafHelper _helper;
	private final JComboBox _plafs;
}
