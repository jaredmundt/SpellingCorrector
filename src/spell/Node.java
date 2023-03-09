package spell;


public class Node implements INode
{
    private Node parent;
    private Node[] nodes;
    private int value;


    public Node(Node p)
    {
        nodes = new Node[26];
        parent = p;
    }

    /**
     * Adds a Node to represent char c
     */
    public Node addLetter(char c) // throws IndexOutOfBoundsException
    {
        int index = c - 97;
        if (index < 0 || index >= 26) throw new IndexOutOfBoundsException();
        if (nodes[index] == null) nodes[index] = new Node(this);
        return nodes[index];
    }


    public Node nextNode(char c) // throws IndexOutOfBoundsException
    {
        int index = c - 97;
        if (index < 0 || index >= 26) throw new IndexOutOfBoundsException();
        return nodes[index];
    }

    /**
     * Returns the frequency count for the word represented by the node.
     */
    @Override
    public int getValue()
    {
        return value;
    }


    /**
     * Increments the frequency count for the word represented by the node.
     */
    @Override
    public void incrementValue()
    {
        ++value;
    }


    /**
     * Returns the child nodes of this node.
     */
    @Override
    public Node[] getChildren()
    {
        return nodes;
    }
}




