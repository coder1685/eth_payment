package com.eth.payment.transferfunds;

public class TransferFundsException extends Exception {

    TransferFundsException(String message, Throwable e) {
        super(message, e);
    }
}
