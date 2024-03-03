package com.xxd.x.auth.exception;


import com.xxd.x.auth.exception.common.WrongPasswordException;
import com.xxd.x.auth.exception.common.UserAlreadyExistsException;
import com.xxd.x.auth.exception.common.UserNotExistException;

public final class Exceptions {
    public static final UnknownException UNKNOWN = new UnknownException();

    public static final UserNotExistException USER_NOT_EXIST = new UserNotExistException();
    public static final UserAlreadyExistsException USER_ALREADY_EXISTS = new UserAlreadyExistsException();
    public static final WrongPasswordException WRONG_PASSWORD = new WrongPasswordException();

    private Exceptions() {
    }
}
