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

import static org.fest.swing.finder.WindowFinder.findFrame;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class ExamplesTest
{
	static final private String SCREENSHOT_PATH = "target/site/images";
	private Robot _robot;
	private FrameFixture _examples;
	private ScreenshotTaker _screenshot;
	
	@BeforeClass(groups = "utest")
	public void launchGui()
	{
		Examples.main(new String[0]);
		_robot = RobotFixture.robotWithCurrentAwtHierarchy();
		_examples = findFrame("Examples").withTimeout(2000).using(_robot);
		_screenshot = new ScreenshotTaker();
		
		// Make sure that screenshots target directory exists
		File dir = new File(SCREENSHOT_PATH);
		// Delete directory and contents if it exists (else snapshot will 
		// not work)
		if (dir.exists() && dir.isDirectory())
		{
			// Delete content first
			for (File file: dir.listFiles())
			{
				file.delete();
			}
			dir.delete();
		}
		dir.mkdirs(); 
		Assert.assertTrue(dir.exists() && dir.isDirectory(), 
			"Directory \"" + SCREENSHOT_PATH + "\" must exist.");
	}
	
	// Maybe add later on some snapshots checks before/after resize
	@Test(dataProvider = "allExamples")
	public void checkExample(String example, boolean resize)
	{
		_examples.button(example).click();
		FrameFixture ex = findFrame(example).withTimeout(2000).using(_robot);
		// Take snapshot
		String snapshot = String.format("%s/%s.png", SCREENSHOT_PATH, example);
		_screenshot.saveComponentAsPng(ex.panel("TOP").component(), snapshot);
		if (resize)
		{
			ex.resizeWidthTo(ex.target.getWidth() * 2);
			ex.resizeWidthTo(ex.target.getWidth() / 3);
		}
		ex.close();
	}

	@AfterClass(groups = "utest")
	public void stopGui()
	{
		_examples.close();
		_examples.cleanUp();
	}
	
	@DataProvider(name = "allExamples")
	public Object[][] getLaunchableExamples()
	{
		return new Object[][]
		{
			{"Example1a", true},
			{"Example1b", true},
			{"Example1c", true},
			{"Example1d", true},
			{"Example1e", true},
			{"Figure177", true},
			{"Figure178", true},
			{"Figure179", true},
			{"Figure179bis", false},
			{"AddressBookDemo", false},
			{"Issue1", false},
			{"Issue2", false},
			{"Issue3", false},
			{"Issue4", false},
		};
	}
}
