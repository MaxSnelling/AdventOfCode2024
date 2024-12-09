package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day9/input.txt";

    private static void solvePart1() {
        List<Integer> fullInput = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line = br.readLine();

            for (int i=0; i<line.length(); i++) {
                int intValue = Integer.parseInt(String.valueOf(line.charAt(i)));
                int inputValue;
                if (i%2 == 0) {
                    inputValue = i / 2;
                } else {
                    inputValue = -1;
                }

                for (int j=0; j<intValue; j++) {
                    fullInput.add(inputValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rightIndex = fullInput.size()-1;
        int leftIndex = 0;
        while (leftIndex < rightIndex) {
            if (fullInput.get(leftIndex) != -1) {
                leftIndex++;
            } else if (fullInput.get(rightIndex) == -1) {
                rightIndex--;
            } else {
                fullInput.set(leftIndex, fullInput.get(rightIndex));
                fullInput.set(rightIndex, -1);
            }
        }

        long checksum = 0;
        for (int i=0; i<fullInput.size(); i++) {
            if (fullInput.get(i) == -1) {
                break;
            }
            checksum += (long) i * fullInput.get(i);
        }
        System.out.println("Checksum: " + checksum);
    }

    private record File(int fileId, int size) {}

    private static void solvePart2() {
        List<File> fullInput = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line = br.readLine();

            for (int i = 0; i < line.length(); i++) {
                int intValue = Integer.parseInt(String.valueOf(line.charAt(i)));
                int inputValue;
                if (i % 2 == 0) {
                    inputValue = i / 2;
                } else {
                    inputValue = -1;
                }
                fullInput.add(new File(inputValue, intValue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            for (int i = fullInput.size() - 1; i > 0; i--) {
                if (fullInput.get(i).fileId != -1) {
                    for (int j = 0; j < i; j++) {
                        if (fullInput.get(j).fileId == -1 &&
                                fullInput.get(i).size <= fullInput.get(j).size) {
                            File spaceFile = fullInput.get(j);
                            File movedFile = fullInput.get(i);
                            File newSpaceFile = new File(-1, spaceFile.size - movedFile.size);
                            fullInput.set(i, new File(-1, movedFile.size));
                            fullInput.remove(j);
                            fullInput.add(j, movedFile);
                            if (newSpaceFile.size < spaceFile.size) {
                                fullInput.add(j + 1, newSpaceFile);
                            }
                            break;
                        }
                    }
                }
            }
            break;
        }

        long checksum = 0;
        int index = 0;
        for (int i=0; i<fullInput.size(); i++) {
            File file = fullInput.get(i);
            if (file.fileId == -1) {
                index += file.size;
            } else {
                for (int j=0; j<file.size; j++) {
                    checksum += (long) index * file.fileId;
                    index++;
                }
            }
        }
        System.out.println("Checksum: " + checksum);
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        solvePart1();
        System.out.println("--- Part 2 ---");
        solvePart2();
    }
}