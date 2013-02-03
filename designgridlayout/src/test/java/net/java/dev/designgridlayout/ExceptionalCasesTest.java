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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.testng.Assert.fail;

@Test(groups = "utest")
public class ExceptionalCasesTest
{	
	@Test public void checkDirectAddComponentToDesignGridContainer() throws Exception
	{
		JLabel label1 = new JLabel("Dummy1");
		JPanel parent = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(parent);
		layout.row().grid(label1);
		try
		{
			JLabel label2 = new JLabel("Dummy2");
			parent.add(label2);
			fail("Adding a component directly to a container using DesigngridLayout " + 
				"should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not use this method");
		}
	}

	@Test public void checkDirectRemoveComponentFromDesignGridContainer() throws Exception
	{
		JLabel label1 = new JLabel("Dummy1");
		JPanel parent = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(parent);
		layout.row().grid(label1);
		try
		{
			parent.remove(label1);
			fail("Removing a component directly to a container using DesignGridLayout " + 
				"should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not use this method");
		}
	}

	@Test public void checkDirectAddComponentToComponentizer() throws Exception
	{
		JLabel label1 = new JLabel("Dummy1");
		JComponent parent = Componentizer.create().fixedPref(label1).component();
		JLabel label2 = new JLabel("Dummy2");
		try
		{
			parent.add(label2);
			fail("Adding a component directly to a container using Componentizer " + 
				"should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not use this method");
		}
	}

	@Test public void checkDirectAddComponentWithStringToComponentizer() throws Exception
	{
		JLabel label1 = new JLabel("Dummy1");
		JComponent parent = Componentizer.create().fixedPref(label1).component();
		JLabel label2 = new JLabel("Dummy2");
		try
		{
			parent.add("", label2);
			fail("Adding a component directly to a container using Componentizer " + 
				"should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not use this method");
		}
	}

	@Test public void checkDirectAddComponentWithObjectToComponentizer() throws Exception
	{
		JLabel label1 = new JLabel("Dummy1");
		JComponent parent = Componentizer.create().fixedPref(label1).component();
		JLabel label2 = new JLabel("Dummy2");
		try
		{
			parent.add(label2, new Object());
			fail("Adding a component directly to a container using Componentizer " + 
				"should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not use this method");
		}
	}

	@Test public void checkDirectRemoveComponentFromComponentizer() throws Exception
	{
		JLabel label1 = new JLabel("Dummy1");
		JComponent parent = Componentizer.create().fixedPref(label1).component();
		try
		{
			parent.remove(label1);
			fail("Removing a component directly to a container using Componentizer " + 
				"should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not use this method");
		}
	}
}
