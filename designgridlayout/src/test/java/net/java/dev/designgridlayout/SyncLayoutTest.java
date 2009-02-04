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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class SyncLayoutTest extends AbstractGuiTest
{
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	@Test public void checkAddressBookDemoWithSync() throws Exception
	{
		checkExample(BetterAddressBookDemo.class);
	}

	@Test public void checkHorizontalFixedHeightRows() throws Exception
	{
		checkExampleAndResizeHeight(
			SyncHorizontalFixedHeightRowsConsistentVGaps.class, 3, 4);
	}

	@Test public void checkHorizontalFixedHeightRowsConsistentVGaps() throws Exception
	{
		checkExampleAndResizeHeight(
			SyncHorizontalFixedHeightRows.class, 3, 4);
	}

	@Test public void checkVerticalConsistentGrids() throws Exception
	{
		checkExampleAndResizeWidth(
			SyncVerticalConsistentGrids.class, 1.25, 1.25, 0.5);
	}

	@Test public void checkVerticalInconsistentGrids() throws Exception
	{
		checkExampleAndResizeWidth(
			SyncVerticalInconsistentGrids.class, 1.25, 1.25, 0.5);
	}

	@Test public void checkVerticalMargins() throws Exception
	{
		checkExample(SyncVerticalCheckMargins.class);
	}

	@Test public void checkVerticalGridsWithAndWithoutLabels() throws Exception
	{
		checkExample(SyncVerticalWithWithoutLabel.class);
	}

	@Test public void checkVerticalHGaps() throws Exception
	{
		checkExample(SyncVerticalCheckHGaps.class);
	}

	@Test public void checkVerticalWithBorders() throws Exception
	{
		checkExample(SyncVerticalWithBorders.class);
	}

	@Test public void checkVerticalThreePanels() throws Exception
	{
		checkExampleAndResizeWidth(SyncVertical3Layouts.class, 1.25, 1.25, 0.5);
	}
}
