package com.capitalone.dashboard.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.capitalone.dashboard.builder.ModelBuilder;
import com.capitalone.dashboard.command.util.CommandLineUtil;
import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.BaseModel;

/**
 * HelmClient implementation that uses helm command line to fetch information about Kube Deployments
 * 
 */

@Component
public class DefaultHelmClient implements HelmClient {
    private static final Log LOG = LogFactory.getLog(DefaultHelmClient.class);
    String result;
    BufferedReader bufferrdReader;
    
    @Override
	public String getCommandResult(String command, Long timeout) throws RuntimeException, IOException, InterruptedException {
		// TODO Auto-generated method stub

		return CommandLineUtil.execCommand(command, timeout);
	}

    @Override
	public Object getCommandResultComposed(String command, String regex, Long timeout,
			Class clazz) throws RuntimeException, IOException, InterruptedException, java.text.ParseException{
		String line = null;	
		boolean matched = false;
		result = getCommandResult(command, timeout);
		
		bufferrdReader = new BufferedReader(new StringReader(result));
		
		List<Object> objectList = new ArrayList<Object>();
		
		
        while ((line = bufferrdReader.readLine()) != null){
        	
        	if(matched) {
        		
        		Object values[]  = (line.split("\\t\\s*"));
        		
				
				  BaseModel model = ModelBuilder.createModelObject(clazz, values);
				  
				  if(model == null) 
					  throw new NoClassDefFoundError();
				  
				  objectList.add(model);
				   }
        	
        	
            if(line.matches(regex)) {
            	matched = true;
            }
            
        }
		
		return objectList;
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


}

