//  Copyright 2005-2011 Jason Aaron Osgood, Jean-Francois Poilpret
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
public class DynamicLayoutTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	// Test row level
	@Test public void checkShowHideRows() throws Exception
	{
		checkExample(ShowHideRowsRealWorldExample1.class);
		// Hide Address
		frame().checkBox("Address").uncheck();
		checkSnapshot("after-1-hide-address");
		// Hide Preferences
		frame().checkBox("Preferences").uncheck();
		checkSnapshot("after-2-hide-preferences");
		// Show Address
		frame().checkBox("Address").check();
		checkSnapshot("after-3-show-address");
		// Show Preferences
		frame().checkBox("Preferences").check();
		checkSnapshot("after-4-show-preferences");
	}

	@Test public void checkShowHideRowGroups() throws Exception
	{
		checkExample(ShowHideRowsRealWorldExample2.class);
		// Hide Address
		frame().checkBox("Address").uncheck();
		checkSnapshot("after-1-hide-address");
		// Hide Preferences
		frame().checkBox("Preferences").uncheck();
		checkSnapshot("after-2-hide-preferences");
		// Show Address
		frame().checkBox("Address").check();
		checkSnapshot("after-3-show-address");
		// Show Preferences
		frame().checkBox("Preferences").check();
		checkSnapshot("after-4-show-preferences");
	}

	//TODO additional tests:
	// - tests with special rows (empty row, variable height, span component
	// - test multiple show/hide must be balanced
}
