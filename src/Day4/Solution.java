package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day4/input.txt";

    private static void solvePart1() {
        List<String> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                input.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int wordCount = 0;
        int height = input.size();
        int width = input.getFirst().length();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (input.get(i).charAt(j) == 'X') {
                    for (int k = 0; k < 8; k++) {
                        int xAngle = (int) Math.round(Math.cos(k * Math.PI / 4));
                        int yAngle = (int) Math.round(Math.sin(k * Math.PI / 4));
                        int xIndex = j + xAngle;
                        int yIndex = i + yAngle;
                        if (isPositionValid(xIndex, yIndex, width, height) &&
                                input.get(yIndex).charAt(xIndex) == 'M') {
                            xIndex = j + 2 * xAngle;
                            yIndex = i + 2 * yAngle;
                            if (isPositionValid(xIndex, yIndex, width, height) &&
                                    input.get(yIndex).charAt(xIndex) == 'A') {
                                xIndex = j + 3 * xAngle;
                                yIndex = i + 3 * yAngle;
                                if (isPositionValid(xIndex, yIndex, width, height) &&
                                        input.get(yIndex).charAt(xIndex) == 'S') {
                                    wordCount++;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Total XMAS count: " + wordCount);
    }

    public static void solvePart2() {
        List<String> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                input.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int wordCount = 0;
        int height = input.size();
        int width = input.getFirst().length();
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (input.get(i).charAt(j) == 'A') {
                    Character[] cornerValues = new Character[4];
                    for (int k = 0; k < 4; k++) {
                        int xAngle = (int) Math.round(Math.cos(Math.PI / 4 + k * Math.PI / 2));
                        int yAngle = (int) Math.round(Math.sin(Math.PI / 4 + k * Math.PI / 2));
                        int xIndex = j + xAngle;
                        int yIndex = i + yAngle;
                        cornerValues[k] = input.get(yIndex).charAt(xIndex);
                    }
                    if ((cornerValues[0] == 'M' && cornerValues[2] == 'S' ||
                            cornerValues[0] == 'S' && cornerValues[2] == 'M')
                            && (cornerValues[1] == 'M' && cornerValues[3] == 'S' ||
                            cornerValues[1] == 'S' && cornerValues[3] == 'M')) {
                        wordCount++;
                    }
                }
            }
        }
        System.out.println("Total X-MAS count: " + wordCount);
    }

    private static boolean isPositionValid(int x, int y, int xLim, int yLim) {
        return x >= 0 && y >= 0 && x <= xLim - 1 && y <= yLim - 1;
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        solvePart1();
        System.out.println("--- Part 2 ---");
        solvePart2();
    }
}
