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

import javax.swing.JList;
import javax.swing.JScrollPane;

public class Rfe10CheckVGaps extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		Rfe10CheckVGaps example = new Rfe10CheckVGaps();
		example.go(true);
	}

	@Override protected void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label1")).add(field("field1")).add(list());
		layout.row().grid(label("Label2")).add(field("field2")).spanRow();
	}

	@Override protected JScrollPane list()
	{
		JScrollPane list = super.list();
		((JList) list.getViewport().getView()).setVisibleRowCount(3);
		return list;
	}
}
