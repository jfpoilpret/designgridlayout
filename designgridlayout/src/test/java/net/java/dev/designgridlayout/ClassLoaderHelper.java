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

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Internal helper class that creates a ClassLoader that is able to load classes
// from all jars in a directory (that is not originally in application's 
// classpath)
// This is used by Issue5 class to enable dynamic testing on various PLAFs 
final class ClassLoaderHelper
{
	static ClassLoader buildClassLoader(
		boolean includeSubDirs, File... directories)
	{
		return buildClassLoader(
			includeSubDirs, Thread.currentThread().getContextClassLoader(), directories);
	}
	
	static ClassLoader buildClassLoader(
		boolean includeSubDirs, ClassLoader parent, File... directories)
	{
		List<URL> allJars = new ArrayList<URL>();
		// Find all Jars in each directory
		for (File dir: directories)
		{
			fillJarsList(allJars, dir, includeSubDirs);
		}
		return new URLClassLoader(allJars.toArray(new URL[allJars.size()]), parent);
	}
	
	static private void fillJarsList(List<URL> jars, File dir, boolean includeSubDirs)
	{
		try
		{
			for (File jar: dir.listFiles(_jarsFilter))
			{
				jars.add(jar.toURI().toURL());
			}
			
			if (includeSubDirs)
			{
				for (File subdir: dir.listFiles(_dirsFilter))
				{
					fillJarsList(jars, subdir, true);
				}
			}
		}
		catch (Exception e)
		{
			_logger.log(
				Level.SEVERE, "fillJarsList() in directory " + dir.getName(), e);
		}
	}

	private ClassLoaderHelper()
	{
	}
	
	static final private FileFilter _jarsFilter = new FileFilter()
	{
		@Override public boolean accept(File pathname)
		{
			return	pathname.isFile()
				&&	pathname.getName().toUpperCase().endsWith(JAR_SUFFIX);
		}
	};

	static final private FileFilter _dirsFilter = new FileFilter()
	{
		@Override public boolean accept(File pathname)
		{
			return	pathname.isDirectory();
		}
	};

	static final private String JAR_SUFFIX = ".JAR";
	static final private Logger _logger = 
		Logger.getLogger(ClassLoaderHelper.class.getName());
}
