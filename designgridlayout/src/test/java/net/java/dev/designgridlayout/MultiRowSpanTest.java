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

package net.java.dev.designgridlayout;

import java.awt.Dimension;
import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class MultiRowSpanTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test public void checkSpanRowsSimplestExample() throws Exception
	{
		checkExample(RowSpan5SimplestExample.class);
	}
	
	@Test public void checkSpanRowsOnOneGrid() throws Exception
	{
		checkExampleAndResize(RowSpan2TwoLists.class);
	}
	
	@Test public void checkSpanRowsSimpleExampleTwoGrids() throws Exception
	{
		checkExample(RowSpan6SimpleExampleOnTwoGrids.class);
	}
	
	@Test public void checkSpanRowsOnSeveralGrids() throws Exception
	{
		checkExampleAndResize(RowSpan1OneList.class);
	}
	
	@Test public void checkSpanRowsAndHeightGrowth() throws Exception
	{
		checkExampleAndResize(RowSpan3TwoListsCustomWeights.class);
	}

	@Test public void checkBadUsageOfSpanRow() throws Exception
	{
		checkExample(RowSpan4ErrorMarkers.class);
	}
	
	@Test public void checkVGapsAlwaysCorrect() throws Exception
	{
		checkExample(Rfe10CheckVGaps.class);
	}
	
	@Test public void checkSpanRowsOnCustomComponent() throws Exception
	{
		launchGui(RowSpan7SpecialComponent.class);
		checkSnapshot("pref-size");
		frame().moveTo(new Point(frame().target.getX(), 0));
		Dimension size = new Dimension(
			frame().target.getWidth(), frame().target.getHeight());
		for (int i = 1; i <= 5; i++)
		{
			size.width += 6;
			size.height += 9;
			frame().resizeTo(size);
			checkSnapshot("resize-" + i);
		}
	}
	
	protected void checkExampleAndResize(Class<? extends AbstractBaseExample> clazz)
		throws Exception
	{
		checkExampleAndResizeHeight(clazz, RESIZE_INCREMENT, RESIZE_STEPS);
	}
	
	static final private int RESIZE_INCREMENT = 3;
	static final private int RESIZE_STEPS = 10;
}
