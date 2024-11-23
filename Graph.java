package PrimProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public void addNode(Node node) {
        if (nodes.contains(node)) {
            System.out.println("Node already exists: " + node.getName());
            return;
        }
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        if (edges.contains(edge)) {
            System.out.println("Edge already exists: " +
                    edge.getStartNode().getName() + " -> " + edge.getEndNode().getName());
            return;
        }

        // Validate that start and end nodes exist in the graph
        Node startNode = findNodeByName(edge.getStartNode().getName());
        Node endNode = findNodeByName(edge.getEndNode().getName());

        if (startNode == null || endNode == null) {
            System.err.println("One or both nodes for the edge are not in the graph.");
            return;
        }

        // Update edge with correct node references
        edge.setStartNode(startNode);
        edge.setEndNode(endNode);

        edges.add(edge);
        System.out.println("Added edge: " + startNode.getName() + " -> " + endNode.getName());
    }

    // Helper method to find a node by name
    public Node findNodeByName(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Graph:\n");
        str.append("Nodes:\n");
        for (Node node : nodes) {
            str.append(node.getName()).append("\n");
        }
        str.append("\n");
        str.append("Edges:\n");
        for (Edge edge : edges) {
            str.append(edge.toString());
            str.append("\n");
        }
        return str.toString();
    }

    public Edge getEdgeWith(String start, String end) {
        for (Edge edge : edges) {
            boolean isSame = (edge.getStartNode().getName().equals(start) && edge.getEndNode().getName().equals(end)) ||
                    (edge.getStartNode().getName().equals(end) && edge.getEndNode().getName().equals(start));
            if (isSame)
                return edge;
        }
        return null;
    }

    public ArrayList<Node> findNeighbours(String nodeName) {
        ArrayList<Node> neighbours = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getStartNode().getName().equals(nodeName)) {
                neighbours.add(edge.getEndNode());
            } else if (edge.getEndNode().getName().equals(nodeName)) {
                neighbours.add(edge.getStartNode());
            }
        }
        return neighbours;
    }

    public Edge findMinimumEdge(ArrayList<Node> visited) {
        Edge minEdge = null; // Initialize to null to signify no edge found
        int minWeight = Integer.MAX_VALUE;

        // Iterate through all visited nodes
        for (Node node : visited) {
            // Find all neighbors of the current node
            ArrayList<Node> neighbours = findNeighbours(node.getName());

            for (Node neighbour : neighbours) {
                // Skip if the neighbor is already visited
                if (visited.contains(neighbour)) {
                    continue;
                }

                // Get the edge connecting the current node and the neighbor
                Edge edge = getEdgeWith(node.getName(), neighbour.getName());

                // If a valid edge is found and its weight is smaller, update minEdge
                if (edge != null && edge.getWeight() < minWeight) {
                    minEdge = edge;
                    minWeight = edge.getWeight();
                }
            }
        }

        return minEdge; // Will return null if no edge is found
    }


    /**
     * Generates positions for nodes in a graph and updates edge positions.
     *
     * The nodes are arranged in a circular layout, centered on the canvas.
     * Edges are updated to reflect the new positions of their start and end nodes.
     * Steps :
     * 1. Check if there are nodes in the graph; if not, exit the method
     * 2. Define layout settings for the circular arrangement of nodes
     * 3. Assign positions to nodes in a circular layout
     * 4. Debug: Print the positions of nodes
     * 5. Update edge positions to reflect the updated node positions
     * 6. Debug: Print the positions of edges
     * @param canvasWidth  The width of the canvas where the graph is drawn.
     * @param canvasHeight The height of the canvas where the graph is drawn.
     */
    public void generatePositions(double canvasWidth, double canvasHeight) {
        // 1. Check if there are nodes in the graph; if not, exit the method
        if (nodes.isEmpty() || edges.isEmpty()) {
            System.err.println("No nodes in the graph to position.");
            return;
        }

        // 2. Define layout settings for the circular arrangement of nodes
        double centerX = canvasWidth / 2; // Center X-coordinate of the canvas
        double centerY = canvasHeight / 2; // Center Y-coordinate of the canvas
        double radius = Math.min(canvasWidth, canvasHeight) / 2.5 - 50; // Radius of the circular layout
        double angleStep = 2 * Math.PI / nodes.size(); // Angle increment for evenly spaced nodes

        // 3. Assign positions to nodes in a circular layout
        for (int i = 0; i < nodes.size(); i++) {
            double angle = i * angleStep; // Calculate the angle for the current node
            double x = centerX + radius * Math.cos(angle); // X-coordinate
            double y = centerY + radius * Math.sin(angle); // Y-coordinate

            nodes.get(i).setPosition(x, y); // Update node position
        }

        // 4. Debug: Print the positions of nodes
        System.out.println("Node Positions:");
        for (Node node : nodes) {
            System.out.printf("Node %s: (%.2f, %.2f)%n", node.getName(), node.getPositionX(), node.getPositionY());
        }

        // 5. Update edge positions to reflect the updated node positions
        for (Edge edge : edges) {

            if (edge.getStartNode() != null && edge.getEndNode() != null) {
                edge.setStartPosition(edge.getStartNode().getPositionX(), edge.getStartNode().getPositionY());
                edge.setEndPosition(edge.getEndNode().getPositionX(), edge.getEndNode().getPositionY());
            } else {
                // 5.1 Handle null nodes in edges with an error message
                System.err.printf("Edge (%s -> %s) has null nodes.%n",
                        edge.getStartNode() != null ? edge.getStartNode().getName() : "null",
                        edge.getEndNode() != null ? edge.getEndNode().getName() : "null");
            }
        }

        // 6. Debug: Print the positions of edges
        System.out.println("\nEdge Positions:");
        for (Edge edge : edges) {
            System.out.printf("Edge (%s -> %s): Start (%.2f, %.2f), End (%.2f, %.2f)%n",
                    edge.getStartNode().getName(), edge.getEndNode().getName(),
                    edge.getStartPositionX(), edge.getStartPositionY(),
                    edge.getEndPositionX(), edge.getEndPositionY());
        }
    }

}
