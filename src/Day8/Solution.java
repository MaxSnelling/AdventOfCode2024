package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day8/input.txt";

    private record Position(int x, int y) {}

    private static boolean isPositionValid(Position antinode1, int xLength, int yLength) {
        return antinode1.x >= 0 && antinode1.x < xLength && antinode1.y >= 0 && antinode1.y < yLength;
    }

    private static void solve(boolean isPart2) {
        int xSize = 0;
        int ySize = 0;
        Map<Character, List<Position>> positionMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            int j = 0;
            while ((line = br.readLine()) != null) {
                xSize = line.length();
                for (int i=0; i<line.length(); i++) {
                    if (line.charAt(i) != '.') {
                        if (positionMap.containsKey(line.charAt(i))) {
                            positionMap.get(line.charAt(i)).add(new Position(i, j));
                        } else {
                            positionMap.put(line.charAt(i), new ArrayList<>(List.of(new Position(i, j))));
                        }
                    }
                }
                j++;
            }
            ySize = j;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Position> antinodes = new HashSet<>();
        for (Character key : positionMap.keySet()) {
            List<Position> positions = positionMap.get(key);
            for (int i=0; i<positions.size()-1; i++) {
                for (int j=i+1; j<positions.size(); j++) {
                    Position position1 = positions.get(i);
                    Position position2 = positions.get(j);

                    for (int k = (isPart2 ? 0 : 1); k<=(isPart2 ? xSize*ySize : 1); k++) {
                        Position antinode1 = new Position(position1.x + k * (position1.x - position2.x), position1.y + k * (position1.y - position2.y));
                        Position antinode2 = new Position(position2.x + k * (position2.x - position1.x), position2.y + k * (position2.y - position1.y));

                        if (isPositionValid(antinode1, xSize, ySize)) {
                            antinodes.add(antinode1);
                        }
                        if (isPositionValid(antinode2, xSize, ySize)) {
                            antinodes.add(antinode2);
                        }
                    }
                }
            }
        }
        System.out.println("Total unique antinodes: " + antinodes.size());
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1 ---");
        solve(false);
        System.out.println("--- Part 2 ---");
        solve(true);
    }
}
