package com.extra;
import java.util.*;

public class Vertices {
	
	private String id;
	private List<Edge> outDegreeList;
	private boolean isVisited = false;

	// Setters and getters
	public void setId(String id ) {
		this.id = id;
	}

	public void setOutDegreeList(List<Edge> outDegreeList) {
		this.outDegreeList = outDegreeList;
	}

	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public String getId() {
		return id;
	}

	public List<Edge> getOutDegreeList() {
		return outDegreeList;
	}

	public boolean getIsVisited() {
		return isVisited;
	}

	// const

	public String findNN() {
		int min = 21; // the max weight is 21
		String nnId = null;
		for (int i = 0;i < outDegreeList.size() ;i++ ) {
			int temp = outDegreeList.get(i).getWeight();
			if (temp < min) {
				min = temp;
				nnId = outDegreeList.get(i).getId();
			}
		}
		return nnId;
	}
}