package com.jzwy.zkx.product.rest.api.common.http;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;

public class CookieUtil {

	public static Cookie newCookie(String name, String value, String domain) throws Exception {
		return newCookie(name, value, domain, null);
	}

	public static Cookie newCookie(String name, String value, String domain, Integer maxAge) throws Exception {
		Cookie cookie = new Cookie(name, value);
		if (!StringUtils.isEmpty(domain)) {
			cookie.setDomain(domain);
		}
		if (null != maxAge) {
			cookie.setMaxAge(maxAge);
		}
		return cookie;
	}

}
