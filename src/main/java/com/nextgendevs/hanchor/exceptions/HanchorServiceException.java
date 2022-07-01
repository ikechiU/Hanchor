package com.nextgendevs.hanchor.exceptions;

public class HanchorServiceException extends RuntimeException{

    private static final long serialVersionUID = 1348771109171435607L;

    public HanchorServiceException(String message)
    {
        super(message);
    }
}