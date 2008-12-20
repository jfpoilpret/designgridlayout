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
public class SmartVerticalResizeTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	//TODO Homogeneize resize count and steps!
	
	@Test public void checkFigure179() throws Exception
	{
		checkExampleAndResize(SmartVerticalResize4RealWorldExample.class);
	}

	@Test public void checkComplexLayout() throws Exception
	{
		checkExampleAndResize(SmartVerticalResize2AllComponents.class);
	}

	@Test public void checkComplexLayoutWithWeights() throws Exception
	{
		launchGui(SmartVerticalResize3CustomWeights.class);
		checkSnapshot("pref-size");
		for (int i = 1; i <= 14; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + 5);
			checkSnapshot("extended-size-" + (i * 5));
		}
	}

	@Test public void checkOneMultiComponentAllVarHeight() throws Exception
	{
		checkExampleAndResize(SmartVerticalResize1Sliders.class);
	}

	@Test public void checkMultiAllVarHeightAndSingleComponent() throws Exception
	{
		checkExampleAndResize(Rfe05SmartVerticalResizeMultiComponent1.class);
	}

	@Test public void checkMultiVarAndFixHeight() throws Exception
	{
		checkExampleAndResize(Rfe05SmartVerticalResizeMultiComponent2.class);
	}

	protected void checkExampleAndResize(Class<? extends AbstractBaseExample> clazz) throws Exception
	{
		launchGui(clazz);
		checkSnapshot("pref-size");
		for (int i = 1; i <= 10; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + 3);
			checkSnapshot("extended-size-" + (i * 3));
		}
	}
}
