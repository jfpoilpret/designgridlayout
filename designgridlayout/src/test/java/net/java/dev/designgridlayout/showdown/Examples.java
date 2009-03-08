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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.java.dev.designgridlayout.AbstractBaseExample;
import net.java.dev.designgridlayout.basics.Basics1SimpleGrid;
import net.java.dev.designgridlayout.basics.Basics2GridColumns;
import net.java.dev.designgridlayout.basics.Basics3GridLabels;
import net.java.dev.designgridlayout.basics.Basics4RightRow;
import net.java.dev.designgridlayout.basics.Basics5NonGridRows;
import net.java.dev.designgridlayout.basics.Basics6NonGridRowsWithFiller;
import net.java.dev.designgridlayout.basics.Basics7RealWorldExample1;
import net.java.dev.designgridlayout.basics.Basics8RealWorldExample2;
import net.java.dev.designgridlayout.basics.Basics9RealWorldExample3;
import net.java.dev.designgridlayout.basics.Misc1CustomizedMargins;
import net.java.dev.designgridlayout.misc.Rfe29ConsistentBaselineSpace;
import net.java.dev.designgridlayout.misc.Rfe29ExactVGaps;
import net.java.dev.designgridlayout.multicomponent.MultiComponentExample;
import net.java.dev.designgridlayout.multigrid.AddressBookDemo;
import net.java.dev.designgridlayout.multigrid.MultiGrid1Simple;
import net.java.dev.designgridlayout.multigrid.MultiGrid2ThreeGridsWithGridSpan;
import net.java.dev.designgridlayout.multigrid.MultiGrid3ThreeComplexGrids;
import net.java.dev.designgridlayout.rowspan.RowSpan2TwoLists;
import net.java.dev.designgridlayout.rowspan.RowSpan3TwoListsCustomWeights;
import net.java.dev.designgridlayout.rowspan.RowSpan4ErrorMarkers;
import net.java.dev.designgridlayout.rowspan.RowSpan5SimplestExample;
import net.java.dev.designgridlayout.rowspan.RowSpan6SimpleExampleOnTwoGrids;
import net.java.dev.designgridlayout.rowspan.RowSpan7SpecialComponent;
import net.java.dev.designgridlayout.rtl.RightToLeft1LTR;
import net.java.dev.designgridlayout.rtl.RightToLeft2RTL;
import net.java.dev.designgridlayout.rtl.RightToLeft3RealWorldExample;
import net.java.dev.designgridlayout.sync.AbstractSyncLayoutExample;
import net.java.dev.designgridlayout.sync.BetterAddressBookDemo;
import net.java.dev.designgridlayout.verticalresize.SmartVerticalResize1Sliders;
import net.java.dev.designgridlayout.verticalresize.SmartVerticalResize3CustomWeights;
import net.java.dev.designgridlayout.verticalresize.SmartVerticalResize4RealWorldExample;
import net.java.dev.designgridlayout.verticalresize.SmartVerticalResize5SameWeight;

public class Examples extends JFrame
{
	public static void main(String[] args) throws Exception
	{
		// First ask for the LAF to use (modal dialog)
		SwingUtilities.invokeAndWait(new Runnable()
		{
			public void run()
			{
				LafChooserDialog dialog = new LafChooserDialog();
				dialog.setVisible(true);
			}
		});
		// Then show the Examples main frame
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
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
		_split.setLeftComponent(_tree);
		_split.setRightComponent(_detail);
		_split.setDividerLocation(_tree.getPreferredSize().width);
		
		setContentPane(_split);
	}

	private void initTree()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultMutableTreeNode node;

		root.add(new DefaultMutableTreeNode(
			new Node("Introduction", AbstractBaseExample.class, false)));

		node = new DefaultMutableTreeNode("Basic Examples");
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - 3 simple rows", Basics1SimpleGrid.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - column span, empty cells", Basics2GridColumns.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - labels", Basics3GridLabels.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Grids - right-centered row", Basics4RightRow.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Non-Grids - left, center, right, filler", Basics5NonGridRows.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Non-Grids - fillers", Basics6NonGridRowsWithFiller.class)));
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

		node = new DefaultMutableTreeNode("Right to Left Support");
		node.add(new DefaultMutableTreeNode(
			new Node("Example 1 - Left to Right", RightToLeft1LTR.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Example 1 - Right to Left", RightToLeft2RTL.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("AddressBookDemo - Right to Left", RightToLeft3RealWorldExample.class)));
		root.add(node);

		node = new DefaultMutableTreeNode(
			new Node("Layouts Synchronization", AbstractSyncLayoutExample.class, false));
		node.add(new DefaultMutableTreeNode(new Node(
			"Example 1 - Address Book Demo (revisited)", BetterAddressBookDemo.class)));
		//TODO more examples and test cases here!
		root.add(node);

		node = new DefaultMutableTreeNode("Miscellaneous");
		node.add(new DefaultMutableTreeNode(
			new Node("Custom Margins", Misc1CustomizedMargins.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Multi-Components", MultiComponentExample.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Exact Vertical Gaps", Rfe29ExactVGaps.class)));
		node.add(new DefaultMutableTreeNode(
			new Node("Consistent Baselines Spacing", Rfe29ConsistentBaselineSpace.class)));
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
			public void valueChanged(TreeSelectionEvent e)
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
