package ru.crock.app.utils.structures.trees;

public interface Visitor<T> {
    void preOrder(Tree<T> n, Action<Node<T>> act);
    void inOrder(Tree<T> t,Action<Node<T>> act);
    void postOrder(Tree<T> n, Action<Node<T>> act);
}
