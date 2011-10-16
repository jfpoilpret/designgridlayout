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

package net.java.dev.designgridlayout.showdown;

import javax.swing.text.JTextComponent;

import net.java.dev.designgridlayout.IExample;

final class Node
{
	public Node(
		String name, Class<? extends IExample> clazz, boolean launchable)
	{
		_name = name;
		_clazz = clazz;
		_launchable = launchable;
	}
	
	public Node(String name, Class<? extends IExample> clazz)
	{
		this(name, clazz, true);
	}
	
	public boolean isLaunchable()
	{
		return _launchable;
	}
	
	public void launch()
	{
		try
		{
			IExample example = _clazz.newInstance();
			example.go(false);
			example.frame().setTitle(_name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void showDescription(JTextComponent dest)
	{
		// Open the file resource (in classpath) containing the description
		String name = _clazz.getSimpleName() + ".desc.txt";
		ReaderHelper.readText(name, dest);
	}

	public void showSource(JTextComponent dest)
	{
		// Open the file resource (in classpath) containing the source code
		String name = _clazz.getSimpleName() + ".java.txt";
		ReaderHelper.readText(name, dest);
	}
	
	@Override public String toString()
	{
		return _name;
	}
	
	final private String _name;
	final private Class<? extends IExample> _clazz;
	final private boolean _launchable;
}
