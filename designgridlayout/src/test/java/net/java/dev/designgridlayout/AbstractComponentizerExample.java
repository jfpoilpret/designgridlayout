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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.java.dev.designgridlayout.Componentizer.Builder;

public abstract class AbstractComponentizerExample extends AbstractExample
{
	@Override public void go(boolean exitOnClose)
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
	
	protected abstract void build(Builder builder);
}
