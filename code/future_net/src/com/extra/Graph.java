package com.extra;
import java.util.*;
import java.util.ArrayList;

public class Graph {

	private HashMap<String, Vertices> verticesMap;
	private List<Edge> edgeList;

	public Graph (List<Edge> edgeList, HashMap<String, Vertices> verticesMap) {
		this.verticesMap = verticesMap;
		this.edgeList = edgeList;
	}

	public Graph(){

	}

	// Setters and getters

	public void setVerticesMap(HashMap<String, Vertices> verticesMap) {
		this.verticesMap = verticesMap;
	}

	public void setEdgeList(List<Edge> edgeList ) {
		this.edgeList = edgeList;
	}

	public HashMap<String, Vertices> getVerticesMap () {
		return verticesMap;
	}

	public List<Edge> getEdgeList () {
		return edgeList;
	}

	public Vertices findVertice(String id) {
		return verticesMap.get(id);
	}

	public Edge findEdge(String source, String target) {
		for (int i = 0; i<edgeList.size(); i++) {
			String s = edgeList.get(i).getSource();
			String t = edgeList.get(i).getDestination();
			if (s.equals(source) && t.equals(target)) {
				return edgeList.get(i);
			}
		}
		return null;
	}

}