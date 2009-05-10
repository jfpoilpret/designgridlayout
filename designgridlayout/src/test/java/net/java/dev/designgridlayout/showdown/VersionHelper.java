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

package net.java.dev.designgridlayout.showdown;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public final class VersionHelper
{
	private VersionHelper()
	{
	}
	
	static public String getVersion()
	{
		if (_version == null)
		{
			try
			{
				// Read all MANIFEST.MF files from classpath
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				Enumeration<URL> resources = loader.getResources(MANIFEST);
				while (resources.hasMoreElements())
				{
					URL resource = resources.nextElement();
					Manifest manifest = new Manifest(resource.openStream());
					Attributes atts = manifest.getMainAttributes();
					// Check this is the right MANIFEST
					String name = atts.getValue("Implementation-Title");
					if (DGL_TITLE.equals(name))
					{
						_version = atts.getValue("Implementation-Version");
						// If snapshot, add build information
						if (_version.endsWith("SNAPSHOT"))
						{
							String build = atts.getValue("Implementation-Build");
							_version += "-" + build;
						}
						_version = "V" + _version;
						break;
					}
				}
			}
			catch (IOException e)
			{
				// No special action, just fall back to undetermined version
			}
			if (_version == null)
			{
				_version = "Unknown Version";
			}
		}
		return _version;
	}
	
	static final private String MANIFEST = "META-INF/MANIFEST.MF";
	static final private String DGL_TITLE = "DesignGridLayout";
	static private String _version = null;
}
