package csc3a.Verification;

import csc3a.GraphClasses.*;
import csc3a.DataStructures.*;  // Import your custom Map and HashedMap


/**
 * Builds a graph from a map of words and their positions.
 * @param wordPositions Map of word -> [x, y] coordinate
 * @param positionMapOut Output map of Vertex -> [x, y] position
 * @return A graph with words as vertices and distances as edge weights
 */
public class GraphBuilder {

    public static Graph<String, Double> buildOCRGraph(
            Map<String, int[]> wordPositions,
            Map<Vertex<String, Double>, int[]> positionMapOut) {

        Graph<String, Double> graph = new Graph<>();
        Map<String, Vertex<String, Double>> wordToVertex = new HashedMap<>();  // Use your custom Map

        // Add all words as vertices
        for (Entry<String, int[]> entry : wordPositions.entrySet()) {
            Vertex<String, Double> vertex = graph.insertVertex(entry.getKey());
            wordToVertex.put(entry.getKey(), vertex);
            positionMapOut.put(vertex, entry.getValue());
        }

        // Add edges between words that are spatially close
        for (Entry<String, int[]> e1 : wordPositions.entrySet()) {
            for (Entry<String, int[]> e2 : wordPositions.entrySet()) {
                if (!e1.getKey().equals(e2.getKey()) &&
                    e1.getKey().compareTo(e2.getKey()) < 0) {

                    int[] pos1 = e1.getValue();
                    int[] pos2 = e2.getValue();
                    double dist = Math.sqrt(Math.pow(pos1[0] - pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2));

                    // Only link  words that are within threshold distance
                    if (dist < 200) {
                        Vertex<String, Double> v1 = wordToVertex.get(e1.getKey());
                        Vertex<String, Double> v2 = wordToVertex.get(e2.getKey());
                        graph.insertEdge(v1, v2, dist);
                    }
                }
            }
        }

        return graph;
    }
}
