import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day5Part1 {
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
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 5\\Part 1" + "\\input.txt");
        List<Long> inputList = new ArrayList<>();
        List<Long> result = new ArrayList<>();
        Maptype currentMap = null;
        Map<Maptype,List<String>> map = new LinkedHashMap<>();
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
                    List<String> tempList = map.getOrDefault(currentMap, new ArrayList<>());
                    tempList.add(line);
                    map.put(currentMap, tempList);
                }
            }
        }
        for(Long input : inputList){
            for(Maptype mapType : map.keySet()){
                List<String> resultList = map.get(mapType);
                Long resultLong = getDestinationNumber(input, resultList);
                input = resultLong != null ? resultLong : input;
            }
            result.add(input);
        }
        System.out.println("Result = " + result);
        System.out.println("Minimum = " + Collections.min(result));
    }

    public static Long getDestinationNumber(Long number, List<String> resultList){
        for(String temp : resultList){
            String[] tempArray = temp.split(" ");
            Long destination = Long.parseLong(tempArray[0]);
            Long source = Long.parseLong(tempArray[1]);
            Long length = Long.parseLong(tempArray[2]);
            if(number >= source && number <= source+length){
                return number + (destination - source);
            }
        }
        return null;
    }
}