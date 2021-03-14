package com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.java.spreadsheetapplication.spreadsheetcreation.Evaluator;
import com.java.spreadsheetapplication.spreadsheetcreation.interpreter.PostFix;
import com.java.spreadsheetapplication.spreadsheetcreation.undo.CellMemento;

public class Cell extends Observable implements Observer
{
	private String position = null;
	private double value = 0.0;
	private String formula = null;
	private List<Cell> subjects = new ArrayList<Cell>();

	private boolean isCellDependent = false;

	public String getPosition()
		{
			return position;
		}

	public void setPosition(String position)
		{
			this.position = position;
		}

	public double getValue()
		{
			return value;
		}

	public void setValue(double val)
		{
			value = val;

			setChanged();
			notifyObservers();
		}

	public boolean isCellDependent()
		{
			return isCellDependent;
		}

	public void setCellDependent(boolean isCellDependent)
		{
			this.isCellDependent = isCellDependent;
		}

	public void setValue()
		{
			if (formula != null && !subjects.isEmpty())
				{

					setValue(eval());
				}
		}

	public String getFormula()
		{
			return formula;
		}

	public void setFormula(String formula)
		{
			this.formula = formula;
			// setValue(eval());

		}

	public List<Cell> getSubjects()
		{
			return subjects;
		}

	public void setSubjects(List<Cell> subjects)
		{
			this.subjects = subjects;
		}

	@Override
	public void update(Observable o, Object arg)
		{
			setValue();

		}

	public void addSubject(Cell subject)
		{
			subject.addObserver(this);
			this.subjects.add(subject);
		}

	public void deleteObserver()
		{
			for (Cell subject : this.subjects)
				{
					subject.deleteObserver(this);

				}
			this.subjects.clear();

		}

	public String toString()
		{
			return "" + value;
		}

	public int countObservers()
		{
			return subjects.size();
		}

	public CellMemento saveToMemento()
		{
			CellMemento oCellMemento = new CellMemento(this.position, this.value, this.formula, this.subjects,
					this.isCellDependent);
			return oCellMemento;
		}

	public void undoFromMemento(CellMemento memento)
		{
			this.position = memento.getPosition();
			this.formula = memento.getFormula();

			// this.value=memento.getValue();
			this.subjects = memento.getSubjects();
			this.isCellDependent = memento.isCellDependent();
			this.setValue(memento.getValue());

		}

	private double eval()
		{
			
			String parsedexpression = formula;

			for (int i = 0; i < subjects.size(); i++)
				{
					double value = subjects.get(i).getValue();
					parsedexpression = parsedexpression.replace(subjects.get(i).getPosition(),
							String.valueOf(value));

				}
			PostFix lastNumber = new Evaluator().parse(parsedexpression);
			
			
			return lastNumber.interpret();
		}

}
