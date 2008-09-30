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

import javax.swing.JSeparator;

public class Example1e extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Example1e example = new Example1e();
		example.go(true);
	}

	@Override public void build(DesignGridLayout layout)
	{
		layout.leftRow().fill().add(label("Special Group")).add(new JSeparator());
		layout.row().label(label(1)).add(button(), button(), button());
		layout.leftRow().fill().add(button(), button(), button());
		layout.centerRow().fill().add(button(), button(), button());
		layout.rightRow().fill().add(button(), button(), button());
	}
}
