/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author mszollosi
 */
public class NotSeperatedTimesException extends RuntimeException{

    private String message;
    
    public NotSeperatedTimesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
