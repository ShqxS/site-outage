package com.kranflex.outage.configuration;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        Exception exception = new Default().decode(methodKey, response);
        log.error(exception.getMessage());

        if (exception instanceof RetryableException) {
            return exception;
        }

        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            log.info("Retryable Status, request will be retried again...");
            return new RetryableException(
                    response.status(),
                    "Server error:" + response.reason(),
                    response.request().httpMethod(),
                    null, response.request());
        }

        return exception;
    }

}
