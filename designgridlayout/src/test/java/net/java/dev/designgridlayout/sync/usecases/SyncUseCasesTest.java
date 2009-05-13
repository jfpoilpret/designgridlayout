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

package net.java.dev.designgridlayout.sync.usecases;

import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import net.java.dev.designgridlayout.AbstractBaseExample;
import net.java.dev.designgridlayout.AbstractGuiTest;

@Test(groups = "utest")
public class SyncUseCasesTest extends AbstractGuiTest
{
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	// Real-life examples of layouts alignment
	@Test public void checkTwoTabbedPanesUnsynced() throws Exception
	{
		checkTabbedPanes(NoSyncTabbedPanes.class, false);
	}

	@Test public void checkTwoTabbedPanesSynced() throws Exception
	{
		checkTabbedPanes(SyncTabbedPanes.class, true);
	}

	//TODO make it more generic (to work with more tabs?
	private void checkTabbedPanes(
		Class<? extends AbstractBaseExample> clazz, boolean resize)
		throws Exception
	{
		launchGui(clazz);
		checkTabsSnapshots("pref");
		if (resize)
		{
			frame().moveTo(new Point(0, 0));
			frame().resizeHeightTo(frame().target.getHeight() + 10);
			checkTabsSnapshots("resize-h10");
			frame().resizeWidthTo(frame().target.getWidth() + 10);
			checkTabsSnapshots("resize-w10");
		}
	}
	
	private void checkTabsSnapshots(String suffix)
	{
		frame().panel("TOP").tabbedPane("TABS").selectTab(0);
		checkSnapshot(suffix + "-tab0");
		frame().panel("TOP").tabbedPane("TABS").selectTab(1);
		checkSnapshot(suffix + "-tab1");
	}
}
