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

import java.awt.Point;

import javax.swing.SwingUtilities;

abstract public class AbstractGuiTest extends AbstractGuiHelper
{
	final protected <T extends AbstractBaseExample, U extends T> void launchGui(
		Class<U> clazz, Initializer<T> initializer) 
		throws Exception
	{
		U example = clazz.newInstance();
		if (initializer != null)
		{
			initializer.init(example);
		}
		example.go(false);
		init(example);
	}
	
	final protected <T extends AbstractBaseExample> void launchGui(Class<T> clazz) 
		throws Exception
	{
		launchGui(clazz, null);
	}
	
	final protected void checkExampleFromEDT(
		final Class<? extends AbstractBaseExample> clazz) throws Exception
	{
		final LaunchAppHolder holder = new LaunchAppHolder();
		SwingUtilities.invokeAndWait(new Runnable()
		{
			public void run()
			{
				AbstractBaseExample example = null;
				try
				{
					example = clazz.newInstance();
					holder.setExample(example);
					example.go(false);
				}
				catch (Exception e)
				{
					holder.setException(e);
				}
			}
		});
		init(holder.getExample());
		if (holder.getException() != null)
		{
			throw holder.getException();
		}
		checkSnapshot();
	}

	// Note: don't use @DataProvider because all tests appear under the same name
	// in maven surefire reports...
	final protected void checkExample(Class<? extends AbstractBaseExample> clazz)
		throws Exception
	{
		launchGui(clazz);
		checkSnapshot();
	}
	
	final protected void checkExampleAndResizeHeight(
		Class<? extends AbstractBaseExample> clazz, int increment, int steps) throws Exception
	{
		launchGui(clazz);
		checkSnapshot("pref-size");
		frame().moveTo(new Point(frame().target.getX(), 0));
		for (int i = 1; i <= steps; i++)
		{
			frame().resizeHeightTo(frame().target.getHeight() + increment);
			checkSnapshot("resize-" + (i * increment));
		}
	}

	final protected void checkExampleAndResizeWidth(
		Class<? extends AbstractBaseExample> clazz, double... ratios) throws Exception
	{
		launchGui(clazz);
		checkSnapshot("pref-size");
		frame().moveTo(new Point(0, frame().target.getY()));
		int width = frame().panel("TOP").target.getWidth();
		int extraWidth = frame().target.getWidth() - width;
		for (int i = 0; i < ratios.length; i++)
		{
			width *= ratios[i];
			frame().resizeWidthTo(width + extraWidth);
			checkSnapshot("resize-" + (i + 1));
		}
	}

    protected interface Initializer<T extends AbstractBaseExample>
	{
		public void init(T instance);
	}
	
	static class LaunchAppHolder
	{
		public void setExample(Object example)
		{
			_example = example;
		}
		
		public Object getExample()
		{
			return _example;
		}
		
		public void setException(Exception e)
		{
			_exception = e;
		}
		
		public Exception getException()
		{
			return _exception;
		}
		
		private Object _example = null;
		private Exception _exception = null;
	}

}
