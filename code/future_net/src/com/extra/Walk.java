package com.extra;
import java.util.*;

public class  Walk {
	
	private Vertices frontier;
	private Edge frontierEdge;
	private List<String> historyVertices;
	private List<Edge> historyEdge;
	private int length;
	private int counter = 0;// counts the number of must passing vertices that has been visited

	// Constructor
	public Walk(Vertices frontier,Edge frontierEdge,List<String> historyVertices,List<Edge> historyEdge,int length){
		this.frontier = frontier;
		this.frontierEdge = frontierEdge;
		this.historyVertices = historyVertices;
		this.historyEdge = historyEdge;
		this.length = length;
	}

	public Walk(){

	}

	// test function
	public void printWalk() {// print everything on the walk
		for (int i = 0;i <  historyVertices.size();i++ ) {
			System.out.print("|"+historyVertices.get(i));
		}
		System.out.println(" ");
		for (int i = 0;i <  historyEdge.size();i++ ) {
			System.out.print("|"+historyEdge.get(i).getId());
		}
		System.out.println("Frontier:"+frontier.getId()+" length:"+length+" counter:"+counter);
	}

	// Setters and getters
	public void setFrontier(Vertices frontier) {
		this.frontier = frontier;
	}

	public void setFrontierEdge(Edge frontierEdge){
		this.frontierEdge = frontierEdge;
	}

	public void setHistoryEdge(List<Edge> historyEdge){
		this.historyEdge = historyEdge;
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

	public Edge getFrontierEdge() {
		return frontierEdge;
	}

	public int getLength() {
		return length;
	}

	public List<String> getHistoryVertices() {
		return historyVertices;
	}

	public List<Edge> getHistoryEdge() {
		return historyEdge;
	}

	public int getCounter() {
		return counter;
	}
}