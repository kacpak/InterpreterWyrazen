import java.util.*;

/**
 * Przechowuje listę danych całkowitoliczbowych w kolumnach a, b, c, d, e, f, g, h
 */
public class Data {

    /**
     * Przechowywane dane
     */
    ArrayList<TreeMap<String, Integer>> data;

    /**
     * Generator liczb pseudolosowych
     */
    Random random;

    /**
     * Tworzy obiekt obsługujący działania na danych w kolumnach [a-h]
     */
    public Data() {
        data = new ArrayList<>();
        random = new Random();
    }

    /**
     * Tworzy obiekt obsługujący działania na danych w kolumnach [a-h] inicjalizując go podanymi danymi
     */
    private Data(ArrayList<TreeMap<String, Integer>> data) {
        this.data = data;
    }

    /**
     * Dodaje podane dane do listy dla kolejych kolumn a, b, c, d, e, f, g, h
     */
    public void addData(int a, int b, int c, int d, int e, int f, int g, int h) {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", a);
        map.put("b", b);
        map.put("c", c);
        map.put("d", d);
        map.put("e", e);
        map.put("f", f);
        map.put("g", g);
        map.put("h", h);
        addData(map);
    }

    /**
     * Dodaje podane dane do listy dla kolejych kolumn a, b, c, d, e, f, g, h
     */
    public void addData(TreeMap<String, Integer> map) {
        data.add(map);
    }

    /**
     * Pobiera dane z listy
     * @param index Indeks na liście
     */
    public TreeMap<String, Integer> getData(int index) {
        return data.get(index);
    }

    /**
     * Pobiera dane z listy w postaci {@see String}
     * @param index Indeks na liście
     */
    public String getDataString(int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : getData(index).entrySet())
            stringBuilder.append(entry.getValue() + " ");

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.size(); i++)
            stringBuilder.append(getDataString(i)).append("\n");

        return stringBuilder.toString();
    }

    /**
     * Generuje 20 przykładowych danych z podanego zakresu i dodaje do listy
     * @param n ilość dodanych danych
     * @param from przedział od
     * @param to przedział do
     */
    public void addRandomDataInRange(int n, int from, int to) {
        for (int i = 0; i < n; i++)
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

    /**
     * Zwraca losową liczbę z przedziału
     */
    private int getRandom(int from, int to) {
        return from + random.nextInt(to - from);
    }

    /**
     * Wybiera dane z listy spełniające podane wyrażenie
     * @param expressionTree
     */
    public Data select(BinaryNode expressionTree) {
        TreeSet<Integer> indexes = selectIndexes(expressionTree);
        ArrayList<TreeMap<String, Integer>> selectedData = new ArrayList<>();
        for (int index : indexes)
            selectedData.add(data.get(index));

        return new Data(selectedData);
    }

    /**
     * Wybiera indexy z listy pasujące do wyrażenia
     * @param expressionTree
     */
    private TreeSet<Integer> selectIndexes(BinaryNode expressionTree) {
        // Jeśli node jest już prostym wyrażeniem pobieramy indexy z listy pasujące do wyrażenia
        if (expressionTree.isSimpleOperation()) {
            BinaryNode leftNode = expressionTree.getLeftNode();
            BinaryNode rightNode = expressionTree.getRightNode();
            String operator = expressionTree.getValue();

            return getSimpleOperationIndexes(leftNode.getValue(), rightNode.getValue(), operator);

        // W przeciwnym razie wywołujemy funkcję rekurencyjnie, aż otrzymamy indexy dla lewego i prawego dzieck
        // i stosujemy odpowiednią operacje logiczną
        } else {
            TreeSet<Integer> leftIndexes = selectIndexes(expressionTree.getLeftNode());
            TreeSet<Integer> rightIndexes = selectIndexes(expressionTree.getRightNode());
            String operator = expressionTree.getValue();

            switch (operator) {
                case "and":
                    return getAndResult(leftIndexes, rightIndexes);
                case "or":
                    return getOrResult(leftIndexes, rightIndexes);
                default:
                    return new TreeSet<>();
            }
        }
    }

    /**
     * Operacja AND na elementach list
     */
    private TreeSet<Integer> getAndResult(TreeSet<Integer> list1, TreeSet<Integer> list2) {
        TreeSet<Integer> set = new TreeSet<>();

        for (int index : list1) {
            if (list2.contains(index))
                set.add(index);
        }

        for (int index : list2) {
            if (list1.contains(index))
                set.add(index);
        }

        return set;
    }

    /**
     * Operacja OR na elementach list
     */
    private TreeSet<Integer> getOrResult(TreeSet<Integer> list1, TreeSet<Integer> list2) {
        TreeSet<Integer> set = new TreeSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return set;
    }

    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
            return true;

        } catch(NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Zwraca indexy pasujące do prostego wyrażenia porównującego wartość wybranej kolumny
     */
    private TreeSet<Integer> getSimpleOperationIndexes(String value1, String value2, String operator) {
        TreeSet<Integer> indexes = new TreeSet<>();
        for (int i = 0; i < data.size(); i++) {
            // Sprawdzam wartości wyrażeń
            TreeMap<String, Integer> row = data.get(i);
            int trueValue1 = isNumeric(value1) ? Integer.parseInt(value1) : row.get(value1);
            int trueValue2 = isNumeric(value2) ? Integer.parseInt(value2) : row.get(value2);

            // Wykonuję operacje
            switch (operator) {
                case "=":
                    if (trueValue1 == trueValue2)
                        indexes.add(i);
                    break;
                case "!=":
                    if (trueValue1 != trueValue2)
                        indexes.add(i);
                    break;
                case ">=":
                    if (trueValue1 >= trueValue2)
                        indexes.add(i);
                    break;
                case "<=":
                    if (trueValue1 <= trueValue2)
                        indexes.add(i);
                    break;
                case ">":
                    if (trueValue1 > trueValue2)
                        indexes.add(i);
                    break;
                case "<":
                    if (trueValue1 < trueValue2)
                        indexes.add(i);
                    break;
            }
        }
        return indexes;
    }


}
