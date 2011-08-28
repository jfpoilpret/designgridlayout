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

package net.java.dev.designgridlayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.fest.assertions.Assertions.assertThat;
import org.testng.annotations.Test;
import static org.testng.Assert.fail;

@Test(groups = "utest")
public class Bug44AddSameComponentTwiceTest
{	
	@Test public void checkAddSameComponentToOneGridRow() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().grid().add(label, label);
			fail("Adding two components to the same row should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}

	@Test public void checkAddSameLabelToOneGridRow() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().grid(label).add(label);
			fail("Adding two components to the same row should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}

	@Test public void checkAddSameComponentToOneLeftRow() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().left().add(label, label);
			fail("Adding two components to the same row should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}

	@Test public void checkAddSameComponentToOneBarRow() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().bar().add(label, Tag.CANCEL).add(label, Tag.APPLY);
			fail("Adding two components to the same row should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}

	@Test public void checkAddSameComponentToTwoRows() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().grid().add(label);
			layout.row().grid().add(label);
			fail("Adding two components to the same layout should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}
	
	@Test public void checkAddSameMultiComponentToTwoRows() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().grid().addMulti(label);
			layout.row().grid().addMulti(label);
			fail("Adding two components to the same layout should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}
	
	@Test public void checkAddMultiSameComponent() throws Exception
	{
		try
		{
			JPanel parent = new JPanel();
			DesignGridLayout layout = new DesignGridLayout(parent);
			JLabel label = new JLabel("Dummy");
			layout.row().grid().addMulti(label, label);
			fail("Adding two components to the same layout should throw IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage()).as("IllegalArgumentException.getMessage()")
				.isEqualTo("Do not add the same component twice");
		}
	}
}
