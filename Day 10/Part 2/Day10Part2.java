import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Learnt a lot. Highest time ever spent on a single day problem.
public class Day10Part2 {
    static boolean[][] dpMap = new boolean[140][140];

    public static enum PipeDirections {
        NS('|', new char[] { 'N', 'S' }),
        EW('-', new char[] { 'E', 'W' }),
        NE('L', new char[] { 'N', 'E' }),
        NW('J', new char[] { 'N', 'W' }),
        SW('7', new char[] { 'S', 'W' }),
        SE('F', new char[] { 'S', 'E' }),
        NONE('.', null),
        CURRENT_POSITION('S', new char[] { 'N', 'S', 'E', 'W' });

        private char symbol;
        private char[] directions;

        PipeDirections(char symbol, char[] directions) {
            this.symbol = symbol;
            this.directions = directions;
        }

        public static PipeDirections getInstance(char input) {
            for (PipeDirections pipeDirections : PipeDirections.values()) {
                if (pipeDirections.symbol == input) {
                    return pipeDirections;
                }
            }
            return PipeDirections.NONE;
        }

        public static PipeDirections getInstanceByDirections(char[] input) {
            labelFor: for (PipeDirections pipeDirections : PipeDirections.values()) {
                char[] directions = pipeDirections.directions;
                Arrays.sort(directions);
                Arrays.sort(input);
                for (int i = 0; i < input.length; i++) {
                    if (directions[i] != input[i]) {
                        continue labelFor;
                    }
                }
                return pipeDirections;
            }
            return null;
        }

        public char[] getDirections() {
            return directions;
        }

        public boolean hasDirection(char direction) {
            if (directions != null) {
                for (int i = 0; i < directions.length; i++) {
                    if (directions[i] == direction) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 10\\Part 2" + "\\input.txt");
        PipeDirections[][] input = new PipeDirections[140][140];
        List<int[]> emptyTiles = new ArrayList<>();
        int[] startPosition = new int[2];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int column = 0;
            for (String line; (line = br.readLine()) != null; column++) {
                if (line.isEmpty()) {
                    continue;
                }
                PipeDirections[] rowPipeDirections = new PipeDirections[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    rowPipeDirections[i] = PipeDirections.getInstance(line.charAt(i));
                    if (line.charAt(i) == 'S') {
                        startPosition = new int[] { column, i };
                    }
                    if (rowPipeDirections[i] == PipeDirections.NONE) {
                        emptyTiles.add(new int[] { column, i });
                    }
                }
                input[column] = rowPipeDirections;
            }
        }
        populateDPMap(input, startPosition[0], startPosition[1]);
        populateSposition(input, startPosition[0], startPosition[1]);
        System.out.println("Result = " + getTilesEnclosed(input, emptyTiles));
    }

    public static int getTilesEnclosed(PipeDirections[][] input, List<int[]> tileList) {
        int result = 0;
        List<int[]> enclosedTiles = new ArrayList<>();
        for (int column = 0; column < input.length; column++) {
            for (int row = 0; row < input[0].length; row++) {
                if (dpMap[column][row]) {
                    continue;
                }
                PipeDirections prevDirection = null;
                int pipes = 0;
                for (int i = 0; i < row; i++) {
                    if (!dpMap[column][i]) {
                        continue;
                    }
                    PipeDirections currentDirection = input[column][i];
                    if (currentDirection == PipeDirections.EW) {
                        continue;
                    }
                    if (currentDirection == PipeDirections.NS) {
                        pipes++;
                        continue;
                    }
                    if ((currentDirection == PipeDirections.NW && prevDirection == PipeDirections.SE) || (currentDirection == PipeDirections.SW && prevDirection == PipeDirections.NE)) {
                        pipes--;
                    }
                    prevDirection = currentDirection;
                }
                if (pipes % 2 != 0) {
                    result++;
                    enclosedTiles.add(new int[] { column, row });
                }
            }
        }
        return result;
    }

    public static void populateDPMap(PipeDirections[][] input, int startColumn, int startRow) {
        boolean flip = false;
        int curretColumn = startColumn;
        int currentRow = startRow;
        labelWhile: while (true) {
            List<int[]> nextDirections = getValidDirectionIndices(input, curretColumn, currentRow);
            if (nextDirections.size() == 0) {
                System.out.println("You fucked up");
            }
            for (int i = 0; i < nextDirections.size(); i++) {
                if (nextDirections.get(i)[0] == startColumn && nextDirections.get(i)[1] == startRow) {
                    if (!flip) {
                        flip = true;
                        continue;
                    }
                    break labelWhile;
                }

                if (dpMap[nextDirections.get(i)[0]][nextDirections.get(i)[1]]) {
                    continue;
                }
                curretColumn = nextDirections.get(i)[0];
                currentRow = nextDirections.get(i)[1];
            }
            dpMap[curretColumn][currentRow] = true;
        }
    }

    public static void populateSposition(PipeDirections[][] input, int curretColumn, int currentRow) {
        String direction = "";
        PipeDirections nextPosition;
        PipeDirections currentPosition = input[curretColumn][currentRow];
        if (currentRow - 1 >= 0 && currentPosition.hasDirection('W')) {
            nextPosition = input[curretColumn][currentRow - 1];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'E')) {
                direction = direction + "W";
            }
        }
        if (currentRow + 1 < 140 && currentPosition.hasDirection('E')) {
            nextPosition = input[curretColumn][currentRow + 1];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'W')) {
                direction = direction + "E";
            }
        }
        if (curretColumn - 1 >= 0 && currentPosition.hasDirection('N')) {
            nextPosition = input[curretColumn - 1][currentRow];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'S')) {
                direction = direction + "N";
            }
        }
        if (curretColumn + 1 < 140 && currentPosition.hasDirection('S')) {
            nextPosition = input[curretColumn + 1][currentRow];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'N')) {
                direction = direction + "S";
            }
        }
        dpMap[curretColumn][currentRow] = true;
        input[curretColumn][currentRow] = PipeDirections.getInstanceByDirections(direction.toCharArray());
    }

    public static List<int[]> getValidDirectionIndices(PipeDirections[][] input, int curretColumn, int currentRow) {
        List<int[]> validDirections = new ArrayList<>();
        PipeDirections nextPosition;
        PipeDirections currentPosition = input[curretColumn][currentRow];
        if (currentRow - 1 >= 0 && currentPosition.hasDirection('W')) {
            nextPosition = input[curretColumn][currentRow - 1];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'E')) {
                validDirections.add(new int[] { curretColumn, currentRow - 1 });
            }
        }
        if (currentRow + 1 < 140 && currentPosition.hasDirection('E')) {
            nextPosition = input[curretColumn][currentRow + 1];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'W')) {
                validDirections.add(new int[] { curretColumn, currentRow + 1 });
            }
        }
        if (curretColumn - 1 >= 0 && currentPosition.hasDirection('N')) {
            nextPosition = input[curretColumn - 1][currentRow];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'S')) {
                validDirections.add(new int[] { curretColumn - 1, currentRow });
            }
        }
        if (curretColumn + 1 < 140 && currentPosition.hasDirection('S')) {
            nextPosition = input[curretColumn + 1][currentRow];
            if (nextPosition != PipeDirections.NONE && isAdjacent(nextPosition.directions, 'N')) {
                validDirections.add(new int[] { curretColumn + 1, currentRow });
            }
        }
        return validDirections;
    }

    public static boolean isAdjacent(char[] pipeDirections, char headingTowards) {
        for (int i = 0; i < pipeDirections.length; i++) {
            if (pipeDirections[i] == headingTowards) {
                return true;
            }
        }
        return false;
    }
}