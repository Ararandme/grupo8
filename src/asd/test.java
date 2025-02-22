package asd;

import java.util.ArrayList;
import java.util.HashMap;

public class test {

    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("A");
        arr.add("B");
        arr.add("C");
        HashMap<Integer,ArrayList<String>> map = new HashMap<>();
        map.put(123,arr);

        System.out.println(map.get(123).get(1));
    }


}
