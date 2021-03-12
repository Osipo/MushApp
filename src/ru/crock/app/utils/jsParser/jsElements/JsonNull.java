package ru.crock.app.utils.jsParser.jsElements;

public class JsonNull extends JsonElement<String> {
    @Override
    public String getValue() {
        return "null";
    }
}
