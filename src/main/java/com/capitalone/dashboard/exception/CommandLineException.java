package com.capitalone.dashboard.exception;

/**
 * Application configuration and bootstrap
 */
public class CommandLineException extends Exception{
		
	public CommandLineException(String command, String regex) {
		
		super("Command: "+ command + "has failed to match Regex: " + regex);
	}

}
