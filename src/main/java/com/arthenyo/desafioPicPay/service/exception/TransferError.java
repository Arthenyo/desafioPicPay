package com.arthenyo.desafioPicPay.service.exception;

public class TransferError extends RuntimeException{
    public TransferError(String msg){
        super(msg);
    }
}
