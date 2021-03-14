package com.java.spreadsheetapplication.spreadsheetcreation.interpreter;
import java.util.Stack;

public class Evaluator {
	
	public PostFix parse(String text)
	{
		Stack<PostFix> stack=new Stack<PostFix>();
		
		String[] words=text.split(" ");
		for(String word:words)
		{
			PostFix lhs=null, rhs=null;
			switch(word)
			{
				case "+":
					rhs=stack.pop();
					lhs=stack.pop();
					stack.push(new Add(lhs,rhs));
				break;
				case "-":
					rhs=stack.pop();
					lhs=stack.pop();
					stack.push(new Substract(lhs,rhs));
				break;
				case "*":
					rhs=stack.pop();
					lhs=stack.pop();
					stack.push(new Multiply(lhs,rhs));
					break;
				case "/":
					rhs=stack.pop();
					lhs=stack.pop();
					stack.push(new Divide(lhs,rhs));
					break;
				case "sin":
					rhs=stack.pop();
					stack.push(new Sin(rhs));
					break;
				case "lg":
					rhs=stack.pop();
					stack.push(new Log(rhs));
					break;
				default:
					double number=Double.valueOf(word.trim());
					stack.push(new Number(number));
					break;
			}
		}
		return stack.pop();
	}

}
