package net.java.dev.designgridlayout;

import javax.swing.BoxLayout;
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
    	_top = new JPanel();
    	BoxLayout box = 
    		new BoxLayout(_top, _vertical ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
    	_top.setLayout(box);
    	_top.setName("TOP");
    	build();
    
    	_frame.add(_top);
    	prePack();
    	_frame.pack();
    	_frame.setLocationRelativeTo(null);
    	_frame.setVisible(true);
    }

	protected DesignGridLayout createSubPanel(Border border)
	{
    	JPanel panel = new JPanel();
    	DesignGridLayout layout = new DesignGridLayout(panel);
    	if (border != null)
    	{
    		panel.setBorder(border);
    	}
    	_top.add(panel);
    	return layout;
	}
	
	protected DesignGridLayout createSubPanel()
    {
		return createSubPanel(null);
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

	private JPanel _top;
	private final boolean _vertical;
}
