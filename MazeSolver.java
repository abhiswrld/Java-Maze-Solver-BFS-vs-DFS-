import java.util.*;

/**
 * Maze Solver: Algorithm Showdown
 * Compares Depth-First Search (DFS) vs Breadth-First Search (BFS)
 * on the same maze to demonstrate pathfinding efficiency.
 */
public class MazeSolver {
    
    // Global Grid Settings
    static char[][] grid;
    static int height, width;
    static Node start;
    static Node end;

    // We store the maze string globally so we can reload it fresh for each run
    static String rawMaze = 
            "#####################\n" +
            "#A  #   #       #   #\n" +
            "# # # # # ##### # # #\n" +
            "# #   #   #   #   # #\n" +
            "# ####### # # #######\n" +
            "#       # # #       #\n" +
            "####### # # # ##### #\n" +
            "#       #   # #     #\n" +
            "# ########### # #####\n" +
            "# #           #     #\n" +
            "# # ######### ##### #\n" +
            "#   #       #     # #\n" +
            "##### ####### ### # #\n" +
            "#     #     #   #   #\n" +
            "# ##### ### # #######\n" +
            "# #     #   # #     #\n" +
            "# # ##### ### # ### #\n" +
            "# #     #     #   # #\n" +
            "# ##### ######### # #\n" +
            "#                 #B#\n" +
            "#####################";

    public static void main(String[] args) {
        System.out.println("--- GIVEN MAZE ---\n");
        System.out.println(rawMaze + "\n");

        // --- ROUND 1: DFS ---
        System.out.println("Depth-First Search (Stack)");
        parseMaze(); // Reset the grid
        int dfsSteps = solveDFS();
        
        System.out.println("\n--------------------------------------------------\n");

        // --- ROUND 2: BFS ---
        System.out.println("Breadth-First Search (Queue)");
        parseMaze(); // Reset the grid
        int bfsSteps = solveBFS();

        // --- FINAL SCOREBOARD ---
        System.out.println("\n==================================");
        System.out.println("       ALGORITHM RESULTS       ");
        System.out.println("==================================");
        System.out.println("DFS Steps (Random Path):   " + dfsSteps);
        System.out.println("BFS Steps (Shortest Path): " + bfsSteps);
        
        if (bfsSteps < dfsSteps) {
            System.out.println("\nWINNER: BFS was more efficient!");
        } else {
            System.out.println("\nWINNER: It was a tie (rare!)");
        }
    }

    /**
     * Parses the raw string into a 2D char array and finds Start/End.
     */
    public static void parseMaze() {
        String[] lines = rawMaze.split("\n");
        height = lines.length;
        width = lines[0].length();
        grid = new char[height][width];

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                char letter = lines[r].charAt(c);
                grid[r][c] = letter;
                if (letter == 'A') start = new Node(r, c, null, "START");
                if (letter == 'B') end = new Node(r, c, null, "GOAL");
            }
        }
    }

    /**
     * Solves using DFS (Stack).
     * Returns the number of steps in the path found.
     */
    public static int solveDFS() {
        Stack<Node> frontier = new Stack<>();
        frontier.push(start);
        boolean[][] visited = new boolean[height][width];

        while (!frontier.isEmpty()) {
            Node current = frontier.pop();

            // Goal Check
            if (current.r == end.r && current.c == end.c) {
                return printPath(current);
            }

            // Neighbor Logic
            if (!visited[current.r][current.c]) {
                visited[current.r][current.c] = true;
                addNeighborsToCollection(current, visited, frontier);
            }
        }
        System.out.println("No path found.");
        return 0;
    }

    /**
     * Solves using BFS (Queue).
     * Returns the number of steps in the path found.
     */
    public static int solveBFS() {
        Queue<Node> frontier = new LinkedList<>();
        frontier.add(start);
        boolean[][] visited = new boolean[height][width];
        visited[start.r][start.c] = true; // BFS marks visited immediately upon adding

        while (!frontier.isEmpty()) {
            Node current = frontier.remove();

            // Goal Check
            if (current.r == end.r && current.c == end.c) {
                return printPath(current);
            }

            addNeighborsToCollection(current, visited, frontier);
        }
        System.out.println("No path found.");
        return 0;
    }

    /**
     * Shared logic to find neighbors and add them to the frontier (Stack or Queue).
     * Note: We use 'Collection' so it accepts both Stack and Queue!
     */
    public static void addNeighborsToCollection(Node current, boolean[][] visited, Collection<Node> frontier) {
        int[] dr = {-1, 1, 0, 0}; 
        int[] dc = {0, 0, 1, -1};
        String[] moves = {"UP", "DOWN", "RIGHT", "LEFT"};

        for (int i = 0; i < 4; i++) {
            int nr = current.r + dr[i];
            int nc = current.c + dc[i];
            
            if (nr >= 0 && nr < height && nc >= 0 && nc < width) {
                if (grid[nr][nc] != '#' && !visited[nr][nc]) {
                    
                    // For BFS, we mark visited immediately to prevent duplicates in queue
                    if (frontier instanceof Queue) {
                        visited[nr][nc] = true;
                    }
                    
                    frontier.add(new Node(nr, nc, current, moves[i]));
                }
            }
        }
    }

    public static int printPath(Node target) {
        List<String> path = new ArrayList<>();
        Node curr = target;
        while (curr.parent != null) {
            path.add(curr.action);
            curr = curr.parent;
        }
        Collections.reverse(path);
        System.out.println("Path Found: " + path);
        System.out.println("Steps taken: " + path.size());
        return path.size();
    }

    static class Node {
        int r, c;
        Node parent;
        String action;
        public Node(int r, int c, Node parent, String action) {
            this.r = r; this.c = c; this.parent = parent; this.action = action;
        }
    }
}