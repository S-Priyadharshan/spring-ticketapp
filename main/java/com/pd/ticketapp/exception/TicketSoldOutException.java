package com.pd.ticketapp.exception;

public class TicketSoldOutException extends EventNotFoundException {
    public TicketSoldOutException(String message) {
        super(message);
    }

    public TicketSoldOutException(String message,Throwable cause){super(message,cause);}

    public TicketSoldOutException(Throwable cause){super(cause);}

    public TicketSoldOutException(String message,Throwable cause,
                                     boolean enableSuppression,boolean writableStackTrace){
        super(message,cause,enableSuppression,writableStackTrace);
    }
}
