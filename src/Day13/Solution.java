package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final String INPUT_FILE_PATH = "src/Day13/input.txt";
    private record Button(Character name, long xAdd, long yAdd) {}
    private record Game(Button buttonA, Button buttonB, long targetX, long targetY) {}

    public static void main(String[] args) {
        List<Game> games = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            ArrayList<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            int index = 0;
            while(true) {
                String line1 = lines.get(index);
                String line2 = lines.get(index + 1);
                String line3 = lines.get(index + 2);
                index += 4;

                String[] split = line1.split("\\+");
                long yAdd = Long.parseLong(split[split.length - 1]);
                long xAdd = Long.parseLong(split[split.length - 2].split(",")[0]);
                Button buttonA = new Button('A', xAdd, yAdd);

                split = line2.split("\\+");
                yAdd = Long.parseLong(split[split.length - 1]);
                xAdd = Long.parseLong(split[split.length - 2].split(",")[0]);
                Button buttonB = new Button('B', xAdd, yAdd);

                long targetX = Long.parseLong(line3.split("=")[1].split(",")[0]) + 10000000000000L;
                long targetY = Long.parseLong(line3.split("=")[2]) + 10000000000000L;

                games.add(new Game(buttonA, buttonB, targetX, targetY));
                if (index >= lines.size()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long tokens = 0;
        for (Game game : games) {
            double bPressesNumerator = game.targetY - ((double) (game.buttonA.yAdd * game.targetX) / game.buttonA.xAdd);
            double bPressesDenominator = game.buttonB.yAdd - ((double) (game.buttonA.yAdd * game.buttonB.xAdd) / game.buttonA.xAdd);
            double bPresses = bPressesNumerator / bPressesDenominator;
            double aPresses = (game.targetX - bPresses * game.buttonB.xAdd) / game.buttonA.xAdd;

            long aRounded = Math.round(aPresses);
            long bRounded = Math.round(bPresses);
            if (aRounded * game.buttonA.xAdd + bRounded * game.buttonB.xAdd == game.targetX &&
                    aRounded * game.buttonA.yAdd + bRounded * game.buttonB.yAdd == game.targetY ){
                tokens += aRounded*3 + bRounded;
            }
        }
        System.out.println(tokens);
    }
}
