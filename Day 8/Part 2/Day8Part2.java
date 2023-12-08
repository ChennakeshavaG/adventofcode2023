import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8Part2 {
    public static long lcm_of_array_elements(List<Long> element_array) {
        long lcm_of_array_elements = 1;
        Long divisor = 2L;

        while (true) {
            Long counter = 0L;
            boolean divisible = false;

            for (int i = 0; i < element_array.size(); i++) {
                if (element_array.get(i) == 1) {
                    counter++;
                }
                if (element_array.get(i) % divisor == 0) {
                    divisible = true;
                    element_array.set(i, element_array.get(i) / divisor);
                }
            }
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            } else {
                divisor++;
            }
            if (counter == element_array.size()) {
                return lcm_of_array_elements;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 8\\Part 2" + "\\input.txt");
        Map<String, String[]> inputMap = new HashMap<>();
        List<String> inputList = new ArrayList<>();
        String path = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains("=")) {
                    String[] splitStrings = line.split("=");
                    String key = splitStrings[0].trim();
                    String[] value = new String[2];
                    splitStrings = splitStrings[1].split(",");
                    for (int i = 0; i < splitStrings.length; i++) {
                        value[i] = splitStrings[i].replaceAll("\\(", "").replaceAll("\\)", "").trim();
                    }
                    if (key.charAt(2) == 'A') {
                        inputList.add(key);
                    }
                    inputMap.put(key, value);
                } else {
                    path = line;
                }
            }
            System.out.println(inputList);
            List<Long> resultList = new ArrayList<>();

            for (int i = 0; i < inputList.size(); i++) {
                int currentPosition = 0;
                Long tempResult = 0L;
                String currentString = inputList.get(i);
                while (true) {
                    if (!inputMap.containsKey(currentString)) {
                        System.out.println("You Fucked Up");
                    }
                    if (path.charAt(currentPosition) == 'L') {
                        currentString = inputMap.get(currentString)[0];
                    } else {
                        currentString = inputMap.get(currentString)[1];
                    }
                    tempResult++;
                    if (currentString.charAt(2) == 'Z') {
                        break;
                    }
                    currentPosition++;
                    if (currentPosition >= path.length()) {
                        currentPosition = 0;
                    }
                    if (currentPosition > Integer.MAX_VALUE - 100) {
                        break;
                    }
                }
                resultList.add(tempResult);
            }
            System.out.println(resultList);
            System.out.println(lcm_of_array_elements(resultList));
        }
    }
}