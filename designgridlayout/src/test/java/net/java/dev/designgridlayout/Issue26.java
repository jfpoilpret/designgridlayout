//  Copyright 2008 Jason Aaron Osgood, Jean-Francois Poilpret
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

public class Issue26 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Issue26 example = new Issue26();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("lbl12"), 2).add(field("field12")).grid(label("lbl3"), 1).add(field("field3"));
		layout.row().grid(label("lbl1"), 1).add(field("field1")).grid(label("lbl23"), 2).add(field("field23"));
	}

	@Override protected JTextField field(String text)
    {
	    JTextField field = super.field(text);
	    field.setEnabled(false);
	    return field;
    }
}
