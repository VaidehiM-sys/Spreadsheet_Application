package com.java.cs635.assignment2.cellstates;


import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.Cell;
import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.SpreadSheet;

public class EquationState implements CellState
{
	@Override
	public void setValue(CellStateContext context)
		{
			/*
			 * We go with the assumption that cell is not dependent on any other cell We unsubscribe it
			 * from all it's subjects,since a fresh formula is being set and it
			 * shouldn't be receiving any updates. We parse the formula and add the
			 * edges(Ex:1->{0,3}] B is dependent on A and D. At the same time, We also add
			 * new subjects to the cell coming from new formula; we then check for edges
			 * whether they are referring to any existing edge or it's own and forming a
			 * cycle.
			 */

			if (context.getValue().toString().contains("$"))
				{
					Cell cell = context.getCell();
					if (context.getValue().toString().contains("$"))
						{
							context.getSpreadSheet().saveCellState(cell);
							cell.deleteObserver();
							SpreadSheet.oGraph.initEdge(SpreadSheet.positionHeaderMap.get(cell.getPosition()));
							cell.setFormula(context.getValue().toString());

							String[] elementsList = context.getValue().toString().split(" ");
							for (String element : elementsList)
								{
									if (SpreadSheet.positionHeaderMap.containsKey(element.trim()))
										{
											int destination = SpreadSheet.positionHeaderMap.get(element);
											int source = SpreadSheet.positionHeaderMap.get(cell.getPosition());
											Cell subject = context.getSpreadSheet().getCell(destination);

											context.getSpreadSheet().addEgde(source, destination);
											context.getSpreadSheet().addSubjectsToCell(subject, cell);

										}

								}

							if (!context.getSpreadSheet().isCircularDependencyPresent())
								cell.setValue();
							else
								{

									context.getSpreadSheet().updateAllCircularReferences();
								}
						}

				} else
					{
						context.setState(new ValueState());
						context.setValue();
					}

		}
}