import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11Part2 {
    static int inputRows = 140;
    static int inputColumns = 140;
    static int columnMultiplicationConstant = 999999;
    static int rowMultiplicationConstant = 999999;
    static List<int[]> planets = new ArrayList<>();
    static char[][] universe = new char[inputColumns][inputRows];

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 11\\Part 1" + "\\input.txt");
        char[][] input = new char[inputColumns][inputRows];
        int column = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    continue;
                }
                input[column] = line.toCharArray();
                column++;
            }
        }
        populateUniverse(input);
        findPlanets();
        List<int[]> pairs = getPairs();
        long result = 0;
        for (int[] pairIndex : pairs) {
            result += calculateDistance(pairIndex[0], pairIndex[1]);
        }
        System.out.println("Result = " + result);
    }

    public static long calculateDistance(int plane1Index, int plane2Index) {
        int[] plane1Coordinates = planets.get(plane1Index);
        int[] plane2Coordinates = planets.get(plane2Index);
        int x1 = plane1Coordinates[0], x2 = plane2Coordinates[0];
        int y1 = plane1Coordinates[1], y2 = plane2Coordinates[1];
        long result = 0;
        for (int i = ((x1 > x2) ? x2 : x1); i < ((x1 > x2) ? x1 : x2); i++) {
            if (universe[i][0] == '@') {
                result += columnMultiplicationConstant;
            } else {
                result++;
            }
        }
        for (int i = ((y1 > y2) ? y2 : y1); i < ((y1 > y2) ? y1 : y2); i++) {
            if (universe[0][i] == '@') {
                result += rowMultiplicationConstant;
            } else {
                result++;
            }
        }
        return result;
    }

    public static List<int[]> getPairs() {
        List<int[]> pairsIndices = new ArrayList<>();
        for (int i = 0; i < planets.size(); i++) {
            for (int j = i + 1; j < planets.size(); j++) {
                pairsIndices.add(new int[] { i, j });
            }
        }
        return pairsIndices;
    }

    public static void findPlanets() {
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe[i].length; j++) {
                if (universe[i][j] == '#') {
                    planets.add(new int[] { i, j });
                }
            }
        }
    }

    public static void populateUniverse(char[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                universe[i][j] = input[i][j];
            }
        }

        // Expand rows.
        int rowLength = universe.length;
        rowSearch: for (int i = 0; i < rowLength; i++) {
            char[] tempRow = universe[i];
            for (int j = 0; j < tempRow.length; j++) {
                if (tempRow[j] == '#') {
                    continue rowSearch;
                }
            }
            i++;
            insertRow(i);
            rowLength = universe.length;
        }

        // Exapand column
        int columnLength = universe[0].length;
        rowLength = universe.length;
        columnSearch: for (int i = 0; i < columnLength; i++) {
            for (int j = 0; j < rowLength; j++) {
                if (universe[j][i] == '#') {
                    continue columnSearch;
                }
            }
            i++;
            insertColumn(i);
            columnLength = universe[0].length;
        }
    }

    public static void insertColumn(int column) {
        for (int i = 0; i < universe.length; i++) {
            char[] tempRow = Arrays.copyOf(universe[i], universe[i].length + 1);
            for (int j = tempRow.length - 1; j > column; j--) {
                tempRow[j] = tempRow[j - 1];
            }
            tempRow[column] = '@';
            universe[i] = tempRow;
        }
    }

    public static void insertRow(int row) {
        int columnLength = universe[0].length;
        char[][] tempArray = Arrays.copyOf(universe, universe.length + 1);
        tempArray[universe.length] = new char[columnLength];
        for (int i = universe.length; i > row; i--) {
            tempArray[i] = tempArray[i - 1];
        }
        tempArray[row] = new char[columnLength];
        Arrays.fill(tempArray[row], '@');
        universe = tempArray;
    }
}