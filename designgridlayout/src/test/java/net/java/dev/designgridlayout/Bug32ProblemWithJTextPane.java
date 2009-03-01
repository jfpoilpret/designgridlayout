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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Bug32ProblemWithJTextPane extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Bug32ProblemWithJTextPane example = new Bug32ProblemWithJTextPane();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		_append.setName("append");
		_append.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				append();
			}
		});
		append();
		layout.row().grid(label("label1")).add(new JScrollPane(_text));
		layout.row().center().add(_append);
	}
	
	private void append()
	{
		StringBuilder text = new StringBuilder(_text.getText());
		for (int i = 0; i < 4; i++)
		{
			text.append("abcdef ghijk lmnop qrst\n");
		}
		_text.setText(text.toString());
	}
	
	private final JTextPane	_text = new JTextPane();
	private final JButton _append = new JButton("Append");
}
