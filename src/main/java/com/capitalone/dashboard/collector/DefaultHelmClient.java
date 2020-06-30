package com.capitalone.dashboard.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.capitalone.dashboard.command.util.CommandLineUtil;
import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.Release;
import com.capitalone.dashboard.util.Supplier;

/**
 * DockerClient implementation that uses docker apis to fetch information about
 * Docker Organization, workspace & run job details.
 */

@Component
public class DefaultHelmClient implements HelmClient {
    private static final Log LOG = LogFactory.getLog(DefaultHelmClient.class);
    private final RestTemplate restTemplate ;
    MultiValueMap<String, String> headerMultiValueMap;
    HttpEntity<String> httpEntity;
    String result;
    BufferedReader bufferrdReader;
    
    
    @Autowired
    public DefaultHelmClient(Supplier<RestOperations> restOperationsSupplier) {
        this.restTemplate =  new RestTemplate();
    }
    
    /**
     * Gets runs for a given Workspace
     * @param url
     * @param apiToken
     * @throws RestClientException
     * @throws MalformedURLException
     * @throws HygieiaException
     */
    public JSONObject getDataAsObject(String url) throws RestClientException, MalformedURLException, HygieiaException{
    	LOG.debug("Making rest call to URL ::" + url);
    	ResponseEntity<String> responseJSON = makeRestCall(url);
    	if (responseJSON != null) 
    		return parseAsObject(responseJSON);
    	else
    		return null;
    }
    
    /**
     * Gets runs for a given Workspace
     * @param url
     * @param apiToken
     * @throws RestClientException
     * @throws MalformedURLException
     * @throws HygieiaException
     */
    public JSONArray getDataAsArray(String url) throws RestClientException, MalformedURLException, HygieiaException{
    	LOG.debug("Making rest call to URL ::" + url);
    	ResponseEntity<String> responseJSON = makeRestCall(url);
    	if (responseJSON != null) 
    		return parseAsArray(responseJSON);
    	else
    		return null;
    }
    
    /**
     * Gets runs for a given Workspace
     * @param url
     * @param apiToken
     * @throws RestClientException
     * @throws MalformedURLException
     * @throws HygieiaException
     */
    public JSONObject getData(String url, String apiToken) throws RestClientException, MalformedURLException, HygieiaException{
    	LOG.debug("Making rest call to URL ::" + url);
    	ResponseEntity<String> responseJSON = makeRestCall(url, apiToken);
    	if (responseJSON != null) 
    		return parseAsObject(responseJSON);
		else
			return null;
    }
    
    
    private ResponseEntity<String> makeRestCall(String url, String apiToken) {
        // No Auth
            return restTemplate.exchange(url, HttpMethod.GET,getHeaders(apiToken),String.class);
    }
    
    
    private ResponseEntity<String> makeRestCall(String url) {
    	// Basic Auth only.
    	return restTemplate.exchange(url, HttpMethod.GET,null,String.class);
    }
    
    private HttpEntity<String> getHeaders(final String apiToken) {
        headerMultiValueMap = new LinkedMultiValueMap<String, String>();
        headerMultiValueMap.add("Authorization",
				"Bearer " + apiToken);
        httpEntity = new HttpEntity<String>(headerMultiValueMap);
        return httpEntity;
    }

    public JSONArray parseAsArray(ResponseEntity<String> response) {
        try {
            return (JSONArray) new JSONParser().parse(response.getBody());
        } catch (ParseException pe) {
            LOG.error(pe.getMessage());
        }
        return new JSONArray();
    }

    public JSONObject parseAsObject(String response) {
        try {
            return (JSONObject) new JSONParser().parse(response);
        } catch (ParseException pe) {
            LOG.error(pe.getMessage());
        }
        return null;
    }

    private int asInt(JSONObject json, String key) {
        String val = str(json, key);
        try {
            if (val != null) {
                return Integer.parseInt(val);
            }
        } catch (NumberFormatException ex) {
            LOG.error(ex.getMessage());
        }
        return 0;
    }

    public String str(JSONObject json, String key) {
        Object value = json.get(key);
        return value == null ? null : value.toString();
    }
    
	
	public JSONArray getJSONArray(JSONObject obj, String key) {
		if(obj.containsKey(key))
			return (JSONArray) obj.get(key);
		
		return null;
	}

	

    /**
     * Date utility
     * @param dateInstance
     * @param offsetDays
     * @param offsetMinutes
     * @return
     */
    private Date getDate(Date dateInstance, int offsetDays, int offsetMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateInstance);
        cal.add(Calendar.DATE, offsetDays);
        cal.add(Calendar.MINUTE, offsetMinutes);
        return cal.getTime();
    }

	public String getCommandResult(String command, Long timeout) throws RuntimeException, IOException, InterruptedException {
		// TODO Auto-generated method stub

		return CommandLineUtil.execCommand(command, timeout);
	}

	public List<Release> getCommandResultComposed(String command, String regex, Long timeout,
			Class clazz) throws RuntimeException, IOException, InterruptedException{
		String line = null;
		boolean matched = false;
		result = getCommandResult(command, timeout);
		String[] getter = regex.split("");
		int i =-1;
		Method[] methods = b
		
		
		bufferrdReader = new BufferedReader(new StringReader(result));
		
		Predicate<Method> isGetter = method -> (Pattern.compile(getter[++i], Pattern.CASE_INSENSITIVE).matcher(method.getName()).find());
		
		Stream.of(clazz.getDeclaredMethods()).anyMatch(method -> {
			
			
					
		
		});
        while ((line = bufferrdReader.readLine()) != null){
        	
        	if(matched) {
        		
        		int i = -1;
        		Stream.of(line.split("\\t\\s*")).forEach(data -> {
        			Object o = clazz.newInstance();
        			clazz.
        			Method method = clazz.getDeclaredMethod("set" +  getter[++i].to)
        			
        			
        			
        			
        			
        		})
        		
        		
        		
        	}
        	
        	
            if(line.matches(regex)) {
            	matched = true;
            }
            
        }
		
		
		
		
		return null;
	}

}

