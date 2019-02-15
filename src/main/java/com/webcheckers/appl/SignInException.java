package com.webcheckers.appl;

/**
 * Thrown if a user attempts to sign in with an invalid (or taken) name.
 *
 * @author Michael Bianconi
 * @since Sprint 1
 */
public class SignInException extends Exception {

    public SignInException(String errorMessage) {
        super(errorMessage);
    }
}