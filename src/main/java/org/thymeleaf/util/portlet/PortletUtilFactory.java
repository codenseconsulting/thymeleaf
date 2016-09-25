package org.thymeleaf.util.portlet;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;


public class PortletUtilFactory {
	
	static class NonPortletUtil implements IPortletUtil {
		
		public final boolean isSupported() {
			return true;
		}
		public final boolean isPortletEnv() {
			return false;
		}

		public final String getPortletNamespace() {
	    	return "";
		}
		public <T> T createActionURL(Class<T> targetClass, String actionName, String portletMode, String windowState,
				Map<String, String> parameters) {
			return null;
		}
		public <T> T createRenderURL(Class<T> targetClass, String portletMode, String windowState,
				Map<String, String> parameters) {
			return null;
		}
		public <T> T createResourceURL(Class<T> targetClass, String resourceId, String cacheability,
				Map<String, String> parameters) {
			return null;
		}
		
		
	}
	
	private static PortletUtilFactory INSTANCE;
	
	private boolean initialized;
	private IPortletUtil portletUtil;
	
	public static PortletUtilFactory getInstance() {
		synchronized(PortletUtilFactory.class) {
			if (INSTANCE == null) {
				INSTANCE = new PortletUtilFactory();
			}
			return INSTANCE;
		}
	}
	private PortletUtilFactory() {
		
	}
	
	void initialize() {
		final ServiceLoader<IPortletUtil> loader = 
				ServiceLoader.load(IPortletUtil.class);
		
		final Iterator<IPortletUtil> it = loader.iterator();
		
		while (it.hasNext()) {
			final IPortletUtil f = it.next();
			if (f.isPortletEnv()) {
				// TODO log this!
				this.portletUtil = f;
				break;
			}
		}
		if (this.portletUtil == null) {
			this.portletUtil = new NonPortletUtil();
		}
		initialized = true;
	}
	
	public IPortletUtil getPortletUtil() {
		synchronized (this) {
			if (!initialized) {
				initialize();
			}
			return portletUtil;
		}
	}
}
