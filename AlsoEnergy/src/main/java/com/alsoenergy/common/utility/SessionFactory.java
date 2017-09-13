package com.alsoenergy.common.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alsoenergy.common.Reader;

public class SessionFactory {
	
	Map<String, String> sessionCookies = new HashMap<String, String>();

	public SessionFactory() {

	}
	public Map<String, String> getSessionCookies() {
		return sessionCookies;
	}

	public void setSessionCookies(Map<String, String> sessionCookies) {
		this.sessionCookies = sessionCookies;
	}

	public ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).setSocketTimeout(timeout)
				.build();
		CloseableHttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.setRedirectStrategy(new DefaultRedirectStrategy() {
					@Override
					public boolean isRedirected(HttpRequest request,
							HttpResponse response, HttpContext context)
							throws ProtocolException {
						if (response.getFirstHeader("Location") != null) {
							String redirectURL = response.getFirstHeader(
									"Location").getValue();
							System.out.println("redirectURL:: " + redirectURL);
							Header[] headerArray = response.getAllHeaders();
							for (Header header : headerArray) {
								if ("Set-Cookie".compareToIgnoreCase(header
										.getName()) == 0) {
									String[] splitCookie = header.getValue()
											.split(" ");
									for (int i = 0; i < splitCookie.length; i++) {
										
										if(splitCookie[i].endsWith(";"))
										{
											splitCookie[i] = splitCookie[i].substring(0, splitCookie[i].length() - 1);
										}
										String[] cookie = splitCookie[i].split("=");
										if(cookie.length==2)
										{
											sessionCookies.put(cookie[0], cookie[1]);
										}else if(cookie.length==1)
										{
											sessionCookies.put(cookie[0], "");
										}
									}
								}
							}
							return true;
						}
						return false;
					}
				}).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}

	public RestTemplate getRestTemplate() {
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(
				new BufferingClientHttpRequestFactory(requestFactory));
		restTemplate.setErrorHandler(new MyResponseErrorHandler());
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor(this));
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

	public String getRequestVerificationToken(RestTemplate restTemplate) {
		ResponseEntity<String> response = restTemplate.getForEntity(
				Reader.getConfigPropertyVal("rest.api.login"),
				String.class);
		Document doc = Jsoup.parse(response.getBody());
		Element inputName = doc
				.select("input[name=__RequestVerificationToken]").first();
		return inputName.attr("value");
	}

	public void loginToPortalViaAPI(String requestToken,
			RestTemplate restTemplate) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("__RequestVerificationToken", requestToken);
		params.set("Username", Reader.getConfigPropertyVal("username"));
		params.set("Password", Reader.getConfigPropertyVal("password"));

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Set-Cookie", getSessionCookiesAsString());
		headers.add("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");
		headers.set("Accept", "text/html");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				params, headers);
		restTemplate.postForObject(
				Reader.getConfigPropertyVal("rest.api.login"), request,
				String.class);
	}

	private String getSessionCookiesAsString() {
		StringBuffer stringBuffer = new StringBuffer();
        Set<Map.Entry<String, String>> entrySet = sessionCookies.entrySet();
        for(Map.Entry<String, String> entry : entrySet )
        {
        	stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "; ");
        }
        System.out.println("sessionCookies to String :: " + stringBuffer.toString());
		return stringBuffer.toString();
	}

}
