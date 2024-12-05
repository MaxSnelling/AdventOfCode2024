package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day5/input.txt";

    private static void solvePart1() {
        List<Integer[]> priorityMap = getPriorityMap();

        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(",")) {
                    List<Integer> numbers = inputToList(line);

                    boolean inputFails = false;
                    for (int i = 0; i < priorityMap.size(); i++) {
                        int value1 = priorityMap.get(i)[0];
                        int value2 = priorityMap.get(i)[1];
                        if (numbers.contains(value1) &&
                                numbers.contains(value2) &&
                                numbers.indexOf(value1) > numbers.indexOf(value2)) {
                            inputFails = true;
                            break;
                        }
                    }

                    if (!inputFails) {
                        total += numbers.get(numbers.size() / 2);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Correct total: " + total);
    }

    private static List<Integer> inputToList(String line) {
        String[] inputParts = line.split(",");
        List<Integer> numbers = new ArrayList<>();
        for (String part : inputParts) {
            try {
                numbers.add(Integer.parseInt(part));
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + part);
            }
        }
        return numbers;
    }

    private static void solvePart2() {
        List<Integer[]> priorityMap = getPriorityMap();

        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(",")) {
                    List<Integer> numbers = inputToList(line);

                    boolean inputFails = false;
                    while (true) {
                        boolean shortTermFail = false;
                        for (int i = 0; i < priorityMap.size(); i++) {
                            int value1 = priorityMap.get(i)[0];
                            int value2 = priorityMap.get(i)[1];
                            if (numbers.contains(value1) &&
                                    numbers.contains(value2) &&
                                    numbers.indexOf(value1) > numbers.indexOf(value2)) {
                                inputFails = true;
                                shortTermFail = true;
                                int index1 = numbers.indexOf(value1);
                                numbers.set(numbers.indexOf(value2), value1);
                                numbers.set(index1, value2);
                            }
                        }
                        if (!shortTermFail) {
                            break;
                        }
                    }

                    if (inputFails) {
                        total += numbers.get(numbers.size() / 2);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Incorrect: " + total);
    }

    private static List<Integer[]> getPriorityMap() {
        List<Integer[]> priorityMap = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    try {
                        priorityMap.add(new Integer[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priorityMap;
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        solvePart1();
        System.out.println("--- Part 2 ---");
        solvePart2();
    }
}

// 3368 too low
// 9736 too high
