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

import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SmartVerticalResize4RealWorldExample extends AbstractDesignGridExample
{
	public static void main(String[] args)
	{
		SmartVerticalResize4RealWorldExample example = new SmartVerticalResize4RealWorldExample();
		example.go(true);
	}

	// CSOFF: MagicNumber
	@Override public void build(DesignGridLayout layout)
	{
		familyList.setVisibleRowCount(6);
		facesList.setVisibleRowCount(6);

		layout.row().grid(usageLabel).add(anyButton).add(textButton)
			.add(displayButton).add(humorButton);
		layout.row().grid(fontLabel).add(familyListScroller).add(facesListScroller);
		layout.emptyRow();
		layout.row().grid(sizeLabel).add(sizeSpinner).add(sizePointsLabel)
			.add(allCapsButton, 2).add(superscriptButton, 2).empty(2);
		layout.row().grid(leadingLabel).add(leaderSpinner).add(leadingPointsLabel)
			.add(smallCapsButton, 2).add(subscriptButton, 2).empty(2);
		layout.row().grid(kernLabel).add(kernSpinner).add(kernPointsLabel)
			.add(hightlightButton, 2).add(redlineButton, 2).empty(2);
		layout.emptyRow();
		layout.row().center().add(setFontButton).add(encodingButton).add(previewButton);
	}
	// CSON: MagicNumber

	static final private String[] FACE_NAMES =
	{
		"Book", "Regular", "Oblique", "Bold", "Bold-Oblique", "Black", "Apple", "Banana"
	};

	// CSOFF: MemberName
	final private JLabel usageLabel = new JLabel("Usage:");
	final private JRadioButton anyButton = new JRadioButton("Any");
	final private JRadioButton textButton = new JRadioButton("Text");
	final private JRadioButton displayButton = new JRadioButton("Display");
	final private JRadioButton humorButton = new JRadioButton("Humor");
	final private JLabel fontLabel = new JLabel("Font:");
	final private JLabel sizeLabel = new JLabel("Size:");
	final private JLabel sizePointsLabel = new JLabel("Pts");
	final private JLabel leadingLabel = new JLabel("Leading:");
	final private JLabel leadingPointsLabel = new JLabel("Pts");
	final private JLabel kernLabel = new JLabel("Kern:");
	final private JLabel kernPointsLabel = new JLabel("Pts");
	final private JButton allCapsButton = new JButton("All Caps");
	final private JButton superscriptButton = new JButton("Superscript");
	final private JButton smallCapsButton = new JButton("Small Caps");
	final private JButton subscriptButton = new JButton("Subscript");
	final private JButton hightlightButton = new JButton("Hilight");
	final private JButton redlineButton = new JButton("Redline");
	final private JButton setFontButton = new JButton("Set Font");
	final private JButton encodingButton = new JButton("Encoding...");
	final private JButton previewButton = new JButton("Preview...");
	final private JList familyList =
		new JList(GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getAvailableFontFamilyNames());
	final private JScrollPane familyListScroller = new JScrollPane(familyList);
	final private JList facesList = new JList(FACE_NAMES);
	final private JScrollPane facesListScroller = new JScrollPane(facesList);
	final private JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(24, 1, 96, 1));
	final private JSpinner leaderSpinner = new JSpinner(new SpinnerNumberModel(27, 1, 96, 1));
	final private JSpinner kernSpinner = new JSpinner(new SpinnerNumberModel(-12, -72, 72, 1));
	// CSON: MemberName
}
