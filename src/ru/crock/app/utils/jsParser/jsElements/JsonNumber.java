package ru.crock.app.utils.jsParser.jsElements;

public class JsonNumber extends JsonElement<Number> {

    private Number n;

    public JsonNumber(Number n){
        this.n = n;
    }

    @Override
    public String toString() {
        return n.toString();
    }

    @Override
    public Number getValue() {
        return n;
    }
}
