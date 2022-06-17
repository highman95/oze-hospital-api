package com.oze.hospitalmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private boolean status = false;
    private T data = null;
    private String message;

    public Response(String message) {
        this.message = message;
    }
}
