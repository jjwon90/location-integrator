package com.jwjung.location.common.config.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class LoggingResponseErrorDecoder implements ErrorDecoder {

    private static final Logger clientLogger = LoggerFactory.getLogger("feign.error.client");
    private static final Logger serverLogger = LoggerFactory.getLogger("feign.error.server");

    private final ErrorDecoder delegate;

    @Override
    public Exception decode(String methodKey, Response response) {
        var ex = delegate.decode(methodKey, response);

        var status = response.status();
        if (status >= 400 && status < 500) {
            logErrorResponseBody(clientLogger, methodKey, ex, response);
        } else if (status >= 500 && status < 600) {
            logErrorResponseBody(serverLogger, methodKey, ex, response);
        }

        return ex;
    }

    private void logErrorResponseBody(Logger logger,
                                      String methodKey,
                                      Exception ex,
                                      Response response) {
        if (!logger.isInfoEnabled()) {
            return;
        }

        var feignException = getFeignException(ex);
        if (feignException == null) {
            return;
        }

        var content = feignException.responseBody();
        if (content.isEmpty()) {
            return;
        }

        ByteBuffer byteBuffer = content.get();
        logger.info("methodKey: {}, status: {}, body: {}",
                methodKey, response.status(), new String(byteBuffer.array(), UTF_8));
    }

    private FeignException getFeignException(Exception ex) {
        if (ex == null) {
            return null;
        }

        if (ex instanceof FeignException) {
            return (FeignException) ex;
        }

        Throwable cur;
        while ((cur = ex.getCause()) != null) {
            if (cur instanceof FeignException) {
                return (FeignException) cur;
            }
        }
        return null;
    }
}