package com.banvien.fc.common.rest.errors;

import org.springframework.validation.FieldError;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestException extends AbstractThrowableProblem {

    private String errorKey;

    private static final long serialVersionUID = 1L;

    public BadRequestException(FieldError fieldError) {
        super(ErrorConstants.BAD_REQUEST_TYPE, fieldError.getCode(), Status.BAD_REQUEST);
        this.errorKey = fieldError.getCode();
    }
    public BadRequestException(String errorKey) {
        super(ErrorConstants.BAD_REQUEST_TYPE, errorKey, Status.BAD_REQUEST);
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
