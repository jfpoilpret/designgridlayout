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

import java.awt.Frame;
import java.awt.Point;
import java.io.File;

import javax.swing.SwingUtilities;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.image.ScreenshotTaker;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.finder.WindowFinder.findFrame;

abstract class AbstractGuiTest
{
	private IExample _example;
	private Robot _robot;
	private FrameFixture _frame;
	private ScreenshotTaker _screenshot;
	
	final protected <T extends IExample, U extends T> void launchGui(
		Class<U> clazz, Initializer<T> initializer) 
		throws Exception
	{
		U example = clazz.newInstance();
		_example = example;
		if (initializer != null)
		{
			initializer.init(example);
		}
		_example.go(false);
		_robot = BasicRobot.robotWithCurrentAwtHierarchy();
		_frame = findFrame(clazz.getSimpleName()).withTimeout(2000).using(_robot);
		_screenshot = new ScreenshotTaker();
	}
	
	final protected <T extends IExample> void launchGui(Class<T> clazz) 
		throws Exception
	{
		launchGui(clazz, null);
	}
	
	final protected void checkExampleFromEDT(
		final Class<? extends IExample> clazz) throws Exception
	{
		final ExceptionHolder holder = new ExceptionHolder();
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				try
				{
					IExample example = clazz.newInstance();
					_example = example;
					_example.go(false);
				}
				catch (Exception e)
				{
					holder.setException(e);
				}
			}
		});
		_robot = BasicRobot.robotWithCurrentAwtHierarchy();
		_frame = findFrame(clazz.getSimpleName()).withTimeout(2000).using(_robot);
		_screenshot = new ScreenshotTaker();
		if (holder.getException() != null)
		{
			throw holder.getException();
		}
		checkSnapshot();
	}

	// Note: don't use @DataProvider because all tests appear under the same name
	// in maven surefire reports...
	final protected void checkExample(Class<? extends IExample> clazz)
		throws Exception
	{
		launchGui(clazz);
		checkSnapshot();
	}
	
	final protected void checkExampleAndResizeHeight(
		Class<? extends IExample> clazz, int increment, int steps) throws Exception
	{
		launchGui(clazz);
		checkSnapshot("pref-size");
		frame().moveTo(new Point(frame().target.getX(), 0));
		for (int i = 1; i <= steps; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + increment);
			checkSnapshot("resize-" + (i * increment));
		}
	}

	final protected void checkExampleAndResizeWidth(
		Class<? extends IExample> clazz, double... ratios) throws Exception
	{
		launchGui(clazz);
		checkSnapshot("pref-size");
		frame().moveTo(new Point(0, frame().target.getY()));
		for (int i = 0; i < ratios.length; i++)
		{
			frame().resizeWidthTo((int) (frame().target.getWidth() * ratios[i]));
			checkSnapshot("resize-" + (i + 1));
		}
	}

	final protected void checkSnapshot()
	{
		checkSnapshot("");
	}
	
	final protected void checkSnapshot(String suffix)
	{
		if (_checkSnapshots)
		{
			// To make sure the mouse is not over a component in the frame, which
			//would change its appearance, hence modify the snapshot.
			hideMouse();
			// Take snapshot of current layout
			if (suffix.length() > 0)
			{
				suffix = "-" + suffix;
			}
			String name = _example.getClass().getSimpleName() + suffix;
			String snapshot = TestConfiguration.SCREENSHOT_PATH + "/" + name + "-org.png";
			_screenshot.saveComponentAsPng(_frame.panel("TOP").component(), snapshot);
			
			// Compare with previously recorded snapshots
			String expected = REFERENCE_SCREENSHOT_PATH + name + ".png";
			assertThat(new File(snapshot)).hasSameContentAs(new File(expected));
		}
		else
		{
			takeSnapshot(suffix);
		}
	}
	
	final protected void takeSnapshot()
	{
		takeSnapshot("");
	}
	
	final protected void takeSnapshot(String suffix)
	{
		// To make sure the mouse is not over a component in the frame, which
		//would change its appearance, hence modify the snapshot.
		hideMouse();
		// Take snapshot
		if (suffix.length() > 0)
		{
			suffix = "-" + suffix;
		}
		String snapshot = TestConfiguration.SCREENSHOT_PATH + "/" + 
			_example.getClass().getSimpleName() + suffix + ".png";
		_screenshot.saveComponentAsPng(_frame.panel("TOP").component(), snapshot);
	}
	
	final protected Robot robot()
	{
		return _robot;
	}
	
	final protected FrameFixture frame()
	{
		return _frame;
	}
	
	final protected void stopGui()
	{
		_frame.close();
		_frame.cleanUp();
	}
	
	final private void hideMouse()
	{
		Frame frame = _frame.component();
		_robot.moveMouse(frame, frame.getWidth() + 2, 0);
		_robot.waitForIdle();
		_robot.moveMouse(frame, frame.getWidth() + 2, frame.getHeight() + 2);
		_robot.waitForIdle();
	}
	
	protected interface Initializer<T extends IExample>
	{
		public void init(T instance);
	}
	
	static class ExceptionHolder
	{
		public void setException(Exception e)
		{
			_exception = e;
		}
		
		public Exception getException()
		{
			return _exception;
		}
		
		private Exception _exception = null;
	}

	static final private boolean _checkSnapshots = !Boolean.getBoolean("screenshots");
	static final public String REFERENCE_SCREENSHOT_PATH = 
		"src/test/resources/screenshots/java6/";
}
