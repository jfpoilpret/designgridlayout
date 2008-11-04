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

import org.jdesktop.layout.Baseline;

// Utility to factor out similar code dealing with common JComponent properties
interface IExtractor
{
	int value(JComponent c);
}

abstract class AbstractExtractor implements IExtractor
{
	protected AbstractExtractor()
	{
	}
}

final class MinWidthExtractor extends AbstractExtractor
{
	static final IExtractor INSTANCE = new MinWidthExtractor();
	
	public int value(JComponent c)
	{
		return c.getMinimumSize().width;
	}
}

final class PrefWidthExtractor implements IExtractor
{
	static final IExtractor INSTANCE = new PrefWidthExtractor();
	
	public int value(JComponent c)
	{
		return c.getPreferredSize().width;
	}
}

final class PrefHeightExtractor implements IExtractor
{
	static final IExtractor INSTANCE = new PrefHeightExtractor();
	
	public int value(JComponent c)
	{
		return c.getPreferredSize().height;
	}
}

final class BaselineExtractor implements IExtractor
{
	static final IExtractor INSTANCE = new BaselineExtractor();
	
	public int value(JComponent c)
	{
		return Baseline.getBaseline(c);
	}
}

