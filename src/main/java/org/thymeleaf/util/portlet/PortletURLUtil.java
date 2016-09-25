package org.thymeleaf.util.portlet;

import java.util.HashMap;
import java.util.Map;

import org.unbescape.uri.UriEscape;

public class PortletURLUtil {

	private enum PortletUrlType {
		ACTION, RENDER, RESOURCE, NONE,
	}

	public static String expandPortletURL(final String url) {
		String resultUrl;
		
		final IPortletUtil portletUtil = PortletUtilFactory.getInstance().getPortletUtil();
		if (portletUtil.isPortletEnv()) {
			PortletUrlType urlType;
			if (url.startsWith("action:")) {
				urlType = PortletUrlType.ACTION;
			} else if (url.startsWith("render:")) {
				urlType = PortletUrlType.RENDER;
			} else if (url.startsWith("resource:")) {
				urlType = PortletUrlType.RESOURCE;
			} else {
				urlType = PortletUrlType.NONE;
			}

			if (urlType != PortletUrlType.NONE) {
				final int startPos = url.indexOf(":");

				final String unprefixed = startPos < url.length() - 1 ? url.substring(startPos + 1) : "";

				final int queryStartPos = unprefixed.indexOf('?');

				final String query = (queryStartPos >= 0 && queryStartPos < unprefixed.length() - 1)
						? unprefixed.substring(queryStartPos + 1) : "";
				
				final String base = (queryStartPos >= 0) ? unprefixed.substring(0,  queryStartPos) : unprefixed;
				
				final Map<String, String> params = new HashMap<String, String>();
				final String[] paramValuePairs = query.split("&");
				for (final String paramValuePair: paramValuePairs) {
					final String[] paramAndValue = paramValuePair.split(":");
					if (paramAndValue.length > 0) {
						final String paramName = UriEscape.unescapeUriQueryParam(paramAndValue[0]);
						if (paramName.length() > 0 && paramAndValue.length > 1) {
							final String paramValue = UriEscape.unescapeUriQueryParam(paramAndValue[1]);
							params.put(paramName, paramValue);
						}
					}
				}
				switch (urlType) {
				case ACTION:
					resultUrl = portletUtil.createActionURL(
							String.class, base, null, null, params);
					break;
				case RENDER:
					resultUrl = portletUtil.createRenderURL(
							String.class, null, null, params);
					break;
				case RESOURCE:
					resultUrl = portletUtil.createResourceURL(
							String.class, base, null, params);
					break;
				default:
					throw new IllegalArgumentException("Unsupported portlet url type: " + urlType);
				}
			} else {
				// Not recognized as portlet URL
				resultUrl = url;
			}
		} else {
			// Not in portlet env
			resultUrl = url;
		}
		return resultUrl;
	}

}
