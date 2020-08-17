package com.capitalone.dashboard.collector;

import java.io.IOException;

/**
 * Client for fetching the Terraform Cloud App data
 */
public interface HelmClient {

	String getCommandResult(String command, Long timeout) throws RuntimeException, IOException, InterruptedException ;
	
	Object getCommandResultComposed(String command, Long timeout, Class<?> clazz) throws RuntimeException, IOException, InterruptedException, java.text.ParseException;

}
