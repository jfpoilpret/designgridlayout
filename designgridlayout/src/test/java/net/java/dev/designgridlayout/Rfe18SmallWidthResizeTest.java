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

import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class Rfe18SmallWidthResizeTest extends AbstractGuiTest
{
	@AfterMethod public void closeGui()
	{
		stopGui();
	}
	
	@Test public void checkAddressBookDemoSmallWidthResize() throws Exception
	{
		launchGui(AddressBookDemo.class);
//		takeSnapshot("preferred");
		checkSnapshot("preferred");
		frame().moveTo(new Point(0, frame().target.getY()));
		frame().resizeWidthTo(frame().target.getWidth() * 9 / 10);
//		takeSnapshot("smaller");
		checkSnapshot("smaller");
		frame().resizeWidthTo(frame().target.getWidth() * 9 / 10);
//		takeSnapshot("smallest");
		checkSnapshot("smallest");
	}
}
