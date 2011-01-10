//  Copyright 2005-2010 Jason Aaron Osgood, Jean-Francois Poilpret
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
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Rfe47ShowHideRowsTestExample extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Rfe47ShowHideRowsTestExample example = new Rfe47ShowHideRowsTestExample();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		JButton showR2 = new JButton("Show R2");
		JButton hideR2 = new JButton("Hide R2");
		JButton showR4 = new JButton("Show R4");
		JButton hideR4 = new JButton("Hide R4");
		JButton showR5 = new JButton("Show R5");
		JButton hideR5 = new JButton("Hide R5");
		JButton showR6 = new JButton("Show R6");
		JButton hideR6 = new JButton("Hide R6");

		layout.row().center().add(showR2, hideR2, showR4, hideR4, showR5, hideR5, showR6, hideR6);
		layout.row().grid(label("Row 1")).add(field("Field 11"), field("Field 12"));
		final IRow row2 = layout.row().grid(label("Row 2")).add(field("Field 21: a long field"));
		layout.row().grid(label("Row 3")).add(field("Field 31"), field("Field 32"), field("Field 33"));
		final IRow row4 = layout.row().grid(label("Row 4")).add(field("Field 41"), list());
		final IRow row5 = layout.row().grid(label("Row 5")).add(field("Field 51")).spanRow();
		final IRow row6 = layout.row().grid(label("Row 6")).add(field("Field 51")).spanRow();
		layout.row().grid(label("Row 7")).add(field("Field 71"), field("Field 72"));

		showR2.addActionListener(new ShowHideAction(row2, true));
		hideR2.addActionListener(new ShowHideAction(row2, false));
		showR4.addActionListener(new ShowHideAction(row4, true));
		hideR4.addActionListener(new ShowHideAction(row4, false));
		showR5.addActionListener(new ShowHideAction(row5, true));
		hideR5.addActionListener(new ShowHideAction(row5, false));
		showR6.addActionListener(new ShowHideAction(row6, true));
		hideR6.addActionListener(new ShowHideAction(row6, false));
	}
	
	private class ShowHideAction implements ActionListener
	{
		public ShowHideAction(IRow row, boolean show)
		{
			_row = row;
			_show = show;
		}
		
		@Override public void actionPerformed(ActionEvent evt)
		{
			if (_show)
			{
				_row.show();
			}
			else
			{
				_row.hide();
			}
			frame().pack();
		}
		
		final private IRow _row;
		final private boolean _show;
	}
}
