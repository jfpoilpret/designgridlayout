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

import javax.swing.SwingUtilities;

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

	@Test public void checkPanelWithoutBorder() throws Exception
	{
		checkExample(Bug20PanelWithBorder2.class);
	}

	@Test public void checkPanelWithBorder() throws Exception
	{
		checkExample(Bug20PanelWithBorder1.class);
	}
}
