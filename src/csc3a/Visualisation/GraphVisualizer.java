package csc3a.Visualisation;

import csc3a.DataStructures.*;
import csc3a.GraphClasses.*;
import csc3a.ImageProcessing.TemplateMatcher;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import org.opencv.core.Point;

public class GraphVisualizer {

    public static BufferedImage visualizeGraph(Graph<String, Integer> graph) {
        mxGraph jGraph = new mxGraph();
        Object parent = jGraph.getDefaultParent();

        Map<Vertex<String, Integer>, Object> vertexMap = new HashMap<>();
        jGraph.getModel().beginUpdate();

        try {
            // Predefined node positions (spaced further apart)
            Map<String, int[]> labelPositions = Map.of(
                "Flag", new int[]{100, 50},
                "Header",     new int[]{400, 50},
                "ID Icon",   new int[]{100, 200},
                "Details",    new int[]{400, 200},
                "Photo",   new int[]{400, 350}
            );

            // Node fill colors
            String[] colors = {
                "#FFCC00", "#FF6666", "#66CCFF",
                "#99FF99", "#CC99FF", "#FF99CC"
            };

            int i = 0;
            for (Vertex<String, Integer> v : graph.vertices()) {
                String label = v.getElement();
                int[] pos = labelPositions.get(label);
                String style = "fillColor=" + colors[i % colors.length] +
                               ";strokeColor=black;fontColor=black";
                Object cell = jGraph.insertVertex(parent, null, label, pos[0], pos[1], 100, 40, style);
                vertexMap.put(v, cell);
                i++;
            }

            // Prevent drawing duplicate edges (undirected)
            Set<String> drawnEdges = new HashSet<>();

            for (Edge<Integer, String> edge : graph.edges()) {
                Vertex<String, Integer>[] endpoints = edge.getEndpoints();
                Vertex<String, Integer> u = endpoints[0];
                Vertex<String, Integer> v = endpoints[1];

                String key = System.identityHashCode(u) < System.identityHashCode(v)
                        ? u.hashCode() + "-" + v.hashCode()
                        : v.hashCode() + "-" + u.hashCode();

                if (!drawnEdges.contains(key)) {
                    // Improved edge style for better label visibility
                    String edgeStyle = "endArrow=classic;startArrow=classic;" +
                                       "strokeColor=black;fontColor=black;" +
                                       "labelBackgroundColor=white;";
                    jGraph.insertEdge(parent, null, edge.getElement().toString(),
                            vertexMap.get(u), vertexMap.get(v), edgeStyle);
                    drawnEdges.add(key);
                }
            }

        } finally {
            jGraph.getModel().endUpdate();
        }

        // Render the graph to a BufferedImage
        BufferedImage image = mxCellRenderer.createBufferedImage(jGraph, null, 1, Color.WHITE, true, null);
        return image;
    }
    
    public static Graph<String, Integer> analyzeImageAndBuildGraph(String baseImagePath, Map<String, String> templateFiles) {
        // 1. Find template positions
        Map<String, Point> positions = new HashMap<>();
        for (String label : templateFiles.keySet()) {
            Point[] match = TemplateMatcher.findTemplateMatch(baseImagePath, templateFiles.get(label));
            positions.put(label, match[0]);
        }

        // 2. Build the graph
        Graph<String, Integer> graph = new Graph<>();
        Map<String, Vertex<String, Integer>> vertices = new HashMap<>();
        for (String label : templateFiles.keySet()) {
            vertices.put(label, graph.insertVertex(label));
        }

        // 3. Add edges
        insertEdge(graph, vertices, positions, "Flag", "Header");
        insertEdge(graph, vertices, positions, "Header", "Details");
        insertEdge(graph, vertices, positions, "Details", "Photo");
        insertEdge(graph, vertices, positions, "ID Icon", "Flag");
        insertEdge(graph, vertices, positions, "ID Icon", "Details");
        insertEdge(graph, vertices, positions, "ID Icon", "Photo");
        insertEdge(graph, vertices, positions, "ID Icon", "Header");

        // 4. Return the logical graph
        return graph;
    }
    
    private static void insertEdge(Graph<String, Integer> graph, Map<String, Vertex<String, Integer>> vMap,
            Map<String, Point> positions, String label1, String label2) {
    	
    	int dist = euclideanDistance(positions.get(label1), positions.get(label2));
    	graph.insertEdge(vMap.get(label1), vMap.get(label2), dist);
    }
    
    public static int euclideanDistance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }
}
