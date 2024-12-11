package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day11/test.txt";
    public static Map<Record, Long> solutionBank = new HashMap<>();
    public record Record(String rock, long blinks) {}

    public static void main(String[] args) {
        List<String> rocks = new ArrayList<>();
        try {
            String content = Files.readString(Paths.get(INPUT_FILE_PATH));
            rocks.addAll(Arrays.asList(content.split("\\s+")));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        long totalP1 = 0;
        long totalP2 = 0;
        for (String rock : rocks) {
            totalP1 += solve(rock, 25);
        }
        for (String rock : rocks) {
            totalP2 += solve(rock, 75);
        }
        System.out.println("Total Part 1: " + totalP1 + ", Part 2: " + totalP2);
    }

    private static long solve(String rock, long blinks) {
        Record record = new Record(rock, blinks);
        if (solutionBank.containsKey(record)) {
            return solutionBank.get(record);
        } else if (rock.isEmpty()) {
            return 0;
        } else if (blinks == 0) {
            return 1;
        }

        String left;
        String right = "";
        if (rock.equals("0")) {
            left = "1";
        } else if (rock.length() % 2 == 0) {
            left = rock.substring(0, rock.length() / 2);
            right = String.valueOf(Long.parseLong(rock.substring(rock.length() / 2)));
        } else {
            left = String.valueOf(Long.parseLong(rock) * 2024);
        }
        solutionBank.put(record, solve(left, blinks-1) + solve(right, blinks-1));
        return solutionBank.get(record);
    }
}