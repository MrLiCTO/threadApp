package com.lingxin.thread.dataStruct;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Stack;

@Setter
@Getter
public class TreeNode<T> {
    TreeNode<T> left;
    TreeNode<T> right;
    T value;

    public TreeNode(T value) {
        super();
        this.value = value;
    }

    private static TreeNode<Integer> createBinTree_prein(int[] pre, int[] in, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd) return null;
        int i = 0;
        for (i = inStart; i < inEnd; i++) {
            if (in[i] == pre[preStart]) break;
        }
        TreeNode<Integer> s = new TreeNode<Integer>(in[i]);
        s.left = createBinTree_prein(pre, in, preStart + 1, preStart + i - inStart, inStart, i - 1);
        s.right = createBinTree_prein(pre, in, preStart + i - inStart + 1, preEnd, i + 1, inEnd);
        return s;
    }

    private static TreeNode<Integer> createBinTree_prein_my(int[] pre, int[] in, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd)
            return null;
        int i = 0;
        for (i = inStart; i < inEnd; i++) {
            if (in[i] == pre[preStart])
                break;
        }
        TreeNode<Integer> treeNode = new TreeNode<Integer>(in[i]);
        treeNode.left = createBinTree_prein_my(pre, in, preStart + 1, preStart + i - inStart, inStart, i - 1);
        treeNode.right = createBinTree_prein_my(pre, in, preStart + i - inStart + 1, preEnd, i + 1, inEnd);
        return treeNode;
    }

    private static void levelOrder_q(TreeNode<Integer> node) {
        if (node == null)
            return;
        LinkedList<TreeNode> qeue = new LinkedList<>();
        qeue.offer(node);
        while (!qeue.isEmpty()) {
            TreeNode pop = qeue.poll();
            System.out.print(pop.value + " ");
            if (pop.left != null){
                qeue.offer(pop.left);
            }
            if (pop.right != null){
                qeue.offer(pop.right);
            }
        }
    }

    private static void preOrder(TreeNode<Integer> node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    private static void preOrder_s(TreeNode<Integer> node) {
        if (node == null)
            return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            System.out.print(pop.value + " ");
            if (pop.right != null)//栈先进后出，所以右节点在前
                stack.push(pop.right);
            if (pop.left != null)
                stack.push(pop.left);
        }
    }

    private static void inOrder(TreeNode<Integer> node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    private static void inOrder_s(TreeNode<Integer> node) {
        if (node == null) return;
        Stack<TreeNode> s1 = new Stack<>();
        while (!s1.isEmpty()||node!=null){
            if (node!=null){
                s1.push(node);
                node=node.left;
            }else{
                node=s1.pop();
                System.out.print(node.value + " ");
                node=node.right;
            }
        }
    }


    private static void lastOrder(TreeNode<Integer> node) {
        if (node == null) return;
        lastOrder(node.left);
        lastOrder(node.right);
        System.out.print(node.value + " ");
    }

    private static void lastOrder_ss(TreeNode<Integer> node) {
        if (node==null)
            return;
        Stack<TreeNode<Integer>> s1 = new Stack<>();
        Stack<TreeNode<Integer>> s2 = new Stack<>();
        s1.push(node);
        while (!s1.isEmpty()){
            TreeNode<Integer> pop = s1.pop();
            s2.push(pop);
            if (pop.left!=null)
                s1.push(pop.left);
            if (pop.right!=null)
                s1.push(pop.right);
        }

        while (!s2.isEmpty())
            System.out.print(s2.pop().value+" ");
    }

    public static void main(String[] args) {
        int[] pres = new int[]{1, 2, 4, 6, 3, 5, 7, 8};
        int[] ints = new int[]{2, 6, 4, 1, 7, 5, 8, 3};
        TreeNode<Integer> root = createBinTree_prein_my(pres, ints, 0, 7, 0, 7);
        System.out.println("先");
        preOrder(root);
        System.out.println();
        System.out.println("先");
        preOrder_s(root);
        System.out.println();
        System.out.println("中");
        inOrder(root);
        System.out.println();
        System.out.println("中");
        inOrder_s(root);
        System.out.println();
        System.out.println("后");
        lastOrder(root);
        System.out.println();
        System.out.println("后");
        lastOrder_ss(root);
        System.out.println();
        System.out.println("层");
        levelOrder_q(root);
    }
}
