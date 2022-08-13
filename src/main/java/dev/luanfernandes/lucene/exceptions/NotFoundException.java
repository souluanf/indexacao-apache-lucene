package dev.luanfernandes.lucene.exceptions;

import java.io.Serial;
public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6830728365180450132L;

    public NotFoundException(String message) {
        super(message);
    }
}
