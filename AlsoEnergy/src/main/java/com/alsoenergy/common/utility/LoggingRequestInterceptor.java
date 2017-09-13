package com.alsoenergy.common.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	public static Logger logger = Logger.getLogger(LoggingRequestInterceptor.class);
	private SessionFactory sf;
	
	public LoggingRequestInterceptor()
	{
		
	}
	public LoggingRequestInterceptor(SessionFactory sf) {
		this.sf=sf;
	}

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        if(cookies!=null)
        {
		for (Iterator<String> iterator = cookies.iterator(); iterator.hasNext();) {
			String cook = (String) iterator.next();
			String[] splitCookie = cook.split(" ");
			for (int i = 0; i < splitCookie.length; i++) {
				if(splitCookie[i].endsWith(";"))
				{
					splitCookie[i] = splitCookie[i].substring(0, splitCookie[i].length() - 1);
				}
				String[] cookie = splitCookie[i].split("=");
				if(cookie.length==2)
				{
					sf.getSessionCookies().put(cookie[0], cookie[1]);
				}else if(cookie.length==1)
				{
					sf.getSessionCookies().put(cookie[0], "");
				}
			}
		}
        }
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
    	System.out.println("===========================request begin================================================");
    	System.out.println("URI         : {}" +  request.getURI());
    	System.out.println("Method      : {}"+ request.getMethod());
    	System.out.println("Headers     : {}"+ request.getHeaders() );
    	System.out.println("Request body: {}"+ new String(body, "UTF-8"));
    	System.out.println("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        System.out.println("============================response begin==========================================");
        System.out.println("Status code  : {}"+ response.getStatusCode());
        System.out.println("Status text  : {}"+ response.getStatusText());
        System.out.println("Headers      : {}"+ response.getHeaders());
        System.out.println("Response body: {}"+ inputStringBuilder.toString());
        System.out.println("=======================response end=================================================");
    }

}