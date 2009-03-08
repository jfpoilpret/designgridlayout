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

package net.java.dev.designgridlayout.bugs;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import net.java.dev.designgridlayout.AbstractBaseExample;
import net.java.dev.designgridlayout.DesignGridLayout;

public class Bug28bBadHeightWhenStartedFromEDT extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Bug28bBadHeightWhenStartedFromEDT example = 
					new Bug28bBadHeightWhenStartedFromEDT();
				example.go(true);
			}
		});
	}
	
	@Override protected void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label:")).add(list(), textarea("content"));
	}

	@Override protected JScrollPane textarea(String content)
	{
		JTextArea area = new JTextArea(5, 10);
		area.setText(content);
		return new JScrollPane(area);
	}
}
