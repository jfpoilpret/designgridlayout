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

import java.awt.ComponentOrientation;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class Issue9Test extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test public void checkLTR() throws Exception
	{
		checkIssue9("LT");
	}
	
	@Test public void checkRTL() throws Exception
	{
		checkIssue9("RT");
	}
	
	@Test(enabled = false) public void checkJapanese() throws Exception
	{
		checkIssue9("TR");
	}
	
	@Test(enabled = false) public void checkMongolian() throws Exception
	{
		checkIssue9("TL");
	}
	
	protected void checkIssue9(String orientation) throws Exception
	{
		OrientationInitializer initializer = new OrientationInitializer(
			ComponentOrientationHelper.getOrientation(new String[]{orientation}));
		launchGui(Issue9.class, initializer);
		String suffix = orientation;
		checkSnapshot("small-" + suffix);
//		takeSnapshot("small-" + suffix);
		frame().resizeWidthTo(frame().target.getWidth() * 2);
		checkSnapshot("large-" + suffix);
//		takeSnapshot("large-" + suffix);
	}
	
	static private class OrientationInitializer implements Initializer<Issue9>
	{
		public OrientationInitializer(ComponentOrientation orientation)
		{
			_orientation = orientation;
		}
		
		public void init(Issue9 instance)
        {
			instance.setOrientation(_orientation);
        }
		
		private final ComponentOrientation _orientation;
	}
}
