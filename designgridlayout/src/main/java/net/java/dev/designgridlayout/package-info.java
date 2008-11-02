//  Copyright 2008 Jason Aaron Osgood, Jean-Francois Poilpret
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

/**
 * {@code DesignGridLayout} is a revolutionary {@link java.awt.LayoutManager} for
 * Swing applications, based on "canonical grids", well-known in publishing.
 * <p/>
 * DesignGridLayout allows to define dialogs that are always visually balanced, 
 * without ever needing any graphical designer, thanks to its fluent and 
 * easy-to-use API, which enables you to literally "visualize" the layout in the 
 * code itself.
 * <p/>
 * Using DesignGridLayout is straightforward:
 * <pre>
 * public class MyPanel extends JPanel {
 *     public MyPanel() {
 *         DesignGridLayout layout = new DesignGridLayout(this);
 *         
 *         layout.row().grid(labelA).add(fieldA);
 *         layout.row().grid(labelB).add(fieldB);
 *         //...
 *         layout.row().center().add(okButton, cancelButton);
 *     }
 *     
 *     private JLabel labelA = new JLabel("aaa");
 *     private JTextField fieldA = new JTextField();
 *     private JLabel labelB = new JLabel("bbb");
 *     private JTextField fieldB = new JTextField();
 *     //...
 *     private JButton okButton = new JButton("OK");
 *     private JButton cancelButton = new JButton("Cancel");
 * }
 * </pre>
 * As you can see in this example, each row of components in the panel can be 
 * set through one single line of source code. Each component is added from left 
 * to right.
 * <p/>
 * Labels (when created with 
 * {@link net.java.dev.designgridlayout.ISubGridStarter#grid(javax.swing.JLabel)})
 * have a special treatment: they are automatically right-aligned and the 
 * label column in the panel has constant width.
 * <p/>
 * All gaps between components and between components and panel borders are
 * automatically calculated by DesignGridLayout according to the current 
 * installed LookAndFeel: no need to hardcode anything!
 * <p/>
 * DesignGridLayout offers 5 types of rows:
 * <ul>
 *  <li>Grid Row: all components added to this kind of row are visually balanced
 *  	according to other grid rows, and follow best practices applied in
 *  	publishing on how to split the panel among "columns" of components</li>
 *  <li>Center Row: all components in this row are centered, they are never sized
 *  	bigger than their preferred size (except when {@code fill()} is
 *  	used (see below))</li>
 *  <li>Left Row: all components in this row are aligned on the left, they are
 *  	never sized bigger than their preferred size (except when {@code fill()} 
 *  	is used (see below))</li>
 *  <li>Right Row: all components in this row are aligned on the right, they are
 *  	never sized bigger than their preferred size (except when {@code fill()}
 *  	is used (see below))</li>
 *  <li>Empty Row: this row has no component but has a fixed height (automatically
 *  	calculated to "visually separate" two groups of components). This is 
 *  	useful when you want to add some space between groups of rows.</li>
 * </ul>
 * Center, Left and Right Rows have a special "fill" option that allows their 
 * extreme component(s) (rightmost component for a Left Row, leftmost component 
 * for a Right Row, and both leftmost and rightmost components for a Center Row) 
 * fill all the extra space. This can be useful for instance when you want to 
 * visually separate groups of rows:
 * <pre>
 *     layout.row().left().fill().add(new JLabel("Group"), new JSeparator());
 * </pre>
 * <p/>
 * DesignGridLayout allows you to add empty rows with a height that is 
 * automatically calculated (depending on the current installed Look &amp; Feel),
 * in order to introduce some space in your layout (e.g. to separate different 
 * groups of logically releated items:
 * <pre>
 *     layout.row().grid().add(...);
 *     layout.emptyRow();
 *     layout.row().right().add(new JButton("OK"), new JButton("Cancel"));
 * </pre>
 * In grid rows (added by calling {@code DesignGridLayout.row().grid()}), you 
 * may specify that a given component spans several columns, this way, you can 
 * ensure that fields that require longer input occupy enough space to render 
 * this input, compared with shorter fields):
 * <pre>
 *     layout.row().grid().add(new JTextField(), new JTextField());
 *     layout.row().grid().add(new JTextField(), 2).add(new JTextField());
 *     layout.row().grid().add(new JTextField()).empty();
 * </pre>
 * In this snippet, the first row has two short text fields on two columns (one
 * per field); the second row has one long text field (on two columns) and one 
 * short text field (on one column).
 * <p/>
 * For any Grid Row, the number of columns is normally defined by the number of
 * added components in the row, components being counted as many times as their
 * associated span (when explicitly specified). Note however, that you can also
 * introduce empty columns in such a row:
 * <pre>
 *     layout.row().grid().empty().add(new JTextField()).empty(2);
 * </pre>
 * This code creates a row with four columns, but only the second contains a 
 * real component.
 * <p/>
 * TODO additional comments on canonical sub-grids
 * 
 * @author Jason Aaron Osgood
 * @author Jean-Francois Poilpret
 * @see net.java.dev.designgridlayout.DesignGridLayout
 */
package net.java.dev.designgridlayout;

