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

package net.java.dev.designgridlayout;

import java.awt.ActiveEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import junit.framework.Assert;

import org.fest.assertions.Assertions;
import org.fest.assertions.ImageAssert;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.image.ScreenshotTaker;

import static org.fest.assertions.Threshold.threshold;
import static org.fest.swing.finder.WindowFinder.findFrame;

abstract public class AbstractGuiTest
{
	private AbstractBaseExample _example;
	private Robot _robot;
	private FrameFixture _frame;
	private ScreenshotTaker _screenshot;
	
	final protected <T extends AbstractBaseExample, U extends T> void launchGui(
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
		//FIXME completely remove if OK on VISTA
		//waitForEmptyEventQ();
	}
	
	final protected <T extends AbstractBaseExample> void launchGui(Class<T> clazz) 
		throws Exception
	{
		launchGui(clazz, null);
	}
	
	final protected void checkExampleFromEDT(
		final Class<? extends AbstractBaseExample> clazz) throws Exception
	{
		final ExceptionHolder holder = new ExceptionHolder();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					AbstractBaseExample example = clazz.newInstance();
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
		//FIXME completely remove if OK on VISTA
		//waitForEmptyEventQ();
		checkSnapshot();
	}

	// Note: don't use @DataProvider because all tests appear under the same name
	// in maven surefire reports...
	final protected void checkExample(Class<? extends AbstractBaseExample> clazz)
		throws Exception
	{
		launchGui(clazz);
		checkSnapshot();
	}
	
	final protected void checkExampleAndResizeHeight(
		Class<? extends AbstractBaseExample> clazz, int increment, int steps) throws Exception
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
		Class<? extends AbstractBaseExample> clazz, double... ratios) throws Exception
	{
		launchGui(clazz);
		checkSnapshot("pref-size");
		frame().moveTo(new Point(0, frame().target.getY()));
		int width = frame().panel("TOP").target.getWidth();
		int extraWidth = frame().target.getWidth() - width;
		for (int i = 0; i < ratios.length; i++)
		{
			width *= ratios[i];
			frame().resizeWidthTo(width + extraWidth);
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
			BufferedImage screenshot = _screenshot.takeScreenshotOf(_frame.panel("TOP").component());
			_screenshot.saveImage(screenshot, snapshot);
			
			// Compare with previously recorded snapshots
			String expected = getReferenceSnapshotPath(suffix);
			try
			{
//				Assertions.assertThat(screenshot).as(expected).isEqualTo(
//					ImageAssert.read(expected), threshold(10));
				Assertions.assertThat(screenshot).as(expected).isEqualTo(
					ImageAssert.read(expected), threshold(1));
			}
			catch (IOException e)
			{
				String message = String.format("Can't read reference screenshot \"%s\", exception = \"%s\"", expected, e);
				Assert.fail(message);
			}
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
		String snapshot = getSnapshotSubPath(TestConfiguration.SCREENSHOT_PATH, suffix);
		System.out.printf("takeSnapshot = %s\n", snapshot);
		_screenshot.saveComponentAsPng(_frame.panel("TOP").component(), snapshot);
	}
	
	final protected String getReferenceSnapshotPath(String suffix)
	{
		String path = getSnapshotSubPath(REFERENCE_SCREENSHOT_PATH, suffix);
		System.out.printf("getReferenceSnapshotPath = %s\n", path);
		return path;
	}
	
	final protected String getSnapshotSubPath(String root, String suffix)
	{
		// Get the directory based on the class package
		String mainPackage = AbstractGuiTest.class.getPackage().getName();
		String examplePackage = getClass().getPackage().getName();
		// Extract only the last part of the example package
		String subpath = examplePackage.substring(mainPackage.length());
		subpath = subpath.replace('.', '/');

		// Make sure path exists, create it if needed
		String path = root + subpath;
		new File(path).mkdirs();
		// Build the complete screenshot file path
		path = path + "/" + _example.getClass().getSimpleName() + suffix + ".png";
		System.out.printf("getSnapshotSubPath = %s\n", path);
		return path;
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
	
	private void waitForEmptyEventQ()
	{
		boolean qEmpty = false;
		JPanel placeHolder  = new JPanel();
		EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
		while (!qEmpty)
		{
			NotifyingEvent e = new NotifyingEvent(placeHolder);
			q.postEvent(e);
			synchronized(e)
			{
				while (!e.isDispatched())
				{
					try
					{
						e.wait();
					} 
					catch (InterruptedException ie)
					{
					}
				}
				qEmpty = e.isEventQEmpty();
			}
		}
	}

    final private void hideMouse()
	{
		Frame frame = _frame.component();
		_robot.moveMouse(frame, frame.getWidth() + 2, 0);
		_robot.waitForIdle();
		_robot.moveMouse(frame, frame.getWidth() + 2, frame.getHeight() + 2);
		_robot.waitForIdle();
	}
	
	protected interface Initializer<T extends AbstractBaseExample>
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

	static private class NotifyingEvent extends PaintEvent implements ActiveEvent
	{
		private boolean dispatched = false;
		private boolean qEmpty = false;

		NotifyingEvent(Component c)
		{
			super(c, PaintEvent.UPDATE, null);
		}
		
		synchronized boolean isDispatched()
		{
			return dispatched;
		}
		
		synchronized boolean isEventQEmpty()
		{
			return qEmpty;
		}

		public void dispatch()
		{
			EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
			synchronized(this)
			{
				qEmpty = (q.peekEvent() == null);
				dispatched = true;
				notifyAll();
			}
		}
	}

    static final private boolean _checkSnapshots = !Boolean.getBoolean("screenshots");
	static final private boolean _isJava5 = 
		System.getProperty("java.version").startsWith("1.5");
	static final public String REFERENCE_SCREENSHOT_PATH = 
		"src/test/resources/screenshots/" + (_isJava5 ? "java5" : "java6");
}
