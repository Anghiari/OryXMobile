package com.oryx.handlers;


public class SessionHandler {

	private static SessionHandler sh = null;
	private static boolean isValidApp = false;

	private SessionHandler() {
	}

	public static SessionHandler getHandler() {
		if (sh == null) {
			return new SessionHandler();
		} else {
			return sh;
		}
	}

	public static boolean isValidApp() {
		return isValidApp;
	}

	public static void setValidApp(boolean isValidApp) {
		SessionHandler.isValidApp = isValidApp;
	}
}
