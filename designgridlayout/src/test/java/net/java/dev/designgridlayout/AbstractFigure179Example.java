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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.java.dev.designgridlayout.AbstractBaseExample;

abstract public class AbstractFigure179Example extends AbstractBaseExample
{
	static final private String[] FACE_NAMES =
	{
		"Book", "Regular", "Oblique", "Bold", "Bold-Oblique", "Black", "Apple", "Banana"
	};

	static final private String[] FAMILY_NAMES =
	{
		"Agency FB", "Aharoni", "Algerian", "Andalus", "Angsana New", "AngsaneUPC", 
		"Arabic Transparent", "Arial", "Arial Black", "Arial Narrow"
	};

	// CSOFF: MemberName
	final protected JLabel usageLabel = new JLabel("Usage:");
	final protected JRadioButton anyButton = new JRadioButton("Any");
	final protected JRadioButton textButton = new JRadioButton("Text");
	final protected JRadioButton displayButton = new JRadioButton("Display");
	final protected JRadioButton humorButton = new JRadioButton("Humor");
	final protected JLabel fontLabel = new JLabel("Font:");
	final protected JLabel sizeLabel = new JLabel("Size:");
	final protected JLabel sizePointsLabel = new JLabel("Pts");
	final protected JLabel leadingLabel = new JLabel("Leading:");
	final protected JLabel leadingPointsLabel = new JLabel("Pts");
	final protected JLabel kernLabel = new JLabel("Kern:");
	final protected JLabel kernPointsLabel = new JLabel("Pts");
	final protected JButton allCapsButton = new JButton("All Caps");
	final protected JButton superscriptButton = new JButton("Superscript");
	final protected JButton smallCapsButton = new JButton("Small Caps");
	final protected JButton subscriptButton = new JButton("Subscript");
	final protected JButton hightlightButton = new JButton("Hilight");
	final protected JButton redlineButton = new JButton("Redline");
	final protected JButton setFontButton = new JButton("Set Font");
	final protected JButton encodingButton = new JButton("Encoding...");
	final protected JButton previewButton = new JButton("Preview...");
	final protected JList familyList = new JList(FAMILY_NAMES);
	final protected JScrollPane familyListScroller = new JScrollPane(familyList);
	final protected JList facesList = new JList(FACE_NAMES);
	final protected JScrollPane facesListScroller = new JScrollPane(facesList);
	final protected JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(24, 1, 96, 1));
	final protected JSpinner leaderSpinner = new JSpinner(new SpinnerNumberModel(27, 1, 96, 1));
	final protected JSpinner kernSpinner = new JSpinner(new SpinnerNumberModel(-12, -72, 72, 1));
	// CSON: MemberName
}
