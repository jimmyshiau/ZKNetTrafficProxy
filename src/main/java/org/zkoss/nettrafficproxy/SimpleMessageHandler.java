package org.zkoss.nettrafficproxy;

import org.zkoss.nettrafficproxy.api.MessageHandler;

public class SimpleMessageHandler implements MessageHandler {
	
	public void log(String msg) {
		System.out.println(msg);
	}

	public void printStackTrace(Exception e) {
		e.printStackTrace();
	}
}
