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

        Node startNode = null;
        for (Node node : graph.getNodes()) {
            if (node.getName().equals(start)) {
                startNode = node;
                visited.add(node);
                prim.addNode(new Node(start));
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

            prim.addEdge(minEdge);

            Node newNode = minEdge.getStartNode();
            if (visited.contains(newNode)) {
                newNode = minEdge.getEndNode();
            }
            if (!visited.contains(newNode)) {
                visited.add(newNode);
                prim.addNode(newNode);
            }
        }
        System.out.println("Prim's algorithm completed:");
        System.out.println(prim);
    }

    public Graph getPrimGraph() {
        return prim;
    }
}
