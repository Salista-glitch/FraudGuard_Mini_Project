package csc3a.Algorithims;

import csc3a.GraphClasses.*;
import java.util.*;

/**
 * Gets the next k nearest neighbors of a vector
 * @param <V>
 * @param <E>
 */
public class KNN<V, E extends Number> {

	/**
	 * Function to perform the KNN Algorithm
	 * @param graph
	 * @param source
	 * @param k
	 * @return List of the nearest neighbors
	 */
    public List<Vertex<V, E>> getKNearestNeighbors(Graph<V, E> graph, Vertex<V, E> source, int k) {
        Map<Vertex<V, E>, Double> distances = new Dijkstra<V, E>().computeShortestPaths(graph, source);
        distances.remove(source); // exclude self

        return distances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }
}
