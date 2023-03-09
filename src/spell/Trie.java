package spell;

public class Trie implements ITrie
{
    private Node root;
    int numWords;
    int numNodes = 1;
    int hashcode;



    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
     *
     * @param word the word being added to the trie
     */
    @Override
    public void add(String word)
    {
        if (root == null) root = new Node(null);
        Node currNode = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); ++i)
        {
            if (currNode.nextNode(word.charAt(i)) == null)
            {
                ++numNodes;
                hashcode += (word.charAt(i) * word.charAt(i));
            }
            currNode = currNode.addLetter(word.charAt(i));
        }
        currNode.incrementValue();
        if (currNode.getValue() == 1) ++numWords;
    }


    /**
     * Searches the trie for the specified word.
     *
     * @param word the word being searched for.
     *
     * @return a reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    @Override
    public INode find(String word)
    {
        Node currNode = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); ++i)
        {
            if (currNode == null) return null;
            currNode = currNode.nextNode(word.charAt(i));
            if (currNode == null) return null;
        }
        if (currNode.getValue() > 0) return currNode;
        return null;
    }


    /**
     * Returns the number of unique words in the trie.
     */
    @Override
    public int getWordCount()
    {
        return numWords;
    }


    /**
     * Returns the number of nodes in the trie.
     */
    @Override
    public int getNodeCount()
    {
        return numNodes;
    }


    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     * MUST BE RECURSIVE.
     */
    @Override
    public String toString()
    {
        StringBuilder allWords = new StringBuilder();
        StringBuilder wordStart = new StringBuilder();
        toStringHelper(root, allWords, wordStart);
        return allWords.toString();
    }


    private void toStringHelper(Node currNode, StringBuilder allWords, StringBuilder wordStart)
    {
        if (currNode == null) return;

        if (currNode.getValue() > 0) allWords.append(wordStart.toString() + "\n");
        for (int i = 0; i < currNode.getChildren().length; ++i)
        {
            Node child = currNode.getChildren()[i];
            if (child != null)
            {
                char childLetter = (char)(i + 97);
                wordStart.append(childLetter);
                toStringHelper(child, allWords, wordStart);
                wordStart.deleteCharAt(wordStart.length() - 1);
            }
        }
    }


    /**
     * Returns the hashcode of this trie.
     * MUST be constant time.
     * @return a uniform, deterministic identifier for this trie.
     */
    @Override
    public int hashCode()
    {
        return (hashcode * numNodes * numWords);
    }


    /**
     * Checks if an object is equal to this trie.
     * MUST be recursive.
     * @param o Object to be compared against this trie
     * @return true if o is a Trie with same structure and node count for each node
     * 		   false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (this == null) return false;
        if (o.getClass() != this.getClass()) return false;
        Trie other = (Trie)(o);
        if (this.hashCode() != other.hashCode()) return false;
        return equalsHelper(root, other.root);
    }


    private boolean equalsHelper(Node currNode, Node objNode)
    {
        if (currNode == null && objNode == null) return true;
        else if (currNode == null || objNode == null) return false;

        for (int i = 0; i < currNode.getChildren().length; ++i)
        {
            Node child = currNode.getChildren()[i];
            Node objChild = objNode.getChildren()[i];
            if (child != null && objChild == null) return false;
            if (child == null && objChild != null) return false;
            if (child != null && objChild != null)
            {
                if (child.getValue() != objChild.getValue()) return false;
                if (!equalsHelper(child, objChild)) return false;
            }
        }
        return true;
    }





}




