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

public class Bug26IndexOutBoundsInMultiGrid extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Bug26IndexOutBoundsInMultiGrid example = new Bug26IndexOutBoundsInMultiGrid();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		//CSOFF: LineLength
		layout.row().grid(label("lbl12"), 2).add(field("field12")).grid(label("lbl3"), 1).add(field("field3"));
		layout.row().grid(label("lbl1"), 1).add(field("field1")).grid(label("lbl23"), 2).add(field("field23"));
		//CSON: LineLength
	}
}
