import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13Part1 {
    // Divide and Conquer Problem.
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 13\\Part 1" + "\\input.txt");
        List<List<String>> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> pattern = new ArrayList<>();
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    input.add(pattern);
                    pattern = new ArrayList<>();
                    continue;
                }
                pattern.add(line);
            }
            input.add(pattern);
        }
        int result = 0;
        for (int j = 0; j < input.size(); j++) {
            if (j % 2 == 0) {
                result = result + getReflectedColumnCount(input.get(j));
            } else {
                result = result + (getReflectedRowCount(input.get(j)) * 100);
            }
        }
        System.out.println("Result = " + result);
    }

    public static int getReflectedColumnCount(List<String> input) {
        int result = 0;
        int i=0;
        while (true) {
            if()
        }
        return result;
    }

    public static int getReflectedRowCount(List<String> input) {
        int result = 0;
        for(String row : input){
            if ()
        }
        return result;
    }
}