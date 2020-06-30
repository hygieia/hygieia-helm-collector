package com.capitalone.dashboard.collector;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;
import org.springframework.web.client.RestClientException;

import com.capitalone.dashboard.misc.HygieiaException;

/**
 * Client for fetching the Terraform Cloud App data
 */
public interface HelmClient {

	String getCommandResult(String command, Long timeout) throws RuntimeException, IOException, InterruptedException ;
	
	Object getCommandResultComposed(String command, String regex, Long timeout, Class clazz) throws RuntimeException, IOException, InterruptedException;

}
