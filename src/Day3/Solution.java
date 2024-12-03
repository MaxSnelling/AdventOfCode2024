package Day3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day3/input.txt";

    private static void solve(boolean isPart2) {
        String input = "";
        try {
            input = Files.readString(Path.of(INPUT_FILE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isPart2) {
            input = "do()" + input;
            String[] dontSections = input.split("don't\\(\\)");
            StringBuilder doSections = new StringBuilder();
            for (String section : dontSections) {
                if (section.contains("do()")) {
                    doSections.append(section.substring(section.indexOf("do()")));
                }
            }
            input = doSections.toString();
        }

        String mulPattern = "mul\\((\\d+),(\\d+)\\)";
        Pattern regex = Pattern.compile(mulPattern);
        Matcher matcher = regex.matcher(input);

        int multiplyTotal = 0;
        while (matcher.find()) {
            multiplyTotal += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        System.out.println("Multiply total: " + multiplyTotal);
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        solve(false);
        System.out.println("--- Part 2 ---");
        solve(true);
    }
}
