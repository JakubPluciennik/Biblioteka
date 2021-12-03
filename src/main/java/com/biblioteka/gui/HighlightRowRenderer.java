package com.biblioteka.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HighlightRowRenderer extends DefaultTableCellRenderer {

  int highlightedRow = -1;

  Color defaultColor, highlightColor, selectionColor;

  public HighlightRowRenderer(Color defaultColor, Color highlightColor, Color selectionColor) {
    this.defaultColor = defaultColor;
    this.highlightColor = highlightColor;
    this.selectionColor = selectionColor;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                 boolean hasFocus, int row, int column) {
    Component comp =
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if (isSelected) {
      comp.setBackground(selectionColor);
    } else if (row == highlightedRow) {
      comp.setBackground(highlightColor);
    } else {
      comp.setBackground(defaultColor);
    }
    return comp;
  }
}