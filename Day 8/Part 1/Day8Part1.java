import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day8Part1 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 8\\Part 1" + "\\input.txt");
        Map<String,String[]> inputMap = new HashMap<>();
        String path = "";
        String startInput = "AAA";
        String output = "ZZZ";
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                if(line.isEmpty()){
                    continue;
                }
                if(line.contains("=")){
                    String[] splitStrings = line.split("=");
                    String key = splitStrings[0].trim();
                    String[] value = new String[2];
                    splitStrings = splitStrings[1].split(",");
                    for(int i=0;i<splitStrings.length; i++){
                        value[i] = splitStrings[i].replaceAll("\\(", "").replaceAll("\\)","").trim();
                    }
                    inputMap.put(key, value);
                } else {
                    path = line;
                }
            }
            int result = 0;
            int currentPosition = 0;
            String currentString = startInput;
            while(true){
                if(!inputMap.containsKey(currentString)){
                    System.out.println("You Fucked Up");
                }
                if(path.charAt(currentPosition) == 'L'){
                    currentString = inputMap.get(currentString)[0];   
                } else {
                    currentString = inputMap.get(currentString)[1];  
                }
                result++;
                if(currentString.equalsIgnoreCase(output)){
                    break;
                }
                currentPosition++;
                if(currentPosition >= path.length()){
                    currentPosition =0;
                }
                if(currentPosition > Integer.MAX_VALUE -100 ){
                    break;
                }
            }
            System.out.println(result);

        }
    }
}