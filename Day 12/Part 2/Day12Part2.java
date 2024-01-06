import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day12Part2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day ${day}\\Part ${part}" + "\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    continue;
                }
                /**
                 * Code Here !!!
                 */
            }
        }
    }
}