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

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class RowSpan7SpecialComponent extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		RowSpan7SpecialComponent example = new RowSpan7SpecialComponent();
		example.go(true);
	}

	@Override protected void build(DesignGridLayout layout)
	{
		JScrollPane picture = new JScrollPane(new Picture());
		picture.setHorizontalScrollBarPolicy(
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		picture.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		layout.row().grid(label("First name:")).add(field("Jean-Francois"))
								.grid(label("Picture:")).add(picture);
		layout.row().grid(label("Last name:")).add(field("Poilpret"))
								.grid().spanRow();
		layout.row().grid(label("Nationality:")).add(field("french"))
								.grid().spanRow();
		layout.row().grid(label("Address 1:")).add(field("Hoang Hoa Tham street"));
		layout.row().grid(label("Address 2:")).add(field("Binh Thanh district"));
		layout.row().grid(label("City:")).add(field("Ho Chi Minh city"));
	}
	
	@Override protected JTextField field(String text)
	{
		JTextField field = super.field(text);
		field.setColumns(10);
		field.setMinimumSize(field.getPreferredSize());
		return field;
	}
}
