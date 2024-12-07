package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day7/input.txt";
    public static boolean isPart2 = false;

    public static boolean canMatchTarget(List<Integer> numbers, long target, int index, long current) {
        // Base case: if we've used all numbers, check if we hit the target
        if (index == numbers.size() - 1) {
            return current == target;
        // Stop calculating if we're already over the target
        } else if (current > target) {
            return false;
        }

        // Try the next operator with the next number
        int nextNumber = numbers.get(index + 1);

        // Try addition
        if (canMatchTarget(numbers, target, index + 1, current + nextNumber)) {
            return true;
        }

        // Try multiplication
        if (canMatchTarget(numbers, target, index + 1, current * nextNumber)) {
            return true;
        }

        // Try combining
        if (isPart2 && canMatchTarget(numbers, target, index + 1, combine(current, nextNumber))) {
            return true;
        }

        return false;
    }

    private static long combine(long nextNumber, long nextNextNumber) {
        return Long.parseLong(Long.toString(nextNumber) + Long.toString(nextNextNumber));
    }

    public static void main(String[] args) {
        long part1Total = 0;
        long part2Total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitInput = line.split(":");
                long targetNumber = Long.parseLong(splitInput[0].trim());

                String[] numberStrs = splitInput[1].trim().split(" ");
                ArrayList<Integer> numbers = new ArrayList<>();
                for (String numberStr : numberStrs) {
                    numbers.add(Integer.parseInt(numberStr));
                }

                isPart2 = false;
                if (canMatchTarget(numbers, targetNumber, 0, numbers.getFirst())) {
                    part1Total += targetNumber;
                }
                isPart2 = true;
                if (canMatchTarget(numbers, targetNumber, 0, numbers.getFirst())) {
                    part2Total += targetNumber;
                }
            }
            System.out.println("Part 1 total: " + part1Total);
            System.out.println("Part 2 total: " + part2Total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
