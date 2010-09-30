//  Copyright 2005-2010 Jason Aaron Osgood, Jean-Francois Poilpret
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
public class MultiGridTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test public void checkSimpleMultiGrid() throws Exception
	{
		checkExample(MultiGrid1Simple.class);
	}

	@Test public void checkThreeMultiGridWithGridSpan() throws Exception
	{
		checkExampleAndResizeWidth(MultiGrid2ThreeGridsWithGridSpan.class, 2.0);
	}
	
	@Test public void checkComplexMultiGrid() throws Exception
	{
		checkExample(MultiGrid3ThreeComplexGrids.class);
	}
	
	@Test public void checkAddressBookDemo() throws Exception
	{
		checkExample(AddressBookDemo.class);
	}
}
