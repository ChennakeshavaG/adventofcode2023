import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6Part1 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 6\\Part 1" + "\\input.txt");
        List<Integer> timeInput = new ArrayList<>();
        List<Integer> distanceInput = new ArrayList<>();
        List<Integer> resultList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                if (line.isEmpty()){
                    continue;
                }
                if (line.contains("Time")){
                    timeInput = Arrays.stream(line.split(":")[1].split(" ")).filter(num -> !num.isEmpty()).mapToInt(num -> Integer.parseInt(num.trim())).boxed().toList();
                } else if (line.contains("Distance")){
                    distanceInput = Arrays.stream(line.split(":")[1].split(" ")).filter(num -> !num.isEmpty()).mapToInt(num -> Integer.parseInt(num.trim())).boxed().toList();
                }
            }
            for(int i=0;i<timeInput.size();i++){
                int time = timeInput.get(i);
                int distance = distanceInput.get(i);
                int count = 0;
                for(int j=0;j<=time;j++){
                    int result = (j)*(time-j);
                    if(result > distance){
                        count++;
                    }
                }
                resultList.add(count);
            }
            int result = 1;
            for(int i : resultList){
                result = result*i;
            }
            System.out.println(result);
        }
    }
}