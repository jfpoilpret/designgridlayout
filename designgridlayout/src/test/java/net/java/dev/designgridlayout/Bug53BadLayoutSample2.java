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

import javax.swing.JScrollPane;

public class Bug53BadLayoutSample2 extends AbstractDesignGridExample
{
	public static void main(String[] args) throws Exception
	{
		Bug53BadLayoutSample2 example = 
			new Bug53BadLayoutSample2();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row(0)
			.grid(3).add(new JScrollPane(button()))
			.grid().empty(1)
			.grid(3).add(new JScrollPane(button()));
		layout.emptyRow();
		layout.row(1).grid().add(new JScrollPane(button()));
		// this causes the rows above to resize
		layout.row().bar().left(button()).right(button());
//		layout.row().bar().right( button() );
	}
}
