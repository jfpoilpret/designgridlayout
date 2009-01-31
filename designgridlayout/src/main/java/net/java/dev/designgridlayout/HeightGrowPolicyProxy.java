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

package net.java.dev.designgridlayout;

import java.awt.Component;

class HeightGrowPolicyProxy implements HeightGrowPolicy
{
	public HeightGrowPolicyProxy()
	{
	}

	public HeightGrowPolicyProxy(HeightGrowPolicy delegate)
	{
		setDelegate(delegate);
	}

	public void setDelegate(HeightGrowPolicy delegate)
	{
		_delegate = delegate;
	}

	public HeightGrowPolicy getDelegate()
	{
		return _delegate;
	}

	public boolean canGrowHeight(Component component)
	{
		return _delegate.canGrowHeight(component);
	}

	public int computeExtraHeight(Component component, int extraHeight)
	{
		return _delegate.computeExtraHeight(component, extraHeight);
	}

	private HeightGrowPolicy _delegate;
}
