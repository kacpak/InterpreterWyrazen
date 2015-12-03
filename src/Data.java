import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Data {
    ArrayList<HashMap<String, Integer>> dane;
    Random random;

    public Data() {
        dane = new ArrayList<>();
        random = new Random();
    }

    public void addData(int a, int b, int c, int d, int e, int f, int g, int h) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", a);
        map.put("b", b);
        map.put("c", c);
        map.put("d", d);
        map.put("e", e);
        map.put("f", f);
        map.put("g", g);
        map.put("h", h);
        dane.add(map);
    }

    public HashMap<String, Integer> getData(int index) {
        return dane.get(index);
    }

    public String getDataString(int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : getData(index).entrySet())
            stringBuilder.append(entry.getValue() + " ");

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dane.size(); i++)
            stringBuilder.append(getDataString(i)).append("\n");

        return stringBuilder.toString();
    }

    public void addExampleData(int from, int to) {
        for (int i = 0; i < 20; i++)
            addData(
                    getRandom(from, to),
                    getRandom(from, to),
                    getRandom(from, to),
                    getRandom(from, to),
                    getRandom(from, to),
                    getRandom(from, to),
                    getRandom(from, to),
                    getRandom(from, to)
            );
    }

    private int getRandom(int from, int to) {
        return from + random.nextInt(to - from);
    }
}
