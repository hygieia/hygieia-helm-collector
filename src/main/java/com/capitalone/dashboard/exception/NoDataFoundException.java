package com.capitalone.dashboard.exception;

/**
 * Application configuration and bootstrap
 */
public class NoDataFoundException extends Exception{
		
	public NoDataFoundException(String command) {
		
		super("Command: "+ command + "has returned no data, Please make sure to run the command on the Command Line");
	}

}
