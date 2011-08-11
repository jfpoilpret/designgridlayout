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

package net.java.dev.designgridlayout.showdown;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.IRow;

@SuppressWarnings("serial")
public class LafChooserDialog extends JDialog
{
	static private final int BUTTONS_PER_ROW = 3;
	
	public LafChooserDialog()
	{
		super((Frame) null, true);
		setTitle("Choose the Look & Feel to use");
		DesignGridLayout layout = new DesignGridLayout(getContentPane());
		List<JButton> buttons = getLAFs();
		// Use max 3 LAFs per row
		int rows = buttons.size() / BUTTONS_PER_ROW;
		int buttonsOnLastRow = buttons.size() % BUTTONS_PER_ROW;
		if (buttonsOnLastRow == 0)
		{
			buttonsOnLastRow = BUTTONS_PER_ROW;
		}
		else
		{
			rows++;
		}
		Iterator<JButton> button = buttons.iterator();
		for (int row = 0; row < rows; row++)
		{
			int numButtons = (row < rows - 1 ? BUTTONS_PER_ROW : buttonsOnLastRow);
			IRow buttonsRow = layout.row().grid();
			for (int column = 0; column < numButtons; column++)
			{
				buttonsRow.add(button.next());
			}
		}
		pack();
		setLocationRelativeTo(null);
	}
	
	private List<JButton> getLAFs()
	{
		List<JButton> buttons = new ArrayList<JButton>();
		for (final LookAndFeelInfo laf: UIManager.getInstalledLookAndFeels())
		{
			JButton button = new JButton(laf.getName());
			button.addActionListener(new ActionListener()
			{
				@Override public void actionPerformed(ActionEvent e)
				{
					setLAF(laf);
					setVisible(false);
				}
			});
			buttons.add(button);
		}
		return buttons;
	}

	static private void setLAF(final LookAndFeelInfo info)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				try
				{
					UIManager.setLookAndFeel(info.getClassName());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
