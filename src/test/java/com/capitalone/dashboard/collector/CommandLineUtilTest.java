package com.capitalone.dashboard.collector;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.capitalone.dashboard.command.util.CommandLineUtil;

public class CommandLineUtilTest {
	
	
	
	@InjectMocks
	HelmSettings dockerSetings;
	
	@Test
	public void helmInstallation() throws RuntimeException, IOException, InterruptedException {
		when(CommandLineUtil.execCommand("helm version", 
				3000l)).thenReturn(getHelmInstallation());
	}
	
	private String getHelmInstallation() {
		return "";
	}
	
	@Test
	public void  
	
	

}
