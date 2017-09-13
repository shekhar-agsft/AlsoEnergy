package com.alsoenergy.common.utility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class APITestUtility {
	String globalCookies;
	String AUTH;

	public String getGlobalCookies() {
		return globalCookies;
	}

	public void setGlobalCookies(String globalCookies) {
		this.globalCookies = globalCookies;
	}

	public String getAUTH() {
		return AUTH;
	}

	public void setAUTH(String aUTH) {
		AUTH = aUTH;
	}

	public static void main(String[] args) throws URISyntaxException,
			IOException {
		APITestUtility testUtility = new APITestUtility();

		ClientHttpRequestFactory requestFactory = testUtility
				.getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(
				new BufferingClientHttpRequestFactory(requestFactory));
		restTemplate.setErrorHandler(new MyResponseErrorHandler());

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);

		ResponseEntity<String> response = restTemplate.getForEntity(
				"https://apps.alsoenergypreview.com/Account/Login",
				String.class);
		List<String> cookies = response.getHeaders().get("Set-Cookie");
		String cookieToSet = "";
		for (Iterator iterator = cookies.iterator(); iterator.hasNext();) {
			String cook = (String) iterator.next();
			String[] c = cook.split(" ");
			cookieToSet = c[0].substring(0, c[0].length() - 1);
		}

		Document doc = Jsoup.parse(response.getBody());
		Element inputName = doc
				.select("input[name=__RequestVerificationToken]").first();
		String __RequestVerificationToken = inputName.attr("value");
		
		/**
		 * Second POST Call
		 */

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("__RequestVerificationToken", __RequestVerificationToken);
		params.set("Username", "agilesoftguest");
		params.set("Password", "agsft2017");

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Set-Cookie", cookieToSet);
		headers.add("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");
		headers.set("Accept", "text/html");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				params, headers);
		String temp = restTemplate.postForObject(
				"https://apps.alsoenergypreview.com/Account/Login", request,
				String.class);
//System.out.println("temp : " + temp);
//		System.out.println(" AUTH captured :: " + testUtility.getAUTH());

		
		/**
		 * Third POST Call
		 */
		headers = new LinkedMultiValueMap<String, String>();
		headers.set(
				"Authorization",
				testUtility.getAUTH().substring(5,
						testUtility.getAUTH().length()));
		headers.set("Referer", "Test");
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<ReturnObject> response2;
		response2 = restTemplate
				.exchange(
						"https://apps.alsoenergypreview.com/api/Portfolio/9007199254750473?lastChanged=2017-09-04T09:18:50Z&mergeHash=11057242&version=7",
						HttpMethod.GET, entity, ReturnObject.class);

		System.out.println(response2.getBody());

	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
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
							// String redirectURL =
							// response.getFirstHeader("Location").getValue();
							// System.out.println("redirectURL:: " +
							// redirectURL);
							Header[] headerArray = response.getAllHeaders();
							for (Header header : headerArray) {
								if ("Set-Cookie".compareToIgnoreCase(header
										.getName()) == 0) {
									globalCookies = header.getValue();
									// System.out.println("globalCookies :: " +
									// globalCookies);
									String[] c = globalCookies.split(" ");
									for (String cookie : c) {
										if (cookie.startsWith("AUTH"))
											AUTH = cookie.substring(0,
													cookie.length() - 1);
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

}
