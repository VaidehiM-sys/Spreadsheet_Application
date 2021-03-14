package com.java.spreadsheetapplication.spreadsheetcreation.interpreter;

public class Sin extends PostFix {


		private PostFix rhs;
		
		
		public Sin(PostFix rhs)
		{
			this.rhs=rhs;
			
		}

		@Override
		public double interpret() {
			
			return Math.sin(rhs.interpret());
		}
		
		
		
	}


