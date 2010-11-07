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

public class Rfe42BarRowsTagsExample extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Rfe42BarRowsTagsExample example = new Rfe42BarRowsTagsExample();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Row 1")).add(field("abcdefghijklmnopqrstuvwxyz")).empty(3);
		layout.row().grid(label("Row 2")).add(field(""));
		layout.emptyRow();
		layout.row().bar().add(button("Help"), Tag.HELP).add(button("< Previous"), Tag.PREVIOUS)
			.add(button("Next >"), Tag.NEXT).add(button("Finish"), Tag.FINISH)
			.add(button("Cancel"), Tag.CANCEL);
		layout.emptyRow();
		layout.row().bar().add(button("OK"), Tag.OK).add(button("Cancel"), Tag.CANCEL)
			.addLeft(button("Left")).addRight(button("Right"));
	}

	@Override protected void init(DesignGridLayout layout)
	{
		// This method is overridden to ensure consistent width is not disabled
//		PlatformHelper.initButtonsTagOrder("L_H/X/NY<>COA_R");
	}
}
