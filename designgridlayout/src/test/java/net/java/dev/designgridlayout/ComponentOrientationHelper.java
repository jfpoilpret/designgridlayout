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

import java.awt.ComponentOrientation;
import java.util.Locale;

//TODO Remove since it seems unused in whole project
final public class ComponentOrientationHelper
{
	private ComponentOrientationHelper()
	{
	}
	
	public static ComponentOrientation getOrientation(String[] args)
	{
		//FIXME Japanese and Mongolian Locale do not work on standard JDK,
		// probably need something special installed in addition...
		if (args.length != 1)
		{
			return ComponentOrientation.LEFT_TO_RIGHT;
		}
		else if ("LT".equals(args[0]))
		{
			return ComponentOrientation.LEFT_TO_RIGHT;
		}
		else if ("RT".equals(args[0]))
		{
			return ComponentOrientation.RIGHT_TO_LEFT;
		}
		else if ("TL".equals(args[0]))
		{
			// Mongolian is one (the only one?) vertical language with columns
			// ordered from left to right
			return ComponentOrientation.getOrientation(new Locale("MN"));
		}
		else if ("TR".equals(args[0]))
		{
			return ComponentOrientation.getOrientation(Locale.JAPANESE);
		}
		else
		{
			return ComponentOrientation.LEFT_TO_RIGHT;
		}
	}
	
	//CSOFF: Regexp
	static public void debugOrientation(ComponentOrientation orientation)
	{
		System.err.println("orientation is " + 
			(orientation.isHorizontal() ? "horizontal" : "vertical"));
		System.err.println("orientation is " + 
			(orientation.isLeftToRight() ? "left to right" : "right to left"));
	}
	//CSON: Regexp
}
