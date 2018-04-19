package algo;

import gui.SquaredGrid;

import java.util.*;

public class PathFindingAlgorithm {

    /*Determining the selected distance based metric for heuristic value*/
    public enum Heuristic { MANHATTAN, EUCLIDEAN, CHEBYSHEV, NONE }
    /*Directions for finding the surrounding(neighbouring) nodes*/
    private enum Direction { NORTH, EAST, SOUTH, WEST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST }

    private int sourceYRowPos, sourceXColPos, targetYRowPos, targetXColPos; // [Row|Col] [Row|Col]
    private Heuristic selectedMetric; // User selected :: distance based metric calculation method
    private int[][] graph; // Skeleton graph with weights
    private Node[][] matrix; // Node Matrix to hold all the node objects.

    private PriorityQueue<Node> openSet; // OpenSet contains all the node that need to be evaluated.
    private Set<Node> closedSet; // Closed Set stores all the evaluated nodes. // TODO REMOVE
    private List<Node> finalPathNodes; // Stores the final backtracked path.

    /**
     * @param graph   weighted graph to create the node matrix. (Including Obstacles)
     * @param sYPos   Source Destination Row(Y) Position (in the graph[][])
     * @param sXPos   Source Destination Column(X) Position (in the graph[][])
     * @param tYPos   Target Destination Row(Y) Position (in the graph[][])
     * @param tXPos   Target Destination Column(X) Position (in the graph[][])
     * @param sMetric User Selected distance based metric heuristic calculation method
     */
    public PathFindingAlgorithm(int[][] graph, int sYPos, int sXPos, int tYPos, int tXPos, Heuristic sMetric) {
        this.graph = graph;
        this.sourceYRowPos = sYPos;
        this.sourceXColPos = sXPos;
        this.targetYRowPos = tYPos;
        this.targetXColPos = tXPos;
        this.selectedMetric = sMetric;

        // Sorted by Ascending order of fCost < , > , ==
        openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));
        closedSet = new HashSet<>();
        finalPathNodes = new ArrayList<>();

        initNodeMatrix(); // First init the node[][] matrix with heuristics.
        addNeighbours(); // Second add all the neighbouring nodes in each node in matrix[][].
    }

    private void initNodeMatrix() {

        // Create the matrix array with the same size of graph (y,x)
        matrix = new Node[graph.length][graph.length];

        for (int y = 0; y < graph.length; y++) { /*Traverse Rows*/
            for (int x = 0; x < graph[y].length; x++) { /*Traverse Each column in Row*/
                // Creates a new node with x, y coordinates and the blocked status.
                Node node = new Node(y, x, graph[y][x] == 0);
                // Set the heuristic value from this node to the end node.
                node.setHCost(getHeuristicVal(node, selectedMetric));
                // Sets the node weight as given in the actual map.
                node.setNodeWeight(graph[y][x]);
                matrix[y][x] = node /* Adds the newly created node (y,x)*/;
            }
        }
    }

    private void addNeighbours() {

        for (Node[] r /*Each Row*/: matrix) {
            for (Node n /*Each node in row (current col)*/: r) {
                /*Cycle through each direction*/
                for (Direction d : Direction.values()) {
                    int[] dir = getDirectionYX(n/*Check Node*/, d/*Direction*/);
                    int row = dir[0], col = dir[1], length = matrix.length;
                    if (row >= length || row < 0 || col >= length || col < 0) continue;
                    n.addNeighbours(matrix[row][col]);
                }
            }
        }
    }

    public void findShortestPath() {

        Node startNode = matrix[sourceYRowPos][sourceXColPos]; // Find and assign the Source Node
        Node endNode = matrix[targetYRowPos][targetXColPos]; // Find and assign the Target Node

        startNode.setGCost(0); // The cost of going from Source to Source is zero.
        startNode.setParent(null); // Setting the parent as null because this is the starting node

        openSet.add(startNode); // Adding the current node to the openSet
        Node currentNode; // Represents the current node that looking at

        /*While openSet is not empty we can keep going*/
        while (!openSet.isEmpty()) {

            currentNode = openSet.poll(); // Retrieves and removes the head of this set.
            /*Checks whether the current looking node is the destination/target node*/
            if (currentNode.equals(endNode)) {
                reConstructPath(currentNode); // Get the original shortest path
                break; // Break the entire loop
            }

            // Get the current node neighbours and check all
            for (Node neighbour : currentNode.getNeighbours()) {
                if (closedSet.contains(neighbour)) continue; // No need to check this.
                if (!neighbour.isVisited() && !neighbour.isBlocked()) {

                    SquaredGrid.colorCheckingNeighbours(neighbour.getYRowNo(), neighbour.getXColNo());
                    // Update the GCost when moving.
                    /*Get the CurrentNodeGCost and and add the Next Node Weight as the Distance*/
                    final double tentativeGScore = currentNode.getGCost() + neighbour.getNodeWeight();
                    /*The new F cost will be the past nodes' GScore + the neighbours distance from the target*/
                    final double newFCost = tentativeGScore + neighbour.getHCost();

                    if (newFCost < neighbour.getFCost()) { // Greedy check
                        neighbour.setParent(currentNode); // Sets came from as the currentNode
                        neighbour.setGCost(tentativeGScore); // Set the temp G value to the new neighbour.
                        neighbour.setFCost(newFCost); // Set the new F Cost to the new neighbour.
                        openSet.add(neighbour); // Now if this is the best we need to check this next
                    }
                }
            }

            currentNode.setVisited(true); // Set the current node as visited
            closedSet.add(currentNode); // add the current node to the evaluated set
        }
    }

    private void reConstructPath(Node node) {

        Node fNode = node; // Assigns the Node without conflicting with the original.
        finalPathNodes.add(fNode); // Adding Target Node first.

        /*Initially we set the starting node parent as null. We need to travers until that*/
        while (fNode.getParent() != null) {
            finalPathNodes.add(fNode); // Push the node to the finalPath
            fNode = fNode.getParent(); // RE-InIt with its parent
        }
    }

    /*Y is the ROW X is the column*/
    private int[] getDirectionYX(Node node, Direction direction) {

        int rowYPos = node.getYRowNo(), colXPos = node.getXColNo();

        switch (direction) {
            case      NORTH: return new int[]{rowYPos - 1, colXPos};
            case       EAST: return new int[]{rowYPos, colXPos + 1};
            case      SOUTH: return new int[]{rowYPos + 1, colXPos};
            case       WEST: return new int[]{rowYPos, colXPos - 1};
            case NORTH_WEST: return new int[]{rowYPos - 1, colXPos - 1};
            case NORTH_EAST: return new int[]{rowYPos - 1, colXPos + 1};
            case SOUTH_EAST: return new int[]{rowYPos + 1, colXPos + 1};
            case SOUTH_WEST: return new int[]{rowYPos + 1, colXPos - 1};
            default:         return new int[]{-1 /*If No Direction*/, -1};
        }

    }

    private double getHeuristicVal(Node cNode, Heuristic type) {

        if (type == Heuristic.MANHATTAN) {
            /*|x1 - x2| + |y1 - y2|*/
            return Math.abs(cNode.getXColNo() - targetXColPos) + Math.abs(cNode.getYRowNo() - targetYRowPos);
        } else if (type == Heuristic.EUCLIDEAN) {
            /*Two dimensional Euclidean*/
            return Math.sqrt(Math.pow(cNode.getXColNo() - targetXColPos, 2) + Math.pow(cNode.getYRowNo() - targetYRowPos, 2));
        } else if (type == Heuristic.CHEBYSHEV) {
            /*Injective metric space. m(|x1 - x2|,|y1 - y2|)*/
            return Math.max(Math.abs(cNode.getXColNo() - targetXColPos), Math.abs(cNode.getYRowNo() - targetYRowPos));
        } else if (type == Heuristic.NONE) {
            return 0.0;
        }

        return 0.0; // Zero H TODO RESOLVE
    }

    /*Return the final constructed path (from Backward) if no path size() == 0*/
    public List<Node> getFinalPathNodes() {
        return finalPathNodes;
    }

    /*Return the generated node matrix[][] array from the weighted graph that given to this class*/
    public Node[][] getMatrix() {
        return matrix;
    }
}
