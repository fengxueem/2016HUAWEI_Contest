/**
 * 实现代码文件
 * 
 * @author Terry, Jing, Mark
 * @since 2016-4-9
 * @version V1.0
 */
package com.routesearch.route;
import java.lang.*;
import java.lang.Integer;
import java.util.*;
import java.util.ArrayList;
import com.extra.Edge;
import com.extra.Walk;
import com.extra.Graph;
import com.extra.Vertices;
import java.util.LinkedList;
import java.util.Queue;

public final class Route
{
    /**
     * 你需要完成功能的入口
     * 
     * @author Terry, Jing, Mark
     * @since 2016-4-9
     * @version V1
     */

    public static String searchRoute(String graphContent, String condition)
    {	
    	// Graph variables
    	String startPoint;
    	String endPoint;
    	String outcomePath;
        List<String> passPoints = new ArrayList<String>();
        List<Edge> edgeList = new ArrayList<Edge>();
        HashMap<String, Vertices>  verticesMap = new HashMap<String, Vertices>();
        Graph graph = new Graph();

        // BFS alg. variables
        int numOfPassPoints; 
        LinkedList<Vertices> frontierList = new LinkedList<Vertices>();
        Vertices currentPoint;
        Edge currentEdge = null;
        Walk currentWalk;
        Walk outcomeWalk = null;
        List<String> historyVertices = new ArrayList<String>();
        List<Edge> historyEdge = new ArrayList<Edge>();
        // Vertices[] childs;
        LinkedList<Walk>  walkList= new LinkedList<Walk>();
        LinkedList<Walk>  walkList1= new LinkedList<Walk>();        
        List<Walk> childWalks;

       	// generate start point, end point and passing points
        condition = condition.replace('\n',',');
       	String[] demand = condition.split("[,|]");//read demand file
       	startPoint = demand[0];
       	endPoint = demand[1];
       	for (int i =0; i <demand.length; i++) {
       		if(i!=0 && i!=1 && i!=demand.length-1) {
       			String v=demand[i];
				passPoints.add(v);
       		} else if (i==demand.length-1) {
            String v=demand[i];
            passPoints.add(v);
          }
       	}

       	// for (int i = 0;i < passPoints.size() ;i++ ) {
       	// 	System.out.println("Pass point #:"+i+" "+passPoints.get(i));
        //   System.out.println(passPoints.get(i));
       	// }
       	
       	// generate edgeList
      	String[] topoContent = graphContent.split(System.getProperty("line.separator"));//read topo file
      	for (int k =0; k < topoContent.length; k++) {
        	String v = topoContent[k];
        	String[] vContent = v.split (",");//four elems in vCont,1st line, 2nd source,3rd:target,4th weight
        	Edge edge = new Edge(vContent[0], vContent[1] , vContent[2], Integer.parseInt(vContent[3]));
        	if (edgeList.isEmpty()) {
		  		edgeList.add(edge);
        	} else {
            // handle repeated edge
				Boolean found = false; // flag of finding repeated edge
			    for (int j = 0;j < edgeList.size() ;j++ ) {
        			Edge tempEdge = edgeList.get(j);
            		if (tempEdge.getSource().equals(edge.getSource()) && tempEdge.getDestination().equals(edge.getDestination()) && tempEdge.getWeight()>edge.getWeight()) {
                		edgeList.get(j).setWeight(edge.getWeight());
						found = true; // found the repeated edge
						break; // found the repeated edge and updated new weight, then jump out of the loop
              		}
				}
				if (!found) { // if no repeated edge, then add this edge
            		edgeList.add(edge);
				}
        	}
       	}
       	graph.setEdgeList(edgeList);

       	// generate vertices map based on edge list
       	for (int i = 0;i < edgeList.size() ;i++ ) {
       		if (i == 0) {
       			verticesMap.put(edgeList.get(i).getSource(),getSrcVertFromEdge(edgeList.get(i))); // always convert the 1st edge into vertice and put it into vertices map
       			verticesMap.put(edgeList.get(i).getDestination(),getDesVertFromEdge(edgeList.get(i)));
       		} else {
       			Vertices temp = verticesMap.get(edgeList.get(i).getSource()); // find if this source vertice is already in the map
       			if(temp != null) { // already in the map
       				temp.getOutDegreeList().add(edgeList.get(i));// add this edge into its ouDegreeList
       			} else { // not in the map
       				verticesMap.put(edgeList.get(i).getSource(),getSrcVertFromEdge(edgeList.get(i))); // convert and put
       			}
       			Vertices temp1 = verticesMap.get(edgeList.get(i).getDestination()); // find if this dest. vertice is already in the map
       			if(temp1 == null) { // not in the map
       				verticesMap.put(edgeList.get(i).getDestination(),getDesVertFromEdge(edgeList.get(i))); // convert and put
       			}
       		}
       	}
       	graph.setVerticesMap(verticesMap);

		    // for (Iterator iter = verticesMap.values().iterator(); iter.hasNext();) {
		    // 	Vertices v = (Vertices)iter.next();
		    // 	System.out.println("Vertice: "+ v.getId()+" outDegree size:"+v.getOutDegreeList().size());
		    // }
       	//DFS
       	numOfPassPoints = passPoints.size();
       	currentPoint = graph.findVertice(startPoint);// find start point
       	currentPoint.setIsVisited(true);
       	currentWalk = new Walk(currentPoint,currentEdge,historyVertices,historyEdge,0);
       	frontierList.offer(currentPoint);//put the start point at the tail of queue
       	walkList.offer(currentWalk);
       	while (!frontierList.isEmpty()&&walkList1.isEmpty()){// loop while the queue is not empty
       		currentPoint = frontierList.poll(); // get current expended point and remove it from queue
       		// currentPoint.setIsVisited(true);
       		currentWalk = walkList.poll(); // get corresponing walk to this point and remove it from queue
        	// System.out.println("Current Point"+currentPoint.getId());
       		childWalks = childWalkGener(currentPoint,currentWalk,graph,passPoints);
       		if (childWalks == null) {
       			continue;
       		}
       		for (int j = 0;j < childWalks.size() ;j++ ) {
            // childWalks.get(j).printWalk();
            	if (childWalks.get(j).getFrontier().getId().equals(endPoint)) {
            		if (childWalks.get(j).getCounter() == numOfPassPoints) {
            			walkList1.offer(childWalks.get(j));
            		}
            	  	continue;
           		}
           		if (j == 0) {
           			walkList.addFirst(childWalks.get(j));
           			frontierList.addFirst(childWalks.get(j).getFrontier());
           		} else {
	       			walkList.offer(childWalks.get(j));// push new childs' walks at the tail of queue
    	   			frontierList.offer(childWalks.get(j).getFrontier());
           		}
       		}
       	}
      	
      	// find the best walk
      	int min = -1; // since -1 is never gonna be the path length
      	for (Iterator iter = walkList1.iterator(); iter.hasNext();) {
			Walk walk = (Walk)iter.next();
			if (min == -1) {
				min = walk.getLength();
				outcomeWalk = walk;
			} else if(walk.getLength() < min){
				min = walk.getLength();
				outcomeWalk = walk;
			}
		}
        // System.out.println("This is outcome walk.");
        // // A stupid way to output final result......hehe
        // System.out.println("Final results:");
        // for (int p= 0;p<outcomeWalk.getHistoryVertices().size() ;p++ ) {
        //   System.out.println(outcomeWalk.getHistoryVertices().get(p));
        // }
        // System.out.println("end:"+outcomeWalk.getFrontier().getId());

        // outcomeWalk.printWalk();
		return output(outcomeWalk);
    }

    public static Vertices getSrcVertFromEdge(Edge edge) {    
        Vertices v = new Vertices();
        v.setId(edge.getSource());
        List<Edge> l = new ArrayList<Edge>();
        l.add(edge);
        v.setOutDegreeList(l);
        return v;
    } 

    public static Vertices getDesVertFromEdge(Edge edge) {    
        Vertices v = new Vertices();
        v.setId(edge.getDestination());
        List<Edge> l = new ArrayList<Edge>();
        v.setOutDegreeList(l);
        return v;
    } 

    public static List<Walk> childWalkGener(Vertices frontier, Walk oldWalk, Graph graph, List<String> passPoints) {
        List<Walk> childWalks = new ArrayList<Walk>();
        List<Edge> childEdges = frontier.getOutDegreeList();
        if (!childEdges.isEmpty()) {
            for (int i = 0; i < childEdges.size(); i++ ) {
                String targetId = childEdges.get(i).getDestination();
                Vertices d = graph.getVerticesMap().get(targetId); 
                if (!oldWalk.getHistoryVertices().contains(targetId)) {
                    Walk w = new Walk();
                    List<String> history = new ArrayList<String>();
                    List<Edge> historyE = new ArrayList<Edge>();
                    for (int q = 0;q < oldWalk.getHistoryVertices().size() ;q++ ) {
                      history.add(oldWalk.getHistoryVertices().get(q));
                    }
                    for (int q = 0;q < oldWalk.getHistoryEdge().size() ;q++ ) {
                      historyE.add(oldWalk.getHistoryEdge().get(q));
                    }
                    history.add(frontier.getId());//put current frontier into history
                    if (oldWalk.getFrontierEdge() != null) {
                      historyE.add(oldWalk.getFrontierEdge());
                    }
                    w.setHistoryVertices( history );
                    w.setHistoryEdge(historyE);
                    if (passPoints.contains(frontier.getId())) {
                        w.setCounter( oldWalk.getCounter() + 1 );
                    }
                    w.setLength( oldWalk.getLength() + childEdges.get(i).getWeight() );
                    w.setFrontier(d);
                    w.setFrontierEdge(childEdges.get(i));
                    childWalks.add(w);
                }// if childs[i] has been visited
            }
            return childWalks;
        }// if childs.size == 0
        return null;
    }

    public static String output(Walk outcomeWalk){
    	String out = "";
    	if (outcomeWalk == null) {
    		return "NA";
    	} else {
    		for (int i = 0;i < outcomeWalk.getHistoryVertices().size() ;i++ ) {
    			out = out.concat(outcomeWalk.getHistoryVertices().get(i));
    			out = out.concat("|");
    		}
    		out = out.concat(outcomeWalk.getFrontier().getId());
    		return out;
    	}
    }
}