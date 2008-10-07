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

import java.awt.Component;

abstract class AbstractClassBasedHeightGrowPolicy<T extends Component>
	implements ClassBasedHeightGrowPolicy
{
	protected AbstractClassBasedHeightGrowPolicy(Class<T> componentClass)
    {
		_componentClass = componentClass;
    }

	public Class<? extends Component> getComponentClass()
	{
		return _componentClass;
	}

	public boolean canGrowHeight(Component component)
	{
		return componentCanGrowHeight(_componentClass.cast(component));
	}
	
	abstract protected boolean componentCanGrowHeight(T component);

	private final Class<T> _componentClass;
}
