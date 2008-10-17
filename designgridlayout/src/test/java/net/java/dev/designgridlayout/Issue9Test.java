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
		checkIssue9("LT", null);
	}
	
	@Test public void checkRTL() throws Exception
	{
		checkIssue9("RT", null);
	}
	
	@Test public void checkForceLTR() throws Exception
	{
		checkIssue9("RT", false);
	}
	
	@Test public void checkForceRTL() throws Exception
	{
		checkIssue9("LT", true);
	}
	
	@Test(enabled = false) public void checkJapanese() throws Exception
	{
		checkIssue9("TR", null);
	}
	
	@Test(enabled = false) public void checkMongolian() throws Exception
	{
		checkIssue9("TL", null);
	}
	
	protected void checkIssue9(String orientation, Boolean rtl) throws Exception
	{
		OrientationInitializer initializer = new OrientationInitializer(
			ComponentOrientationHelper.getOrientation(new String[]{orientation}),
			rtl);
		launchGui(Issue9.class, initializer);
		String suffix;
		if (rtl != null)
		{
			suffix = orientation + "-" + (rtl ? "RT" : "LT");
		}
		else
		{
			suffix = orientation;
		}
		checkSnapshot("small-" + suffix);
		frame().resizeWidthTo(frame().target.getWidth() * 2);
		checkSnapshot("large-" + suffix);
	}
	
	static private class OrientationInitializer implements Initializer<Issue9>
	{
		public OrientationInitializer(ComponentOrientation orientation, Boolean rtl)
		{
			_orientation = orientation;
			_rtl = rtl;
		}
		
		public void init(Issue9 instance)
        {
			instance.setOrientation(_orientation);
			if (_rtl != null)
			{
				instance.setForceOrientation(_rtl);
			}
        }
		
		private final ComponentOrientation _orientation;
		private final Boolean _rtl;
	}
}
