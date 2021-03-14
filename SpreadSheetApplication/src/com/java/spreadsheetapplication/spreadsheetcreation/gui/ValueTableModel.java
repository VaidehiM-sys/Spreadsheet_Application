package com.spreadsheetapplication.spreadsheetcreation.gui;

import com.java.spreadsheetapplication.spreadsheetcreation.cellstates.CellStateContext;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.Cell;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.SpreadSheet;

public class ValueTableModel extends CustomTableModel {

	private SpreadSheet spreadSheet;
	public ValueTableModel(SpreadSheet spreadSheet) {
		super(spreadSheet);
		this.spreadSheet=spreadSheet;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return (getSpreadSheet().getCell(columnIndex).isCellDependent()==false)?getSpreadSheet().getCell(columnIndex).getValue():"Error";
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		viewData[row][col] = value.toString();
		Cell cell = this.getSpreadSheet().getCell(col);
		cell.setPosition(getColumnName(col));
		CellStateContext context=new CellStateContext(cell, value,spreadSheet);
		context.setValue();
		fireTableCellUpdated(row, col);
	}
}
