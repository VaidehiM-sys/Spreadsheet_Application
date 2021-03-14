package com.java.spreadsheetapplication.spreadsheetcreation.gui;

import javax.swing.table.AbstractTableModel;

import com.java.spreadsheetapplication.spreadsheetcreation.cellstates.CellStateContext;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.Cell;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.SpreadSheet;

public abstract class CustomTableModel extends AbstractTableModel
{
	protected String[] headerList;
	protected String[][] viewData;
	SpreadSheet spreadSheet;

	public CustomTableModel(SpreadSheet spreadSheet)
		{
			this.spreadSheet = spreadSheet;

		}

	@Override
	public String getColumnName(int column)
		{
			return headerList[column];
		}

	public void setColumnIdentifiers(String[] headerList)
		{
			this.headerList = headerList;
		}

	public String[][] getViewData()
		{
			return viewData;
		}

	public SpreadSheet getSpreadSheet()
		{
			return spreadSheet;
		}

	public void setSpreadSheet(SpreadSheet spreadSheet)
		{
			this.spreadSheet = spreadSheet;
		}

	public void setViewData(String[][] equationViewData)
		{
			this.viewData = equationViewData;
		}

	@Override
	public int getColumnCount()
		{
			return headerList.length;
		}

	@Override
	public int getRowCount()
		{

			return 1;
		}

	@Override
	public abstract Object getValueAt(int rowIndex, int columnIndex);

	@Override
	public boolean isCellEditable(int row, int col)
		{

			return true;
		}

	@Override
	public abstract void setValueAt(Object value, int row, int col);

}
