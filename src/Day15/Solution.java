package Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day15/input.txt";
    public record Position(int x, int y) {}
    public record Instruction(int xAdd, int yAdd) {}

    public static void solve1() {
        List<String> inputLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                inputLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Position botPosition = null;
        List<List<Character>> grid = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        for (int i=0; i<inputLines.size(); i++) {
            String line = inputLines.get(i);
            if (line.contains("#") && !line.contains("#####")) {
                List<Character> row = new ArrayList<>();
                for (int j=1; j<line.length()-1; j++) {
                    if (line.charAt(j) == '@') {
                        botPosition = new Position(j-1, i-1);
                        row.add('.');
                    } else {
                        row.add(line.charAt(j));
                    }
                }
                grid.add(row);
            } else if (line.contains("<")) {
                for (int j=0; j<line.length(); j++) {
                    Character instruction = line.charAt(j);
                    if (instruction.equals('^')) {
                        instructions.add(new Instruction(0, -1));
                    } else if(instruction.equals('>')) {
                        instructions.add(new Instruction(1, 0));
                    } else if(instruction.equals('v')) {
                        instructions.add(new Instruction(0, 1));
                    } else {
                        instructions.add(new Instruction(-1, 0));
                    }
                }
            }
        }

        for (Instruction instruction : instructions) {
            Position nextPosition = new Position(botPosition.x + instruction.xAdd, botPosition.y + instruction.yAdd);
            if (isPositionOutOfBounds(nextPosition, grid)) {
                continue;
            }
            Character nextLocation = grid.get(nextPosition.y).get(nextPosition.x);
            if (nextLocation == '.') {
                botPosition = nextPosition;
            } else if (nextLocation == 'O') {
                Position shiftPosition = nextPosition;
                while (true) {
                    shiftPosition = new Position(shiftPosition.x + instruction.xAdd, shiftPosition.y + instruction.yAdd);
                    if (isPositionOutOfBounds(shiftPosition, grid)) {
                        break;
                    }
                    Character shiftLocation = grid.get(shiftPosition.y).get(shiftPosition.x);
                    if (shiftLocation == '#') {
                        break;
                    } else if (shiftLocation == '.') {
                        grid.get(shiftPosition.y).set(shiftPosition.x, 'O');
                        grid.get(nextPosition.y).set(nextPosition.x, '.');
                        botPosition = nextPosition;
                        break;
                    }
                }
            }
        }

        long total = 0L;
        for (int i=0; i<grid.size(); i++) {
            for (int j=0; j<grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == 'O') {
                    total += (j+1) + (100L * (i+1));
                }
            }
        }
        System.out.println("Total: " + total);
    }

    public static boolean isPositionOutOfBounds(Position position, List<List<Character>> grid) {
        return position.x < 0 || position.x >= grid.size() || position.y < 0 || position.y >= grid.size();

    }

    public static void main(String[] args) {
        solve1();
    }
}

