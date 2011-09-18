package org.zkoss.nettrafficproxy;


public class MessageHandler {
	
	private static MessageHandler _handler;
	
	
	public void log(String msg) {
		System.out.println(msg);
	}

	public void printStackTrace(Exception e) {
		e.printStackTrace();
	}

	public static void setInstance(MessageHandler handler) {
		_handler = handler;
	}

	public static MessageHandler getInstance() {
		return _handler;
	}

}
