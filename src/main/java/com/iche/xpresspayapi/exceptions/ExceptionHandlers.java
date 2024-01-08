package com.iche.xpresspayapi.exceptions;



import com.iche.xpresspayapi.dto.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIResponse<ExceptionResponse>> invalid(InvalidCredentialsException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        APIResponse<ExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponse<ExceptionResponse>> UserNotFound(UserNotFoundException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        APIResponse<ExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<APIResponse<ExceptionResponse>> userDisabled(UserDisabledException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        APIResponse<ExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<APIResponse<ExceptionResponse>> userAlreadyExistsException(UserAlreadyExistsException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        APIResponse<ExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BillersRequestException.class)
    public ResponseEntity<APIResponse<ExceptionResponse>> BillersRequestException(BillersRequestException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        APIResponse<ExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
