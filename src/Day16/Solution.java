package Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day16/input.txt";

    public record Position(int x, int y) {
    }

    public record Direction(int xAdd, int yAdd) {
    }

    public record Record(Position position, Direction direction) {
    }

    public record State(Position position, Direction direction, int cost) {
    }

    public static long shortestPath = Integer.MAX_VALUE;

    public static Set<Record> bestSeats = new HashSet<>();

    public static void main(String[] args) {
        List<String> inputLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                inputLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Position reindeerPosition = null;
        Direction reindeerDirection = new Direction(1, 0);

        List<List<Character>> grid = new ArrayList<>();
        for (int i = 0; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'S') {
                    reindeerPosition = new Position(j, i);
                    row.add('.');
                } else {
                    row.add(line.charAt(j));
                }
            }
            grid.add(row);
        }
        int total = solveMaze(reindeerPosition, reindeerDirection, grid);
        System.out.println("Cheapest route: " + total);

        solveMazeForBestSeats(reindeerPosition, reindeerDirection, grid, 0, new ArrayList<>());
        System.out.println(bestSeats.size());
    }

    public static int solveMaze(Position startPosition, Direction startDirection, List<List<Character>> grid) {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(state -> state.cost));
        Set<Record> visited = new HashSet<>();

        // Add the initial position to the priority queue
        pq.offer(new State(startPosition, startDirection, 0));

        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            Position position = currentState.position;
            Direction direction = currentState.direction;
            int score = currentState.cost;

            Record currentRecord = new Record(position, direction);
            // If this state has been visited or is blocked, skip it
            if (visited.contains(currentRecord) || grid.get(position.y).get(position.x) == '#') {
                continue;
            }

            visited.add(currentRecord);

            // If we reach the end, return the score
            if (grid.get(position.y).get(position.x) == 'E') {
                shortestPath = Math.min(shortestPath, score);
                return score;
            }

            // Explore the next states
            int nextScore = score + 1;

            // Move in the current direction
            Position nextPosition1 = new Position(position.x + direction.xAdd, position.y + direction.yAdd);
            if (!visited.contains(new Record(nextPosition1, direction))) {
                pq.offer(new State(nextPosition1, direction, nextScore));
            }

            // Rotate 90 degrees clockwise
            Direction newDirection2 = new Direction(direction.yAdd, -direction.xAdd);
            if (!visited.contains(new Record(position, newDirection2))) {
                pq.offer(new State(position, newDirection2, score + 1000));
            }

            // Rotate 90 degrees counter-clockwise
            Direction newDirection3 = new Direction(-direction.yAdd, direction.xAdd);
            if (!visited.contains(new Record(position, newDirection3))) {
                pq.offer(new State(position, newDirection3, score + 1000));
            }
        }

        return Integer.MAX_VALUE;
    }

    public static int solveMazeForBestSeats(Position position, Direction direction, List<List<Character>> grid, int score, List<Record> history) {
        Record currentRecord = new Record(position, direction);
//        if (solutionBank.containsKey(currentRecord) && score >= solutionBank.get(currentRecord)) {
//            return Long.MAX_VALUE;
//        }
        if (score > shortestPath || history.contains(currentRecord) || grid.get(position.y).get(position.x) == '#') {
            return Integer.MAX_VALUE;
        }
        history.add(currentRecord);
        if (grid.get(position.y).get(position.x) == 'E') {
            shortestPath = Math.min(shortestPath, score);
            return score;
        }

        int total1 = solveMazeForBestSeats(new Position(position.x + direction.xAdd, position.y + direction.yAdd), direction, grid, score+1, new ArrayList<>(history));
        int total2 = solveMazeForBestSeats(position, new Direction(direction.yAdd, -direction.xAdd), grid, score+1000, new ArrayList<>(history));
        int total3 = solveMazeForBestSeats(position, new Direction(-direction.yAdd, direction.xAdd), grid, score+1000, new ArrayList<>(history));

        int minTotal = Math.min(Math.min(total1, total2), total3);
//        if (solutionBank.containsKey(currentRecord) && solutionBank.get(currentRecord) > minTotal) {
//            solutionBank.put(currentRecord, minTotal);
//            System.out.println(minTotal);
//        }
        return minTotal;
    }


}
