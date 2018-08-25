package com.study.baselibrary.network;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class CustomNetwokException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    public CustomNetwokException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CustomNetwokException(String message, int errorCode, String errorMessage) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CustomNetwokException(String message, Throwable cause, int errorCode, String errorMessage) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CustomNetwokException(Throwable cause, int errorCode, String errorMessage) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "CustomNetwokException:"+"errorCode:"+errorCode+"errorMessage:"+errorMessage;
    }
}
