package ru.crock.app.utils.structures.trees;

public class Node<T> {
    protected T value;

    public void setValue(T val) {
        this.value = val;
    }

    public T getValue() {
        return value;
    }

    public Node(T val){
        this.value = val;
    }

    public Node(){
        this.value = null;
    }
}
