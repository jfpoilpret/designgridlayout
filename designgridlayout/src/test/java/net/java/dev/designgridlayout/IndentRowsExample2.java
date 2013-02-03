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

import javax.swing.JTextField;

public class IndentRowsExample2 extends AbstractDesignGridExample
{
	public static void main(String[] args) throws Exception
	{
		IndentRowsExample2 example = new IndentRowsExample2();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.labelAlignment(LabelAlignment.LEFT);

		//CSOFF: LineLength
		layout.row().grid(label("Label 11:")).grid(label("Label 21:"));
		layout.row().grid(label("Label 12:")).indent().add(field("field12")).grid(label("Label 22:")).indent().add(field("field22"));
		layout.row().grid(label("Label 13:")).indent().add(field("field13")).grid(label("Label 23:")).indent().add(field("field23"));
		//CSON: LineLength
	}

	@Override protected JTextField field(String text)
	{
		JTextField field = super.field(text);
		field.setColumns(10);
		return field;
	}
}
