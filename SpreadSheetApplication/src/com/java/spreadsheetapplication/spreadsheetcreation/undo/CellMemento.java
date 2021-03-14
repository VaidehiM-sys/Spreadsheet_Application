package com.java.spreadsheetapplication.spreadsheetcreation.undo;

import java.util.ArrayList;
import java.util.List;

import com.java.spreadsheetapplication.spreadsheetcreation.spreadsheet.Cell;

public class CellMemento
{
	private String position;
	private double value = 0.0;
	private String formula = null;
	private boolean isCellDependent = false;
	private List<Cell> subjects = new ArrayList<Cell>();

	public CellMemento(String position, double value, String formula, List<Cell> subjects, boolean isCellDependent)
		{
			this.position = position;
			this.value = value;
			this.formula = formula;
			this.subjects = subjects;
			this.isCellDependent = isCellDependent;
		}

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

	public void setValue(double value)
		{
			this.value = value;
		}

	public String getFormula()
		{
			return formula;
		}

	public void setFormula(String formula)
		{
			this.formula = formula;
		}

	public List<Cell> getSubjects()
		{
			return subjects;
		}

	public void setSubjects(List<Cell> subjects)
		{

			this.subjects = subjects;
		}

	public boolean isCellDependent()
		{
			return isCellDependent;
		}

	public void setCellDependent(boolean isCellDependent)
		{
			this.isCellDependent = isCellDependent;
		}

}
