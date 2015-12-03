import java.util.Arrays;

public class Tree {
    private Tree() {
        // Empty
    }

    public static BinaryNode makeTree(String... tokens) {
        if (tokens.length == 0)
            return null;

        if (tokens.length == 1)
            return new BinaryNode(tokens[0]);

        BinaryNode node = new BinaryNode(tokens[tokens.length - 1]);
        int arguments = tokens.length;
        node.setLeftNode(makeTree(Arrays.copyOfRange(tokens, 0, arguments / 2)));
        node.setRightNode(makeTree(Arrays.copyOfRange(tokens, arguments / 2, arguments - 1)));

        return node;
    }

    public static void printTree(BinaryNode node) {
        printTree(node, 0);
    }

    private static void printTree(BinaryNode node, int level) {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++)
            tabs.append("\t");

        System.out.println(tabs + node.getValue());
        if (node.getRightNode() != null)
            printTree(node.getLeftNode(), level + 1);
        if (node.getRightNode() != null)
            printTree(node.getRightNode(), level + 1);
    }
}
