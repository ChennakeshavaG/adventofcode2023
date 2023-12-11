import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10Part1 {
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
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 10\\Part 1" + "\\input.txt");
        PipeDirections[][] input = new PipeDirections[140][140];
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
                }
                input[column] = rowPipeDirections;
            }
        }
        List<int[]> initialDirectionIndices = getValidDirectionIndices(input, startPosition[0], startPosition[1]);
        dpMap[startPosition[0]][startPosition[1]] = true;
        if (initialDirectionIndices.size() == 0) {
            System.out.println("Glorious input fuck up");
        }
        System.out.println("Result = " + getMaxDistance(input, startPosition[0], startPosition[1]));
    }

    public static int getMaxDistance(PipeDirections[][] input, int startColumn, int startRow) {
        int distance = 0;
        int curretColumn = startColumn;
        int currentRow = startRow;
        labelWhile: while (true) {
            List<int[]> nextDirections = getValidDirectionIndices(input, curretColumn, currentRow);
            if (nextDirections.size() == 0) {
                System.out.println("You fucked up");
            }
            for (int i = 0; i < nextDirections.size(); i++) {
                if (nextDirections.get(i)[0] == startColumn && nextDirections.get(i)[1] == startRow && distance > 1) {
                    distance++;
                    break labelWhile;
                }

                if (dpMap[nextDirections.get(i)[0]][nextDirections.get(i)[1]]) {
                    continue;
                }
                curretColumn = nextDirections.get(i)[0];
                currentRow = nextDirections.get(i)[1];
            }
            dpMap[curretColumn][currentRow] = true;
            distance++;
        }
        return distance / 2;
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