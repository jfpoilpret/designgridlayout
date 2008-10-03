This directory is used by Issue5 sample class.
Issue5 sample shows DesignGridLayout with rows containing various kinds of
components with variable heights (e.g. JTable, JList, JSlider, JTextArea).

Issue5 allows the tester to select the Look & Feel (LAF) he wants to use for the 
tests.

In order to test Issue5 on a given LAF, you need to:

1. Copy the jar for this LAF in the plafs directory
2. Edit the plafs.txt file and add one line for each LAF you want to test:
the line must contains the full class name of the LAF to use.

When launching Issue5 class, then the first row has a combo with all LAFs listed
in plafs.txt; selecting one and clicking the "Combo-selected LAF" button will
dynamically change the Look & Feel of the sample dialog.

Clicking "System LAF" will choose the System default Look & Feel.
Clicking "Java LAF" will choose the Java cross-platform Look & Feel (ie "Metal"
in Java 5 & 6).
