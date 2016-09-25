package org.thymeleaf.util.portlet.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.BaseURL;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.thymeleaf.util.portlet.IPortletUtil;

public abstract class AbstractPortletUtil implements IPortletUtil {

	
	protected AbstractPortletUtil() {
		
	}
	public abstract boolean isSupported();
	public abstract boolean isPortletEnv();
	
	protected abstract PortletResponse getPortletResponse();

	
	public String getPortletNamespace() {
    	final PortletResponse r = getPortletResponse();
    	return (r != null) ? r.getNamespace() : "";
	}
	
	static void checkValidBaseURLClass(final Class<?> clazz, final Class<? extends BaseURL> supportedBaseURL) {
		if (!(clazz.isAssignableFrom(supportedBaseURL) || clazz.equals(String.class))) {
			throw new IllegalArgumentException("Unsupported Portlet URL class: '" + 
					clazz.getName() + "'");
		}
	}
	
	public RenderResponse getRenderResponse(boolean requireValid) {
		PortletResponse r = getPortletResponse();
		if (r != null) {
			if (r instanceof RenderResponse) {
				return (RenderResponse)r;
			} else if (requireValid) {
				throw new RuntimeException("Current response is not RenderResponse");
			} else {
				return null;
			}
		} else if (requireValid) {
			throw new RuntimeException("No current PortletResponse available");
		} else {
			return null;
		}
	}
	
	static void setPortletURLProperties(PortletURL portletUrl, String portletMode, String windowState, String secure) {
		try {
			if (portletMode != null && portletMode.length() > 0) {
				portletUrl.setPortletMode(new PortletMode(portletMode.trim().toLowerCase()));
			}
			if (windowState != null && windowState.length() > 0) {
				portletUrl.setWindowState(new WindowState(windowState.trim().toLowerCase()));
			}
			if (secure != null && secure.length() > 0) {
				if (secure.equalsIgnoreCase("true") || secure.equalsIgnoreCase("yes")) {
					portletUrl.setSecure(true);
				} else {
					portletUrl.setSecure(false);
				}
			}
		} catch (PortletModeException e) {
			throw new IllegalArgumentException("Invalid portlet mode requested: " + portletMode, e);
		} catch (WindowStateException e) {
			throw new IllegalArgumentException("Invalid window state requested: " + windowState, e);
		} catch (PortletSecurityException e) {
			throw new IllegalArgumentException("Invalid portlet security requested: " + secure, e);
		}
	}
	
	static void setBaseURLParameters(BaseURL url, Map<String, String> parameters) {
		String portletMode = null;
		String windowState = null;
		String secure = null;
		
		for (Map.Entry<String, String> e: parameters.entrySet()) {
			String key = e.getValue();
			String value = e.getValue();
			if (key.equalsIgnoreCase("portletMode")) {
				portletMode = value;
			} else if (key.equalsIgnoreCase("windowState")) {
				windowState = value;
			} else if (key.equalsIgnoreCase("secure")) {
				secure = value;
			} else {
				url.setParameter(key, value);
			}
		}
		if (url instanceof PortletURL) {
			setPortletURLProperties((PortletURL)url, portletMode, windowState, secure);
		}
	}

	static <T> T returnBaseURL(Class<T> targetClass, BaseURL url) {
		if (targetClass.equals(String.class)) {
			StringWriter sw = new StringWriter();
			try {
				url.write(sw);
			} catch (IOException e) {
				throw new RuntimeException("Failed to convert BaseURL to String", e);
			}
			sw.flush();
			return targetClass.cast(sw.toString());
		} else {
			return targetClass.cast(url);
		}
	}
	
	public <T> T createActionURL(Class<T> targetClass, String actionName, String portletMode,
			String windowState, Map<String, String> parameters) {
		checkValidBaseURLClass(targetClass, PortletURL.class);
		
		RenderResponse rr = getRenderResponse(true);
		
		PortletURL portletUrl = rr.createActionURL();
		portletUrl.setParameter(ActionRequest.ACTION_NAME, actionName);
		setBaseURLParameters(portletUrl, parameters);
		setPortletURLProperties(portletUrl, portletMode, windowState, null);
		
		return returnBaseURL(targetClass, portletUrl);
	}
	
	

	public <T> T createRenderURL(Class<T> targetClass, String portletMode, String windowState, Map<String, String> parameters) {
		checkValidBaseURLClass(targetClass, PortletURL.class);
		
		RenderResponse rr = getRenderResponse(true);
		
		PortletURL portletUrl = rr.createRenderURL();
		setBaseURLParameters(portletUrl, parameters);
		setPortletURLProperties(portletUrl, portletMode, windowState, null);
		
		return returnBaseURL(targetClass, portletUrl);
	}

	public <T> T createResourceURL(Class<T> targetClass, String resourceId, String cacheability, Map<String, String> parameters) {
		checkValidBaseURLClass(targetClass, ResourceURL.class);
		
		RenderResponse rr = getRenderResponse(true);
		
		ResourceURL resourceUrl = rr.createResourceURL();
		
		if (resourceId != null) {
			resourceUrl.setResourceID(resourceId);
		}
		if (cacheability != null && cacheability.length() > 0) {
			resourceUrl.setCacheability(cacheability.toUpperCase());
		}
		
		setBaseURLParameters(resourceUrl, parameters);
		
		for (Map.Entry<String, String> e: parameters.entrySet()) {
			String key = e.getValue();
			String value = e.getValue();
			if (value == null || value.length() == 0) {
				continue;
			}
			if (key.equalsIgnoreCase("resourceId")) {
				resourceUrl.setResourceID(value);
			} else if (key.equalsIgnoreCase("cacheability")) {
				resourceUrl.setCacheability(value.toUpperCase());
			}
		}
		
		return returnBaseURL(targetClass, resourceUrl);
	}

}