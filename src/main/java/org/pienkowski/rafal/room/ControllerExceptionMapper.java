package org.pienkowski.rafal.room;

import org.pienkowski.rafal.room.api.InvalidAmountException;
import org.pienkowski.rafal.room.api.WrongRoomTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionMapper {

    @ExceptionHandler({InvalidAmountException.class, WrongRoomTypeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected String handleInvalidArguments(RuntimeException ex) {
        return ex.getMessage();
    }
}
