package com.iche.xpresspayapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private String message;
    private LocalDateTime localDateTime;
    private Integer statusCode;
}
