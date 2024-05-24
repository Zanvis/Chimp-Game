package vistula.ap.l08_piwowarski_62024_chimp_game2;
import java.util.Random;

public class ArrayPermutation {
    public static int[] shuffleFisherYeats(int[] oldArray) {
        Random rndap = new Random();
        int cap;
        int[] newArrayap = oldArray;
        for (int j, i = oldArray.length-1; i>0; i--){
            j = rndap.nextInt(i+1);
            cap = newArrayap[j];
            newArrayap[j] = newArrayap[i];
            newArrayap[i] = cap;
        }
        return newArrayap;
    }
}
