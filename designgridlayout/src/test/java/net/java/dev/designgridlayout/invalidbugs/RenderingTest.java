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
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.fest.assertions.Assertions;
import org.fest.assertions.ImageAssert;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.image.ScreenshotTaker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Threshold.threshold;
import static org.fest.swing.finder.WindowFinder.findFrame;

import net.java.dev.designgridlayout.rowspan.Picture;

import junit.framework.Assert;

public class RenderingTest
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
		checkScreenshot();
	}
	
	private void checkScreenshot()
	{
		_robot = BasicRobot.robotWithCurrentAwtHierarchy();
		_frameFixture = findFrame(getClass().getSimpleName()).withTimeout(2000).using(_robot);
		_screenshot = new ScreenshotTaker();
		//FIXME completely remove if OK on VISTA
		//waitForEmptyEventQ();

		BufferedImage actual = _screenshot.takeScreenshotOf(_frame.getContentPane());
		String actualPath = REFERENCE_SCREENSHOT_PATH + "/RenderingTest-org.png";
		_screenshot.saveComponentAsPng(_frame.getContentPane(), actualPath);

		String expectedPath = REFERENCE_SCREENSHOT_PATH + "/RenderingTest.png";
		try
		{
			Assertions.assertThat(actual).as(expectedPath).isEqualTo(
				ImageAssert.read(expectedPath), threshold(1));
		}
		catch (IOException e)
		{
			String message = String.format(
				"Can't read reference screenshot \"%s\", exception = \"%s\"", 
				expectedPath, e);
			Assert.fail(message);
		}
	}

	@AfterMethod public void stopGui()
	{
		_frameFixture.close();
		_frameFixture.cleanUp();
	}

	private void go(boolean exitOnClose)
	{
		_frame.setName(getClass().getSimpleName());

		_frame.setDefaultCloseOperation(exitOnClose
			? JFrame.EXIT_ON_CLOSE
			: WindowConstants.DISPOSE_ON_CLOSE);
		_frame.getContentPane().setLayout(new BorderLayout());
		_frame.getContentPane().add(new Picture());
		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	final private JFrame _frame = new JFrame("Test Rendering");
	private Robot _robot;
	private FrameFixture _frameFixture;
	private ScreenshotTaker _screenshot;

	static final private boolean _isJava5 = 
		System.getProperty("java.version").startsWith("1.5");
	static final public String REFERENCE_SCREENSHOT_PATH = 
		"src/test/resources/screenshots/" + (_isJava5 ? "java5" : "java6");
}
