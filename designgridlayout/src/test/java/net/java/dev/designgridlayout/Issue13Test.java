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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class Issue13Test extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test(dataProvider = "panels")
	public void createIndividualGridSnapshot(int panel) throws Exception
	{
		PanelInitializer initializer = new PanelInitializer(panel);
		launchGui(Issue13b.class, initializer);
		checkSnapshot("small-grid-" + panel);
	}
	
	@Test public void checkMultiLabelLayout() throws Exception
	{
		launchGui(Issue13a.class);
		checkSnapshot("small");
		frame().resizeWidthTo(frame().target.getWidth() * 2);
		checkSnapshot("large");
	}
	
	@Test public void checkComplexMultiLabelLayout() throws Exception
	{
		launchGui(Issue13c.class);
		checkSnapshot();
	}
	
	@DataProvider(name = "panels")
	public Object[][] getPanelIndexes()
	{
		return new Object[][] {{0}, {1}, {2}};
	}

	static private class PanelInitializer implements Initializer<Issue13b>
	{
		public PanelInitializer(int panel)
		{
			_panel = panel;
		}
		
		public void init(Issue13b instance)
        {
			instance.setPanelIndex(_panel);
        }
		
		private final int _panel;
	}
}
