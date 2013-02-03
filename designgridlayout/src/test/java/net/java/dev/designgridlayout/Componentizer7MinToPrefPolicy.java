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

import net.java.dev.designgridlayout.Componentizer.Builder;
import net.java.dev.designgridlayout.Componentizer.WidthPolicy;

public class Componentizer7MinToPrefPolicy extends AbstractComponentizerExample
{
	public static void main(String[] args)
	{
		Componentizer7MinToPrefPolicy example = new Componentizer7MinToPrefPolicy();
		example.go(true);
	}
	
	@Override protected void build(Builder builder)
	{
		builder.add(WidthPolicy.MIN_TO_PREF, field("Select a file for upload"))
			.add(WidthPolicy.PREF_FIXED, button("Select..."));
	}
}
