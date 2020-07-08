package com.cloudera.poverty.base.handler;

import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.R;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.utils.ExceptionUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error();
    }
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public R error(ExpiredJwtException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.JWT_REASON);
    }

    @ExceptionHandler(PaException.class)
    @ResponseBody
    public R error(PaException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public R error(SignatureException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.LOGIN_AUTH);
    }
}