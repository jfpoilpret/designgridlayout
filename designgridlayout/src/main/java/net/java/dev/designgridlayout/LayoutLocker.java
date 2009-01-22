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

// This class is used to manage "layout locking", ie that, after computations
// have been performed, library users will not attempt to modify the layout
// (eg by adding rows, adding components to rows, changing the margins...)
// Any such attempt would throw an exception
final class LayoutLocker
{
	void checkUnlocked() throws IllegalStateException
	{
		if (_locked)
		{
			throw new IllegalStateException(
				"DesignGridLayout instance has been locked, hence modify the " +
				"layout is no more possible");
		}
	}
	
	void lock()
	{
		_locked = true;
	}
	
	private boolean _locked = false;
}
