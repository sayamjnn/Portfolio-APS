class Restaurant {
    String name;
    String cuisineType;
    int rating;
    
    // Constructor to initialize the restaurant details
    public Restaurant(String name, String cuisineType, int rating) {
        this.name = name;
        this.cuisineType = cuisineType;
        this.rating = rating;
    }
}

class Node {
    Restaurant data;
    Node left;
    Node right;
    int height;
    public Node(Restaurant data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

public class AVLTree {
    Node root;

    public AVLTree() {
        this.root = null;
    }
    private int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    public Node insert(Node node, Restaurant data) {
        if (node == null)
            return (new Node(data));

        if (data.name.compareTo(node.data.name) < 0)
            node.left = insert(node.left, data);
        else if (data.name.compareTo(node.data.name) > 0)
            node.right = insert(node.right, data);
        else 
            return node;

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);


        if (balance > 1 && data.name.compareTo(node.left.data.name) < 0)
            return rightRotate(node);

        if (balance < -1 && data.name.compareTo(node.right.data.name) > 0)
            return leftRotate(node);

        if (balance > 1 && data.name.compareTo(node.left.data.name) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && data.name.compareTo(node.right.data.name) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.println("Name: " + node.data.name + ", Cuisine Type: " + node.data.cuisineType + ", Rating: " + node.data.rating);
            inorder(node.right);
        }
    }

    public void displayRestaurants() {
        System.out.println("Restaurants in sorted order:");
        inorder(root);
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        tree.root = tree.insert(tree.root, new Restaurant("Canara", "Italian", 4));
        tree.root = tree.insert(tree.root, new Restaurant("Annaleela", "Mexican", 5));
        tree.root = tree.insert(tree.root, new Restaurant("Trupti", "Chinese", 3));
        tree.root = tree.insert(tree.root, new Restaurant("Dominos", "Indian", 4));
        tree.root = tree.insert(tree.root, new Restaurant("BenneDose", "Japanese", 5));

        tree.displayRestaurants();
    }
}
