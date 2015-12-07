import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static String defaultExpression = "(a = 1 and b = 2) or (a = 2 and b = 1)";

    public static void main(String[] args) {
        // Wypisanie wygenerowanych danych
        println("Dane:");
        Data data = new Data();
        data.addRandomDataInRange(20, 0, 50);
        data.addData(1, 2, 5, 10, 18, 33, 15, 0);
        data.addData(1, 2, 5, 45, 18, 33, 15, 0);
        data.addData(2, 1, 452, 10, 18, 33, 15, 0);
        println(data.toString());
        println();


        while (true) {
            // Podanie wyrażenia lub ustawienie domyślnego
            println("Podaj swoje wyrażenie:");
            String enteredExpression = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                enteredExpression = br.readLine();

            } catch (IOException e) {
                // Expression pozostaje null
                e.printStackTrace();
            }

            if (enteredExpression == null || enteredExpression.isEmpty()) {
                enteredExpression = defaultExpression;
                println("Wybrano wyrażenie przykładowe:");
            } else
                println("Wybrane wyrażenie:");



            // Inicjalizuję nasze wyrażenie
            Expression expression;
            try {
                expression = new Expression(enteredExpression);
                println(expression.getExpression());
                println();

            } catch (UnsupportedOperationException e) {
                println("Wprowadzono nielegalne znaki.");
                println();
                continue;
            }


            // Wyświetlenie drzewa operacji
            println("Drzewo operacji:");
            BinaryNode tree;
            try {
                tree = expression.getExpressionTree();
                BinaryExpressionTreeGenerator.printTree(tree);

            } catch (Exception e) {
                println("Nie udało się utworzyć drzewa operacji.");
                println();
                continue;
            }
            println();


            // Wyświetlenie wybranych danych
            println("Wybrane dane:");
            try {
                Data selectedData = data.select(tree);
                println(selectedData.toString());
                println();

            } catch (Exception e) {
                println("Wystąpił problem przy wyświetlaniu wyników.");
                println();
            }
        }
    }

    public static void println(String... text) {
        for (String c : text)
            System.out.print(c + " ");
        System.out.println();
    }
}
