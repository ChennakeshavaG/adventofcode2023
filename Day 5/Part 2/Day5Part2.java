
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// YOLO BruteForce. < 5 min (Ryzen 7 CPU)
public class Day5Part2 {
    public enum Maptype{
        SEED_TO_SOIL("seed-to-soil"),
        SOIL_TO_FERTILIZER("soil-to-fertilizer"),
        FERTILIZER_TO_WATER("fertilizer-to-water"),
        WATER_TO_LIGHT("water-to-light"),
        LIGHT_TO_TEMPERATURE("light-to-temperature"),
        TEMPERATURE_TO_HUMIDITY("temperature-to-humidity"),
        HUMIDITY_TO_LOCATION("humidity-to-location");

        private final String text;
        Maptype(String text){
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }

        public static Maptype getMaptype(String input){
            for(Maptype maptype : Maptype.values()){
                if(input.contains(maptype.toString())){
                    return maptype;
                }
            }
            return null;
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 5\\Part 2" + "\\input.txt");
        List<Long> inputList = new ArrayList<>();
        Long result = Long.MAX_VALUE;
        Maptype currentMap = null;
        Map<Maptype,List<List<Long>>> map = new LinkedHashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                if (line.isEmpty()){
                    continue;
                }
                if(line.contains("seeds:")){
                    String[] temp;
                    temp = line.split(":");
                    line = temp[1];
                    inputList = Arrays.stream(line.split(" ")).filter(num -> !num.isEmpty()).mapToLong(num -> Long.parseLong(num.trim())).boxed().toList();
                    continue;
                }
                
                if (line.contains("map")){
                    currentMap = Maptype.getMaptype(line);
                    continue;
                }

                if (currentMap != null){
                    List<List<Long>> tempList = map.getOrDefault(currentMap, new ArrayList<>());
                    List<Long> lineList = new ArrayList<>();
                    String[] tempArray = line.split(" ");
                    lineList.add(Long.parseLong(tempArray[0]));
                    lineList.add(Long.parseLong(tempArray[1]));
                    lineList.add(Long.parseLong(tempArray[2]));
                    tempList.add(lineList);
                    map.put(currentMap, tempList);
                }
            }
        }
        for(int i=0;i<inputList.size();i+=2){
            System.out.println("Current i = " + i);
            Long input = inputList.get(i);
            Long length = inputList.get(i+1);
            for(Long j = 0L;j<length;j++){
                Long seed=input + j;
                for(Maptype mapType : map.keySet()){
                    List<List<Long>> resultList = map.get(mapType);
                    Long resultLong = getDestinationNumber(seed, resultList);
                    seed = resultLong != null ? resultLong : seed;
                }
                if(seed < result){
                    result = seed;
                }  
            } 
        }
        System.out.println("Result = " + result);
    }

    public static Long getDestinationNumber(Long number, List<List<Long>> resultList){
        for(List<Long> temp : resultList){
            Long destination = temp.get(0);
            Long source = temp.get(1);
            Long length = temp.get(2);
            if(number >= source && number < source+length){
                return destination + (number - source);
            }
        }
        return null;
    }
}