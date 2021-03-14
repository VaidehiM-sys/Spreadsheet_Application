package com.java.spreadsheetapplication.spreadsheetcreation.gui;

import java.awt.FlowLayout;
import java.util.Arrays;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.table.TableModel;

import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.Cell;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.SpreadSheet;

import com.java.cs635.assignment2.undo.CellMemento;

public class GUI
{

	public JFrame frame;
	public JPanel panel;

	public JToggleButton toggleButton;
	public JButton undoButton;
	public JScrollPane tableScrollPane;

	public GUI()
		{

			frame = new JFrame("SpreadSheet");
			panel = new JPanel(new FlowLayout());
			undoButton = new JButton("Undo");
			toggleButton = new JToggleButton("Toggle View");

		}

	public void initScrollPane(JTable table)
		{
			tableScrollPane = new JScrollPane(table);
			tableScrollPane.setBorder(BorderFactory.createTitledBorder("Value View"));
			tableScrollPane.setBounds(50, 50, 400, 100);
		}

	public void setFrameProperties()
		{
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800, 600);
		}

	public JTable initJTable(TableModel tableModel)
		{

			JTable table = new JTable(tableModel);
			return table;
		}

	public void setTableModelsProperties(CustomTableModel oTableModel, String[] headerList, String[][] viewData)
		{

			oTableModel.setColumnIdentifiers(headerList);
			oTableModel.setViewData(viewData);
		}

	public void setSpreadSheetTableComponents(String[] headerList, String[][] valueViewData, String[][] equationViewData,
			SpreadSheet spreadsheet)
		{

			char headerLetter = 'A';
			for (int i = 0; i < spreadsheet.numberOfColumns; i++)
				{

					headerList[i] = "$" + headerLetter;
					SpreadSheet.positionHeaderMap.put("$" + headerLetter, i);
					spreadsheet.getCell(i).setPosition("$" + headerLetter);
					headerLetter++;

					valueViewData[0][i] = String.valueOf(spreadsheet.getCell(i).getValue());
					equationViewData[0][i] = String.valueOf(spreadsheet.getCell(i).getValue());

				}

		}

	public static void main(String[] args)
		{
			SpreadSheet.numberOfColumns = 9;
			SpreadSheet spreadsheet = new SpreadSheet(SpreadSheet.numberOfColumns);
			GUI oGUI = new GUI();
			oGUI.setFrameProperties();
			String[] headerList = new String[SpreadSheet.numberOfColumns];
			String[][] valueViewData = new String[1][SpreadSheet.numberOfColumns];
			String[][] equationViewData = new String[1][SpreadSheet.numberOfColumns];
			oGUI.setSpreadSheetTableComponents(headerList, valueViewData, equationViewData, spreadsheet);
			CustomTableModel valueTableModel = new ValueTableModel(spreadsheet);
			CustomTableModel equationTableModel = new EquationTableModel(spreadsheet);

			oGUI.setTableModelsProperties(valueTableModel, headerList, valueViewData);
			oGUI.setTableModelsProperties(equationTableModel, headerList, valueViewData);
			JTable valueViewTable = oGUI.initJTable(valueTableModel);
			JTable equationViewTable = oGUI.initJTable(equationTableModel);
			oGUI.initScrollPane(valueViewTable);

			valueViewTable.getModel().addTableModelListener(new TableModelListener()
				{

					@Override
					public void tableChanged(TableModelEvent e)
						{
							if (e.getType() == TableModelEvent.UPDATE)
								{

									valueViewTable.repaint();
									equationViewTable.repaint();
									valueViewTable.getSelectionModel().clearSelection();
								}

						}
				});

			equationViewTable.getModel().addTableModelListener(new TableModelListener()
				{

					@SuppressWarnings("static-access")
					@Override
					public void tableChanged(TableModelEvent e)
						{
							if (e.getType() == TableModelEvent.UPDATE)
								{

									valueViewTable.repaint();
									equationViewTable.repaint();
									equationViewTable.getSelectionModel().clearSelection();
								}

						}
				});
			oGUI.undoButton.addActionListener(e ->
				{
					try
						{
							CellMemento OLastCellState = spreadsheet.cellCareTaker.getLastCellState();
							int position = Arrays.asList(headerList).indexOf((OLastCellState.getPosition()));
							Cell oCurrentCellState = spreadsheet.getCell(position);
							oCurrentCellState.undoFromMemento(OLastCellState);
							oCurrentCellState.hasChanged();
							oCurrentCellState.notifyObservers();

							valueViewTable.repaint();

							equationViewTable.repaint();
						} catch (Exception exception)
						{
							exception.getMessage();
						}

				});

			oGUI.toggleButton.addActionListener(e ->
				{
					if (oGUI.toggleButton.isSelected())
						{
							oGUI.tableScrollPane.setViewportView(equationViewTable);
							oGUI.tableScrollPane.setBorder(BorderFactory.createTitledBorder("Equation View"));

						} else
							{
								oGUI.tableScrollPane.setViewportView(valueViewTable);
								oGUI.tableScrollPane.setBorder(BorderFactory.createTitledBorder("Value View"));

							}
				});

			oGUI.panel.add(oGUI.toggleButton);
			oGUI.panel.add(oGUI.undoButton);
			oGUI.panel.add(oGUI.tableScrollPane);

			oGUI.frame.add(oGUI.panel);
			oGUI.frame.setVisible(true);

		}

}
