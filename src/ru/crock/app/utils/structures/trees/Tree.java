package ru.crock.app.utils.structures.trees;

public interface Tree<T> {
    Node<T> parent(Node<T> node);
    Node<T> leftMostChild(Node<T> node);
    Node<T> rightSibling(Node<T> node);
    Node<T> root();
    T value(Node<T> node);
    void setVisitor(Visitor<T> visitor);
    int getCount();
    void clear();//FROM ICollection<T>
}
