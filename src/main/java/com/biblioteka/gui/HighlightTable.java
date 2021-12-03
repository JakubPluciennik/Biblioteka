package com.biblioteka.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class HighlightTable extends JTable {

  HighlightRowRenderer cellRenderer;


  public HighlightTable(TableModel tableModel, Color defaultColor, Color selectionColor,
                        Color highlightColor) {
    super(tableModel);
    cellRenderer = new HighlightRowRenderer(defaultColor, selectionColor, highlightColor);

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        cellRenderer.highlightedRow = rowAtPoint(e.getPoint());
        updateUI();
      }
    });

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cellRenderer.highlightedRow = -1;
        updateUI();
      }
    });

    setFont(new Font("Sans", Font.PLAIN, 14));
    setBorder(new LineBorder(Color.black));
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  // brudny hack który powoduje że przy zaznaczaniu wierszy w tabeli ostatni z nich nie znika
  @Override
  public int getSelectedRow() {
    int row = super.getSelectedRow();
    if (row == -1) {
      return -2;
    }
    return row;
  }

  @Override
  public TableCellRenderer getCellRenderer(int row, int column) {
    return cellRenderer;
  }
}