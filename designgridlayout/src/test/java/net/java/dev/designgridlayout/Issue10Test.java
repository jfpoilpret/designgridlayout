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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class Issue10Test extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test public void checkSpanRowsOnOneGrid() throws Exception
	{
		launchGui(Issue10.class);
		checkSnapshot("pref-size");
		for (int i = 1; i <= 10; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + 3);
			checkSnapshot("extended-size-" + (i * 3));
		}
	}
	
	@Test public void checkSpanRowsAndHeighGrowth() throws Exception
	{
		launchGui(Issue10c.class);
		checkSnapshot("pref-size");
		for (int i = 1; i <= 10; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + 3);
			checkSnapshot("extended-size-" + (i * 3));
		}
	}

	@Test public void checkSpanRowsOnSeveralGrids() throws Exception
	{
		launchGui(Issue10b.class);
		checkSnapshot("pref-size");
		for (int i = 1; i <= 10; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + 3);
			checkSnapshot("extended-size-" + (i * 3));
		}
	}
	
	@Test public void checkBadUsageOfSpanRow() throws Exception
	{
		launchGui(Issue10a.class);
		checkSnapshot();
	}
}
