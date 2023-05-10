package graph;

import graph.WeightedGraph;
import java.util.*;
import maze.Juncture;
import maze.Maze;

/**
 * <P>
 * The MazeGraph is an extension of WeightedGraph. The constructor converts a
 * Maze into a graph.
 * </P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/**
	 * <P>
	 * Construct the MazeGraph using the "maze" contained in the parameter to
	 * specify the vertices (Junctures) and weighted edges.
	 * </P>
	 * 
	 * <P>
	 * The Maze is a rectangular grid of "junctures", each defined by its X and Y
	 * coordinates, using the usual convention of (0, 0) being the upper left
	 * corner.
	 * </P>
	 * 
	 * <P>
	 * Each juncture in the maze should be added as a vertex to this graph.
	 * </P>
	 * 
	 * <P>
	 * For every pair of adjacent junctures (A and B) which are not blocked by a
	 * wall, two edges should be added: One from A to B, and another from B to A.
	 * The weight to be used for these edges is provided by the Maze. (The Maze
	 * methods getMazeWidth and getMazeHeight can be used to determine the number of
	 * Junctures in the maze. The Maze methods called "isWallAbove",
	 * "isWallToRight", etc. can be used to detect whether or not there is a wall
	 * between any two adjacent junctures. The Maze methods called "getWeightAbove",
	 * "getWeightToRight", etc. should be used to obtain the weights.)
	 * </P>
	 * 
	 * @param maze to be used as the source of information for adding vertices and
	 *             edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		super();
		List<Juncture> junctureList = new ArrayList<>(); // list of intersections in maze
		Map<String, Juncture> junctureMap = new HashMap<>();
		// maps coordinate or each juncture to the intersection itself
		/*
		 * Goes through every junction
		 */
		for (int x = 0; x < maze.getMazeWidth(); x++) {
			for (int y = 0; y < maze.getMazeHeight(); y++) {
				Juncture j = new Juncture(x, y);
				String coordinate = "(" + x + ", " + y + ")";
				junctureMap.put(coordinate, j);
				addVertex(j);
				junctureList.add(j);
			}
		}
		/*
		 * Determines if each juncture is above, below, right, or left
		 */
		for (Juncture j : junctureList) {
			if (!maze.isWallAbove(j)) {
				String above = "(" + j.getX() + ", " + (j.getY() - 1) + ")";
				Juncture junctureAbove = junctureMap.get(above);
				addEdge(j, junctureAbove, maze.getWeightAbove(j));
			}
			if (!maze.isWallBelow(j)) {
				String below = "(" + j.getX() + ", " + (j.getY() + 1) + ")";
				Juncture junctureBelow = junctureMap.get(below);
				addEdge(j, junctureBelow, maze.getWeightBelow(j));
			}
			if (!maze.isWallToLeft(j)) {
				String left = "(" + (j.getX() - 1) + ", " + j.getY() + ")";
				Juncture junctureLeft = junctureMap.get(left);
				addEdge(j, junctureLeft, maze.getWeightToLeft(j));
			}
			if (!maze.isWallToRight(j)) {
				String right = "(" + (j.getX() + 1) + ", " + j.getY() + ")";
				Juncture junctureRight = junctureMap.get(right);
				addEdge(j, junctureRight, maze.getWeightToRight(j));
			}
		}
	}
}
