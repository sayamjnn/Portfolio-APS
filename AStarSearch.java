import java.util.*;

public class AStarSearch {

    static class Node {
        int x, y;
        int g; // Cost from start to this node
        int h; // Heuristic cost to goal
        int f; // Total cost (g + h)
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.g = 0;
            this.h = 0;
            this.f = 0;
            this.parent = null;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static List<Node> astarSearch(int[][] grid, Node start, Node goal) {
        List<Node> path = new ArrayList<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a.f));
        Set<Node> closedSet = new HashSet<>();

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                // Reconstruct path
                Node node = current;
                while (node != null) {
                    path.add(node);
                    node = node.parent;
                }
                Collections.reverse(path);
                System.out.println("Total distance: " + current.g);
                return path;
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(grid, current)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int weight = grid[neighbor.x][neighbor.y];
                int tentative_g = current.g + weight;

                if (!openSet.contains(neighbor) || tentative_g < neighbor.g) {
                    neighbor.g = tentative_g;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    private static List<Node> getNeighbors(int[][] grid, Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0}; // Directions: Up, Down, Left, Right
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newX = node.x + dx[i];
            int newY = node.y + dy[i];

            if (isValid(grid, newX, newY)) {
                neighbors.add(new Node(newX, newY));
            }
        }
        return neighbors;
    }

    private static boolean isValid(int[][] grid, int x, int y) {
        int rows = grid.length;
        int cols = grid[0].length;
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] != -1; // Assuming -1 represents an obstacle
    }

    private static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Manhattan distance heuristic
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0, 1, 4, -1, -1},
            {-1, 0, 2, 5, 12},
            {-1, -1, 0, 2, -1},
            {-1, -1, -1, 0, 3},
            {-1, -1, -1, -1, 0}
        };

        Node start = new Node(0, 0);
        Node goal = new Node(4, 4);

        List<Node> path = astarSearch(grid, start, goal);
        if (path != null) {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}
