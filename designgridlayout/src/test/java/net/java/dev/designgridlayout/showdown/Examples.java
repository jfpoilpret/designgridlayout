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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.java.dev.designgridlayout.AbstractDesignGridExample;
import net.java.dev.designgridlayout.AddressBookDemo;
import net.java.dev.designgridlayout.Basics1SimpleGrid;
import net.java.dev.designgridlayout.Basics2GridColumns;
import net.java.dev.designgridlayout.Basics3GridLabels;
import net.java.dev.designgridlayout.Basics4RightRow;
import net.java.dev.designgridlayout.Basics5NonGridRows;
import net.java.dev.designgridlayout.Basics6NonGridRowsWithFiller;
import net.java.dev.designgridlayout.Basics7RealWorldExample1;
import net.java.dev.designgridlayout.Basics8RealWorldExample2;
import net.java.dev.designgridlayout.Basics9RealWorldExample3;
import net.java.dev.designgridlayout.IndentRowsExample;
import net.java.dev.designgridlayout.Misc1CustomizedMargins;
import net.java.dev.designgridlayout.MultiComponentExample;
import net.java.dev.designgridlayout.MultiGrid1Simple;
import net.java.dev.designgridlayout.MultiGrid2ThreeGridsWithGridSpan;
import net.java.dev.designgridlayout.MultiGrid3ThreeComplexGrids;
import net.java.dev.designgridlayout.Rfe29ConsistentBaselineSpace;
import net.java.dev.designgridlayout.Rfe29ExactVGaps;
import net.java.dev.designgridlayout.Rfe40LeftLabelAlignmentExample;
import net.java.dev.designgridlayout.Rfe40PlatformLabelAlignmentExample;
import net.java.dev.designgridlayout.Rfe41ConsistentWidthAcrossNonGridRowsExample;
import net.java.dev.designgridlayout.Rfe41IndependentWidthAcrossNonGridRowsExample;
import net.java.dev.designgridlayout.Rfe42BarRowsTagsExample;
import net.java.dev.designgridlayout.RightToLeft1LTR;
import net.java.dev.designgridlayout.RightToLeft2RTL;
import net.java.dev.designgridlayout.RightToLeft3RealWorldExample;
import net.java.dev.designgridlayout.RowSpan2TwoLists;
import net.java.dev.designgridlayout.RowSpan3TwoListsCustomWeights;
import net.java.dev.designgridlayout.RowSpan4ErrorMarkers;
import net.java.dev.designgridlayout.RowSpan5SimplestExample;
import net.java.dev.designgridlayout.RowSpan6SimpleExampleOnTwoGrids;
import net.java.dev.designgridlayout.RowSpan7SpecialComponent;
import net.java.dev.designgridlayout.ShowHideRowsRealWorldExample1;
import net.java.dev.designgridlayout.ShowHideRowsRealWorldExample2;
import net.java.dev.designgridlayout.ShowHideRowsRealWorldExample3;
import net.java.dev.designgridlayout.ShowHideRowsRealWorldExample4;
import net.java.dev.designgridlayout.SmartVerticalResize1Sliders;
import net.java.dev.designgridlayout.SmartVerticalResize3CustomWeights;
import net.java.dev.designgridlayout.SmartVerticalResize4RealWorldExample;
import net.java.dev.designgridlayout.SmartVerticalResize5SameWeight;

@SuppressWarnings("serial")
public class Examples extends JFrame
{
	public static void main(String[] args) throws Exception
	{
		// First ask for the LAF to use (modal dialog)
		SwingUtilities.invokeAndWait(new Runnable()
		{
			@Override public void run()
			{
				LafChooserDialog dialog = new LafChooserDialog();
				dialog.setVisible(true);
			}
		});
		// Then show the Examples main frame
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				Examples examples = new Examples();
				examples.pack();
				examples.setLocationRelativeTo(null);
				examples.setVisible(true);
			}
		});
	}
	
	private Examples()
	{
		super("DesignGridLayout Usage Examples");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Initialize tree
		initTree();

		// Add listeners
		initListeners();
		
		// Further initialization
		_tree.setSelectionRow(0);
		_tree.setShowsRootHandles(false);
		_tree.setCellRenderer(new NodeRenderer());
		
		// Layout frame
		JScrollPane treeScroller = new JScrollPane(_tree);
		treeScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		treeScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_split.setLeftComponent(treeScroller);
		_split.setRightComponent(_detail);
		_split.setDividerLocation(treeScroller.getPreferredSize().width);
		
		setContentPane(_split);
	}

	private void initTree()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultMutableTreeNode node;

		root.add(new DefaultMutableTreeNode(
			new Node("Introduction", AbstractDesignGridExample.class, false)));

		node = new DefaultMutableTreeNode("Basic Examples");
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - 3 simple rows", Basics1SimpleGrid.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - column span, empty cells", Basics2GridColumns.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - labels", Basics3GridLabels.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Non-Grids - right-centered row", Basics4RightRow.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Non-Grids - left, center, right, filler", Basics5NonGridRows.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Non-Grids - fillers", Basics6NonGridRowsWithFiller.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Non-Grids - command bars", Rfe42BarRowsTagsExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example - Figure 177", Basics7RealWorldExample1.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example - Figure 178", Basics8RealWorldExample2.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example - JGoodies sample", Basics9RealWorldExample3.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Smart Vertical Resize");
		node.add(new DefaultMutableTreeNode(
			new Node("Vertical Slider", SmartVerticalResize1Sliders.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("List, Tables, Text Areas, Sliders", SmartVerticalResize5SameWeight.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Same with custom weights", SmartVerticalResize3CustomWeights.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example - Figure 179", SmartVerticalResize4RealWorldExample.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Multiple Grids");
		node.add(new DefaultMutableTreeNode(
			new Node("2 grids on 2 rows", MultiGrid1Simple.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("3 grids with grid-span", MultiGrid2ThreeGridsWithGridSpan.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Complex layout with 3 grids", MultiGrid3ThreeComplexGrids.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example - Address Book Demo", AddressBookDemo.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Row Span");
		node.add(new DefaultMutableTreeNode(
			new Node("2-rows span list (1 grid)", RowSpan5SimplestExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("2-rows span list (2 grids)", RowSpan6SimpleExampleOnTwoGrids.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("2 lists with row-span", RowSpan2TwoLists.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Same with custom weights", RowSpan3TwoListsCustomWeights.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Markers on bad API usage", RowSpan4ErrorMarkers.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example - Contact entry", RowSpan7SpecialComponent.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Label Alignment in Grids");
		node.add(new DefaultMutableTreeNode(
			new Node("AddressBookDemo - Platform Alignment", Rfe40PlatformLabelAlignmentExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("AddressBookDemo - Left Alignment", Rfe40LeftLabelAlignmentExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("AddressBookDemo - Right Alignment", AddressBookDemo.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Right to Left Support");
		node.add(new DefaultMutableTreeNode(
			new Node("Example 1 - Left to Right", RightToLeft1LTR.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example 1 - Right to Left", RightToLeft2RTL.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("AddressBookDemo - Right to Left", RightToLeft3RealWorldExample.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Dynamic Layouts");
		node.add(new DefaultMutableTreeNode(
			new Node("Example 1 - Row-based Hiding", ShowHideRowsRealWorldExample1.class)));
		root.add(node);
		node.add(new DefaultMutableTreeNode(
			new Node("Example 2 - RowGroup-based Hiding", ShowHideRowsRealWorldExample2.class)));
		root.add(node);
		node.add(new DefaultMutableTreeNode(
			new Node("Example 3 - Same with beautiful icons", ShowHideRowsRealWorldExample3.class)));
		root.add(node);
		node.add(new DefaultMutableTreeNode(
			new Node("Example 4 - Same with indented rows", ShowHideRowsRealWorldExample4.class)));
		root.add(node);

		node = new DefaultMutableTreeNode("Miscellaneous");
		node.add(new DefaultMutableTreeNode(
			new Node("Row Indenting", IndentRowsExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Custom Margins", Misc1CustomizedMargins.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Multi-Components", MultiComponentExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Exact Vertical Gaps", Rfe29ExactVGaps.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Consistent Baselines Spacing", Rfe29ConsistentBaselineSpace.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Consistent Widths across Non-Grid Rows", Rfe41ConsistentWidthAcrossNonGridRowsExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Independent Widths across Non-Grid Rows", Rfe41IndependentWidthAcrossNonGridRowsExample.class)));
		root.add(node);

		DefaultTreeModel model = new DefaultTreeModel(root);
		_tree.setModel(model);
		_tree.setRootVisible(false);
		
		// Expand the whole tree
		for(int i = 0; i < _tree.getRowCount(); i++)
		{
			_tree.expandRow(i);
		}
	}
	
	private void initListeners()
	{
		_tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override public void valueChanged(TreeSelectionEvent e)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
					e.getNewLeadSelectionPath().getLastPathComponent();
				Object data = node.getUserObject();
				if (data instanceof Node)
				{
					_detail.setCurrentNode((Node) data);
				}
				else if (data instanceof String)
				{
					_detail.setCurrentTopic((String) data);
				}
				else
				{
					_detail.setCurrentNode(null);
				}
			}
		});
		// Listen to double-clicks
		_tree.addMouseListener(new MouseAdapter()
		{
			@Override public void mousePressed(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					_detail.launch();
				}
			}
		});
	}
	
	final private JSplitPane _split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	final private JTree _tree = new JTree();
	final private DetailPanel _detail = new DetailPanel();
}
