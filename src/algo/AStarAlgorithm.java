package algo;

import gui.SquaredGrid;

import java.util.*;
import java.util.logging.Logger;

public class AStarAlgorithm {

    /*Determining the selected distance based metric for heuristic value*/
    public enum Heuristic {
        MANHATTAN, EUCLIDEAN, CHEBYSHEV
    }

    /*Directions for finding the surrounding(neighbouring) nodes*/
    private enum Direction {
        NORTH, EAST, SOUTH, WEST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST
    }

    private int sourceYPos, sourceXPos, targetYPos, targetXPos; // Row|Col Row|Col
    private Heuristic selectedMetric; // User selected :: distance based metric calculation method
    private int[][] graph; // Skeleton graph with weights
    private Node[][] matrix; // Node Matrix to holdall the node objects.

    private PriorityQueue<Node> openSet; // OpenSet contains all the node that need to be evaluated.
    private Set<Node> closedSet; // Closed Set stores all the evaluated nodes.
    private List<Node> finalPathNodes; // Stores the final backtracked path.

    SquaredGrid grid;

    public AStarAlgorithm(int[][] graph, int sYPos /*Row*/, int sXPos/*Col*/,
                          int tYPos/*Row*/, int tXPos/*Col*/, Heuristic sMetric, SquaredGrid grid) {
        this.graph = graph;
        this.sourceYPos = sYPos;
        this.sourceXPos = sXPos;
        this.targetYPos = tYPos;
        this.targetXPos = tXPos;
        this.selectedMetric = sMetric;
        this.grid = grid;

        // Sorted by Ascending order of fCost
        openSet = new PriorityQueue<>(new Comparator<Node>() {

            //overriding the compare method to compare two nodes from the fCost
            @Override
            public int compare(Node current, Node next) {
                if (current.getFCost() < next.getFCost()) {
                    return -1;
                } else if (current.getFCost() > next.getFCost()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        closedSet = new HashSet<>();
        finalPathNodes = new ArrayList<>();

        initNodeMatrix();
        addNeighbours();
    }

    private void initNodeMatrix() {

        // Create the matrix array with the same size of graph (y,x)
        matrix = new Node[graph.length][graph.length];

        for (int y = 0; y < graph.length; y++) {
            for (int x = 0; x < graph[y].length; x++) {
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
                    try {
                        // Aggressively throws IndexOutOfBoundsException
                        n.addNeighbours(matrix[dir[0 /*Row*/]][dir[1 /*Col*/]]);
                    } catch (IndexOutOfBoundsException e) {
                        Logger.getLogger(getClass().getName())
                                .warning("INDEX OUT-OF-RANGE " + d.toString() + " | " + e.getMessage());
                    }
                }
            }
        }
    }

    public void findShortestPath() {

        Node startNode = matrix[sourceYPos][sourceXPos]; // Find and assign the Source Node
        Node endNode = matrix[targetYPos][targetXPos]; // Find and assign the Target Node

        startNode.setGCost(0); // The cost of going from Source to Source is zero.
        startNode.setParent(null); // Setting the parent as null because this is the sourcePos

        openSet.add(startNode); // Adding the current node to the openSet
        Node currentNode; // Represents the current node that looking at

        /*While openSet is not empty we can keep going*/
        while (!openSet.isEmpty()) {

            currentNode = openSet.poll();
            currentNode.setVisited(true);
            closedSet.add(currentNode);

            /*Checks whether the current looking node is the destination/target node*/
            if (currentNode.equals(endNode) || currentNode.getHCost() == 0) {
                System.out.println("Reached To The End"); // For Debug Purposes
                closedSet.add(currentNode); // Add to the closed set
                backtrackToOrigin(currentNode); // Get the original shortest path
                break; // Break the entire loop
            }

            // Get the current node neighbours and check all
            for (Node neighbour : currentNode.getNeighbours()) {
                if (!neighbour.isVisited() && !neighbour.isBlocked() && !closedSet.contains(neighbour)) {

                    grid.colorNeighbours(neighbour.getYPos(), neighbour.getXPos());
                    /*Get the CurrentNodeGCost and and add the Next Node Weight as the Distance*/
                    final double tentativeGScore = currentNode.getGCost() + neighbour.getNodeWeight();
                    /*The new F cost will be the past nodes' GScore + the neighbours distance from the target*/
                    final double newFCost = neighbour.getGCost() + neighbour.getHCost();
//                    if (!openSet.contains(neighbour)) {
//                        openSet.add(neighbour);
//                    } else if (newFCost < neighbour.getGCost()) {
//                        neighbour.setGCost(tentativeGScore);
//                        neighbour.setParent(currentNode);
//
//                        if (!openSet.contains(neighbour))
//                            openSet.add(neighbour); // Adding the next neighbour
//                    }
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    } else if (newFCost >= neighbour.getGCost()) {
                        // Bad Move
                        continue;
                    }

                    neighbour.setParent(currentNode);
                    neighbour.setGCost(tentativeGScore);
                    neighbour.setFCost(newFCost);
                }
            }
        }
    }

    private void backtrackToOrigin(Node node) {

        Node fNode = node; // Assigns the Node without conflicting with the original.
        finalPathNodes.add(fNode); // Adding Target Node first.

        while (fNode.getParent() != null) {
            fNode = fNode.getParent();
            finalPathNodes.add(fNode);
        }
    }

    /*Y is the ROW X is the column*/
    private int[] getDirectionYX(Node node, Direction direction) {

        /* Current Node X and Y Positions in the Matrix */
        int rowYPos = node.getYPos(), colXPos = node.getXPos();

        switch (direction) {
            case NORTH:
                return new int[]{rowYPos - 1, colXPos};
            case EAST:
                return new int[]{rowYPos, colXPos + 1};
            case SOUTH:
                return new int[]{rowYPos + 1, colXPos};
            case WEST:
                return new int[]{rowYPos, colXPos - 1};
            case NORTH_WEST:
                return new int[]{rowYPos - 1, colXPos - 1};
            case NORTH_EAST:
                return new int[]{rowYPos - 1, colXPos + 1};
            case SOUTH_EAST:
                return new int[]{rowYPos + 1, colXPos + 1};
            case SOUTH_WEST:
                return new int[]{rowYPos + 1, colXPos - 1};
            default:
                return new int[]{-1 /*If No Direction*/, -1};
        }

    }

    private double getHeuristicVal(Node cNode, Heuristic type) {

        if (type == Heuristic.MANHATTAN) {
            return Math.abs(cNode.getXPos() - targetXPos) + Math.abs(cNode.getYPos() - targetYPos);
        } else if (type == Heuristic.EUCLIDEAN) {
            return Math.sqrt(Math.pow(cNode.getXPos() - targetXPos, 2) + Math.pow(cNode.getYPos() - targetYPos, 2));
        } else if (type == Heuristic.CHEBYSHEV) {
            return Math.max(Math.abs(cNode.getXPos() - targetXPos), Math.abs(cNode.getYPos() - targetYPos));
        } else {
            throw new UnsupportedOperationException("Invalid Heuristic Type.\n" +
                    "Supported Types: AStarAlgorithm.Heuristic.? (MANHATTAN, EUCLIDEAN, CHEBYSHEV)");
        }
    }

    public List<Node> getFinalPathNodes() {
        return finalPathNodes;
    }

    public Node[][] getMatrix() {
        return matrix;
    }
}
