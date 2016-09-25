package org.thymeleaf.util.portlet;

import java.util.Map;

/**
 * Utilities for portlet environments.
 * 
 * @author mikko
 *
 */
public interface IPortletUtil {
// IMPLEMENTATION NOTE: do not expose javax.portlet-api classes directly in
// this interface!
	
	boolean isSupported();
	boolean isPortletEnv();
	String getPortletNamespace();
	
	<T> T createActionURL(final Class<T> targetClass, final String actionName, final String portletMode, final String windowState, final Map<String, String> parameters);
	<T> T createRenderURL(final Class<T> targetClass, final String portletMode, final String windowState, final Map<String, String> parameters);
	<T> T createResourceURL(final Class<T> targetClass, final String resourceId, final String cacheability, final Map<String, String> parameters);
}
