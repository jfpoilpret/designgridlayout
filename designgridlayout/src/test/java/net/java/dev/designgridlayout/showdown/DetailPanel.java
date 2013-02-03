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

package net.java.dev.designgridlayout.showdown;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.java.dev.designgridlayout.DesignGridLayout;

@SuppressWarnings("serial")
class DetailPanel extends JPanel
{
	static final private int DESCRIPTION_ROWS = 13;
	static final private int SOURCE_FONT_SIZE = 12;
	static final private int SOURCE_TAB_SIZE = 4;
	static final private int SOURCE_ROWS = 20;
	static final private int SOURCE_COLUMNS = 96;
	
	public DetailPanel()
	{
		// Setup components
		_txpDescription.setFont(_lblDescription.getFont());
		_txpDescription.setEditable(false);
		_txpDescription.setLineWrap(true);
		_txpDescription.setWrapStyleWord(true);
		_txpDescription.setRows(DESCRIPTION_ROWS);
		
		JScrollPane descScroller = new JScrollPane(_txpDescription);
		descScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		_txpSource.setFont(new Font("Monospaced", Font.PLAIN, SOURCE_FONT_SIZE));
		_txpSource.setEditable(false);
		_txpSource.setLineWrap(false);
		_txpSource.setTabSize(SOURCE_TAB_SIZE);
		_txpSource.setColumns(SOURCE_COLUMNS);
		_txpSource.setRows(SOURCE_ROWS);
		
		JScrollPane sourceScroller = new JScrollPane(_txpSource);
		sourceScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sourceScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Layout
		DesignGridLayout layout = new DesignGridLayout(this);
		layout.row().grid(_lblDescription).add(descScroller);
		layout.emptyRow();
		layout.row().center().add(_btnShow);
		layout.emptyRow();
		layout.row().grid(_lblSource).add(sourceScroller);

		// Actions on Buttons
		_btnShow.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				launch();
			}
		});
	}
	
	public void launch()
	{
		if (_currentNode != null)
		{
			_currentNode.launch();
		}
	}
	
	public void setCurrentNode(Node node)
	{
		_currentNode = node;
		if (_currentNode != null)
		{
			_currentNode.showDescription(_txpDescription);
			_currentNode.showSource(_txpSource);
			_btnShow.setEnabled(_currentNode.isLaunchable());
		}
		else
		{
			_txpDescription.setText("");
			_txpSource.setText("");
			_btnShow.setEnabled(false);
		}
	}
	
	public void setCurrentTopic(String topic)
	{
		setCurrentNode(null);
		String name = topic + ".desc.txt";
		ReaderHelper.readText(name, _txpDescription);
	}
	
	final private JLabel _lblDescription = new JLabel("Description:");
	final private JTextArea _txpDescription = new JTextArea();
	final private JButton _btnShow = new JButton("Show Result...");
	final private JLabel _lblSource = new JLabel("Source:");
	final private JTextArea _txpSource = new JTextArea();
	
	private Node _currentNode;
}
