package com.trimly.exceptions;

public class ResourceFoundException extends RuntimeException {
    public ResourceFoundException(String msg) {
        super(msg);
    }
    public ResourceFoundException() {
        super("Resource found.");
    }
}
