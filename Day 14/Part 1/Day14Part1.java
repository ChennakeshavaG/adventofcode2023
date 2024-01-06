import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day14Part1 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 14\\Part 1" + "\\input.txt");
        char[][] input = new char[100][100];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int i = 0;
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    continue;
                }
                input[i] = line.toCharArray();
                i++;
            }
        }
        input = tiltPlatform(input);
        int result = 0;
        for (int i = 0; i < input.length; i++) {
            int row = 0;
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == 'O') {
                    row++;
                }
            }
            result = result + row * (input.length - i);
        }
        System.out.println("Result = " + result);
    }

    public static char[][] tiltPlatform(char[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (input[i][j] == 'O') {
                    input = tiltRock(input, i, j);
                }
            }
        }
        return input;
    }

    public static char[][] tiltRock(char[][] input, int row, int column) {
        for (int i = row; i > 0; i--) {
            if (i == 0) {
                continue;
            }
            if (i - 1 == 0 && input[i - 1][column] == '.') {
                input[row][column] = '.';
                input[0][column] = 'O';
                break;
            }
            if (input[i - 1][column] == '#' || input[i - 1][column] == 'O') {
                if (input[i][column] == '.') {
                    input[row][column] = '.';
                    input[i][column] = 'O';
                    break;
                } else {
                    break;
                }
            }
        }
        return input;
    }
}