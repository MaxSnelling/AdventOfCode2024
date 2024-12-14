package Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day14/input.txt";
    public static int WIDTH = 101;
    public static int HEIGHT = 103;

    public static void solvePart1() {
        List<Robot> robots = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitInput = line.split("=");
                int xPos = Integer.parseInt(splitInput[1].split(",")[0]);
                int yPos = Integer.parseInt(splitInput[1].split(",")[1].split(" ")[0]);
                int xVelocity = Integer.parseInt(splitInput[2].split(",")[0]);
                int yVelocity = Integer.parseInt(splitInput[2].split(",")[1]);
                robots.add(new Robot(xPos, yPos, xVelocity, yVelocity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<100; i++) {
            for (Robot robot : robots) {
                int newXPosition = normalise(robot.xPos + robot.xVelocity, WIDTH);
                int newYPosition = normalise(robot.yPos + robot.yVelocity, HEIGHT);
                robot.xPos = newXPosition;
                robot.yPos = newYPosition;
            }
        }

        int q1Count = 0;
        int q2Count = 0;
        int q3Count = 0;
        int q4Count = 0;
        for (Robot robot : robots) {
            if (robot.xPos < WIDTH / 2) {
                if (robot.yPos < HEIGHT / 2) {
                    q1Count++;
                } else if (robot.yPos > (HEIGHT / 2)) {
                    q2Count++;
                }
            } else if (robot.xPos > (WIDTH / 2)) {
                if (robot.yPos < HEIGHT / 2) {
                    q3Count++;
                } else if (robot.yPos > (HEIGHT / 2)) {
                    q4Count++;
                }
            }
        }
        int total = q1Count * q2Count * q3Count * q4Count;
        System.out.println("Total: " + total);
    }

    public static int normalise(int position, int limit) {
        if (position < 0) {
            return position + limit;
        }
        return position % limit;
    }

    private static void solvePart2() {
        List<Robot> robots = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitInput = line.split("=");
                int xPos = Integer.parseInt(splitInput[1].split(",")[0]);
                int yPos = Integer.parseInt(splitInput[1].split(",")[1].split(" ")[0]);
                int xVelocity = Integer.parseInt(splitInput[2].split(",")[0]);
                int yVelocity = Integer.parseInt(splitInput[2].split(",")[1]);
                robots.add(new Robot(xPos, yPos, xVelocity, yVelocity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = 0;
        while (true) {
            index++;
            for (Robot robot : robots) {
                int newXPosition = normalise(robot.xPos + robot.xVelocity, WIDTH);
                int newYPosition = normalise(robot.yPos + robot.yVelocity, HEIGHT);
                robot.xPos = newXPosition;
                robot.yPos = newYPosition;
            }

            if (areLongXYLines(robots)) {
                System.out.println("First index of tree: " + index);
                break;
            }
        }
    }

    public static boolean areLongXYLines(List<Robot> robots) {
        Map<Integer, List<Integer>> verticalLines = new HashMap<>();
        Map<Integer, List<Integer>> horizontalLines = new HashMap<>();

        for (Robot robot : robots) {
            verticalLines.putIfAbsent(robot.xPos, new ArrayList<>());
            verticalLines.get(robot.xPos).add(robot.yPos);

            horizontalLines.putIfAbsent(robot.yPos, new ArrayList<>());
            horizontalLines.get(robot.yPos).add(robot.xPos);
        }

        int maxVertical = 0;
        int maxHorizontal = 0;

        // Find the longest vertical line
        for (Map.Entry<Integer, List<Integer>> xCoord : verticalLines.entrySet()) {
            List<Integer> yCoordinates = xCoord.getValue();
            Collections.sort(yCoordinates);
            int currentMax = 1;
            for (int i = 1; i < yCoordinates.size(); i++) {
                if (yCoordinates.get(i) == yCoordinates.get(i - 1) + 1) {
                    currentMax++;
                } else {
                    maxVertical = Math.max(maxVertical, currentMax);
                    currentMax = 1;
                }
            }
            maxVertical = Math.max(maxVertical, currentMax);
        }

        // Find the longest horizontal line
        for (Map.Entry<Integer, List<Integer>> yCoord : horizontalLines.entrySet()) {
            List<Integer> xCoordinates = yCoord.getValue();
            Collections.sort(xCoordinates);
            int currentMax = 1;
            for (int i = 1; i < xCoordinates.size(); i++) {
                if (xCoordinates.get(i) == xCoordinates.get(i - 1) + 1) {
                    currentMax++;
                } else {
                    maxHorizontal = Math.max(maxHorizontal, currentMax);
                    currentMax = 1;
                }
            }
            maxHorizontal = Math.max(maxHorizontal, currentMax);
        }

        if (maxVertical > 10 && maxHorizontal > 10) {
            System.out.println("Longest vertical line length: " + maxVertical);
            System.out.println("Longest horizontal line length: " + maxHorizontal);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        solvePart1();
        solvePart2();
    }
}
