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

package net.java.dev.designgridlayout.sync;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import net.java.dev.designgridlayout.AbstractBaseExample;
import net.java.dev.designgridlayout.AbstractGuiTest;

@Test(groups = "utest")
public class SyncLayoutTest extends AbstractGuiTest
{
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	// Real-life example of horizontal alignment
	@Test public void checkAddressBookDemoWithSync() throws Exception
	{
		checkExample(BetterAddressBookDemo.class);
	}

	//----------------------------------
	// All TC for horizontal alignments
	//----------------------------------

	@Test public void checkHorizontalFixedHeightRows() throws Exception
	{
		checkHorizontalExample(SyncHorizontalFixedHeightRows.class);
	}

	@Test public void checkHorizontalFixedHeightComplexRows() throws Exception
	{
		checkHorizontalExample(SyncHorizontalFixedHeightRowsComplexRows.class);
	}

	@Test public void checkHorizontalFixedHeightRowsConsistentBaselineSpacing() 
		throws Exception
	{
		checkHorizontalExample(SyncHorizontalFixedHeightRowsConsistentBaselineSpacing.class);
	}

	@Test public void checkHorizontalDifferentCountFixedHeightRows() throws Exception
	{
		checkHorizontalExample(SyncHorizontalDifferentCountOfFixedHeightRows.class);
	}

	@Test public void checkHorizontalDifferentCountFixedHeightRowsConsistentBaselineSpacing() 
		throws Exception
	{
		checkHorizontalExample(SyncHorizontalDifferentCountOfFixedHeightRowsConsistentBaselineSpacing.class);
	}

	@Test public void checkHorizontalDifferentCountVariableHeightRows() throws Exception
	{
		checkHorizontalExample(SyncHorizontalDifferentCountOfConsistentVariableHeightRows.class);
	}

	@Test public void checkHorizontalConsistentVariableHeightRows() throws Exception
	{
		checkHorizontalExample(SyncHorizontalConsistentVariableHeightRows.class);
	}

	//TODO re-enable when the matching sync-policy is ready!
	@Test(enabled = false)
	public void checkHorizontalInconsistentVariableHeightRows() throws Exception
	{
		checkHorizontalExample(SyncHorizontalVariableHeightRows.class);
	}

	//--------------------------------
	// All TC for vertical alignments
	//--------------------------------
	
	@Test public void checkVerticalConsistentGrids() throws Exception
	{
		checkVerticalExample(SyncVerticalConsistentGrids.class);
	}

	@Test public void checkVerticalInconsistentGrids() throws Exception
	{
		checkVerticalExample(SyncVerticalInconsistentGrids.class);
	}

	@Test public void checkVerticalMargins() throws Exception
	{
		checkVerticalExample(SyncVerticalCheckMargins.class);
	}

	@Test public void checkVerticalGridsWithAndWithoutLabels() throws Exception
	{
		checkVerticalExample(SyncVerticalWithWithoutLabel.class);
	}

	@Test public void checkVerticalHGaps() throws Exception
	{
		checkVerticalExample(SyncVerticalCheckHGaps.class);
	}

	@Test public void checkVerticalWithBorders() throws Exception
	{
		checkVerticalExample(SyncVerticalWithBorders.class);
	}

	@Test public void checkVerticalThreePanels() throws Exception
	{
		checkVerticalExample(SyncVertical3Layouts.class);
	}
	
	protected void checkHorizontalExample(Class<? extends AbstractBaseExample> clazz)
		throws Exception
	{
		checkExampleAndResizeHeight(clazz, 5, 3);
	}
	
	protected void checkVerticalExample(Class<? extends AbstractBaseExample> clazz)
		throws Exception
	{
		checkExampleAndResizeWidth(clazz, 1.25, 1.25, 0.5);
	}
}
