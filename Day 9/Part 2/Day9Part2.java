import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Part2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 9\\Part 2" + "\\input.txt");
        Long result = 0L;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    continue;
                }
                List<Integer> inputList = Arrays.stream(line.split(" ")).filter(num -> !num.isEmpty()).mapToInt(num -> Integer.parseInt(num.trim())).boxed().collect(Collectors.toList());
                result += findPrevious(inputList);
            }
        }
        System.out.println(result);
    }

    public static int findPrevious(List<Integer> inputList) {
        boolean isDiffArray = isDiffArray(inputList);
        if (isDiffArray) {
            List<Integer> subList = new ArrayList<>();
            int len = inputList.size();
            for (int i = 1; i < len; i++) {
                subList.add(inputList.get(i) - inputList.get(i - 1));
            }
            return inputList.get(0) - findPrevious(subList);

        } else {
            return inputList.get(0);
        }
    }

    public static boolean isDiffArray(List<Integer> inputList) {
        for (int i = 1; i < inputList.size(); i++) {
            if (inputList.get(i) != inputList.get(i - 1)) {
                return true;
            }
        }
        return false;
    }
}