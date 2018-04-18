package algo;

import java.util.*;

public class AStarAlgorithm {

    /*Determining the selected distance based metric for heuristic value*/
    public enum Heuristic {
        MANHATTAN, EUCLIDEAN, CHEBYSHEV
    }

    /*Directions for finding the surrounding(neighbouring) nodes*/
    private enum Direction {
        NORTH, EAST, SOUTH, WEST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST
    }

    private int targetXPos, targetYPos, sourceXPos, sourceYPos; // sX, sY, tX, tY
    private Heuristic selectedMetric; // User selected :: distance based metric calculation method
    private int[][] graph; // Skeleton graph with weights
    private Node[][] matrix; // Node Matrix to holdall the node objects.

    private PriorityQueue<Node> openSet; // OpenSet contains all the node that need to be evaluated.
    private Set<Node> closedSet; // Closed Set stores all the evaluated nodes.
    private List<Node> finalPathNodes; // Stores the final backtracked path.

    public AStarAlgorithm(int[][] graph, int sXPos, int sYPos, int tXPos, int tYPos, Heuristic sMetric) {
        this.graph = graph;
        this.sourceXPos = sXPos;
        this.sourceYPos = sYPos;
        this.targetXPos = tXPos;
        this.targetYPos = tYPos;
        this.selectedMetric = sMetric;

        // Sorted by Ascending order of fCost
        openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));
        closedSet = new HashSet<>();
        finalPathNodes = new ArrayList<>();

        initNodeMatrix();
        addNeighbours();
    }

    private void initNodeMatrix() {

        // Create the matrix array with the same size of graph (x, y)
        matrix = new Node[graph.length][graph.length];
        for (int x = 0; x < graph.length; x++) {
            for (int y = 0; y < graph[x].length; y++) {
                // Creates a new node with x, y coordinates and the blocked status.
                Node node = new Node(x, y, graph[x][y] == 0);
                // Set the heuristic value from this node to the end node.
                node.setHCost(getHeuristicVal(node, selectedMetric));
                // Sets the node weight as given in the actual map.
                node.setNodeWeight(graph[x][y]);
                matrix[x][y] = node /* Adds the newly created node (x,y)*/;
            }
        }
    }

    private void addNeighbours() {

        for (Node[] r /*Each Row*/: matrix) {
            for (Node n /*Each node in Row*/: r) {
                /*Cycle through each direction*/
                for (Direction d : Direction.values()) {
                    int[] dir = getDirectionXY(n/*Check Node*/, d/*Direction*/);
                    try {
                        // Aggressively throws IndexOutOfBoundsException
                        n.addNeighbours(matrix[dir[0 /*Row*/]][dir[1 /*Col*/]]);
                    } catch (IndexOutOfBoundsException e) {
//                        Logger.getLogger(getClass().getName())
//                                .warning("INDEX OUT-OF-RANGE " + d.toString() + " | " + e.getMessage());
                    }
                }
            }
        }
    }

    public void findShortestPath() {

        Node startNode = matrix[sourceXPos][sourceYPos]; // Find and assign the Source Node
        Node endNode = matrix[targetXPos][targetYPos]; // Find and assign the Target Node

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
                System.out.println("Reached To The End"); // Debug Purposes
                closedSet.add(currentNode); // Add to the closed set
                backtrackToOrigin(currentNode); // Get the original shortest path
                break; // Break the entire loop
            }

            // Get the current node neighbours and check all
            for (Node neighbour : currentNode.getNeighbours()) {
                if (!neighbour.isVisited() && !neighbour.isBlocked() && !closedSet.contains(neighbour)) {
                    /*Get the CurrentNodeGCost and and add the Next Node Weight as the Distance*/
                    final double tentativeGScore = currentNode.getGCost() + neighbour.getNodeWeight();
                    /*The new F cost will be the past nodes' GScore + the neighbours distance from the target*/
                    final double newFCost = tentativeGScore + neighbour.getHCost();
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
                        continue;
                    }

                    neighbour.setGCost(tentativeGScore);
                    neighbour.setHCost(newFCost);
                    neighbour.setParent(currentNode);
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

    private int[] getDirectionXY(Node node, Direction direction) {

        /* Current Node X and Y Positions in the Matrix */
        int cNXPos = node.getXPos(), cNYPos = node.getYPos();

        switch (direction) {
            case NORTH:
                return new int[]{--cNXPos, cNYPos};
            case EAST:
                return new int[]{cNXPos, ++cNYPos};
            case SOUTH:
                return new int[]{++cNXPos, cNYPos};
            case WEST:
                return new int[]{cNXPos, --cNYPos};
            case NORTH_WEST:
                return new int[]{--cNXPos, --cNYPos};
            case NORTH_EAST:
                return new int[]{--cNXPos, ++cNYPos};
            case SOUTH_EAST:
                return new int[]{++cNXPos, ++cNYPos};
            case SOUTH_WEST:
                return new int[]{++cNXPos, --cNYPos};
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
