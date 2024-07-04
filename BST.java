import java.util.Scanner;

public class BST {
    TreeNode root;

    public BST() {
        this.root = null;
    }

    public TreeNode insert(TreeNode root, int data) {
        if (root == null) {
            root = new TreeNode(data);
            System.out.println("Root node inserted into tree");
            return root;
        }

        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }

        return root;
    }

    public void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + "\t");
            inorder(root.right);
        }
    }

    public void preorder(TreeNode root) {
        if (root != null) {
            System.out.print(root.data + "\t");
            preorder(root.left);
            preorder(root.right);
        }
    }

    public void postorder(TreeNode root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + "\t");
        }
    }

    public TreeNode deleteNode(TreeNode root, int data) {
        if (root == null) {
            System.out.println("Item not found");
            return root;
        }

        if (data < root.data) {
            root.left = deleteNode(root.left, data);
        } else if (data > root.data) {
            root.right = deleteNode(root.right, data);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.data = minValue(root.right);
            root.right = deleteNode(root.right, root.data);
        }

        return root;
    }

    public int minValue(TreeNode root) {
        int minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    public static void main(String[] args) {
        BST bst = new BST();
        Scanner scanner = new Scanner(System.in);
        int choice, data;

        while (true) {
            System.out.println("\n******** Menu *************");
            System.out.println("1-Insert into BST");
            System.out.println("2-Inorder Traversal");
            System.out.println("3-Preorder Traversal");
            System.out.println("4-Postorder Traversal");
            System.out.println("5-Delete from BST");
            System.out.println("6-Exit");
            System.out.println("*****************************");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter the item to insert:");
                    data = scanner.nextInt();
                    bst.root = bst.insert(bst.root, data);
                    break;
                case 2:
                    if (bst.root == null)
                        System.out.println("Tree is empty");
                    else {
                        System.out.println("Inorder Traversal is...");
                        bst.inorder(bst.root);
                        System.out.println();
                    }
                    break;
                case 3:
                    if (bst.root == null)
                        System.out.println("Tree is empty");
                    else {
                        System.out.println("Preorder Traversal is...");
                        bst.preorder(bst.root);
                        System.out.println();
                    }
                    break;
                case 4:
                    if (bst.root == null)
                        System.out.println("Tree is empty");
                    else {
                        System.out.println("Postorder Traversal is...");
                        bst.postorder(bst.root);
                        System.out.println();
                    }
                    break;
                case 5:
                    System.out.println("Enter the item to be deleted:");
                    data = scanner.nextInt();
                    bst.root = bst.deleteNode(bst.root, data);
                    break;
                case 6:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
}
