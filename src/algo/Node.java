package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private Node parent; // The parent of this node
    private boolean blocked; // The node viability status
    private boolean visited; // The node checked status

    // f(n) = g(n) + h(n)
    private double gCost; // Distance from the starting node to this node
    private double hCost; // heuristic: Distance from the end node to this node
    private double fCost; // The total cost (g + h)
    private double nodeWeight; // Weight of this node

    private int yRowNo; // The row number of this node in the graph (Matrix)
    private int xColNo; // The column number of this node in the graph (Matrix)

    private List<Node> neighbours; // Neighbours of this node.

    Node(int yRowNo, int xColNo, boolean blocked) {
        this.yRowNo = yRowNo;
        this.xColNo = xColNo;
        this.blocked = blocked;
        this.gCost = Integer.MAX_VALUE;
        this.neighbours = new ArrayList<>();
    }

    public void addNeighbours(Node neighbour) {
        neighbours.add(neighbour);
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
        fCost = (gCost + hCost);
        return fCost;
    }

    public double getNodeWeight() {
        return nodeWeight;
    }

    public int getYRowNo() {
        return yRowNo;
    }

    public int getXColNo() {
        return xColNo;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
    }

    public void setNodeWeight(double nodeWeight) {
        this.nodeWeight = nodeWeight;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setFCost(double fCost) {
        this.fCost = fCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return blocked == node.blocked &&
                visited == node.visited &&
                Double.compare(node.gCost, gCost) == 0 &&
                Double.compare(node.hCost, hCost) == 0 &&
                Double.compare(node.fCost, fCost) == 0 &&
                Double.compare(node.nodeWeight, nodeWeight) == 0 &&
                yRowNo == node.yRowNo &&
                xColNo == node.xColNo &&
                Objects.equals(parent, node.parent) &&
                Objects.equals(neighbours, node.neighbours);
    }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", blocked=" + blocked +
                ", visited=" + visited +
                ", gCost=" + gCost +
                ", hCost=" + hCost +
                ", fCost=" + fCost +
                ", nodeWeight=" + nodeWeight +
                ", yRowNo=" + yRowNo +
                ", xColNo=" + xColNo + '}';
    }
}
