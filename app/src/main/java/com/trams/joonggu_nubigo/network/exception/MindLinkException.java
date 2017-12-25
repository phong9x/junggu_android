package com.trams.joonggu_nubigo.network.exception;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by thangbk on 12/22/14.
 */
public class MindLinkException extends VolleyError {
    private String messageError;
    private int errorCode;

    public MindLinkException(String messageError, int errorCode) {
        this.messageError = messageError;
        this.errorCode = errorCode;
    }

    public MindLinkException(VolleyError volleyError) {
        if (volleyError instanceof TimeoutError) {
            this.errorCode = ExceptionConstant.TIME_OUT;
            this.messageError = volleyError.getMessage();
        } else if (volleyError instanceof ParseError) {
            this.errorCode = ExceptionConstant.ERROR_CODE_PARSE_JSON;
            this.messageError = volleyError.getMessage();
        } else if (volleyError instanceof NetworkError){
            this.errorCode = ExceptionConstant.NO_NETWORK;
            this.messageError = "No network";
        } else if (volleyError instanceof AuthFailureError) {

        } else if (volleyError instanceof MindLinkException) {

        }

    }


    @Override
    public String getMessage() {
        return messageError;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageError() {
        return messageError;
    }
}
