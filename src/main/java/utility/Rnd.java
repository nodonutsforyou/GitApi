package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MVostrikov on 07.10.2016.
 */
public class Rnd {


    public static Random rnd = new Random(System.currentTimeMillis()); //Todo I know, that it is a bad style to store random. But this is an only place we use it. So have't desided best place to store utility class with it


//    private static Rnd ourInstance = new Rnd();
//
//    public static Rnd getInstance() {
//        return ourInstance;
//    }

    private Rnd() {
    }

    private static List<String> generatedValues = new ArrayList<>();

    /**
     * generate random string. As rnd - it is bad to store it here.
     */
    public static String generateString(int size) {
        StringBuilder str = new StringBuilder();
        str.append((char)(rnd.nextInt(26) + 'A')); //first is Uppercase
        for (int i=1; i<size; i++) { //if size<1 - will still return 1 capital char string
            str.append((char)(rnd.nextInt(26) + 'a'));
        }

        String generated = str.toString();

        while (generatedValues.contains(generated)) {
            if(generatedValues.size()>=Math.pow(10,size)-2) size++; //TODO explain
            generated = generateString(size); //TODO can make infinite loop
        }
        generatedValues.add(generated);

        return generated;
    }
    /**
     * generate random string. As rnd - it is bad to store it here.
     */
    public static String generateText(int size) {
        StringBuilder str = new StringBuilder();
        while(size>0) {
            str.append(generateString(10)).append(' ');
        }

        String generated = str.toString();

        return generated;
    }
    /**
     * generate random string with numbers. As rnd - it is bad to store it here.
     */
    public static String generateNumber(int size) {
        StringBuilder str = new StringBuilder();
        for (int i=0; i<size; i++) {
            str.append(Integer.toString(rnd.nextInt(10)));
        }
        return str.toString();
    }
}
