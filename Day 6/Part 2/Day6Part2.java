import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day6Part2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 6\\Part 2" + "\\input.txt");
        Long timeInput = 0L;
        Long distanceInput= 0L;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                if (line.isEmpty()){
                    continue;
                }
                if (line.contains("Time")){
                    timeInput = Long.parseLong(String.join("", (line.split(":")[1].split(" "))));
                } else if (line.contains("Distance")){
                    distanceInput = Long.parseLong(String.join("", (line.split(":")[1].split(" "))));
                }
            }
            int count = 0;
            for(Long i=0L;i<timeInput;i++){
                Long result = (i)*(timeInput-i);
                if(result > distanceInput){
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}