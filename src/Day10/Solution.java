package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day10/input.txt";
    static int[][] directions = {
            {0, 1},  // Right
            {1, 0},  // Down
            {0, -1}, // Left
            {-1, 0}  // Up
    };
    static List<List<int[]>> allPaths = new ArrayList<>();
    static Map<Coordinate, Set<Coordinate>> trailheads = new HashMap<>();
    static int totalRouteCount = 0;

    public record Coordinate(int x, int y) {}

    public static void main(String[] args) {
        List<int[]> gridList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Convert each line into an integer array
                int[] row = line.chars().map(c -> c - '0').toArray();
                gridList.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<gridList.size(); i++) {
            for (int j=0; j<gridList.get(i).length; j++) {
                if (gridList.get(i)[j] == 0) {
                    List<int[]> currentPath = new ArrayList<>();
                    boolean[][] visited = new boolean[gridList.get(0).length][gridList.size()];
                    findPaths(gridList, j, i, 0, currentPath, visited);
                }
            }
        }

        int trailheadCount=0;
        for (Coordinate destination : trailheads.keySet()) {
            trailheadCount += trailheads.get(destination).size();
        }
        System.out.println("Total trailheads: " + trailheadCount);
        System.out.println("Total route count: " + totalRouteCount);

    }


    private static void findPaths(List<int[]> grid, int x, int y, int target, List<int[]> currentPath, boolean[][] visited) {
        if (x < 0 || y >= grid.size() || y < 0 || x >= grid.getFirst().length || visited[x][y] || grid.get(y)[x] != target) {
            return;
        }

        currentPath.add(new int[]{x, y});
        visited[x][y] = true;

        if (grid.get(y)[x] == 9 && target == 9) {
            Coordinate initialPosition = new Coordinate(currentPath.getFirst()[0], currentPath.getFirst()[1]);
            Coordinate finalPosition = new Coordinate(x, y);
            if (trailheads.containsKey(initialPosition)) {
                Set<Coordinate> destinations = trailheads.get(initialPosition);
                destinations.add(finalPosition);
                trailheads.put(initialPosition, destinations);
            } else {
                trailheads.put(initialPosition, new HashSet<>(Set.of(finalPosition)));
            }
            totalRouteCount++;
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                findPaths(grid, newX, newY, target + 1, currentPath, visited);
            }
        }

        currentPath.removeLast();
        visited[x][y] = false;
    }
}
