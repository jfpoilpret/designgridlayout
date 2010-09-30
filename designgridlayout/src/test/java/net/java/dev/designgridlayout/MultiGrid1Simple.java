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

// This example checks the preferred size calculated when having a large 
// component spanning several grids
public class MultiGrid1Simple extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		MultiGrid1Simple example = new MultiGrid1Simple();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("1")).add(field("X")).grid(label("1")).add(field("Y"));
		layout.row().grid(label("2")).add(field("XXXXYYYYZZZZ"));
		layout.row().center().add(button("OK"));
	}
}
