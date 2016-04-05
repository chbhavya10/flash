package com.sermon.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class RequestUtils {

	public static String getBaseURL(HttpServletRequest request){
		StringBuffer baseUrl = new StringBuffer();
		int serverPort = request.getServerPort();
		String serverScheme = request.getScheme();
		String serverName = request.getServerName();
		baseUrl.append(serverScheme).append("://").append(serverName);
		if(serverPort !=443 && serverPort !=80 && serverPort !=-1){
			baseUrl.append(":"+serverPort);
		}
		return baseUrl.toString();
	}
	public static String getBaseURLWithContext(HttpServletRequest request){
		return getBaseURL(request) + request.getContextPath();
	}
	
	public static HttpServletRequest getCurrentHttpRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
}
