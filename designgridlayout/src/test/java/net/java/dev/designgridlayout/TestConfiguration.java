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

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class TestConfiguration
{
	static final public String SCREENSHOT_PATH = "target/site/images";
	
	@BeforeGroups(groups = "utest")
	public void prepareSnapshotDirectory()
	{
		// Make sure that screenshots target directory exists
		File dir = new File(SCREENSHOT_PATH);
		// Delete directory and contents if it exists (else snapshot will 
		// not work)
		if (dir.exists() && dir.isDirectory())
		{
			// Delete content first
			for (File file: dir.listFiles())
			{
				file.delete();
			}
			dir.delete();
		}
		dir.mkdirs(); 
		Assert.assertTrue(dir.exists() && dir.isDirectory(), 
			"Directory \"" + SCREENSHOT_PATH + "\" must exist.");
	}
}
