package com.revature.common.util.aspect;

import com.revature.common.dtos.ErrorResponse;
import com.revature.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAspect {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DuplicateGroupNameException.class, DuplicateRequestException.class, })
    public ErrorResponse conflictExceptionHandler(Exception e) {
        return new ErrorResponse(409, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({GroupNotFoundException.class, PostNotFoundException.class, UserNotFoundException.class})
    public ErrorResponse notFoundExceptionHandler(Exception e) {
        return new ErrorResponse(404, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRequestException.class})
    public ErrorResponse badRequestExceptionHandler(Exception e) {
        return new ErrorResponse(400, e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedRequestException.class})
    public ErrorResponse unauthorizedExceptionHandler(Exception e) {
        return new ErrorResponse(401, e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({WrongUserException.class})
    public ErrorResponse forbiddenExceptionHandler(Exception e) {
        return new ErrorResponse(403, e);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorResponse unsupportedMediaTypeExceptionHandler(Exception e) {
        return new ErrorResponse(415, e);
    }
}
