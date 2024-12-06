package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day6/input.txt";

    public static void main(String[] args) {
        int currentX = 0;
        int currentY = 0;
        double orientation = 0;
        int maxX = 0;
        int maxY = 0;
        List<List<Integer>> obstacles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                maxX = line.length();
                maxY++;
                List<Integer> obstaclesRow = new ArrayList<>();
                for (int i=0; i<line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        obstaclesRow.add(i);
                    } else if (line.charAt(i) != '.') {
                        currentX = i;
                        currentY = obstacles.size();
                    }
                }
                obstacles.add(obstaclesRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int initialX = currentX;
        int initialY = currentY;

        Set<String> guardLocations = new HashSet<>();
        while (true) {
            guardLocations.add(currentX + "," +currentY);
            int nextX = currentX + (int) Math.round(Math.sin(orientation));
            int nextY = currentY - (int) Math.round(Math.cos(orientation));
            if (nextX == maxX || nextY == maxY || nextX == -1 || nextY == -1) {
                break;
            } else if (obstacles.get(nextY).contains(nextX)) {
                orientation = (orientation + Math.PI / 2) % (2 * Math.PI);
            } else {
                currentX = nextX;
                currentY = nextY;
            }
        }
        System.out.println("Guard locations visited: " + guardLocations.size());

        int loopCounter = 0;
        for (int i=0; i<maxX; i++) {
            for (int j=0; j<maxY; j++) {
                currentX = initialX;
                currentY = initialY;
                int orientationInt = 0;
                if (!obstacles.get(j).contains(i) && !(initialX == i && initialY == j)) {
                    obstacles.get(j).add(i);
                    Set<String> history = new HashSet<>();
                    while (true) {
                        if (history.contains("x:" + currentX + "y:" + currentY + "o:" + orientationInt)) {
                            loopCounter++;
                            break;
                        } else {
                            history.add("x:" + currentX + "y:" + currentY + "o:" + orientationInt);
                        }
                        int nextX = currentX + (int) Math.round(Math.sin(orientationInt * Math.PI / 2));
                        int nextY = currentY - (int) Math.round(Math.cos(orientationInt * Math.PI / 2));
                        if (nextX == maxX || nextY == maxY || nextX == -1 || nextY == -1) {
                            break;
                        } else if (obstacles.get(nextY).contains(nextX)) {
                            orientationInt = (orientationInt + 1) % 4;
                        } else {
                            currentX = nextX;
                            currentY = nextY;
                        }
                    }
                    obstacles.get(j).remove(Integer.valueOf(i));
                }
            }
        }
        System.out.println("Obstacle count: " + loopCounter);
    }
}