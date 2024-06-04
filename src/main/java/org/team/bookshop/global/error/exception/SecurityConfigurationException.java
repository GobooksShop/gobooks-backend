package org.team.bookshop.global.error.exception;

import org.team.bookshop.global.error.ErrorCode;

public class SecurityConfigurationException extends ApiException {

  public SecurityConfigurationException(String message) {
    super(message, ErrorCode.SECURITY_CONFIGURATION_ERROR);
  }
}
