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

// Issue 22: no hgap between components when layout has no grid row at all
public class Bug22WrongGapsIfNoGridRow extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Bug22WrongGapsIfNoGridRow example = new Bug22WrongGapsIfNoGridRow();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().left().add(button("Previous"), button("Next"));
		layout.row().center().add(button("Previous"), button("Next"));
		layout.row().right().add(button("Previous"), button("Next"));
	}
}
