import java.io.File;
import java.io.FileInputStream;

public class Day3Part1 {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 3\\Part 1" + "\\input.txt");
        int input,row=0,column=0,result =0;;
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
                    String number;
                    if((int) inputArray[i][j] >= 48 && (int) inputArray[i][j] <= 57){
                        number = returnAdjacentNumber(inputArray,i,j);
                        System.out.println(number);
                        if(isValid(inputArray,i,j,number.length())){
                            result += Integer.parseInt(number);
                        }
                        j += number.length();
                    }
                }
            }
        }
        System.out.println("Result = " + result);
        return;
    }

    public static String returnAdjacentNumber(char[][] inputArray,int i,int j){
        String number = "";
        while ((int) inputArray[i][j] >= 48 && (int) inputArray[i][j] <= 57){
            number += inputArray[i][j];
            j++;
        }
        return number;
    }

    public static boolean isValid(char[][] inputArray,int i,int j, int totalNumbers){
        for(int row = i-1 >= 0 ? i-1 : i;row<=i+1;row ++){
            for(int column = j-1 >= 0 ? j-1 : j;column <= j+totalNumbers;column++){
                char c = inputArray[row][column];
                if (((int) c < 48 || (int) c > 57) && (int) c != 46 && (int) c != 0) {
                    return true;
                }
            }
        }
        return false;
    }
}