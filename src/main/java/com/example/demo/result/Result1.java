package com.example.demo.result;

import lombok.Data;

/**
 * @author Evan
 * @date 2019/4
 */
@Data
public class Result1 {
  private int code;
  private String message;
  private Object result;

  Result1(int code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.result = data;
  }
}
