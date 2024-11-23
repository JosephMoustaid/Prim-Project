package PrimProject;

public class Node {
    protected String name;
    protected double positionX;
    protected double positionY;

    public Node(String name) {
        this.name = name;
    }

    public Node(Node node) {
        this.name = node.getName();
        this.positionX = node.getPositionX();
        this.positionY = node.getPositionY();
    }
    // hasPosition method
    public boolean hasPosition() {
        return this.positionX != 0 && this.positionY != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return node.getName().equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
