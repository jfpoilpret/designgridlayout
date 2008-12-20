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

import java.io.File;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.image.ScreenshotTaker;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.finder.WindowFinder.findFrame;

abstract class AbstractGuiTest
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
		_robot = RobotFixture.robotWithCurrentAwtHierarchy();
		_frame = findFrame(clazz.getSimpleName()).withTimeout(2000).using(_robot);
		_screenshot = new ScreenshotTaker();
	}

	final protected <T extends AbstractBaseExample> void launchGui(Class<T> clazz) 
		throws Exception
	{
		launchGui(clazz, null);
	}

	// Note: don't use @DataProvider because all tests appear under the same name
	// in maven surefire reports...
	final protected void checkExample(Class<? extends AbstractBaseExample> clazz)
		throws Exception
	{
		launchGui(clazz);
//		takeSnapshot();
		checkSnapshot();
	}
	
	final protected void checkSnapshot()
	{
		checkSnapshot("");
	}
	
	final protected void checkSnapshot(String suffix)
	{
//		takeSnapshot(suffix);
		// Take snapshot of current layout
		if (suffix.length() > 0)
		{
			suffix = "-" + suffix;
		}
		String name = _example.getClass().getSimpleName() + suffix + ".png";
		String snapshot = TestConfiguration.SCREENSHOT_PATH + "/" + name;
		_screenshot.saveComponentAsPng(_frame.panel("TOP").component(), snapshot);
		
		// Compare with previously recorded snapshots
		String expected = REFERENCE_SCREENSHOT_PATH + name;
		assertThat(new File(snapshot)).hasSameContentAs(new File(expected));
	}
	
	final protected void takeSnapshot()
	{
		takeSnapshot("");
	}
	
	final protected void takeSnapshot(String suffix)
	{
		// Take snapshot
		if (suffix.length() > 0)
		{
			suffix = "-" + suffix;
		}
		//TODO remove "-org" later
		String snapshot = TestConfiguration.SCREENSHOT_PATH + "/" + 
			_example.getClass().getSimpleName() + suffix + "-org.png";
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
	
	protected interface Initializer<T extends AbstractBaseExample>
	{
		public void init(T instance);
	}

	static final public String REFERENCE_SCREENSHOT_PATH = 
		"src/test/resources/screenshots/";
}
