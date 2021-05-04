package org.pienkowski.rafal.room.api;

public class WrongRoomTypeException extends RuntimeException {

    public WrongRoomTypeException(String message) {
        super(message);
    }
}
