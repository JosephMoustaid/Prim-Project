package PrimProject;

import java.util.ArrayList;

public class Prim {
    private Graph graph; // Original graph
    private Graph prim; // Graph representing the MST

    public Prim(Graph graph) {
        this.graph = graph;
    }

    public void run(String start) {
        ArrayList<Node> visited = new ArrayList<>();
        prim = new Graph();
        for (Node node : graph.getNodes()) {
            prim.addNode(node);
        }

        Node startNode = null;
        for (Node node : graph.getNodes()) {
            if (node.getName().equals(start)) {
                startNode = node;
                visited.add(node);
                prim.addNode(node); // Use the same reference
                break;
            }
        }

        if (startNode == null) {
            System.out.println("Start node not found in the graph!");
            return;
        }

        while (visited.size() < graph.getNodes().size()) {
            Edge minEdge = graph.findMinimumEdge(visited);
            if (minEdge == null) {
                System.out.println("Graph is not connected!");
                break;
            }

            prim.addEdge(minEdge); // Add the edge directly

            Node newNode = minEdge.getStartNode();
            if (visited.contains(newNode)) {
                newNode = minEdge.getEndNode();
            }

            if (!visited.contains(newNode)) {
                visited.add(newNode);
                prim.addNode(newNode); // Use the original node reference
            }

            System.out.println("Minimum edge added: " + minEdge);
            System.out.println("Current MST: " + prim);
        }

        System.out.println("Prim's algorithm completed:");
        System.out.println(prim);
    }

    public Graph getPrimGraph() {
        return prim;
    }
}
