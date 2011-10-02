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

import javax.swing.JComponent;

final public class Componentizer
{
	static public Builder create()
	{
		return new ComponentizerLayout(new MultiComponent());
	}
	
	static public interface Builder
	{
		public Builder withSmartVerticalResize();
		public Builder withoutSmartVerticalResize();
		public Builder add(WidthPolicy width, JComponent... children);
		public Builder fixedPref(JComponent... children);
		public Builder prefAndMore(JComponent... children);
		public Builder minToPref(JComponent... children);
		public Builder minAndMore(JComponent... children);
		public JComponent component();
	}
	
	static public enum WidthPolicy
	{
		PREF_FIXED,
		MIN_TO_PREF,
		PREF_AND_MORE,
		MIN_AND_MORE
	}
	
	static private class MultiComponent extends JComponent
	{
		private static final long serialVersionUID = 1L;

		@Override public int getBaseline(int width, int height)
		{
			return ((ComponentizerLayout) getLayout()).getBaseline();
		}
	}
}
