package com.jwjung.location.config.feign;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("deprecation")
public class FeignConfig {
    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new OptionalDecoder(
                new EmptyOrOptionalResponseDecoder(
                        new ResponseEntityDecoder(
                                new SpringDecoder(messageConverters))));
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder(
                new LoggingResponseErrorDecoder(
                        new ErrorDecoder.Default()
                ));
    }
}
