package org.thymeleaf.util.portlet;

/**
 * A utility to resolve if we have portlet API's in the current runtime context.
 * 
 * @author mikko
 *
 */
class PortletEnvResolver {

	private static final String PORTLET_RESPONSE_CLASS_NAME = "javax.portlet.PortletResponse";
//	private static final String PORTLET_REQUEST_ATTRIBUTES_CLASS_NAME = "org.springframework.web.portlet.context.PortletRequestAttributes";
	
	public static boolean isPortletEnv() {
		// Bind classes lazily
		try {
			Class.forName(PORTLET_RESPONSE_CLASS_NAME);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
