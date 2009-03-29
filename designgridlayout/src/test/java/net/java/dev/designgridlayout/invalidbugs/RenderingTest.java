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

package net.java.dev.designgridlayout.invalidbugs;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import net.java.dev.designgridlayout.AbstractGuiHelper;
import net.java.dev.designgridlayout.rowspan.Picture;

public class RenderingTest extends AbstractGuiHelper
{
	public static void main(String[] args)
	{
		RenderingTest example = new RenderingTest();
		example.go(true);
	}
	
	@Test(groups = "special")
	public void checkRenderingScreenshot()
	{
		go(false);
		init(this);
		checkSnapshot();
	}	

	@AfterMethod(groups = "special") public void cleanUp()
	{
		stopGui();
	}

	private void go(boolean exitOnClose)
	{
		_frame = new JFrame(getClass().getSimpleName());
		_frame.setName(getClass().getSimpleName());

		_frame.setDefaultCloseOperation(exitOnClose
			? JFrame.EXIT_ON_CLOSE
			: WindowConstants.DISPOSE_ON_CLOSE);
		_frame.getContentPane().setLayout(new BorderLayout());
		_frame.getContentPane().setName("TOP");
		_frame.getContentPane().add(new Picture());
		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	private JFrame _frame;
}
