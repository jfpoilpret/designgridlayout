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

import javax.swing.JButton;

public class Example1d extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Example1d example = new Example1d();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label(1)).add(button()).add(button());
		layout.row().grid(label(2)).add(button()).add(button()).add(button());
		layout.row().grid(label(3)).add(button(), 2).add(button());
		layout.row().grid(label(4)).add(button()).add(button()).add(button()).empty();
		layout.emptyRow();
		layout.row().right().add(new JButton("Cancel")).add(new JButton("OK"));
	}
	// CSON: MagicNumber
}
