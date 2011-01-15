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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Issue 35: when DGL form has a JPanel that contains variable-height children
public class Bug35PanelWithVariableHeightChild extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Bug35PanelWithVariableHeightChild example = new Bug35PanelWithVariableHeightChild();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		JPanel pane = new JPanel(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.insets = new Insets(2, 2, 2, 2);


		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		pane.add(textarea("a\nb\nc\nd", 3), constraints);

		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		pane.add(field("field"), constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		pane.add(textarea("a\nb\nc\nd", 2), constraints);

		constraints.gridy = 1;
		pane.add(textarea("a\nb\nc\nd", 4), constraints);
		
		layout.row().grid().add(pane);
	}

	protected JScrollPane textarea(String content, int rows)
	{
		JTextArea area = new JTextArea(rows, 10);
		area.setText(content);
		return new JScrollPane(area);
	}
}
