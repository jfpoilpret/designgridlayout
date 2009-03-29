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

package net.java.dev.designgridlayout.multicomponent;

import net.java.dev.designgridlayout.AbstractFigure179Example;
import net.java.dev.designgridlayout.DesignGridLayout;

public class Figure179bis extends AbstractFigure179Example
{
	public static void main(String[] args)
	{
		Figure179bis example = new Figure179bis();
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
		layout.row().grid(sizeLabel).addMulti(2, sizeSpinner, sizePointsLabel)
			.add(allCapsButton, 2).add(superscriptButton, 2).empty(2);
		layout.row().grid(leadingLabel).addMulti(2, leaderSpinner, leadingPointsLabel)
			.add(smallCapsButton, 2).add(subscriptButton, 2).empty(2);
		layout.row().grid(kernLabel).addMulti(2, kernSpinner, kernPointsLabel)
			.add(hightlightButton, 2).add(redlineButton, 2).empty(2);
		layout.emptyRow();
		layout.row().center().add(setFontButton).add(encodingButton).add(previewButton);
	}
	// CSON: MagicNumber
}
