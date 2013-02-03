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

import java.awt.ComponentOrientation;

import net.java.dev.designgridlayout.Componentizer.Builder;

public class Componentizer3FixedVariableFixedRTL extends AbstractComponentizerExample
{
	public static void main(String[] args)
	{
		Componentizer3FixedVariableFixedRTL example = new Componentizer3FixedVariableFixedRTL();
		example.go(true);
	}
	
	@Override protected void build(Builder builder)
	{
		frame().applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		//CSOFF: LineLength
		builder.fixedPref(label("Total:")).prefAndMore(field("999,999.99")).fixedPref(label("USD"));
		//CSON: LineLength
	}
}
