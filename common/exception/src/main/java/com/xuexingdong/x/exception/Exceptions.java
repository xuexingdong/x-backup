package com.xxd.x.exception;


import com.xxd.x.exception.common.UserAlreadyExistsException;
import com.xxd.x.exception.common.UserNotExistException;
import com.xxd.x.exception.common.WrongPasswordException;

public final class Exceptions {
    public static final UnknownException UNKNOWN = new UnknownException();

    public static final UserNotExistException USER_NOT_EXIST = new UserNotExistException();
    public static final UserAlreadyExistsException USER_ALREADY_EXISTS = new UserAlreadyExistsException();
    public static final WrongPasswordException WRONG_PASSWORD = new WrongPasswordException();

    private Exceptions() {
    }
}
