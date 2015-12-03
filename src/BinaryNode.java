public class BinaryNode {

    private String value;

    private BinaryNode leftNode;
    private BinaryNode rightNode;

    public BinaryNode(String value) {
        this.value = value;
    }

    public boolean isValue() {
        return leftNode == null && rightNode == null;
    }

    public BinaryNode getLeftNode() {
        return leftNode;
    }

    public BinaryNode getRightNode() {
        return rightNode;
    }

    public BinaryNode setLeftNode(BinaryNode node) {
        leftNode = node;
        return this;
    }

    public BinaryNode setRightNode(BinaryNode node) {
        rightNode = node;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}