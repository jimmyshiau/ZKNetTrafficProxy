package org.zkoss.nettrafficproxy.api;

public interface MessageHandler {
	
	public void log(String msg);

	public void printStackTrace(Exception e);
}
