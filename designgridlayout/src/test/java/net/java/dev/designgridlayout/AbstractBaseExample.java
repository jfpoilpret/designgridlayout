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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public abstract class AbstractBaseExample
{
	protected JFrame _frame = null;

	public JFrame frame()
	{
		return _frame;
	}

	public void go(boolean exitOnClose)
	{
		_frame = new JFrame(name());
		_frame.setName(getClass().getSimpleName());

		_frame.setDefaultCloseOperation(exitOnClose
		    ? JFrame.EXIT_ON_CLOSE
		    : WindowConstants.DISPOSE_ON_CLOSE);
		JPanel top = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(top);
		top.setLayout(layout);
		top.setName("TOP");

		build(layout);

		_frame.add(top);
		_frame.pack();
		_frame.setVisible(true);
	}

	public static JButton button()
	{
		return new JButton("Button");
	}
	
	public static JLabel label(int num)
	{
		return label("Row " + num);
	}

	public static JLabel label(String label)
	{
		return new JLabel(label);
	}

	public abstract void build(DesignGridLayout layout);

	public abstract String name();
}
