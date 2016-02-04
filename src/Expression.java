import java.util.*;
import java.util.regex.Pattern;

/**
 * Klasa obsługująca wyrażenie matematyczne
 */
public class Expression {

    /**
     * Oryginalne wyrażenie
     */
    private final String expressionString;

    /**
     * Tokeny w odwrotnej notacji polskiej
     */
    private final String[] tokensInNotation;

    /**
     * Drzewo opisujące wyrażenie
     */
    private TreeNode expressionTree;

    /**
     * Regex z dozwolonymi znakami w wrażeniu
     */
    private final static String allowedCharactersPattern = "^(or|and|=|>|<|!|[a-h0-9]|\\s|\\(|\\))+$";

    /**
     * Tworzy nowe wyrażenie
     * @param expression wyrażenie
     * @throws UnsupportedOperationException gdy wprowadzono nielegalne znaki
     */
    public Expression(String expression) throws UnsupportedOperationException {
        expressionString = expression.toLowerCase();

        if (!isCorrectExpression(expressionString))
            throw new UnsupportedOperationException();

        tokensInNotation = convertTokensToReversePolishNotation();
    }

    /**
     * Sprawdza czy wprowadzono tylko dozwolone znaki, nie sprawdza czy równanie ma sens
     */
    public static boolean isCorrectExpression(String expression) {
        return Pattern.matches(allowedCharactersPattern, expression);
    }

    /**
     * Wprowadzone wyrażenie
     */
    public String getExpression() {
        return expressionString;
    }

    /**
     * Tokeny wyrażenia w naturalnym porządku
     */
    private String[] getExpressionTokens() {
        // Upewniam się że wszystkie operatory/znaki specjalne są odpowiednio rozdzielone w wyrażeniu
        String expression = expressionString
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ");

        for (Map.Entry<String, Integer> operator : ExpressionOperators.getPrecedence().entrySet())
            expression = expression.replaceAll(operator.getKey(), " " + operator.getKey() + " ");

        // Przywracam operatory które mogły ulec zniszczeniu przy powyższym dodawaniu spacji
        expression = expression
                .replaceAll(">\\s*=", ">=")
                .replaceAll("<\\s*=", "<=")
                .replaceAll("!\\s*=", "!=");

        // Zwracam tokeny dzieląc je po białych znakach
        return expression.toLowerCase().split("\\s+");
    }

    /**
     * Drzewo binarne reprezentujące wyrażenie
     */
    public TreeNode getExpressionTree() {
        // Lazy initialization
        if (null != expressionTree)
            return expressionTree;

        // Tworze kolejkę operatorów
        LinkedList<String> queue = new LinkedList<>(Arrays.asList(tokensInNotation));

        // Odwracam ją by zaczynała się od najwyższego operatora
        Collections.reverse(queue);

        // Tworzę najwyższy element usuwając go z kolejki
        expressionTree = new TreeNode(queue.poll());
        TreeNode lastNode = expressionTree;

        // Zmienna określająca ostatnią zmianę
        boolean isLastTokenOperator = true;

        while (queue.size() > 0) {
            if (isLastTokenOperator) {
                TreeNode leftNode = new TreeNode(queue.poll());

                lastNode.setLeftNode(leftNode);
                lastNode = leftNode;

            } else {
                TreeNode parent = lastNode.getFirstParentWithEmptyRightNode();
                lastNode = new TreeNode(queue.poll());

                parent.setRightNode(lastNode);
            }

            isLastTokenOperator = ExpressionOperators.isOperator(lastNode.getValue());
        }

        return expressionTree;
    }

    /**
     * Wypisuje podane drzewo
     */
    public void printExpressionTree() {
        printExpressionTree(getExpressionTree(), 0);
    }
    private void printExpressionTree(TreeNode node, int level) {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++)
            tabs.append("\t");

        System.out.println(tabs + node.getValue());
        if (node.getRightNode() != null)
            printExpressionTree(node.getLeftNode(), level + 1);
        if (node.getRightNode() != null)
            printExpressionTree(node.getRightNode(), level + 1);
    }

    /**
     * Sortuje tokeny z porządku naturalnego do odwrotnej notacji polskiej
     */
    private String[] convertTokensToReversePolishNotation() {
        // Przygotowuję listę wynikową tokenów w notacji oraz stos operatorów
        ArrayList<String> result = new ArrayList<>();
        Stack<String> operatorsStack = new Stack<>();

        for (String token : getExpressionTokens()) {
            // Pomijaj puste tokeny
            if (token.isEmpty())
                continue;

            // Jeśli to operator, to dodaj poprzedni do wyniku (jeśli jest bardziej wiążący), a nowy dodaj na stos
            if (ExpressionOperators.isOperator(token)) {
                while (!operatorsStack.empty() && ExpressionOperators.isOperator(operatorsStack.peek())) {
                    if (ExpressionOperators.compareOperators(token, operatorsStack.peek()) <= 0) {
                        result.add(operatorsStack.pop());
                        continue;
                    }
                    break;
                }
                operatorsStack.push(token);

                // Dodaj "(" do stosu
            } else if (token.equals("(")) {
                operatorsStack.push(token);

                // Jeśli ")" wykonaj dodaj do wyniku kolejne zapisane operatory z tego nawiasu
            } else if (token.equals(")")) {
                while (!operatorsStack.empty() && !operatorsStack.peek().equals("("))
                    result.add(operatorsStack.pop());
                operatorsStack.pop();

                // Jeśli nie jest to żaden z operatorów dodaj do wyniku
            } else
                result.add(token);
        }

        // Dodaj pozostałe operatory do wyniku
        while (!operatorsStack.empty())
            result.add(operatorsStack.pop());

        String[] newTokens = new String[result.size()];
        return result.toArray(newTokens);
    }
}
