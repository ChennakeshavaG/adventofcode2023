import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day7Part1 {
    public static enum HandType{
        FIVE_OF_A_KIND(1),
        FOUR_OF_A_KIND(2),
        FULL_HOUSE(3),
        THREE_OF_A_KIND(4),
        TWO_PAIR(5),
        ONE_PAIR(6),
        HIGH_CARD(7);
        int strength;
        HandType(int strength){
            this.strength = strength;
        }
        public int getStrength(){
            return strength;
        }
        public static HandType getHandType(String cards) {
            Map<String,Integer> cardsMap = new HashMap<>();
            for(int i=0;i<cards.length();i++){
                Integer count = cardsMap.getOrDefault(cards.charAt(i) + "", 0);
                count++;
                cardsMap.put(cards.charAt(i) + "",count);
            }
            List<Entry<String, Integer>> cardList = new ArrayList<>(cardsMap.entrySet());
            Integer size = cardList.size();
            if(size == 1){
                // Only one card
                return FIVE_OF_A_KIND;
            } else if (size == 2){
                // Only 2 cards
                Integer value= cardList.get(0).getValue();
                if(value == 1 || value == 4 ){
                    return FOUR_OF_A_KIND;
                } else {
                    return FULL_HOUSE;
                }
            } else if (size == 3) {
                // Only 3 cards
                for(Entry<String, Integer> entry : cardList){
                    if(entry.getValue() == 3){
                        return THREE_OF_A_KIND;
                    }
                }
                return TWO_PAIR;
            } else if (size == 4) {
                // 4 different cards
                return ONE_PAIR;
            } else {
                // distinct cards
                return HIGH_CARD;
            }
        }
    }
    public static int getCardRank(char c){
        //Lower the card rank, less strong it is.
        if((int) c >= 50 && (int) c <= 57){
            return (int) c - 48;
        } else if (c == 'T'){
            return 10;
        } else if (c == 'J'){
            return 11;
        } else if (c == 'Q'){
            return 12;
        } else if (c == 'K'){
            return 13;
        } else if (c == 'A'){
            return 14;
        }
        System.out.println("You Fucked up");
        return -1;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") +"\\adventofcode\\Day 7\\Part 1" + "\\input.txt");
        Map<String,Integer> inputMap = new HashMap<>();
        List<String> cards = null;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] temp;
            for(String line; (line = br.readLine()) != null; ) {
                // Negative integer, first argument is less than second
                // Zero, first argument is equal to second
                // Positive integer as the first argument is greater than the second
                temp =line.split(" ");
                inputMap.put(temp[0], Integer.parseInt(temp[1]));
                cards = new ArrayList<>(inputMap.keySet());
                cards.sort((card1, card2) -> {
                    Integer card1Strength = HandType.getHandType(card1).getStrength();
                    Integer card2Strength = HandType.getHandType(card2).getStrength();
                    if (card1Strength == card2Strength ) {
                        for(int i=0;i<card1.length();i++){
                            int card1Rank = getCardRank(card1.charAt(i));
                            int card2Rank = getCardRank(card2.charAt(i));
                            if(card1Rank != card2Rank){
                                return card1Rank < card2Rank ? -1 : 1;
                            }
                        }
                    }
                    return card1Strength > card2Strength ? -1 : 1;
                });
            }
            System.out.println(cards);
            int i=1;
            int result = 0;
            for(String card : cards){
                result += inputMap.get(card)*i;
                i++;
            }
            System.out.println(result);
        }
    }
}