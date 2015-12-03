import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static String defaultExpression = "(a = 1 and b = 2) or (a = 2 and b = 1)";

    public static void main(String[] args) {
        // Wypisanie wygenerowanych danych
        println("Dane:");
        Data data = new Data();
        data.addExampleData(0, 50);
        data.addData(1, 2, 5, 10, 18, 33, 15, 0);
        data.addData(1, 2, 5, 45, 18, 33, 15, 0);
        data.addData(2, 1, 452, 10, 18, 33, 15, 0);
        println(data.toString());
        println();


        // Podanie wyrażenia lub ustawienie domyślnego
        println("Podaj swoje wyrażenie:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String expression = null;
        try {
            expression = br.readLine();
        } catch (IOException e) {
            // Nic
        }
        expression = expression == null || expression.isEmpty() ? defaultExpression : expression;

        println("Wybrane wyrażenie:");
        println(expression);
        println();


        // Wyświetlenie drzewa operacji
        println("Drzewo operacji:");
        String[] newExpression = ReversePolishNotation.execute(expression);
        BinaryNode node = Tree.makeTree(newExpression);
        Tree.printTree(node);
        println();


        // Wyświetlenie wybranych danych
        println("Wybrane dane:");
        Data selectedData = data.select(node);
        println(selectedData.toString());
        println();
    }

    public static void println(String... text) {
        for (String c : text)
            System.out.print(c + " ");
        System.out.println();
    }
}
