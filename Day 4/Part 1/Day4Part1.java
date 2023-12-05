import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Day4Part1 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 4\\Part 1" + "\\input.txt");
        String[] temp;
        int result =0;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String inputString;
                int count =0;
                temp = line.split(":");
                inputString = temp[1];
                temp = inputString.split("\\|");
                String winString = temp[0];
                String myString = temp[1];
                List<Integer> winArray = Arrays.stream(winString.split(" ")).filter(num -> !num.isEmpty()).mapToInt(num -> Integer.parseInt(num.trim())).boxed().toList();
                List<Integer> myArray = Arrays.stream(myString.split(" ")).filter(num -> !num.isEmpty()).mapToInt(num -> Integer.parseInt(num.trim())).boxed().toList();
                for (Integer winInteger : winArray) {
                    for (Integer myInteger : myArray) {
                        if(winInteger == myInteger){
                            count++;
                        }
                    }
                }
                result += Math.pow(2, count-1);
            }
        }
        System.out.println("Result = " + result);
    }
}
