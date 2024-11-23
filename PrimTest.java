package PrimProject;

public class PrimTest {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Add nodes
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);
        graph.addNode(e);

        // Add edges
        graph.addEdge(new Edge(a, b, 3));
        graph.addEdge(new Edge(a, c, 2));
        graph.addEdge(new Edge(a, d, 6));
        graph.addEdge(new Edge(b, c, 4));
        graph.addEdge(new Edge(c, d, 4));
        graph.addEdge(new Edge(b, e, 2));

        System.out.println(graph);
        // Run Prim's algorithm
        Prim prim = new Prim(graph);
        prim.run("A");
    }
}
