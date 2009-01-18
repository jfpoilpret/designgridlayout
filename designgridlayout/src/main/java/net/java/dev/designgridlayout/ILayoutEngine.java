package net.java.dev.designgridlayout;

import java.awt.Dimension;

// The interface implemented by all layout engines, those which actually
// perform all the work
interface ILayoutEngine
{
	public void margins(double top, double left, double bottom, double right);
	public void forceConsistentVGaps();

	public Dimension minimumLayoutSize();
	public Dimension preferredLayoutSize();
	public void layoutContainer();
}
