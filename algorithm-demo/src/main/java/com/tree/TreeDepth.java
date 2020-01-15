package com.tree;

import java.util.LinkedList;

public class TreeDepth {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setRight(node5);
        node5.setRight(node6);
        System.out.println(maxDepth(root));
    }

    /**
     * 二叉树深度
     *
     * @param rootNode
     * @return
     */
    public static int maxDepth(TreeNode rootNode) {
        if (rootNode == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(rootNode.left), maxDepth(rootNode.right));
    }

    public static int getNodeNum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<TreeNode> list = new LinkedList();
        list.add(root);

        return 0;
    }

    public static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }

}
