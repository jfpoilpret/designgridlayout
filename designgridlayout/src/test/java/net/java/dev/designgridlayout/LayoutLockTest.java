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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "utest")
public class LayoutLockTest
{
	@BeforeClass public void initLayout()
	{
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		_layout = new DesignGridLayout(panel);
		_layout.row().center().add(new JButton("Button"));
		_creator = _layout.row();
		_grid = _layout.row().grid().add(new JButton("Button"));
		_nonGrid = _layout.row().center().add(new JButton("Button"));
		frame.setContentPane(panel);
		frame.pack();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedMargins()
	{
		_layout.margins(0.0);
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedForceConsistentVGaps()
	{
		_layout.forceConsistentVGaps();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedEmptyRow()
	{
		_layout.emptyRow();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedRowCreator()
	{
		_layout.row();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedRowCreatorGrid()
	{
		_creator.grid();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedRowCreatorCenter()
	{
		_creator.center();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedRowCreatorLeft()
	{
		_creator.left();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedRowCreatorRight()
	{
		_creator.right();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedGridRowAdd()
	{
		_grid.add(new JButton());
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedGridRowAddMulti()
	{
		_grid.addMulti(new JButton());
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedGridRowEmpty()
	{
		_grid.empty();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedGridRowSpanRow()
	{
		_grid.spanRow();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedGridRowGrid()
	{
		_grid.grid();
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedNonGridRowAdd()
	{
		_nonGrid.add(new JButton());
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedNonGridRowAddMulti()
	{
		_nonGrid.addMulti(new JButton());
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void checkLockedNonGridRowFill()
	{
		_nonGrid.fill();
	}
	
	private DesignGridLayout _layout;
	private IRowCreator _creator;
	private ISpannableGridRow _grid;
	private INonGridRow _nonGrid;
}
