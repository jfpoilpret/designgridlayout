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

package net.java.dev.designgridlayout.policy;

import java.awt.Component;

/**
 * Defines policies applied by {@link DesignGridLayout} regarding the
 * behavior of components in a layout during vertical resize.
 * <p/>
 * This tells {@link DesignGridLayout} whether a given {@link Component} can
 * accept vertical space beyond its preferred height or not, thus defining
 * which rows in a layout can grow in height or not.
 * <p/>
 * This is also used by {@link DesignGridLayout} "Smart Vertical Resize" feature
 * to determine, for components that can grow vertically, how much of the extra
 * available height (during user vertical resize) is really useful to them,
 * thus enabling the possibility to have a smart behavior of e.g. 
 * {@link javax.swing.JList} (embedded in a {@link javax.swing.JScrollPane})
 * during resize, i.e. showing only rows in their full height, never partially.
 * <p/>
 * {@link DesignGridLayout} already defines default behaviors for all components
 * inside a {@link javax.swing.JScrollPane} and for vertical 
 * {@link javax.swing.JSlider}s. But you can add your own additional polcies
 * for specific custom components.
 * TODO show sample code how to create / add new policy to DGL!
 * 
 * @author Jean-Francois Poilpret
 * @since 1.2
 * @see TODO
 */
public interface HeightGrowPolicy
{
	/**
	 * Checks if a {@link Component} can grow in height. 
	 * @param component the component to test
	 * @return {@code true} if {@code component} has a variable height;
	 * {@code false} if {@code component} has a fixed height.
	 */
	public boolean canGrowHeight(Component component);
	
	/**
	 * Computes the maximum amount of extra height that a {@link Component} can
	 * use.
	 * @param component the component to test
	 * @param extraHeight the amount of available extra height 
	 * @return the maximum amount of extra height that {@code component} can use
	 * without exceeding {@code extraHeight}
	 */
	public int computeExtraHeight(Component component, int extraHeight);
}
