package ru.crock.app.utils.jsParser;

public class JsonParseException extends RuntimeException {
    public JsonParseException(String message, Throwable throwable){
        super(message,throwable);
    }
}
