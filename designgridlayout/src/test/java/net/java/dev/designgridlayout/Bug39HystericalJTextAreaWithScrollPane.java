//  Copyright 2005-2013 Jason Aaron Osgood, Jean-Francois Poilpret
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

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Issue 39: when DGL form has JTextArea and this form is within a JScrollPane
public class Bug39HystericalJTextAreaWithScrollPane extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Bug39HystericalJTextAreaWithScrollPane example = 
			new Bug39HystericalJTextAreaWithScrollPane();
		example.go(true);
	}

	@Override protected void addTopPanel(JComponent top)
	{
		JScrollPane scroller = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setViewportView(top);
		super.addTopPanel(scroller);
	}

	@Override public void build(DesignGridLayout layout)
	{
//		layout.row().grid().add(new JCheckBox("Abc")).add(new JCheckBox("123"));
		JTextArea area = new JTextArea(2, 10);
		area.setLineWrap(true);
		layout.row().grid().add(area);
	}
}
