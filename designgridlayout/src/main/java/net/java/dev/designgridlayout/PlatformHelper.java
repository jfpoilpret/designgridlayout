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

import javax.swing.JLabel;

final class PlatformHelper
{
	private PlatformHelper()
	{
	}
	
	static int getDefaultAlignment()
	{
		switch (platform())
		{
			case MACINTOSH:
			return JLabel.TRAILING;
			
			case LINUX:
			case WINDOWS:
			case OTHER:
			default:
			return JLabel.LEADING;
		}
	}
	
	static private Platform platform()
	{
		String os = System.getProperty("os.name");
		if (os.startsWith("Mac OS"))
		{
			return Platform.MACINTOSH;
		}
		else if (os.startsWith("Linux"))
		{
			return Platform.LINUX;
		}
		else if (os.startsWith("Windows"))
		{
			return Platform.WINDOWS;
		}
		else
		{
			return Platform.OTHER;
		}
	}
	
	static private enum Platform
	{
		WINDOWS,
		MACINTOSH,
		LINUX,
		OTHER
	}
}
