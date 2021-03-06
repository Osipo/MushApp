package ru.crock.app.utils.structures.trees;

import java.util.List;

public interface PositionalTree<T> {
    void addTo(Node<T> n, T item);
    Node<T> rightMostChild(Node<T> n);
    List<Node<T>> getChildren(Node<T> n);
    void visit(VisitorMode order, Action<Node<T>> act);
    PositionalTree<T> getSubTree(Node<T> n);
}
