package algo;

class Node {

    private Node parent; // The parent of this node
    private boolean blocked; // The node viability status
    private boolean visited; // The node checked status

    // f(n) = g(n) + h(n)
    private double gCost; // Distance from the starting node to this node
    private double hCost; // heuristic: Distance from the end node to this node
    private double fCost; // The total cost (g + h)
    private double nodeWeight; // Weight of this node

    private int xPos; // The row number of this node in the graph (Matrix)
    private int yPos; // The column number of this node in the graph (Matrix)

    /**
     * This constructs a node with the actual graph location.
     *
     * @param xPos The Row number in the graph (Node's Actual X position in the map)
     * @param yPos The Column number in the graph (Node's Actual Y position in the map)
     */
    public Node(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isVisited() {
        return visited;
    }

    public double getGCost() {
        return gCost;
    }

    public double getHCost() {
        return hCost;
    }

    public double getFCost() {
        return fCost;
    }

    public double getNodeWeight() {
        return nodeWeight;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(parent, blocked, visited, gCost, hCost, fCost, nodeWeight, xPos, yPos);
    }

    @Override
    public String toString() {
        return "Node [" +
                "  P=" + parent +
                ", B=" + blocked +
                ", V=" + visited +
                ", G=" + gCost +
                ", H=" + hCost +
                ", F=" + (gCost + hCost) +
                ", W=" + nodeWeight +
                ", gX=" + xPos +
                ", gY=" + yPos + "  ]";
    }
}
