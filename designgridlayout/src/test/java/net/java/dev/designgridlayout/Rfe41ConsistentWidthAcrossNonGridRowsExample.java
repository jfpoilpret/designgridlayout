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

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JSeparator;

public class Rfe41ConsistentWidthAcrossNonGridRowsExample extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Rfe41ConsistentWidthAcrossNonGridRowsExample example = 
			new Rfe41ConsistentWidthAcrossNonGridRowsExample();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.row().left().add(label("1234567890")).add(new JSeparator()).fill().withOwnRowWidth();
		layout.row().right().add(button("1"), button("2"), button("3"));
		layout.row().right().add(button("1"), button("2"));
		layout.row().right().add(button("ABCDE"), button("FGHIJ"));
		layout.row().left().add(button("ABC"), button("F"));
		layout.row().center().add(button("OK"), button("Cancel"));
		layout.row().left().add(label("123")).add(new JSeparator()).fill().withOwnRowWidth();
	}

	@Override protected void init(DesignGridLayout layout)
	{
	}

	@Override protected JButton button(String text)
	{
		JButton button = super.button(text);
		Dimension dim = button.getPreferredSize();
		dim = new Dimension(dim.width / 2, dim.height);
		button.setMinimumSize(dim);
		return button;
	}
}
