package com.extra;
import java.util.*;

public class  Walk {
	
	private Vertices frontier;
	private List<String> historyVertices;
	private int length;
	private int counter = 0;// counts the number of must passing vertices that has been visited

	// Constructor
	public Walk(Vertices frontier,List<String> historyVertices,int length){
		this.frontier = frontier;
		this.historyVertices = historyVertices;
		this.length = length;
	}

	public Walk(){

	}

	public void printWalk() {
		for (int i = 0;i <  historyVertices.size();i++ ) {
			System.out.print("|"+historyVertices.get(i));
		}
		System.out.println("Frontier:"+frontier.getId()+" length:"+length+" counter:"+counter);
	}

	// Setters and getters
	public void setFrontier(Vertices frontier) {
		this.frontier = frontier;
	}
	
	public void setHistoryVertices(List<String> historyVertices) {
		this.historyVertices = historyVertices;
	}

	public void setLength(int length){
		this.length = length;
	}

	public void setCounter(int counter){
		this.counter = counter;
	}

	public Vertices getFrontier() {
		return frontier;
	}

	public int getLength() {
		return length;
	}

	public List<String> getHistoryVertices() {
		return historyVertices;
	}

	public int getCounter() {
		return counter;
	}
}