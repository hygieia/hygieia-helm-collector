package com.capitalone.dashboard.exception;

/**
 * Application configuration and bootstrap
 */
public class CommandLineException extends Exception {

    public CommandLineException(String command) {

        super("Command: " + command);
    }

}
