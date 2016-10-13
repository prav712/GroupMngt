package ist.school.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class Temp {


    public static void main(String[] args) {
         List<Integer> integers = new ArrayList<>();
             List<Integer> integers1 = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        integers1.add(1);
        integers1.add(0);
        integers1.add(4);

        integers.removeAll(integers1);
        integers.forEach(integer -> System.out.println(integer));
    }
}
