/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.szollosi.timelogger.root.exceptions;

/**
 *
 * @author mszollosi
 */
public class NotSeparatedTimesException extends RuntimeException{

    private final String message;
    
    public NotSeparatedTimesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
