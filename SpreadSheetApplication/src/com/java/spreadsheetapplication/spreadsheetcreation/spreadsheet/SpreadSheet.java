package com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.java.cs635.assignment2.circulardependency.Graph;
import com.java.cs635.assignment2.gui.GUI;
import com.java.cs635.assignment2.undo.CellCaretaker;
import com.java.cs635.assignment2.undo.CellMemento;

public class SpreadSheet {
	public static int numberOfColumns = 9;
	public static Graph oGraph;
	public static CellCaretaker cellCareTaker;
	private Cell[] cells;
	public static Map<String, Integer> positionHeaderMap;

	public SpreadSheet(int columnsCount) {
		oGraph = new Graph(numberOfColumns);
		numberOfColumns=columnsCount;
		cells=new Cell[columnsCount];
		cellCareTaker = new CellCaretaker();
		positionHeaderMap = new HashMap<String, Integer>();
		for(int i=0;i<columnsCount;i++)
			{

				cells[i]=new Cell();

			}

	}



	public Cell getCell(int col) {
		if (0 <= col &&  col < numberOfColumns) {
			return cells[col];
		}
		return null;
	}


	public void setCell(Cell oCell,int col) {
		cells[col]=oCell;
	}

	public String toString() {
		String result = "";

		for(int j = 0; j < numberOfColumns; j++) {
			result = result + cells[j] + '\t';
			result += '\n';
		}


		return result;
	}

	public void saveCellState(Cell cell)
		{
			CellMemento oCellMemento=cell.saveToMemento();
			SpreadSheet.cellCareTaker.addCellState(oCellMemento);	
		}

	public void restoreLastCellState() throws Exception
		{
			CellMemento OLastCellState = SpreadSheet.cellCareTaker.getLastCellState();
			int position = positionHeaderMap.get(OLastCellState.getPosition());
			Cell oCurrentCellState = this.getCell(position);
			oCurrentCellState.undoFromMemento(OLastCellState);
		}


	public void addSubjectsToCell(Cell subject,Cell cell)
		{
			cell.addSubject(subject);
		}

	public void addEgde(int source,int destination)
		{
			SpreadSheet.oGraph.addEdge(source, destination);
		}


	public boolean isCircularDependencyPresent()
		{
			return SpreadSheet.oGraph.isCyclic();
		}

	public boolean[] traceCircularReferences()
		{
			return SpreadSheet.oGraph.getRecursionStack();
		}

	public boolean traceCircularReferenceForACell(int position)
		{
			return SpreadSheet.oGraph.getRecursionStack()[position];

		}

	public void markCircularReference(Cell dependentCell)
		{
			dependentCell.setCellDependent(true);
		}

	public void updateAllCircularReferences()
		{
			SpreadSheet.oGraph.isCyclic();
			boolean[] recStack= this.traceCircularReferences();
			for(int i=0;i<SpreadSheet.positionHeaderMap.size();i+=1)
				{
					if(recStack[i]==true)
						cells[i].setCellDependent(true);
					else cells[i].setCellDependent(false);
					
				}
		}



}
