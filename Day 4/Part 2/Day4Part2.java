import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day4Part2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 4\\Part 2" + "\\input.txt");
        String[] temp;
        int result = 0;
        int currentGame = 0;
        List<Integer> gameList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                if(currentGame >= gameList.size()){
                    gameList.add(currentGame ,1);
                } else {
                    gameList.set(currentGame, gameList.get(currentGame) +1);
                }
                String inputString;
                int count = 0;
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

                gameList =  populateWinGames(currentGame, count, gameList.get(currentGame) ,gameList);
                 
                currentGame++;
            }
        }
        for (Integer integer : gameList) {
            result += integer;
        }
        System.out.println("Result = " + result);
    }

    public static List<Integer> populateWinGames(int currentGame, int count, int increment, List<Integer> gameList){
        for(int i = currentGame+1; i<=currentGame+count;i++){
            if(i >= gameList.size()){
                gameList.add(i,increment);
            } else {
                gameList.set(i, gameList.get(i) +increment);
            }
        }
        return gameList;
    }
}
