package ru.crock.app.utils.structures.trees;

public interface Action<T> {
    void perform(T arg);
}
