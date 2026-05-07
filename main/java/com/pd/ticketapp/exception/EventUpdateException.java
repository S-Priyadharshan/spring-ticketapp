package com.pd.ticketapp.exception;

public class EventUpdateException extends EventNotFoundException{
    public EventUpdateException(String message) {
        super(message);
    }

    public EventUpdateException(String message,Throwable cause){super(message,cause);}

    public EventUpdateException(Throwable cause){super(cause);}

    public EventUpdateException(String message,Throwable cause,
                                       boolean enableSuppression,boolean writableStackTrace){
        super(message,cause,enableSuppression,writableStackTrace);
    }
}
