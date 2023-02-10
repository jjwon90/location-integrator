package com.jwjung.location.common.config.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder;

    @Override
    public Exception decode(String methodKey, Response response) {
        var ex = defaultDecoder.decode(methodKey, response);
        if (ex instanceof FeignException) {
            var status = ((FeignException) ex).status();
            if (status >= 400 && status < 500) {
                return enrich((FeignException.FeignClientException) ex);
            }
        }

        return ex;
    }

    private FeignException.FeignClientException enrich(FeignException.FeignClientException ex) {
        return new FeignException.FeignClientException(
                ex.status(),
                getMessage(ex),
                ex.request(),
                ex.responseBody()
                        .map(ByteBuffer::array)
                        .orElse(null),
                ex.responseHeaders()
        );
    }

    private String getMessage(FeignException.FeignClientException ex) {
        String content = ex.contentUTF8();
        String originalMessage = ex.getMessage();
        return content.isEmpty() ? originalMessage : originalMessage + "; content: " + content;
    }
}
