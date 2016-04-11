package com.extra;
import java.util.*;

public class Edge {
	
	private String id;
	private String source;
	private String destination;
	private int weight;

	// Constructor
	public Edge(String id, String source , String destination, int weight){
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	// Setters and getters

	public void setId( String id) {
		this.id = id;
	}

	public void setSource( String source) {
		this.source = source;
	}

	public void setDestination( String destination) {
		this.destination = destination;
	}

	public void setWeight( int weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public String getSource() {
		return source;
	}
	
	public String getDestination(){
		return destination;
	}

	public int getWeight() {
		return weight;
	}
}