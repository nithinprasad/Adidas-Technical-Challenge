package com.prasad.nithin.subscription.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ErrorBody {
    String code;
    String message;
    String field;
    Object value;
}