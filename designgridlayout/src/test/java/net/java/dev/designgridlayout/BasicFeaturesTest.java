//  Copyright 2005-2013 Jason Aaron Osgood, Jean-Francois Poilpret
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
public class BasicFeaturesTest extends AbstractGuiTest
{
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	@Test public void checkSimpleGrid() throws Exception
	{
		checkExample(Basics1SimpleGrid.class);
	}

	@Test public void checkSimpleGridWithColumnSpan() throws Exception
	{
		checkExample(Basics2GridColumns.class);
	}

	@Test public void checkSimpleGridWithLabels() throws Exception
	{
		checkExample(Basics3GridLabels.class);
	}

	@Test public void checkSimpleGridPlusRightRow() throws Exception
	{
		checkExample(Basics4RightRow.class);
	}

	@Test public void checkNonGridRows() throws Exception
	{
		checkExample(Basics5NonGridRows.class);
	}

	@Test public void checkFigure177() throws Exception
	{
		checkExample(Basics7RealWorldExample1.class);
	}

	@Test public void checkFigure178() throws Exception
	{
		checkExample(Basics8RealWorldExample2.class);
	}

	@Test public void checkJGoodiesSkeleton() throws Exception
	{
		checkExample(Basics9RealWorldExample3.class);
	}

	@Test public void checkCustomizedMargins() throws Exception
	{
		checkExample(Misc1CustomizedMargins.class);
	}

	@Test public void checkNonGridRowsWithFillers() throws Exception
	{
		checkExample(Basics6NonGridRowsWithFiller.class);
	}

	@Test public void checkIndentRowGrid() throws Exception
	{
		checkExample(IndentRowsExample1.class);
	}

	@Test public void checkIndentRowSubGrid() throws Exception
	{
		checkExample(IndentRowsExample2.class);
	}

	@Test public void checkIndentRowLeftRow() throws Exception
	{
		checkExample(IndentRowsExample3.class);
	}

	@Test public void checkIndentRowManyLevels() throws Exception
	{
		checkExample(IndentRowsExample4.class);
	}

	@Test public void checkIndentRow() throws Exception
	{
		checkExample(IndentRowsExample.class);
	}
}
