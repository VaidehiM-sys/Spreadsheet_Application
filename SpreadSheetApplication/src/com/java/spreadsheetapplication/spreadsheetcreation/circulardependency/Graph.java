package com.java.spreadsheetapplication.spreadsheetcreation.circulardependency;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	private final int noOfNodes; 
	private final List<List<Integer>> adjacencyList; 
	boolean[] visited;
	boolean[] recursionStack ;

	public Graph(int noOfNodes)  
		{ 
			this.noOfNodes = noOfNodes; 
			adjacencyList = new ArrayList<>(noOfNodes); 

			for (int i = 0; i < noOfNodes; i++) 
				adjacencyList.add(new LinkedList<>()); 
		}

	public void initEdge(int i)
		{
			this.adjacencyList.get(i).clear();
		}


	public boolean[] getVisited() {
		return visited;
	}


	public void setVisited(boolean[] visited) {
		this.visited = visited;
	}


	public boolean[] getRecursionStack() {
		return recursionStack;
	}


	public void setRecursionStack(boolean[] recStack) {
		this.recursionStack = recStack;
	}


	public boolean isCyclicUtil(int i, boolean[] visited, 
			boolean[] recStack)  
		{ 
			// Mark the current node as visited and
			// part of recursion stack 
			if (recStack[i]) 
				return true; 

			if (visited[i]) 
				return false; 

			visited[i] = true; 

			recStack[i] = true; 
			List<Integer> children = adjacencyList.get(i); 

			for (Integer c: children) 
				if (isCyclicUtil(c, visited, recStack)) 
					{

						return true; 
					}     
			recStack[i] = false; 

			return false; 
		} 

	public void addEdge(int source, int dest) { 
		adjacencyList.get(source).add(dest); 
	} 

	// Returns true if the graph contains a  
	// cycle, else false. 

	public boolean isCyclic()  
		{ 
			visited = new boolean[noOfNodes];
			recursionStack = new boolean[noOfNodes]; 

			for (int i = 0; i < noOfNodes; i++) 
				{
					if (isCyclicUtil(i, visited, recursionStack)) 
						return true; 
				}
			return false; 
		} 


}
