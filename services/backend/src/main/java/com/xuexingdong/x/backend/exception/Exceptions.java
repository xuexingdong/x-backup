package com.xuexingdong.x.backend.exception;

import com.xuexingdong.x.backend.exception.activity.PointNotEnoughException;
import com.xuexingdong.x.backend.exception.common.SecretKeyErrorException;
import com.xuexingdong.x.backend.exception.common.UserAlreadyExistsException;
import com.xuexingdong.x.backend.exception.common.UserNotExistException;

public final class Exceptions {
    public static final UnknownException UNKNOWN = new UnknownException();

    public static final SecretKeyErrorException SECRET_KEY_ERROR = new SecretKeyErrorException();
    public static final UserNotExistException USER_NOT_EXIST = new UserNotExistException();
    public static final UserAlreadyExistsException USER_ALREADY_EXISTS = new UserAlreadyExistsException();

    public static final PointNotEnoughException POINT_NOT_ENOUGH = new PointNotEnoughException();

    private Exceptions() {
    }
}
