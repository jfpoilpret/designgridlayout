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

import javax.swing.JComponent;

final class MultiComponent extends JComponent
{
	MultiComponent(HeightGrowPolicy heightTester, OrientationPolicy orientation, 
		JComponent... children)
	{
		_layout = new HorizontalLayout(this, heightTester, orientation);
		_children = children;
		setLayout(_layout);
		_layout.add(children);
	}
	
	public JComponent[] getChildren()
	{
		return _children;
	}

	public int getBaseline(int width, int height)
	{
		return _layout.getBaseline();
	}
	
	private final HorizontalLayout _layout;
	private final JComponent[] _children;
}
