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
public class ComponentizerTest extends AbstractGuiTest
{
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	@Test public void checkComponentizerWith2FixedChildren() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer1TwoFixedChildren.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWith1Fixed1VariableChildren() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer2OneFixedOneVariableChildren.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWith1Fixed1Variable1FixedChildren() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer3FixedVariableFixedChildren.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWith1Fixed1Variable1FixedChildrenRTL() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer3FixedVariableFixedRTL.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWith1Variable1FixedChildren() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer4OneVariableOneFixedChildren.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWithFixedChildrenWithVariableHeight() throws Exception
	{
		checkExampleAndResizeHeight(Componentizer5FixedChildrenWithVariableHeight.class, 5, 8);
	}

	@Test public void checkComponentizerWithFixedChildrenWithVariableHeightButNoSmartResize() 
		throws Exception
	{
		checkExampleAndResizeHeight(
			Componentizer5FixedChildrenWithVariableHeightNoSmartResize.class, 5, 8);
	}

	@Test public void checkComponentizerWithinDesignGrid() throws Exception
	{
		checkExampleAndResizeHeight(Componentizer6WithinDesignGrid.class, 5, 8);
	}

	@Test public void checkComponentizerWithMinToPrefPolicy() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer7MinToPrefPolicy.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWithMinAndMorePolicy() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer8MinAndMorePolicy.class,
			1.5d, 0.5d);
	}

	@Test public void checkComponentizerWithAllPolicies() throws Exception
	{
		checkExampleAndResizeWidth(Componentizer9ManyChildren.class,
			1.5d, 0.5d, 0.5d);
	}
}
