package ru.crock.app.utils.jsParser.jsElements;

public class JsonBoolean extends JsonElement<Boolean> {

    private boolean v;
    public JsonBoolean(Character c){
        v = (c == 't');
    }

    @Override
    public Boolean getValue() {
        return v;
    }

}
