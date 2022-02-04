package com.velvet.models.result;

public class Result<T> {

    public final T data;
    public final Throwable error;

    public static <T> Result<T> success(T data) {
        return new Result<T>(data, null);
    }

    public static <T> Result<T> error(Exception exception) {
        return new Result<T>(null, exception);
    }

    public Result(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }

    public boolean isError() {
        return error != null;
    }
}
