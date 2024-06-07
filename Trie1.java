import java.util.HashSet;
import java.util.Set;

public class Trie1 {
    static class Node {
        Node[] children;
        boolean endOfWord;

        public Node() {
            children = new Node[26];
            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
            endOfWord = false;
        }
    }

    static Node root = new Node();
    public static void insert(String word) {
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (curr.children[index] == null) {
                curr.children[index] = new Node();
            }
            if (i == word.length() - 1) {
                curr.children[index].endOfWord = true;
            }
            curr = curr.children[index];
        }
    }

    public static void displayAllMiddleSequenceStrings(String sequence) {
        Set<String> result = new HashSet<>();
        dfs(root, sequence, new StringBuilder(), 0, sequence.length(), result);
        for (String str : result) {
            System.out.println(str);
        }
    }

    private static void dfs(Node node, String sequence, StringBuilder currentWord, int index, int length, Set<String> result) {
        if (node == null) return;
        if (index == length && node.endOfWord) {
            result.add(currentWord.toString());
        }
        if (index < length) {
            int nextIndex = sequence.charAt(index) - 'a';
            if (node.children[nextIndex] != null) {
                currentWord.append(sequence.charAt(index));
                dfs(node.children[nextIndex], sequence, currentWord, index + 1, length, result);
                currentWord.setLength(currentWord.length() - 1);
            }
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                currentWord.append((char) ('a' + i));
                dfs(node.children[i], sequence, currentWord, index, length, result);
                currentWord.setLength(currentWord.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        String words[] = { "kamattiffins", "kamatkosher", "kamatcafe", "kamathotel", "kamatdose","panjurli" };
        for (String word : words) {
            insert(word);
        }

        String sequence = "kamat";
        System.out.println("Words containing middle sequence \"" + sequence + "\":");
        displayAllMiddleSequenceStrings(sequence);
    }
}
