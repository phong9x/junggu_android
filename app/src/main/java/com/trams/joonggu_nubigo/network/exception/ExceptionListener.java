package com.trams.joonggu_nubigo.network.exception;

/**
 * Created by phuongnh on 3/19/15.
 */
public interface ExceptionListener {
    public abstract void onErrorListener(int errorCode, String message);
}
