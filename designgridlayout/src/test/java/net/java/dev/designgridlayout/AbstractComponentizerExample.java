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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public abstract class AbstractComponentizerExample
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

		JPanel contentPane = new JPanel();
		contentPane.setName("TOP");
		_frame.setContentPane(contentPane);
		ComponentizerLayout layout = new ComponentizerLayout(contentPane);
		layout.withSmartVerticalResize();
		build(layout);

		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}
	
	protected JButton button()
	{
		return button("Button");
	}
	
	protected JButton button(String text)
	{
		return new JButton(text);
	}
	
	protected JLabel label(int num)
	{
		return label("Row " + num);
	}

	protected JLabel label(String label)
	{
		return new JLabel(label);
	}

	protected JTextField field(String text)
	{
		JTextField field = new JTextField(text);
		return field;
	}
	
	protected JScrollPane textarea(String content)
	{
		JTextArea area = new JTextArea(3, 10);
		area.setText(content);
		return new JScrollPane(area);
	}
	
	protected JScrollPane list()
	{
		JList list = new JList(GUITARS);
		list.setVisibleRowCount(2);
		return new JScrollPane(list);
	}

	protected abstract void build(ComponentizerLayout layout);

	public String name()
	{
		return getClass().getSimpleName();
	}

	static private final Object[] GUITARS =
	{
		"Fender Telecaster",
		"Fender Stratocaster",
		"Gibson Les Paul",
	};
}
