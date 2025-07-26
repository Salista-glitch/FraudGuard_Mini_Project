package csc3a.Algorithims;

import csc3a.GraphClasses.*;
import java.util.*;
/**
 * Class to find the the similarity between two graphs
 * @param <V>
 * @param <E>
 */
public class GraphSimilarityAnalyzer<V, E extends Number> {

    private final Dijkstra<V, E> dijkstra;
    private final KNN<V, E> knn;

    /**
     * Constructor that accepts two algorithm objects
     * @param dijkstra
     * @param knn
     */
    public GraphSimilarityAnalyzer(Dijkstra<V, E> dijkstra, KNN<V, E> knn) {
        this.dijkstra = dijkstra;
        this.knn = knn;
    }

    /**
     * Function that compares two graphs
     * @param g1
     * @param g2
     * @return The similarity score
     */
    public double compare(Graph<V, E> g1, Graph<V, E> g2) {
        double pathScore = compareUsingDijkstra(g1, g2);
        double knnScore = compareUsingKNN(g1, g2, 3);

        //Return the score average
        return (pathScore + knnScore) / 2.0;
    }

    
    /**
     * Compares the graphs using dijkstras' algorithm
     * @param g1
     * @param g2
     * @return similarity score
     */
    private double compareUsingDijkstra(Graph<V, E> g1, Graph<V, E> g2) {
        double totalDiff = 0;
        int count = 0;

        for (Vertex<V, E> v1 : g1.vertices()) {
            Vertex<V, E> v2 = findMatchingVertex(g2, v1);
            if (v2 == null) continue;

            Map<Vertex<V, E>, Double> dist1 = dijkstra.computeShortestPaths(g1, v1);
            Map<Vertex<V, E>, Double> dist2 = dijkstra.computeShortestPaths(g2, v2);

            for (Vertex<V, E> t1 : dist1.keySet()) {
                Vertex<V, E> t2 = findMatchingVertex(g2, t1);
                if (t2 != null && dist2.containsKey(t2)) {
                    double d1 = dist1.get(t1);
                    double d2 = dist2.get(t2);
                    totalDiff += Math.abs(d1 - d2);
                    count++;
                }
            }
        }

        if (count == 0) return 0.0;
        double avgDiff = totalDiff / count;
        return 1.0 / (1.0 + avgDiff);
    }

    
    /**
     * Compares the graphs using KNN algorithm
     * @param g1
     * @param g2
     * @return similarity score
     */
    private double compareUsingKNN(Graph<V, E> g1, Graph<V, E> g2, int k) {
        int match = 0, total = 0;

        for (Vertex<V, E> v1 : g1.vertices()) {
            Vertex<V, E> v2 = findMatchingVertex(g2, v1);
            if (v2 == null) continue;

            List<Vertex<V, E>> list1 = knn.getKNearestNeighbors(g1, v1, k);
            List<Vertex<V, E>> list2 = knn.getKNearestNeighbors(g2, v2, k);

            for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
                if (list1.get(i).getElement().equals(list2.get(i).getElement())) {
                    match++;
                }
                total++;
            }
        }

        return total == 0 ? 0.0 : (double) match / total;
    }

    /**
     * Helper function to find a matching vertex in the graph
     * @param graph
     * @param target
     * @return the found vertex
     */
    private Vertex<V, E> findMatchingVertex(Graph<V, E> graph, Vertex<V, E> target) {
        for (Vertex<V, E> v : graph.vertices()) {
            if (v.getElement().equals(target.getElement())) {
                return v;
            }
        }
        return null;
    }
}
