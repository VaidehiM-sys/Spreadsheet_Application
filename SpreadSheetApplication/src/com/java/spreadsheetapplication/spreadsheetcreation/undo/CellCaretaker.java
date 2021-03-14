package com.java.spreadsheetapplication.spreadsheetcreation.undo;

import java.util.Stack;

public class CellCaretaker
{

	Stack<CellMemento> mementoStack = new Stack<CellMemento>();

	public CellMemento getLastCellState() throws Exception
		{
			if (mementoStack.size() < 1)
				throw new Exception("UnderFlow case: No more values to restore.");

			else
				{
					CellMemento cellMemento = mementoStack.pop();
					return cellMemento;
				}
		}

	public void addCellState(CellMemento memento)
		{
			mementoStack.push(memento);
		}

}
