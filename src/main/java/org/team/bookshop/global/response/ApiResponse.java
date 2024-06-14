package org.team.bookshop.global.response;

import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.ErrorMessage;

public class ApiResponse<T> {

  private final ErrorMessage message;

  private final T data;

  private ApiResponse(ErrorMessage message, T data) {
    this.message = message;
    this.data = data;
  }

  public static ApiResponse<?> success() {
    return new ApiResponse<>(null, null);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<T>(null, data);
  }

  public static ApiResponse<?> error(ErrorCode code) {
    return new ApiResponse<>(new ErrorMessage(code), null);
  }

  public static ApiResponse<?> error(ErrorCode code, Object errorData) {
    return new ApiResponse<>(new ErrorMessage(code, errorData), null);
  }

  public ErrorMessage getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}
