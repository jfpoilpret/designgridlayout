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

import javax.swing.JComponent;

//TODO javadoc
public interface ISpannableGridRow extends IGridRow
{
	//TODO javadoc
	public abstract ISpannableGridRow spanRow();

	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent[])
	 */
	public abstract ISpannableGridRow add(JComponent... children);
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#add(javax.swing.JComponent, int)
	 */
	public abstract ISpannableGridRow add(JComponent child, int span);
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#empty()
	 */
	public abstract ISpannableGridRow empty();
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#empty(int)
	 */
	public abstract ISpannableGridRow empty(int span);
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(javax.swing.JComponent[])
	 */
	public abstract ISpannableGridRow addMulti(JComponent... children);
	
	/*
	 * (non-Javadoc)
	 * @see net.java.dev.designgridlayout.IGridRow#addMulti(int, javax.swing.JComponent[])
	 */
	public abstract ISpannableGridRow addMulti(int span, JComponent... children);
}
