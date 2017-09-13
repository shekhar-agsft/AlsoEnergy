package com.alsoenergy.pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;

import com.alsoenergy.common.FunctionLibrary;
import com.alsoenergy.common.Reader;
import com.alsoenergy.common.utility.SessionFactory;
import com.alsoenergy.restapi.response.objects.Hardware;
import com.alsoenergy.restapi.response.objects.Portfolio;
import com.alsoenergy.restapi.response.objects.SiteDevice;
import com.alsoenergy.restapi.response.objects.Sites;

public class SitePage {

	public Logger logger = Logger.getLogger(SitePage.class);
	private FunctionLibrary functionLibrary;

	@FindBy(how = How.XPATH, using = "//img[contains(@src,'https://www.alsoenergy.com/pub/Images/Device')]")
	public List<WebElement> deviceCount = new ArrayList<>();

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Operating Since')]")
	public WebElement siteStatus;

	@FindBy(how=How.XPATH,using="//div[contains(@style,'flex-basis: 80%')]")
	public List<WebElement> deviceNames=new ArrayList<>();
	
	private WebDriver driver;

	public SitePage(WebDriver driver) {
		this.driver = driver;
		functionLibrary = new FunctionLibrary(driver);
	}

	/**
	 *
	 * @param sites
	 *            Function Checks the total number of devices inside each site
	 */
	public void getDeviceCountForSites(List<WebElement> sites,RestTemplate restTemplate,SessionFactory sessionFactory) {
		logger.info("Inside site getDeviceCount" + sites.size());
		
		/**
		 * 1.Get all the site list response from rest api
		 * 2.Storted all sites and there key value to call device api
 		 * 3. Get all the list of site names.
		 * 4 . construct the XPATH for each List names.	
		 * 5 .Perform click on that element
		 * 6. Check site is  operation or not 
		 * 7 . If navigate . back else verify step 8
		 * 8 . verify devices are matching with rest api data
		 */
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		
		headers.set(
				"Authorization",
				sessionFactory.getSessionCookies().get("AUTH"));
		headers.set("Referer", "Test");
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Portfolio> siteResponse=getRestApiSitesList(restTemplate,entity);
		HashMap<String,String> siteKeys=new HashMap<String,String>();
		
		for(Sites site:siteResponse.getBody().getSites())
		{
			siteKeys.put(site.getName(), site.getKey());
		}
	    if(!siteKeys.isEmpty())
	    {
		String failureMsg="";
		for (int i = 0; i < sites.size(); i++) {
			String siteName=sites.get(i).getText();
			
			WebElement we = driver.findElement(By.xpath("//*[@id='app']//div[contains(@class,'fixedDataTableRowLayout_rowWrapper')]//div[contains(@class,'public_fixedDataTableCell_cellContent')]/div/div[2]/div[contains(text(),'" + siteName + "')]"));
			we.click();
			if (functionLibrary.isElementNotPresent(siteStatus)) {
				logger.error("Site"  +siteName+  "is not Operating");
				driver.navigate().back();
			} 
			else 				
			{  
				logger.info("site device key"+siteKeys.get(siteName));
				ResponseEntity<SiteDevice> deviceResponse=getRestApiDeviceList(restTemplate,entity,siteKeys.get(siteName));
				Hardware[] deviceList=deviceResponse.getBody().getHardware();
				if(functionLibrary.isElementPresent(deviceCount))
			    {
				logger.info("There are"+" "+""+deviceCount.size()+" "+ "devices for site::"+siteName+" "+  "are as follows  ");
				int webElementItr=0;
				for (WebElement webElement : deviceNames)
				{    
				if(webElement.getText().contains(deviceList[webElementItr].getName()))
				{
					logger.info("Device matched : site device name"+webElement.getText() +": api device name"+deviceList[webElementItr].getName());
				}
				else
				{
					failureMsg=" Portfolia site name "+siteName+"  : Device name is  "+ webElement.getText() +" not matching api device name "+deviceList[webElementItr].getName();
				}
				webElementItr++;
				}
				driver.navigate().back();
			 }
			else
			{
				Assert.fail("Device list not found on portfolia site : "+ siteName +" : failure page :"+driver.getCurrentUrl());
			}			
			}
			
			if(failureMsg!="")
			{
				Assert.fail("Rest api device list not matched with portfolia site device"+failureMsg);
			}
		}
	    }
	    else
	    {
	    	Assert.fail("Portfoilia site rest api service response failed.");
	    }
		
	}
	
	/**
	 * Get portfolio sites rest api response object
	 * @param restTemplate
	 * @param entity
	 * @return
	 */
	public ResponseEntity<Portfolio> getRestApiSitesList(RestTemplate restTemplate,HttpEntity<?> entity)
	{	
		ResponseEntity<Portfolio> response = restTemplate
				.exchange(
						Reader.getConfigPropertyVal("rest.api.sitelist")+"/"+"9007199254750473?lastChanged=2017-09-04T09:18:50Z&mergeHash=11057242&version=7",
						HttpMethod.GET, entity, Portfolio.class);
		return response;
	}
	/**
	 * Get site device list rest api response object
	 * @param restTemplate
	 * @param entity
	 * @param deviceKey
	 * @return
	 */
	public ResponseEntity<SiteDevice> getRestApiDeviceList(RestTemplate restTemplate,HttpEntity<?> entity,String deviceKey)
	{	
		ResponseEntity<SiteDevice> response = restTemplate
				.exchange(
						Reader.getConfigPropertyVal("rest.api.devicelist")+"/"+deviceKey+"?lastChanged=2017-09-06T11:18:59Z&version=5",
						HttpMethod.GET, entity, SiteDevice.class);
		return response;
	}
}
