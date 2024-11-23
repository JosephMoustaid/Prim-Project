package PrimProject;

public class PrimTest {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Add nodes
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);

        // Add edges
        graph.addEdge(new Edge(a, b, 3));
        graph.addEdge(new Edge(a, c, 1));
        graph.addEdge(new Edge(b, c, 7));
        graph.addEdge(new Edge(b, d, 5));
        graph.addEdge(new Edge(c, d, 2));

        // Run Prim's algorithm
        Prim prim = new Prim(graph);
        prim.run("A");
    }
}
