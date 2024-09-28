package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {

        // ログにエラー内容を出力
        log.error("エラーが発生しました。メソッド名：{}, 例外クラス名：{}",
                ex.getStackTrace()[0].getMethodName(),
                ex.getClass().getName());

        // エラーページを表示
        return "errorView";
    }
}
