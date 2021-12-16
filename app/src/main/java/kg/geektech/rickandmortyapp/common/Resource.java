package kg.geektech.rickandmortyapp.common;

import androidx.annotation.NonNull;

public class Resource<T> {

    @NonNull
    public final Status status;
    public final T data;
    public final String message;

    public Resource(@NonNull Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data){
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String message, T data){
        return new Resource<>(Status.ERROR, data, message);
    }

    public static <T> Resource<T> loading(){
        return new Resource<>(Status.LOADING, null, null);
    }

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}
