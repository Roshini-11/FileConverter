package com.brimmatech.FileConverter.responseHandling;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseMessage<T> {
    private Integer code;
    private String message;
    private T data;
}

