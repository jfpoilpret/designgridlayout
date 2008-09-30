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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

public class Examples extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Examples examples = new Examples();
		examples.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().add(launch("Example1a")).add(launch("Example1b"))
			.add(launch("Example1c")).add(launch("Example1d")).add(launch("Example1e"));
		layout.row().add(launch("Figure177")).add(launch("Figure178"))
			.add(launch("Figure179")).add(launch("Figure179bis"));
		layout.row().add(launch("AddressBookDemo"));
		layout.row().add(launch("Issue1")).add(launch("Issue2"))
			.add(launch("Issue3")).add(launch("Issue4"));
	}

	public JButton launch(String name)
	{
		Action action = new AbstractAction(name)
		{
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				// CSOFF: IllegalCatch
				try
				{
					Class c = Class.forName(
						"net.java.dev.designgridlayout." + getValue(Action.NAME));
					AbstractBaseExample example = (AbstractBaseExample) c.newInstance();
					example.go(false);
				}
				catch (Exception e1)
				{
					// CSOFF: GenericIllegalRegexp
					e1.printStackTrace();
					// CSON: GenericIllegalRegexp
				}
				// CSON: IllegalCatch
			}
		};
		JButton button = new JButton(action);
		button.setName(name);
		return button;
	}
}
