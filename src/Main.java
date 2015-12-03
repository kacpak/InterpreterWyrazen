import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        String expression = "(a = 1 and b = 2) or (a = 2 and b = 1)";

        println("Expression:");
        println(expression);
        println();

        println("Expression Tree:");
        String[] newExpression = ReversePolishNotation.execute(expression);
        BinaryNode node = Tree.makeTree(newExpression);
        Tree.printTree(node);
        println();

        println("Data:");
        Data data = new Data();
        data.addExampleData(0, 50);
        println(data.toString());
        println();

        println("Selected data:");

        println();
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static void println(String... text) {
        for (String c : text)
            System.out.print(c + " ");
        System.out.println();
    }
}
