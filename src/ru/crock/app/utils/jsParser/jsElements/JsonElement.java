package ru.crock.app.utils.jsParser.jsElements;

import java.util.ArrayList;

public abstract class JsonElement<T> {
    public abstract T getValue();

    @Override
    public String toString(){
        return getValue().toString();
    }

}
