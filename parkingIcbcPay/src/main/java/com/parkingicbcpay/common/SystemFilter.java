package com.parkingicbcpay.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemFilter implements Filter {
	private HashMap urlRewriteList;
	@SuppressWarnings("unchecked")
	public void init(FilterConfig filterConfig) throws ServletException {
		urlRewriteList = new HashMap();
		urlRewriteList.put("/", "/");
	}

	public void doFilter(ServletRequest httpServletRequest, ServletResponse httpServletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = ((HttpServletRequest) httpServletRequest);
		HttpServletResponse response = ((HttpServletResponse) httpServletResponse);
		String key = request.getServletPath();
		if(null != urlRewriteList.get(key))
		{
			request.getRequestDispatcher((String)urlRewriteList.get(key)).forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}
		
	private  Map<String,Cookie> ReadCookieMap(HttpServletRequest request){ 
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}

	public void destroy() {
	}
	
}
