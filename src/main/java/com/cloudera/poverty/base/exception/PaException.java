package com.cloudera.poverty.base.exception;

import com.cloudera.poverty.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class PaException extends RuntimeException{

    private Integer code;

    public PaException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public PaException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "PaException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
