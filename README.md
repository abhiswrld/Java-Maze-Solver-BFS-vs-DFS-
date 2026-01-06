# Java Maze Solver: BFS vs DFS
An algorithmic pathfinding tool that solves mazes using two different approaches: Depth-First Search (DFS) and Breadth-First Search (BFS). The program runs both algorithms on the same grid to demonstrate the difference in efficiency and path generation.

## Features
* **Algorithm Comparison:** Automatically runs both a Stack-based DFS and a Queue-based BFS.
* **Scoreboard:** Prints a final comparison of steps taken, proving BFS's "shortest path" guarantee.
* **Polymorphism:** Uses a shared `addNeighbors` method that works dynamically for both Stacks and Queues.
* **Path Reconstruction:** Backtracks from the goal to visualize the exact route taken.

## How to Run
1.  Compile the code:
    ```bash
    javac MazeSolver.java
    ```
2.  Run the showdown:
    ```bash
    java MazeSolver
    ```

## Technical Concepts
* **BFS (Queue):** Explores layer-by-layer. Guaranteed to find the shortest path in an unweighted grid.
* **DFS (Stack):** Explores aggressively deep. Often finds *a* path, but rarely the *shortest* path.
* **Time Complexity:** Both run in $O(V + E)$, but BFS is optimal for distance.

## Output Example
```text
>>> Round 1: Depth-First Search (Stack)
Path Found: [DOWN, DOWN, RIGHT, UP, RIGHT, ...]
Steps taken: 84

>>> Round 2: Breadth-First Search (Queue)
Path Found: [DOWN, RIGHT, RIGHT, DOWN, ...]
Steps taken: 42

==================================
       ALGORITHM RESULTS       
==================================
DFS Steps (Random Path):   84
BFS Steps (Shortest Path): 42

WINNER: BFS was more efficient!
