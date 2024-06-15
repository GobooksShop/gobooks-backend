package org.team.bookshop.global.response;

import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.ErrorMessage;

public class ApiResponse<T> {

  /**
   * private record Headers(int resultCode, String resultMessage, boolean isSuccess) {
   * <p>
   * public static Headers success() { return new Headers(200, "Success", true); }
   * <p>
   * public static Headers created() { return new Headers(201, "Created", true); }
   * <p>
   * public static Headers noContent() { return new Headers(204, "No Content", true); }
   * <p>
   * public static Headers badRequest() { return new Headers(400, "Bad Request", false); }
   * <p>
   * public static Headers notFound() { return new Headers(404, "Not Found", false); }
   * <p>
   * public boolean getIsSuccess() { return isSuccess; } }
   */
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
