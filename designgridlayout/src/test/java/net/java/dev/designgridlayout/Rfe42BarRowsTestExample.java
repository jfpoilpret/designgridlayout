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

public class Rfe42BarRowsTestExample extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Rfe42BarRowsTestExample example = new Rfe42BarRowsTestExample();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().bar().left(button("Left"));
		layout.row().bar().center(button("Center"));
		layout.row().bar().right(button("Right"));
		layout.row().bar().left(button("Left")).center(button("Center"));
		layout.row().bar().left(button("Left")).right(button("Right"));
		layout.row().bar().center(button("Center")).right(button("Right"));
		layout.row().bar().left(button("Left")).center(button("Center"))
			.right(button("Right")).right(button("Right 2"));
	}

	@Override protected void init(DesignGridLayout layout)
	{
		// This method is overridden to ensure consistent width is not disabled
	}
}
