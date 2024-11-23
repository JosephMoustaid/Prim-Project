package PrimProject;

public class Edge {
    protected int weight;
    protected Node start;
    protected Node end;
    protected double x1, y1, x2, y2;

    public Edge(Node start, Node end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Node getStartNode() {
        return start;
    }

    public Node getEndNode() {
        return end;
    }

    public void setStartNode(Node node) {
        this.start = node;
    }

    public void setEndNode(Node node) {
        this.end = node;
    }

    public void setStartPosition(double x, double y) {
        this.x1 = x;
        this.y1 = y;
    }

    public void setEndPosition(double x, double y) {
        this.x2 = x;
        this.y2 = y;
    }

    public double getStartPositionX() {
        return x1;
    }

    public double getStartPositionY() {
        return y1;
    }

    public double getEndPositionX() {
        return x2;
    }

    public double getEndPositionY() {
        return y2;
    }

    @Override
    public String toString() {
        return start.getName() + " --" + weight + "--> " + end.getName();
    }
}
