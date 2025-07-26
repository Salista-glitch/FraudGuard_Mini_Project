package csc3a.Algorithims;

import csc3a.GraphClasses.*;
import java.util.*;

/**
 * Computes the shortest paths between the source node and all the nodes in the graph
 * @param <V>
 * @param <E>
 */
public class Dijkstra<V, E extends Number> {

	/**
	 * Performs the Dijkstras' Algorithm
	 * @param graph
	 * @param source
	 * @return A map of the vertices
	 */
    public Map<Vertex<V, E>, Double> computeShortestPaths(Graph<V, E> graph, Vertex<V, E> source) {
        Map<Vertex<V, E>, Double> distances = new HashMap<>();
        PriorityQueue<Vertex<V, E>> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
        Set<Vertex<V, E>> visited = new HashSet<>();

        for (Vertex<V, E> v : graph.vertices()) {
            distances.put(v, Double.POSITIVE_INFINITY);
        }
        distances.put(source, 0.0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex<V, E> u = queue.poll();
            if (!visited.add(u)) continue;

            for (Edge<E, V> edge : graph.vertEdges(u)) {
                Vertex<V, E> v = graph.opposite(u, edge);
                if (!visited.contains(v)) {
                    double alt = distances.get(u) + edge.getElement().doubleValue();
                    if (alt < distances.get(v)) {
                        distances.put(v, alt);
                        queue.add(v);
                    }
                }
            }
        }

        return distances;
    }
}
