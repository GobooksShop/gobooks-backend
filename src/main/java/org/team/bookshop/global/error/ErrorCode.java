package org.team.bookshop.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.logging.LogLevel;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

  // Common
  INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value", LogLevel.ERROR),
  METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value", LogLevel.ERROR),
  ENTITY_NOT_FOUND(400, "C003", " Entity Not Found", LogLevel.ERROR),
  INTERNAL_SERVER_ERROR(500, "C004", "Server Error", LogLevel.ERROR),
  INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value", LogLevel.ERROR),
  HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied", LogLevel.ERROR),
  NO_HANDLER_FOUND(404, "C007", "No Handler found", LogLevel.ERROR),
  NO_RESOURCE_FOUND(404, "C008", "No Resource found", LogLevel.ERROR),

  //USER
  EMAIL_ALREADY_REGISTERED(400, "U001", "Email already registered", LogLevel.ERROR),
  NO_EXISTING_USER(400, "U002", "User doesn't exists", LogLevel.ERROR),
  //BOOK

  //CATEGORY
  INVALID_PARENT_CATEGORY(400, "C009", "Invalid Parent Category", LogLevel.ERROR),

  //ORDER
  NOT_ENOUGH_STOCK_QUANTITY(400, "O001", "Stock Quantity of Product is not enough", LogLevel.ERROR),
  NO_EXISTING_BOOK(404, "O002", "Book with entered id doesn't exist", LogLevel.ERROR),
  INVALID_PRODUCT_PRICE_INFO(400, "O003", "Entered Price doesn't match", LogLevel.ERROR),
  NO_EXISTING_ORDER(404, "O004", "Order with entered id doesn't exist", LogLevel.ERROR),
  CANNOT_CANCEL_ORDER(404, "O005", "Delivery has been started, can't cancel order", LogLevel.ERROR),

  //PAYMENT
  PORTONE_BAD_REQUEST(400, "C011", "Bad Request", LogLevel.ERROR),

  NO_PAYMENT_INFO_WITH_ORDER(404, "C012", "There is no payment information with entered Order", LogLevel.ERROR),

  //Secure
  SECURITY_CONFIGURATION_ERROR(500, "S001", "Security Configuration Error", LogLevel.ERROR),
  AUTHENTICATION_FAILURE(401, "S002", "Authentication Failure", LogLevel.ERROR),
  BANDED_TOKEN(403, "S003", "Token is Baned", LogLevel.ERROR),
  INVALID_REFRESH_TOKEN(401, "S004", "Invalid refresh token", LogLevel.ERROR),
  EXPIRED_ACCESS_TOKEN(401, "S005", "Token refreshed, please retry with new access token",
      LogLevel.ERROR);;

  private final String code;
  private final String message;
  private int status;
  private final LogLevel logLevel;

  ErrorCode(final int status, final String code, final String message, LogLevel logLevel) {
    this.status = status;
    this.message = message;
    this.code = code;
    this.logLevel = logLevel;
  }

  public String getMessage() {
    return this.message;
  }

  public String getCode() {
    return code;
  }

  public int getStatus() {
    return status;
  }

  public LogLevel getLogLevel() {
    return logLevel;
  }

}