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

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Bug33ProblemWithJTextArea extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Bug33ProblemWithJTextArea example = new Bug33ProblemWithJTextArea();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid().add(textarea(true));
		layout.row().grid().add(textarea(false));
	}
	
	protected JScrollPane textarea(boolean initRows)
	{
		JTextArea area = new JTextArea();
		if (initRows)
		{
//			area.setRows(3);
			area.setColumns(10);
		}
		area.setText("");
		return new JScrollPane(area);
	}
}
