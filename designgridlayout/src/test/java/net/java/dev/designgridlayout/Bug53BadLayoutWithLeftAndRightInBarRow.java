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

public class Bug53BadLayoutWithLeftAndRightInBarRow extends AbstractDesignGridExample
{
	public static void main(String[] args) throws Exception
	{
		Bug53BadLayoutWithLeftAndRightInBarRow example = new Bug53BadLayoutWithLeftAndRightInBarRow();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
//		layout.row()
//			.grid(3).add(button())
//			.grid().empty(1)
//			.grid(3).add(button());
//		layout.row().grid().add(button());
		// this causes the rows above to resize
		layout.row().bar().left(button()).right(button());
	}
}
