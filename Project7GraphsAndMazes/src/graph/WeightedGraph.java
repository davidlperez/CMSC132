package graph;

import java.util.*;

/**
 * <P>
 * This class represents a general "directed graph", which could be used for any
 * purpose. The graph is viewed as a collection of vertices, which are sometimes
 * connected by weighted, directed edges.
 * </P>
 * 
 * <P>
 * This graph will never store duplicate vertices.
 * </P>
 * 
 * <P>
 * The weights will always be non-negative integers.
 * </P>
 * 
 * <P>
 * The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.
 * </P>
 * 
 * <P>
 * The Weighted Graph will maintain a collection of "GraphAlgorithmObservers",
 * which will be notified during the performance of the graph algorithms to
 * update the observers on how the algorithms are progressing.
 * </P>
 */
public class WeightedGraph<V> {

	/*
	 * Map implementation of a graph
	 */
	private Map<V, Map<V, Integer>> graph;

	/*
	 * Collection of observers. Be sure to initialize this list in the constructor.
	 * The method "addObserver" will be called to populate this collection. Your
	 * graph algorithms (DFS, BFS, and Dijkstra) will notify these observers to let
	 * them know how the algorithms are progressing.
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;

	/**
	 * Initialize the data structures to "empty", including the collection of
	 * GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		graph = new HashMap<>();
		observerList = new ArrayList<>();
	}

	/**
	 * Add a GraphAlgorithmObserver to the collection maintained by this graph
	 * (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/**
	 * Add a vertex to the graph. If the vertex is already in the graph, throw an
	 * IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in the graph
	 */
	public void addVertex(V vertex) {
		if (containsVertex(vertex)) { // if vertex is already in the graph
			throw new IllegalArgumentException();
		}
		graph.put(vertex, new HashMap<>());
	}

	/**
	 * Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		if (graph.containsKey(vertex)) {
			return true;
		}
		return false;
	}

	/**
	 * <P>
	 * Add an edge from one vertex of the graph to another, with the weight
	 * specified.
	 * </P>
	 * 
	 * <P>
	 * The two vertices must already be present in the graph.
	 * </P>
	 * 
	 * <P>
	 * This method throws an IllegalArgumentExeption in three cases:
	 * </P>
	 * <P>
	 * 1. The "from" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 2. The "to" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 3. The weight is less than 0.
	 * </P>
	 * 
	 * @param from   the vertex the edge leads from
	 * @param to     the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex is not in the graph, or
	 *                                  the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		/*
		 * Checks if either vertex is not in the graph or if the weight is negative
		 */
		if (!containsVertex(from) || !containsVertex(to) || weight < 0) {
			throw new IllegalArgumentException();
		}
		graph.get(from).put(to, weight);
	}

	/**
	 * <P>
	 * Returns weight of the edge connecting one vertex to another. Returns null if
	 * the edge does not exist.
	 * </P>
	 * 
	 * <P>
	 * Throws an IllegalArgumentException if either of the vertices specified are
	 * not in the graph.
	 * </P>
	 * 
	 * @param from vertex where edge begins
	 * @param to   vertex where edge terminates
	 * @return weight of the edge, or null if there is no edge connecting these
	 *         vertices
	 * @throws IllegalArgumentException if either of the vertices specified are not
	 *                                  in the graph.
	 */
	public Integer getWeight(V from, V to) {
		/*
		 * ensures both vertices are in the graph
		 */
		if (!containsVertex(from) || !containsVertex(to)) {
			throw new IllegalArgumentException();
		}
		return graph.get(from).get(to);
	}

	/**
	 * <P>
	 * This method will perform a Breadth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyBFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without processing further vertices.
	 * </P>
	 * 
	 * This and the following search closely follow the pseudo-code gone over in
	 * lecture.
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoBFS(V start, V end) {
		/*
		 * Lets the GUI know a Breadth-First Search is starting
		 */
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}
		Set<V> visitedSet = new HashSet<>();
		Queue<V> discovered = new LinkedList<>(); // Queue for BFS
		discovered.add(start); // adding start to queue
		V curr; // marker to keep track of current vertex
		while (!discovered.isEmpty()) {
			curr = discovered.remove();
			if (!visitedSet.contains(curr)) {
				/*
				 * Visiting each vertex
				 */
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(curr);
				}
				visitedSet.add(curr); // add to visitedSet once each vertex is visited
				if (curr.equals(end)) { // search is over when end vertex is reached
					/*
					 * Lets the GUI know search is over
					 */
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return; // no further processing
				}
				/*
				 * Visits each adjacency
				 */
				for (V adjacent : graph.keySet()) {
					if (getWeight(curr, adjacent) != null) { // check if an adjacency exists; null if none
						if (!visitedSet.contains(adjacent)) { // check if visitedSet already contains the adjacency
							discovered.add(adjacent);
						}
					}
				}
			}
		}
	}

	/**
	 * <P>
	 * This method will perform a Depth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without visiting further vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		/*
		 * Notifies the GUI of starting search
		 */
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		Set<V> visitedSet = new HashSet<>();
		Stack<V> discovered = new Stack<>(); // Stack for Depth First
		V curr; // current vertex
		discovered.push(start); // adding start to stack
		while (!discovered.isEmpty()) { // ensures queue is not empty
			curr = discovered.pop(); // assigns curr to removed vertex from queue
			if (!visitedSet.contains(curr)) {
				/*
				 * visit vertex
				 */
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(curr);
				}
				visitedSet.add(curr); // add curr to visitedSet
				if (curr.equals(end)) { // search is over when end vertex is reached
					/*
					 * alerts to GUI search is over
					 */
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return; // no further processing
				}
				/*
				 * for each adjacency
				 */
				for (V adjacent : graph.keySet()) {
					if (getWeight(curr, adjacent) != null) {
						if (!visitedSet.contains(adjacent)) {
							discovered.push(adjacent);
						}
					}
				}
			}
		}
	}

	/**
	 * <P>
	 * Perform Dijkstra's algorithm, beginning at the "start" vertex.
	 * </P>
	 * 
	 * <P>
	 * The algorithm DOES NOT terminate when the "end" vertex is reached. It will
	 * continue until EVERY vertex in the graph has been added to the finished set.
	 * </P>
	 * 
	 * <P>
	 * Before the algorithm begins, this method goes through the collection of
	 * Observers, calling notifyDijkstraHasBegun on each Observer.
	 * </P>
	 * 
	 * <P>
	 * Each time a vertex is added to the "finished set", this method goes through
	 * the collection of Observers, calling notifyDijkstraVertexFinished on each one
	 * (passing the vertex that was just added to the finished set as the first
	 * argument, and the optimal "cost" of the path leading to that vertex as the
	 * second argument.)
	 * </P>
	 * 
	 * <P>
	 * After all of the vertices have been added to the finished set, the algorithm
	 * will calculate the "least cost" path of vertices leading from the starting
	 * vertex to the ending vertex. Next, it will go through the collection of
	 * observers, calling notifyDijkstraIsOver on each one, passing in as the
	 * argument the "lowest cost" sequence of vertices that leads from start to end
	 * (I.e. the first vertex in the list will be the "start" vertex, and the last
	 * vertex in the list will be the "end" vertex.)
	 * </P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end   special vertex used as the end of the path reported to observers
	 *              via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		/*
		 * Lets the GUI know Dijkstra lowest cost has begun
		 */
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}
		/*
		 * data structure initializations
		 */
		Set<V> finishedSet = new HashSet<>();
		Map<V, V> pred = new HashMap<>(); // map predecessors <adjacent, current>
		Map<V, Integer> cost = new HashMap<>(); // map of cost from vertex
		/*
		 * Sets initial values of null and infinity
		 */
		for (V vertex : graph.keySet()) {
			pred.put(vertex, null);
			cost.put(vertex, Integer.MAX_VALUE); // MAX_VALUE is infinity
		}
		cost.put(start, 0); // costs 0 to get to start from start
		while (finishedSet.size() < graph.size()) {
			V curr = null;
			int min = Integer.MAX_VALUE;
			for (V vertex : graph.keySet()) {
				/*
				 * checks that finishedSet does not contain and cost is less than current
				 * minimum
				 */
				if (!finishedSet.contains(vertex) && cost.get(vertex) < min) {
					min = cost.get(vertex);
					curr = vertex;
				}
			}
			finishedSet.add(curr);
			/*
			 * tells the GUI all adjacencies for one vertex is finished
			 */
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(curr, min);
			}
			/*
			 * For all adjacencies
			 */
			for (V adjacent : graph.keySet()) {
				if (getWeight(curr, adjacent) != null) { // null if no edge exists
					if (!finishedSet.contains(adjacent)) {
						/*
						 * Updates cost with lower costs
						 */
						if (cost.get(curr) + getWeight(curr, adjacent) < cost.get(adjacent)) {
							cost.put(adjacent, cost.get(curr) + getWeight(curr, adjacent));
							// puts cost to get to vertex + prev cost
							pred.put(adjacent, curr); // updates predecessor to the one with lowest cost
						}
					}
				}
			}
		}
		/*
		 * Go through in reverse order to ensure shortest possible path/least cost
		 */
		ArrayList<V> leastCost = new ArrayList<>();
		V curr = end; // end is the start since reverse
		while (!curr.equals(start)) {
			leastCost.add(0, curr);
			curr = pred.get(curr);
		}
		leastCost.add(0, curr);
		/*
		 * Notifies GUI end of Dijkstra
		 */
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(leastCost);
		}
	}

}
