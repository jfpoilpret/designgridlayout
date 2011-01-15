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
public class SmartVerticalResizeTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
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
		checkExampleAndResizeHeight(SmartVerticalResize3CustomWeights.class, 5, 14);
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

	@Test public void checkFigure179WithoutSmartResize() throws Exception
	{
		checkExampleAndResize(SmartVerticalResizeDisabled4RealWorldExample.class);
	}

	protected void checkExampleAndResize(Class<? extends AbstractBaseExample> clazz) throws Exception
	{
		checkExampleAndResizeHeight(clazz, RESIZE_INCREMENT, RESIZE_STEPS);
	}
	
	static final private int RESIZE_INCREMENT = 3;
	static final private int RESIZE_STEPS = 10;
}
