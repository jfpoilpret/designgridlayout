// Copyright 2009 Jason Aaron Osgood, Jean-Francois Poilpret
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
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
import java.awt.Toolkit;
import java.awt.event.PaintEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import org.fest.assertions.Assertions;
import org.fest.assertions.ImageAssert;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.image.ScreenshotTaker;

import static org.fest.assertions.Threshold.threshold;
import static org.fest.swing.finder.WindowFinder.findFrame;

import junit.framework.Assert;

abstract public class AbstractGuiHelper
{
	protected final void init(Object example)
	{
		_example = example;
		_robot = BasicRobot.robotWithCurrentAwtHierarchy();
		_frame = findFrame(example.getClass().getSimpleName())
			.withTimeout(2000).using(_robot);
		_screenshot = new ScreenshotTaker();
		// FIXME completely remove if OK on VISTA
		// waitForEmptyEventQ();
	}

	final protected Robot robot()
	{
		return _robot;
	}
	
	final protected FrameFixture frame()
	{
		return _frame;
	}
	
	protected final void checkSnapshot()
	{
		checkSnapshot("");
	}

	protected final void checkSnapshot(String suffix)
	{
		if (_checkSnapshots)
		{
			// To make sure the mouse is not over a component in the frame,
			// which
			// would change its appearance, hence modify the snapshot.
			hideMouse();
			// Take snapshot of current layout
			if (suffix.length() > 0)
			{
				suffix = "-" + suffix;
			}
			String name = _example.getClass().getSimpleName() + suffix;
			String snapshot = TestConfiguration.SCREENSHOT_PATH + "/" + name + "-org.png";
			BufferedImage screenshot =
			    _screenshot.takeScreenshotOf(_frame.panel("TOP").component());
			_screenshot.saveImage(screenshot, snapshot);

			// Compare with previously recorded snapshots
			String expected = getReferenceSnapshotPath(suffix);
			try
			{
				// Assertions.assertThat(screenshot).as(expected).isEqualTo(
				// ImageAssert.read(expected), threshold(10));
				Assertions.assertThat(screenshot).as(expected).isEqualTo(
				    ImageAssert.read(expected), threshold(1));
			}
			catch(IOException e)
			{
				String message =
				    String.format(
				        "Can't read reference screenshot \"%s\", exception = \"%s\"", expected,
				        e);
				Assert.fail(message);
			}
		}
		else
		{
			takeSnapshot(suffix);
		}
	}

	protected final void takeSnapshot()
	{
		takeSnapshot("");
	}

	protected final void takeSnapshot(String suffix)
	{
		// To make sure the mouse is not over a component in the frame, which
		// would change its appearance, hence modify the snapshot.
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

	protected final String getReferenceSnapshotPath(String suffix)
	{
		String path = getSnapshotSubPath(REFERENCE_SCREENSHOT_PATH, suffix);
		System.out.printf("getReferenceSnapshotPath = %s\n", path);
		return path;
	}

	protected final String getSnapshotSubPath(String root, String suffix)
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

	protected final void stopGui()
	{
		_frame.close();
		_frame.cleanUp();
	}

	private final void hideMouse()
	{
		Frame frame = _frame.component();
		_robot.moveMouse(frame, frame.getWidth() + 2, 0);
		_robot.waitForIdle();
		_robot.moveMouse(frame, frame.getWidth() + 2, frame.getHeight() + 2);
		_robot.waitForIdle();
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

	private Object _example;
	private Robot _robot;
	private FrameFixture _frame;
	private ScreenshotTaker _screenshot;

	private static final boolean _checkSnapshots = !Boolean.getBoolean("screenshots");
	private static final boolean _isJava5 =
	    System.getProperty("java.version").startsWith("1.5");
	private static final String REFERENCE_SCREENSHOT_PATH =
	    "src/test/resources/screenshots/" + (_isJava5 ? "java5" : "java6");
}
