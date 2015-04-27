package com.snk.fundamentus.controller;

import javax.swing.table.AbstractTableModel;

public class TableStockModel extends AbstractTableModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String[] columnNames;
    private final Object[][] data;

    public TableStockModel(final Object[][] data, final String[] columnNames) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(final int col) {
        return columnNames[col];
    }

    public Object getValueAt(final int row, final int col) {
        return data[row][col];
    }

    public Class getColumnClass(final int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(final int row, final int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        }
        else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(final Object value, final int row, final int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
