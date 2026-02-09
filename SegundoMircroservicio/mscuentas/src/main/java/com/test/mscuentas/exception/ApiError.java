package com.test.mscuentas.exception;


import java.time.Instant;

public record ApiError(
        String message,
        Instant timestamp,
        String path) {
}
