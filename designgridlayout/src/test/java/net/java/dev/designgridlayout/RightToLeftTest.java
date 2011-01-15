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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class RightToLeftTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test public void checkLTR() throws Exception
	{
		checkRtlExample(RightToLeft1LTR.class);
	}
	
	@Test public void checkRTL() throws Exception
	{
		checkRtlExample(RightToLeft2RTL.class);
	}

	//TODO Find a way to test with Japanese
	@Test(enabled = false) public void checkJapanese() throws Exception
	{
		checkRtlExample(RightToLeft1LTR.class);
	}
	
	//TODO Find a way to test with Mongolian
	@Test(enabled = false) public void checkMongolian() throws Exception
	{
		checkRtlExample(RightToLeft1LTR.class);
	}
	
	@Test public void checkRealExampleRTL() throws Exception
	{
		checkExample(RightToLeft3RealWorldExample.class);
	}

	protected void checkRtlExample(Class<? extends AbstractRightToLeft> clazz) 
		throws Exception
	{
		checkExampleAndResizeWidth(clazz, 2.0);
	}
}
