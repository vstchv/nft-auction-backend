package nftauction.web.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputError {
  String message;
  InputErrorCode errorCode;
}
