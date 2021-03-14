package com.java.spreadsheetapplication.spreadsheetcreation.interpreter;

import java.util.Hashtable;

public class InterpreterContext
	{
		private Hashtable<String,Double> interpreterContext= new Hashtable<String,Double>(); 
		
		public void SetContext(String variable, double value)
		{
			interpreterContext.put(variable, value);
			
		}
		
		public Hashtable<String,Double> getContext()
		{
			return interpreterContext;
		}
		
	}
