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

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public abstract class AbstractSyncLayoutExample extends AbstractBaseExample
{
	protected AbstractSyncLayoutExample(boolean vertical)
	{
		_vertical = vertical;
	}

	protected AbstractSyncLayoutExample()
	{
		this(true);
	}

	@Override protected void build(DesignGridLayout layout)
	{
	}

	@Override public void go(boolean exitOnClose)
    {
    	_frame = new JFrame(name());
    	_frame.setName(getClass().getSimpleName());
    
    	_frame.setDefaultCloseOperation(exitOnClose
    		? JFrame.EXIT_ON_CLOSE
    		: WindowConstants.DISPOSE_ON_CLOSE);
    	_top = createTopPanel();
    	_top.setName("TOP");
    	build();
    
    	_frame.add(_top);
    	prePack();
    	_frame.pack();
    	_frame.setLocationRelativeTo(null);
    	_frame.setVisible(true);
    }
	
	protected JComponent createTopPanel()
	{
    	JPanel top = new JPanel();
    	BoxLayout box = new BoxLayout(
    		top, _vertical ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
    	top.setLayout(box);
		return top;
	}

	protected DesignGridLayout createSubPanel(Border border, String position)
	{
    	JPanel panel = new JPanel();
    	DesignGridLayout layout = new DesignGridLayout(panel);
    	if (border != null)
    	{
    		panel.setBorder(border);
    	}
    	_top.add(panel, position);
    	return layout;
	}
	
	protected DesignGridLayout createSubPanel(Border border)
	{
		return createSubPanel(border, null);
	}
	
	protected DesignGridLayout createSubPanel(String position)
	{
    	return createSubPanel(null, position);
	}
	
	protected DesignGridLayout createSubPanel()
    {
		return createSubPanel(null, null);
    }
	
	@Override protected JTextField field(String text)
	{
		JTextField field = super.field(text);
		field.setColumns(3);
		field.setMinimumSize(field.getPreferredSize());
		field.setColumns(5);
		return field;
	}

	abstract protected void build();

	private JComponent _top;
	private final boolean _vertical;
}
