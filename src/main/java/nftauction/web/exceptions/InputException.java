package nftauction.web.exceptions;

public class InputException extends RuntimeException {
  InputErrorCode errorCode;

  public InputException(InputErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public InputException(InputErrorCode errorCode, String message,
                        Throwable throwable) {
    super(message, throwable);
    this.errorCode = errorCode;
  }

  public InputErrorCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(InputErrorCode errorCode) {
    this.errorCode = errorCode;
  }

}
