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

import javax.swing.JComponent;

public class ActualBug54Sample extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		ActualBug54Sample example = new ActualBug54Sample();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		layout.labelAlignment(LabelAlignment.LEFT);
		layout.forceConsistentBaselinesDistance();
		
		JComponent row1item = Componentizer.create()
			.fixedPref(field("abcdefghijklm")).fixedPref(label("Drug:")).prefAndMore(field("123456789012345678901234567890"))
			.component();
		layout.row().grid(label("Pharmacist Name:")).add(row1item);
		layout.row().grid(label("Issue Type:")).add(field("abcdef"));
		JComponent row3item = Componentizer.create()
			.prefAndMore(field("abcdefghijklmnopqrstuv")).fixedPref(button("..."))
			.component();
		layout.row().grid(label("Related To:")).add(row3item);
		layout.row().grid(label("Desired Outcome:")).add(field("abcdef"));
		layout.emptyRow();

		layout.row().left().add(label("Actions"));
		layout.row().left().fill().add(table());
		layout.emptyRow();
		
		layout.row().left().add(label("Updates"));
		layout.row().left().fill().add(table());
		layout.row().right().add(button("Add"), button("Update"));
		layout.emptyRow();

		layout.row().center().add(button("Save"), button("Cancel"));
	}
	// CSON: MagicNumber
}
