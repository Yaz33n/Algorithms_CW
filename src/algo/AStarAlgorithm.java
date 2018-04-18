package algo;

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

    private int targetXPos, targetYPos;
    private int sourceXPos, sourceYPos;
    private int[][] graph;
    private Heuristic selectedMetric;
    private Node[][] matrix;

    public AStarAlgorithm(int[][] graph, int sourceXPos, int sourceYPos,
                          int targetXPos, int targetYPos, Heuristic selectedMetric) {
        this.graph = graph;
        this.selectedMetric = selectedMetric;
        this.sourceXPos = sourceXPos;
        this.sourceYPos = sourceYPos;
        this.targetXPos = targetXPos;
        this.targetYPos = targetYPos;

        initNodeMatrix();
        addNeighbours();
    }

    private void initNodeMatrix() {

        matrix = new Node[graph.length][graph.length];

        for (int x = 0; x < graph.length; x++) {
            for (int y = 0; y < graph[x].length; y++) {
                Node node = new Node(x, y, graph[x][y] == 0);
                node.setHCost(getHeuristicVal(node, selectedMetric));
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
                        Logger.getLogger(getClass().getName())
                                .warning("INDEX OUT-OF-RANGE " + d.toString() + " | " + e.getMessage());
                    }
                }
            }
        }
    }

    public void printMatrix() {
//        for (Node[] a : matrix) {
//            for (Node n : a) {
//                for (Node nei : n.getNeighbours()) {
//                    System.out.println(nei);
//                }
//            }
//        }

        System.out.println("length " + matrix[0][0].getNeighbours().size());
        for (Node nei : matrix[0][0].getNeighbours()) {
            System.out.println(nei);
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


}
