package com.java.spreadsheetapplication.spreadsheetcreation.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.junit.jupiter.api.Test;

import com.java.spreadsheetapplication.spreadsheetcreation.gui.CustomTableModel;
import com.java.spreadsheetapplication.spreadsheetcreation.gui.EquationTableModel;
import com.java.spreadsheetapplication.spreadsheetcreation.gui.GUI;
import com.java.spreadsheetapplication.spreadsheetcreation.gui.ValueTableModel;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.SpreadSheet;

class TestAdder
{

	@Test()
	public void testInterpreterUsingPostFix()
		{
			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);

			valueTableModel.setValueAt("1", 0, 0);
			valueTableModel.setValueAt("1", 0, 1);
			equationTableModel.setValueAt("$A $B +", 0, 2);
			equationTableModel.setValueAt("$A $B -", 0, 3);
			equationTableModel.setValueAt("$A $B *", 0, 4);
			equationTableModel.setValueAt("$A $B * sin", 0, 5);
			equationTableModel.setValueAt("$A $B * lg", 0, 6);
			assertEquals(2, spreadsheet.getCell(2).getValue());
			assertEquals(0, spreadsheet.getCell(3).getValue());
			assertEquals(1, spreadsheet.getCell(4).getValue());

		}

	@Test()
	public void verifyUpdatdValuesInObservers()
		{
			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			valueTableModel.setValueAt("1", 0, 0);
			valueTableModel.setValueAt("1", 0, 1);
			equationTableModel.setValueAt("$A $B +", 0, 2);
			assertEquals(2, spreadsheet.getCell(2).getValue());
			valueTableModel.setValueAt("5", 0, 1);
			assertEquals(6, spreadsheet.getCell(2).getValue());
		}

	@Test()
	public void testUndoOperationForTheLastUpdatedValueInACell()
		{
			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			valueTableModel.setValueAt("1", 0, 0);
			valueTableModel.setValueAt("5", 0, 0);

			try
				{
					spreadsheet.restoreLastCellState();
				} catch (Exception e)
				{

					e.printStackTrace();
				}

			assertEquals(1.0, valueTableModel.getValueAt(0, 0));

		}

	@Test
	public void testCircularReferenceDetection()
		{
			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			valueTableModel.setValueAt("1", 0, 0);
			equationTableModel.setValueAt("$A $C +", 0, 1);
			equationTableModel.setValueAt("$D 1 +", 0, 2);
			equationTableModel.setValueAt("$B 2 *", 0, 3);

			assertEquals("Error", valueTableModel.getValueAt(0, 1));
			assertEquals("Error", valueTableModel.getValueAt(0, 2));
			assertEquals("Error", valueTableModel.getValueAt(0, 3));

		}

	@Test
	public void testUndoWithNoMementoObjectInCareTaker()
		{
			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			valueTableModel.setValueAt("1", 0, 0);
			valueTableModel.setValueAt("2", 0, 0);

			try
				{
					spreadsheet.restoreLastCellState();

					spreadsheet.restoreLastCellState();
					spreadsheet.restoreLastCellState();
				} catch (Exception e)
				{
					assertEquals("UnderFlow case: No more values to restore.", e.getMessage());
				}

		}

	@Test
	public void testErrorStateRemovalForSingleDependency()
		{

			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			JTable valueViewTable = oGUI.initJTable(valueTableModel);
			JTable equationViewTable = oGUI.initJTable(equationTableModel);
			oGUI.initScrollPane(valueViewTable);

			valueTableModel.setValueAt("1", 0, 0);
			equationTableModel.setValueAt("$B", 0, 1);

			equationTableModel.setValueAt("$A", 0, 1);

			assertEquals(1.0, spreadsheet.getCell(1).getValue());

		}

	@Test
	public void testErrorStateRemovalForMultipleDependentColumnsByValue()
		{

			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			JTable valueViewTable = oGUI.initJTable(valueTableModel);
			JTable equationViewTable = oGUI.initJTable(equationTableModel);
			oGUI.initScrollPane(valueViewTable);

			valueTableModel.setValueAt("1", 0, 0);
			equationTableModel.setValueAt("$A $C +", 0, 1);
			equationTableModel.setValueAt("$D 1 +", 0, 2);
			equationTableModel.setValueAt("$B 2 *", 0, 3);

			valueTableModel.setValueAt("1", 0, 1);
			assertEquals(1.0, spreadsheet.getCell(1).getValue());
			assertEquals(3.0, spreadsheet.getCell(2).getValue());
			assertEquals(2.0, spreadsheet.getCell(3).getValue());
		}

	@Test
	public void testErrorStateRemovalForMultipleDependentColumnsByFormula()
		{

			int numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[numberOfColumns];
			String[][] valueViewData = new String[1][numberOfColumns];
			String[][] equationViewData = new String[1][numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			JTable valueViewTable = oGUI.initJTable(valueTableModel);
			JTable equationViewTable = oGUI.initJTable(equationTableModel);
			oGUI.initScrollPane(valueViewTable);

			valueTableModel.setValueAt("1", 0, 0);
			equationTableModel.setValueAt("$A $C +", 0, 1);
			equationTableModel.setValueAt("$D 1 +", 0, 2);
			equationTableModel.setValueAt("$B 2 *", 0, 3);

			equationTableModel.setValueAt("$A", 0, 1);
			assertEquals(1.0, spreadsheet.getCell(1).getValue());
			assertEquals(3.0, spreadsheet.getCell(2).getValue());
			assertEquals(2.0, spreadsheet.getCell(3).getValue());
		}

}
