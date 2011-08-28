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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.testng.annotations.Test;

@Test(groups = "utest")
public class Bug43ConstructingLayoutWithJFrame
{
	@Test public void checkNewDesignGridLayoutOnJFrame() throws Exception
	{
		JFrame frame = new JFrame();
		DesignGridLayout layout = new DesignGridLayout(frame);
		layout.row().grid(new JLabel("Dummy")).add(new JTextField(20));
		frame.pack();
		frame.setVisible(true);
		frame.dispose();
	}

	@Test public void checkNewDesignGridLayoutOnJDialog() throws Exception
	{
		JDialog frame = new JDialog();
		DesignGridLayout layout = new DesignGridLayout(frame);
		layout.row().grid(new JLabel("Dummy")).add(new JTextField(20));
		frame.pack();
		frame.setVisible(true);
		frame.dispose();
	}
}
