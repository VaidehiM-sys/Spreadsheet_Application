package com.java.spreadsheetapplication.spreadsheetcreation.interpreter;

public class Add extends PostFix {

	private PostFix lhs;
	private PostFix rhs;
	
	public Add(PostFix lhs,PostFix rhs)
	{
		this.lhs=lhs;
		this.rhs=rhs;
	}

	@Override
	public double interpret() {
		
		return lhs.interpret()+rhs.interpret();
	}
	
	
	
}
