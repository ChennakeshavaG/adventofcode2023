import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day12Part1 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "\\adventofcode\\Day 12\\Part 1" + "\\input.txt");
        int result = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    continue;
                }
                System.out.println(line);
                String[] split = line.split(" ");
                List<Integer> groups =  Arrays.stream(line.split(":")[1].split(" ")).filter(num -> !num.isEmpty()).mapToInt(num -> Integer.parseInt(num.trim())).boxed().toList();
                result += getTotalPossibilities(split[0].toCharArray(),groups);
            }
        }
        System.out.println("Result = " + result);
    }

    public static int getTotalPossibilities(char[] springs, List<Integer> groups) {
        int result = 0;
        for(int i=0;i<springs.length;i++){
            if(springs[i] == '?'){
                // 2 possibilities. Fill with . or #
                
            }
        }
        return result;
    }

    public static boolean isValidRecord(char[] spring, List<Integer> groups){
        // List<String> springs = Arrays.stream(record.split(".")).filter(spring -> !spring.isEmpty()).toList();
        // if(springs.size() != groups.size()){
        //     return false;
        // } else {
        //     for(int i=0;i<springs.size();i++){
        //         if(springs.get(i).length() == groups.get(i)){

        //         } else {
        //             return false;
        //         }
        //     }
        // }
        return true;
    }
}