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
 * DockerClient implementation that uses docker apis to fetch information about
 * Docker Organization, workspace & run job details.
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
			Class clazz) throws RuntimeException, IOException, InterruptedException{
		String line = null;
		boolean matched = false;
		result = getCommandResult(command, timeout);
		//List<Method> methods = new ArrayList<Method>(); 
		
		bufferrdReader = new BufferedReader(new StringReader(result));
		// Flash the below
		//Predicate<Method> isGetter = method -> (Pattern.compile(getter[++i], Pattern.CASE_INSENSITIVE).matcher(method.getName()).find());
		
		//(Stream.of(clazz.getDeclaredMethods()).anyMatch(isGetter)); flash the purpose of anymatch
		
		//Stream.of(clazz.getDeclaredMethods()).filter(isGetter).collect(Collectors.toList());
		
		List<Object> objectList = new ArrayList<Object>();
		
		
        while ((line = bufferrdReader.readLine()) != null){
        	
        	if(matched) {
        		
        		Object values[]  = (line.split("\\t\\s*"));
        		// in java or in subjective coding if you can solve something with abstract algo , then you can use abstartc design patterns
        		
				/*
				 * BaseModel model ;//= ModelBuilder.createModelObject(clazz, values);
				 * 
				 * if(model == null) throw new NoClassDefFoundError();
				 * 
				 * objectList.add(model);
				 */   	}
        	
        	
            if(line.matches(regex)) {
            	matched = true;
            }
            
        }
		
		return null;
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

