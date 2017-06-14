package io.mycat.mydog.logs;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class MyDogLog {

	protected static Log log = null;

	public static Log getLog() {
		log = null == log ? new SystemStreamLog():log ;
		return log;
	}
	
	public static void setLog(Log log) {
		MyDogLog.log = log;
	}
	
}
