package com.iche.xpresspayapi.dto.response;

import com.iche.xpresspayapi.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class APIResponse<T> {

    private String message;
    private String time;
    private T data;
    public APIResponse (T data){
        this.message = "processed Successfully";
        this.time = DateUtils.saveDate(LocalDateTime.now());
        this.data = data;
    }
}