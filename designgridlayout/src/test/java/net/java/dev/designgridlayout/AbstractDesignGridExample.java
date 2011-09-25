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

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public abstract class AbstractDesignGridExample extends AbstractExample
{
	@Override public void go(boolean exitOnClose)
	{
		_frame = new JFrame(name());
		_frame.setName(getClass().getSimpleName());

		_frame.setDefaultCloseOperation(exitOnClose
			? JFrame.EXIT_ON_CLOSE
			: WindowConstants.DISPOSE_ON_CLOSE);
		JPanel top = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(top);
		top.setName("TOP");

		init(layout);
		build(layout);

		addTopPanel(top);
		prePack();
		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}
	
	protected void init(DesignGridLayout layout)
	{
		layout.labelAlignment(LabelAlignment.RIGHT);
		layout.withoutConsistentWidthAcrossNonGridRows();
	}

	protected void addTopPanel(JComponent top)
	{
		_frame.add(top);
	}
	
	protected void prePack()
	{
	}

	protected abstract void build(DesignGridLayout layout);
}
