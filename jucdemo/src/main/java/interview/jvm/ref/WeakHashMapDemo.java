package interview.jvm.ref;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashmap();
        System.out.println("=============================");
        myWeakHashMap();

    }

    private static void myWeakHashMap() {
        Map<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";

        map.put(key, value);
        System.gc();
        System.out.println(map+"\t"+map.size());

        key = null;
        System.gc();
        System.out.println(map+"\t"+map.size());

        System.gc();
        System.out.println(map+"\t"+map.size());

    }

    private static void myHashmap() {
        HashMap<Object, Object> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";

        map.put(key, value);
        System.gc();
        System.out.println(map);

        key = null;
        System.gc();
        System.out.println(map);

        System.gc();
        System.out.println(map+"\t"+map.size());


    }
}
