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

public class RowSpan6SimpleExampleOnTwoGrids extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		RowSpan6SimpleExampleOnTwoGrids example = new RowSpan6SimpleExampleOnTwoGrids();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label 1:")).add(field("field1")).grid(label("List:")).add(list());
		layout.row().grid(label("Label 2:")).add(field("field2")).grid().spanRow();
	}
}
