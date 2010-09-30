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

import javax.swing.JSlider;

// Shows issue 5 with lack of variable height rows
public class Rfe05SmartVerticalResizeMultiComponent2 extends AbstractBaseExample
{
	public static void main(String[] args)
	{
		Rfe05SmartVerticalResizeMultiComponent2 example = new Rfe05SmartVerticalResizeMultiComponent2();
		example.go(true);
	}
	
	@Override public void build(DesignGridLayout layout)
	{
		layout.row().grid(label("Label1")).add(field("Field1")).empty();
		layout.row().grid(label("Power"))
			.addMulti(slider(), field("Field2")).add(field("Field3"));
		layout.row().center().add(button(), button(), button());
	}
	
	static private JSlider slider()
	{
		JSlider slider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		return slider;
	}
}
