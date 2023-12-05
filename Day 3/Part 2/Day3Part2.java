import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Day3Part2 {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 3\\Part 2" + "\\input.txt");
        int input,row=0,column=0;
        long result =0;
        char[][] inputArray = new char[150][150];
        try (FileInputStream fis = new FileInputStream(file)) {
            while ((input = fis.read()) != -1) {
                inputArray[row][column] = (char) input;
                column++;
                if (input == '\n'){
                    row++;
                    column = 0;
                }

            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        for(int i=0;i<150;i++){
            for(int j=0;j<150;j++){
                // Check for invalid input;
                if ((int) inputArray[i][j] > 0){
                    ArrayList<Integer> numbers;
                    if((int) inputArray[i][j] == 42){
                        numbers = returnAdjacentNumbers(inputArray,i,j);
                        int multiplier = 1;
                        if(numbers.size() == 2){
                            for (Integer integer : numbers) {
                                multiplier = multiplier * integer;
                            }
                        }
                        if(multiplier != 1 ){
                            result = result + multiplier;
                        }
                    }
                }
            }
        }
        System.out.println("Result = " + result);
        return;
    }

    public static ArrayList<Integer> returnAdjacentNumbers(char[][] inputArray,int i,int j){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int row = i-1 >= 0 ? i-1 : i; row<=(i+1); row ++){
            for(int column = j-1 >= 0 ? j-1 : j; column <= (j+1); column++){
                char c = inputArray[row][column];
                if ((int) c >= 48 && (int) c <= 57) {
                    int[] number =  getNumber(inputArray, row, column);
                    numbers.add(number[0]);
                    int startIndex = number[1];
                    int endIndex = number[2];
                    if(!(endIndex == j-1 || startIndex == j+1)){
                        column = column +10;
                    }
                }
            }
        }
        return numbers;
    }

    public static int[] getNumber(char[][] inputArray,int i,int j){
        String number = "";
        int startIndex = -1 , endIndex = -1, row=i;
        for(int column=j;column>=0;column--){
            char c = inputArray[row][column];
            if(column == 0 && (int) c >= 48 && (int) c <= 57){
                startIndex = column;
                break;
            }
            if (!((int) c >= 48 && (int) c <= 57)) {
                startIndex = column+1;
                break;
            }
        }
        for(int column =j; (int) inputArray[row][column] != 0;column++){
            char c = inputArray[row][column];
            if (!((int) c >= 48 && (int) c <= 57) && (int) c != 0) {
                endIndex = column-1;
                break;
            }
        }
        
        if (startIndex == -1 || endIndex == -1){
            System.out.println("You Fucked Up");
        }
        for(int k = startIndex; k <= endIndex; k++){
            number += inputArray[i][k];
        }
        return new int[]{Integer.parseInt(number),startIndex,endIndex};
    }
}