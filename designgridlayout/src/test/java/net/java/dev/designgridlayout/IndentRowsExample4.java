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

public class IndentRowsExample4 extends AbstractBaseExample
{
	public static void main(String[] args) throws Exception
	{
		IndentRowsExample4 example = new IndentRowsExample4();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().left().add(button("Button 1"));
		layout.row().left().indent().add(button("Button 2"));
		layout.row().left().indent(2).add(button("Button 3"));
		layout.row().left().indent(3).add(button("Button 4"));
	}
}
