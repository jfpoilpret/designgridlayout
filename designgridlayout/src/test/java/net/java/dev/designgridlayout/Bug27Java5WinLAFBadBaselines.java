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

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Bug27Java5WinLAFBadBaselines extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
//				setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				Bug27Java5WinLAFBadBaselines example = new Bug27Java5WinLAFBadBaselines();
				example.go(true);
				example.displayBaselines("after show:");
			}
		});
	}
	
	static public void setLookAndFeel(String laf)
	{
		try
		{
			UIManager.setLookAndFeel(laf);
//			if (_frame != null)
//			{
//				_frame.pack();
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override protected void build(DesignGridLayout layout)
	{
		layout.row().grid(label).add(list);
	}
	
	@Override protected void prePack()
	{
		displayBaselines("prePack:");
	}
	
	@Override protected void preDisplay()
	{
		displayBaselines("preDisplay:");
	}

	protected void displayBaselines(String title)
	{
		System.out.println(title);
		System.out.println("label = " + BaselineHelper.getBaseline(label));
		System.out.println("list = " + BaselineHelper.getBaseline(list));
		System.out.println();
	}

	final private JLabel label = label("Label:");
	final private JScrollPane list = list();
}
