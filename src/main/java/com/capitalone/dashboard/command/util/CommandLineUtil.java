package com.capitalone.dashboard.command.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

public class CommandLineUtil {
	
	/*
	 * public static void main(String[] args) throws RuntimeException, IOException,
	 * InterruptedException { String result = (execCommand("netstat -a -o -n",
	 * 3000l)); }
	 */
	
	
	public static String execCommand(String cmd, Long timeout) throws RuntimeException, IOException, InterruptedException {
		
			CommandLine commandLine = CommandLine.parse(cmd);

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			Executor executor = new DefaultExecutor();

			// put a watchdog with a timeout
			ExecuteWatchdog watchdog = new ExecuteWatchdog(new Long(timeout) * 1000);
			executor.setWatchdog(watchdog);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PumpStreamHandler psh = new PumpStreamHandler(os);
			executor.setStreamHandler(psh);

			executor.execute(commandLine, resultHandler);
			resultHandler.waitFor();
			int exitVal = resultHandler.getExitValue();
			if (exitVal != 0) {
				return null;
			}
			 
			
			return (os.toString());

		}

}
