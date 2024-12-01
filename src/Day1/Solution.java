package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day1/input.txt";

    private record InputLists(List<Integer> list1, List<Integer> list2) {}

    private static InputLists getInputLists() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(INPUT_FILE_PATH))) {
            while (scanner.hasNextInt()) {
                list1.add(scanner.nextInt());
                list2.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new InputLists(list1, list2);
    }

    public static void part1() {
        InputLists inputLists = getInputLists();
        List<Integer> list1 = inputLists.list1();
        List<Integer> list2 = inputLists.list2();

        Collections.sort(list1);
        Collections.sort(list2);

        int differenceTotal = 0;
        for (int i = 0; i < list1.size(); i++) {
            differenceTotal += abs(list1.get(i) - list2.get(i));
        }
        System.out.println("Total difference: " + differenceTotal);
    }

    public static void part2() {
        InputLists inputLists = getInputLists();
        List<Integer> list1 = inputLists.list1();
        List<Integer> list2 = inputLists.list2();

        int total = 0;
        for (int i = 0; i < list1.size(); i++) {
            int matchCount = 0;
            for (int j = 0; j < list1.size(); j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    matchCount++;
                }
            }
            total += matchCount * list1.get(i);
        }
        System.out.println("Total: " + total);
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        part1();
        System.out.println("--- Part 2 ---");
        part2();
    }
}
