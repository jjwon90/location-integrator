package com.jwjung.location.common.config.feign;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Type;

@RequiredArgsConstructor
public class EmptyOrOptionalResponseDecoder implements Decoder {
    private final Decoder decoder;
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.status() == 404 || response.status() == 204) {
            return Util.emptyValueOf(type);
        }

        var result = decoder.decode(response, type);
        if (response.status() == 200 && result == null) {
            return Util.emptyValueOf(type);
        }

        return result;
    }
}
