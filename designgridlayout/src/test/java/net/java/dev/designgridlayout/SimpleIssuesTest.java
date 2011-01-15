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

import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class SimpleIssuesTest extends AbstractGuiTest
{
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	@Test public void checkRfe05SmartVerticalResizeMultiComponent1() throws Exception
	{
		checkExample(Bug01MissingGapLabelComponent.class);
	}

	@Test public void checkBug02NonGridRowsUnusedInPrefSize() throws Exception
	{
		checkExample(Bug02NonGridRowsUnusedInPrefSize.class);
	}

	@Test public void checkBug03JScrollPaneResizeProblem() throws Exception
	{
		checkExample(Bug03JScrollPaneResizeProblem.class);
	}

	@Test public void checkBug22WrongGapsIfNoGridRow() throws Exception
	{
		checkExample(Bug22WrongGapsIfNoGridRow.class);
	}

	@Test public void checkBug24BadBottomMarginIfEmptyRowAtLast() throws Exception
	{
		checkExample(Bug24BadBottomMarginIfEmptyRowAtLast.class);
	}

	@Test public void checkBug25InoperantEmptyRowIfPackTwice() throws Exception
	{
		checkExample(Bug25InoperantEmptyRowIfPackTwice.class);
	}

	@Test public void checkBug26IndexOutBoundsInMultiGrid() throws Exception
	{
		checkExample(Bug26IndexOutBoundsInMultiGrid.class);
	}

	@Test public void checkBug27Java5WinLAFBadBaselines() throws Exception
	{
		checkExample(Bug27Java5WinLAFBadBaselines.class);
	}

	@Test public void checkBug28aBadHeightWhenStartedFromEDT() throws Exception
	{
		checkExampleFromEDT(Bug28aBadHeightWhenStartedFromEDT.class);
	}

	@Test public void checkBug28bBadHeightWhenStartedFromEDT() throws Exception
	{
		checkExampleFromEDT(Bug28bBadHeightWhenStartedFromEDT.class);
	}

	@Test public void checkBug30ExceptionRowsWithoutComponents() throws Exception
	{
		checkExample(Bug30ExceptionRowsWithoutComponents.class);
	}

	@Test public void checkBug31GridRowWithLabelButNoComponent() throws Exception
	{
		checkExample(Bug31GridRowWithLabelButNoComponent.class);
	}

	@Test public void checkBug32ProblemWithJTextPane() throws Exception
	{
		// First problem: bad initial baseline
		checkExample(Bug32ProblemWithJTextPane.class);
		// Second problem: very bad baseline after appending a lot of text
		frame().button("append").click();
		frame().resizeWidthTo(frame().component().getWidth() + 1);
		checkSnapshot("after-append");
	}
	
	@Test public void checkBug35PanelWithVariableHeightChild() throws Exception
	{
		// Show in default size
		checkExample(Bug35PanelWithVariableHeightChild.class);
		// Problem: resize vertically
		frame().resizeHeightTo(frame().component().getHeight() + 50);
		checkSnapshot("after-resize");
	}

	@Test public void checkBug36SmartVerticalResize() throws Exception
	{
		launchGui(SmartVerticalResize4RealWorldExample.class);
		checkSnapshot("bug36-pref-size");
		frame().moveTo(new Point(frame().target.getX(), 0));
		for (int i = 1; i <= BUG36_STEPS; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + BUG36_INCREMENT);
			checkSnapshot("bug36-resize-" + (i * BUG36_INCREMENT));
		}
		for (int i = 1; i <= BUG36_STEPS; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() - BUG36_INCREMENT);
			checkSnapshot("bug36-reverse-" + (i * BUG36_INCREMENT));
		}
	}

	@Test public void checkBug38WrongVGapsWithAddMulti() throws Exception
	{
		checkExample(Bug38WrongVGapsWithAddMulti.class);
	}

	@Test public void checkPanelWithoutBorder() throws Exception
	{
		checkExample(Bug20PanelWithBorder2.class);
	}

	@Test public void checkPanelWithBorder() throws Exception
	{
		checkExample(Bug20PanelWithBorder1.class);
	}
	
	static final private int BUG36_INCREMENT = 3;
	static final private int BUG36_STEPS = 6;
}
