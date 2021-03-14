package com.java.spreadsheetapplication.spreadsheetcreation.gui;

import java.util.Arrays;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import com.java.spreadsheetapplication.spreadsheetcreation.cellstates.CellStateContext;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.Cell;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.SpreadSheet;
import com.java.spreadsheetapplication.spreadsheetcreation.undo.CellMemento;

public class EquationTableModel extends CustomTableModel
{

	private SpreadSheet spreadSheet;

	public EquationTableModel(SpreadSheet spreadSheet)
		{
			super(spreadSheet);
			this.spreadSheet = spreadSheet;

		}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
		{


			return this.getSpreadSheet().getCell(columnIndex).getFormula() == null
					? String.valueOf(this.getSpreadSheet().getCell(columnIndex).getValue())
							: String.valueOf(this.getSpreadSheet().getCell(columnIndex).getFormula());
		}

	@Override
	public void setValueAt(Object value, int row, int col)
		{

			viewData[row][col] = value.toString();
			Cell cell = this.getSpreadSheet().getCell(col);

			cell.setPosition(getColumnName(col));
			CellStateContext context = new CellStateContext(cell, value, spreadSheet);
			context.setValue();

			fireTableCellUpdated(row, col);

		}

}
