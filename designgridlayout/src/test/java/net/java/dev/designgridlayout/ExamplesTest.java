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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class ExamplesTest extends AbstractGuiTest
{
	// Note: don't use @DataProvider because all tests appear under the same name
	// in maven surefire reports...
	protected void checkExample(Class<? extends AbstractBaseExample> clazz, boolean resize)
		throws Exception
	{
		launchGui(clazz);
//		takeSnapshot();
		checkSnapshot();
		if (resize)
		{
			frame().resizeWidthTo(frame().target.getWidth() * 2);
//			frame().resizeWidthTo(frame().target.getWidth() / 3);
		}
	}
	
	@AfterMethod(groups = "utest")
	public void cleanUp()
	{
		stopGui();
	}
	
	@Test public void checkExample1a() throws Exception
	{
		checkExample(Example1a.class, true);
	}

	@Test public void checkExample1b() throws Exception
	{
		checkExample(Example1b.class, true);
	}

	@Test public void checkExample1c() throws Exception
	{
		checkExample(Example1c.class, true);
	}

	@Test public void checkExample1d() throws Exception
	{
		checkExample(Example1d.class, true);
	}

	@Test public void checkExample1e() throws Exception
	{
		checkExample(Example1e.class, true);
	}

	@Test public void checkExample1f() throws Exception
	{
		checkExample(Example1f.class, true);
	}

	@Test public void checkFigure177() throws Exception
	{
		checkExample(Figure177.class, true);
	}

	@Test public void checkFigure178() throws Exception
	{
		checkExample(Figure178.class, true);
	}

	@Test public void checkFigure179() throws Exception
	{
		checkExample(Figure179.class, true);
	}

	@Test public void checkFigure179bis() throws Exception
	{
		checkExample(Figure179bis.class, false);
	}

	@Test public void checkAddressBookDemo() throws Exception
	{
		checkExample(AddressBookDemo.class, false);
	}

	@Test public void checkIssue1() throws Exception
	{
		checkExample(Issue1.class, false);
	}

	@Test public void checkIssue2() throws Exception
	{
		checkExample(Issue2.class, false);
	}

	@Test public void checkIssue3() throws Exception
	{
		checkExample(Issue3.class, false);
	}

	@Test public void checkIssue4() throws Exception
	{
		checkExample(Issue4.class, false);
	}

	@Test public void checkIssue22() throws Exception
	{
		checkExample(Issue22.class, false);
	}

	@Test public void checkIssue23() throws Exception
	{
		checkExample(Issue23.class, false);
	}

	@Test public void checkIssue24() throws Exception
	{
		checkExample(Issue24.class, false);
	}

	@Test public void checkIssue25() throws Exception
	{
		checkExample(Issue25.class, false);
	}

	@Test public void checkIssue26() throws Exception
	{
		checkExample(Issue26.class, false);
	}
}
