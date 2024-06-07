import java.util.*;

public class RadixTree {
    static class Node {
        Map<Character, Node> children;
        boolean isEndOfWord;

        Node() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }
    private Node root;

    public RadixTree() {
        root = new Node();
    }

    public void insert(String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new Node());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return false;
            }
            current = current.children.get(ch);
        }
        return current.isEndOfWord;
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(Node current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord) {
                return false; 
            }
            current.isEndOfWord = false;
            return current.children.isEmpty(); 
        }

        char ch = word.charAt(index);
        Node node = current.children.get(ch);
        if (node == null) {
            return false; 
        }

        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);

        if (shouldDeleteCurrentNode) {
            current.children.remove(ch);
            return current.children.isEmpty(); 
        }

        return false;
    }

    public List<String> displayWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        Node node = getNodeForPrefix(prefix);
        if (node != null) {
            displayWordsFromNode(node, prefix, result);
        }
        return result;
    }

    private Node getNodeForPrefix(String prefix) {
        Node current = root;
        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return null; // Prefix not found
            }
            current = current.children.get(ch);
        }
        return current;
    }
    private void displayWordsFromNode(Node node, String prefix, List<String> result) {
        if (node.isEndOfWord) {
            result.add(prefix);
        }
        for (char ch : node.children.keySet()) {
            displayWordsFromNode(node.children.get(ch), prefix + ch, result);
        }
    }

    public List<String> displayContainingSubstring(String substring) {
        List<String> result = new ArrayList<>();
        for (char ch : root.children.keySet()) {
            searchSubstring(root.children.get(ch), new StringBuilder().append(ch), substring, result);
        }
        return result;
    }
    private void searchSubstring(Node node, StringBuilder currentWord, String substring, List<String> result) {
        if (currentWord.toString().contains(substring) && node.isEndOfWord) {
            result.add(currentWord.toString());
        }
        for (char ch : node.children.keySet()) {
            currentWord.append(ch);
            searchSubstring(node.children.get(ch), currentWord, substring, result);
            currentWord.setLength(currentWord.length() - 1);
        }
    }
    public static void main(String[] args) {
        RadixTree trie = new RadixTree();

        String[] restaurants = {"kamattiffins", "kamatkosher", "kamatcafe", "kamathotel", "panjurli"};

        for (String restaurant : restaurants) {
            trie.insert(restaurant);
        }
        String substring = "ka";
        List<String> wordsWithSubstring = trie.displayContainingSubstring(substring);
        System.out.println("Words containing substring '" + substring + "': " + wordsWithSubstring);
    }
}
