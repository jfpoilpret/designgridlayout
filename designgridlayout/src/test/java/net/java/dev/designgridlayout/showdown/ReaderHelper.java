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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.text.JTextComponent;

final class ReaderHelper
{
	private ReaderHelper()
	{
	}
	
	static public void readText(String name, JTextComponent dest)
	{
		name = RESOURCE_PATH + name;
		InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream(name);
		if (in == null)
		{
			dest.setText(String.format(ERROR_READ_CONTENT, name));
			dest.setCaretPosition(0);
			return;
		}
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder content = new StringBuilder();
			boolean first = true;
			while (true)
			{
				String line = reader.readLine();
				if (line == null)
				{
					break;
				}
				if (!first)
				{
					content.append('\n');
				}
				else
				{
					first = false;
				}
					
				content.append(line);
			}
			dest.setText(content.toString());
			dest.setCaretPosition(0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			dest.setText(String.format(ERROR_READ_CONTENT, name));
			dest.setCaretPosition(0);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	static final private String RESOURCE_PATH = "showdown/";
	static final private String ERROR_READ_CONTENT =
		"Sorry, an error occurred while trying to read content from classpath " +
		"resource \"%s\"";
}
