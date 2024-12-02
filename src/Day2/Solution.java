package Day2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day2/input.txt";

    public static void solve(boolean isPart2) {
        int safeCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> inputList = parseLineToList(line);

                if (isDataValid(inputList)) {
                    safeCount++;
                } else {
                    if (isPart2) {
                        for (int i = 0; i < inputList.size(); i++) {
                            List<Integer> modifiedInputList = new ArrayList<>(inputList);
                            modifiedInputList.remove(i);
                            if (isDataValid(modifiedInputList)) {
                                safeCount++;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number of safe inputs: " + safeCount);
    }

    private static List<Integer> parseLineToList(String line) {
        String[] numberStrings = line.split("\\s+");
        List<Integer> numbers = new ArrayList<>();
        for (String num : numberStrings) {
            numbers.add(Integer.parseInt(num));
        }
        return numbers;
    }

    private static boolean isDataValid(List<Integer> inputList) {
        return (isAscending(inputList) || isDescending(inputList)) && isSafeIncrement(inputList);
    }

    private static boolean isAscending(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) > numbers.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDescending(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) < numbers.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSafeIncrement(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            int difference = abs(numbers.get(i) - numbers.get(i + 1));
            if (difference == 0 || difference > 3) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        solve(false);
        System.out.println("--- Part 2 ---");
        solve(true);
    }
}