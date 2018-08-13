/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.szollosi.timelogger.root.exceptions;

/**
 *
 * @author precognox
 */
public class NotExpectedTimeOrderException extends RuntimeException{
    
    private String message = "The endtime can not be less than the starttime! ";

    public NotExpectedTimeOrderException() {
    }

    public NotExpectedTimeOrderException(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return message;
    }
    
    
}
