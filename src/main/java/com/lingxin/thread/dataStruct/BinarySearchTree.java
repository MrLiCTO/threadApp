package com.lingxin.thread.dataStruct;

public class BinarySearchTree { // 二叉搜索树类
    private class Node { // 节点类
        int data; // 数据域
        Node right; // 右子树
        Node left; // 左子树
    }

    private Node root; // 树根节点

    public void insert(int key) {
        Node node = new Node();
        node.data = key;
        if (root == null)
            root = new Node();
        else {
            Node parent = new Node();
            Node current = root;
            while (true) {
                parent = current;
                if (key > current.data) {
                    current = current.right;
                    if (current == null) {
                        parent.right = node;
                        return;
                    }
                } else if (key < current.data) {
                    current = current.left;
                    if (current == null) {
                        parent.left = node;
                        return;
                    }
                } else {//相等，节点已存在，不操作
                    return;
                }
            }
        }
    }

    public Node find(int key) {
        if (root == null)
            return null;
        Node cur = root;
        while (cur != null) {
            int data = cur.data;
            if (key > data) {
                cur = cur.right;
            } else if (key < data) {
                cur = cur.left;
            } else {
                return cur;
            }
        }
        return null;
    }

    public Node findMin(Node node) {
        if (node == null)
            return null;
        Node left = new Node();
        while (node.left != null)
            left = node.left;
        return left;
    }

    public Node remove(int key,Node node) {
        if (node==null)
            return null;
        int camp=node.data-key;
        if (camp>0){
            node.right=remove(key,node.right);
        }else if (camp<0){
            node.left=remove(key,node.left);
        }else if (node.left!=null&&node.right!=null){
            node.data = findMin(node).data;
            node.right = remove(key, node.right);
        }else{
            node=(node.left!=null)?node.left:node.right;
        }
        return node;
    }
}