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
	
	@Test public void checkVerticalConsistentGrids() throws Exception
	{
		checkExampleAndResizeWidth(
			SyncLayoutVertical2LayoutsConsistentGrids.class, 1.25, 1.25, 0.5);
	}

	@Test public void checkVerticalInconsistentGrids() throws Exception
	{
		checkExampleAndResizeWidth(
			SyncLayoutVertical2LayoutsInconsistentGrids.class, 1.25, 1.25, 0.5);
	}

	@Test public void checkVerticalMargins() throws Exception
	{
		checkExample(SyncLayoutVerticalCheckMargins.class);
	}

	@Test public void checkVerticalGridsWithAndWithoutLabels() throws Exception
	{
		checkExample(SyncLayoutVertical2LayoutsWithWithoutLabel.class);
	}

	@Test public void checkVerticalHGaps() throws Exception
	{
		checkExample(SyncLayoutVerticalCheckHGaps.class);
	}

	@Test public void checkVerticalWithBorders() throws Exception
	{
		checkExample(SyncLayoutVertical2LayoutsWithBorders.class);
	}

	@Test public void checkVerticalThreePanels() throws Exception
	{
		checkExampleAndResizeWidth(SyncLayoutVertical3Layouts.class, 1.25, 1.25, 0.5);
	}
}
