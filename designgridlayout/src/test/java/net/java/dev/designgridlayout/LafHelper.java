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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class LafHelper
{
	static private final String PLAF_DIR = "plafs";
	static private final String PLAF_LIST = PLAF_DIR + "/plafs.txt";

	public LafHelper()
	{
		File plafDir = new File(PLAF_DIR);
		if (plafDir.exists() && plafDir.isDirectory())
		{
			_loader = ClassLoaderHelper.buildClassLoader(false, plafDir);
			_plafs = readPlafsList();
		}
		else
		{
			_loader = Thread.currentThread().getContextClassLoader();
			_plafs = new String[0];
		}
		UIManager.put("ClassLoader", _loader);
	}
	
	public String[] getPlafs()
	{
		return _plafs;
	}
	
	public void setPlaf(String plaf) throws Exception
	{
		Class<? extends LookAndFeel> clazz = 
			_loader.loadClass(plaf).asSubclass(LookAndFeel.class);
		LookAndFeel laf = clazz.newInstance();
		UIManager.setLookAndFeel(laf);
	}
	
	private String[] readPlafsList()
	{
		List<String> plafs = new ArrayList<String>();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(PLAF_LIST));
			String plaf;
			while ((plaf = reader.readLine()) != null)
			{
				plafs.add(plaf);
			}
			return plafs.toArray(new String[plafs.size()]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return new String[0];
		}
		finally
		{
			close(reader);
		}
	}
	
	static private void close(Closeable stream)
	{
		try
		{
			if (stream != null)
			{
				stream.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private final ClassLoader _loader;
	private final String[] _plafs;
}
