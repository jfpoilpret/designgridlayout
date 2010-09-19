//  Copyright 2009 Jean-Francois Poilpret
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

package net.java.dev.designgridlayout.bugs;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.java.dev.designgridlayout.AbstractBaseExample;
import net.java.dev.designgridlayout.DesignGridLayout;

public class Bug39HystericalJTextAreaWithJScrollPane extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Bug39HystericalJTextAreaWithJScrollPane example = new Bug39HystericalJTextAreaWithJScrollPane();
		example.go(true);
	}

	@Override protected void build(DesignGridLayout layout)
	{
		// uncommenting _both_ lines somehow prevents bug from happening 
//		layout.row().grid().add(new JCheckBox("Use Flex")).add(new JCheckBox("Hot"));
//		layout.row().grid().grid(new JLabel("Allowed Usage"));
		JTextArea textArea = new JTextArea(2, 10);
		textArea.setLineWrap(true);

		// workaround: set _both_ min and preferred size
//		textArea.setMinimumSize(new Dimension(10, 10));
//		textArea.setPreferredSize(new Dimension(10, 10));
//		layout.row().center().add(textArea);
		layout.row().grid().add(textArea);
	}

	@Override protected JComponent wrapPanel(JPanel top)
	{
		JScrollPane scrollPane = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(top);
		return scrollPane;
	}
}
