/*
 * Project Title - Minimum Spanning Tree
 * Group members: Yadukumari Shanthigrama Ramesh, Farid Rahman, David, Xanthia
 * Windows10 and Java compiler 1.8
 */
import java.util.*;
import java.util.Map.Entry;

//Yadukumari
public class MinimumSpanningTree<E> extends Graph<E> {
	private PriorityQueue<Edge<E>> minHeap;
	LinkedStack<Edge<E>> undoStack = new LinkedStack<Edge<E>>();

	public MinimumSpanningTree() {
		minHeap = new PriorityQueue<Edge<E>>();
	}

	public void clear() {
		minHeap.clear();
	}

	//@SuppressWarnings("unchecked")
	public ArrayList<Edge<E>> applyPrim(Vertex<E> cityname) {
		ArrayList<Edge<E>> result = new ArrayList<Edge<E>>();
		HashMap<E, Vertex<E>> setOfVertices;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIter;
		LinkedList<Vertex<E>> vertexArray = new LinkedList<Vertex<E>>();
		int count = 0;
		Vertex<E> source = null, dest = null;
		double weight;
		Pair<Vertex<E>, Double> singleEdge;
		Edge<E> edge;
		Iterator<Entry<E, Vertex<E>>> vertexIter;
		boolean find = false;
		
		// Build set of vertices
		setOfVertices = new HashMap<>(vertexSet);
		if (setOfVertices.isEmpty()) {
			return null;
		}
		
		// Find starting vertex
		for (vertexIter = setOfVertices.entrySet().iterator(); vertexIter.hasNext();) {
			source = vertexIter.next().getValue();
			if (source.equals(cityname)) {
				find = true;
				break;
			}
		}
		if(!find){
			System.out.println("Sorry.... "+cityname.data+" is not containd any cellTower to show the minimum path.\n");
			return null;
		}
		while (count < setOfVertices.size()) {
			for (edgeIter = source.adjList.entrySet().iterator(); edgeIter.hasNext();) {
				singleEdge = edgeIter.next().getValue();
				if (singleEdge == null) {
					//source = singleEdge.first;
					continue;
				}
				dest = singleEdge.first;
				weight = singleEdge.second;
				// If dest is not visited or source is not visited
				if (!dest.isVisited() || !source.isVisited()) {
					minHeap.add(new Edge<E>(source, dest, weight));
				}
			}
			if(minHeap.isEmpty()){
				continue;
			}
			// Remove minimum edge from heap and add it to result if either source/dest is not visited
			edge = minHeap.remove();
			if (!(edge.dest.isVisited()) || !(edge.source.isVisited())) {
				result.add(edge);
			}
			
			// Mark source as visited if not already
			if (!vertexArray.contains(edge.source)) {
				edge.source.visit();
				vertexArray.add(edge.source);
				count++;
			}
			
			// Mark dest as visited it not already
			if (!vertexArray.contains(edge.dest)) {
				edge.dest.visit();
				vertexArray.add(edge.dest);
				count++;
			}
			
			// Next source is the dest
			source = edge.dest;
		}
		
		return result;
	}
	
	//Farid
	//Used to store removed elements in Stack
	public void undoAdd(Vertex<E> source, Vertex<E> dest, double cost){
		Edge<E> edge = new Edge<E>(source,dest, cost);
		undoStack.push(edge);
	}
	//Farid
	//It add the removed vertices with weight to the graph
	public void undoRemoval(){
		Edge<E> tempEdge = undoStack.pop();
		if(tempEdge != null){
			System.out.println("\nRestored removed edge = " + tempEdge);
			this.addEdge(tempEdge.source.getData(), tempEdge.dest.getData(), tempEdge.cost);
		}else{
			System.out.println("\nNo edge was removed to undo it");
		}
		//return tempEdge;
		
	}


}
