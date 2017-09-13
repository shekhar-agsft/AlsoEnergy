package com.alsoenergy.tests;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alsoenergy.common.CrossBrowser;
import com.alsoenergy.common.Reader;
import com.alsoenergy.common.utility.SessionFactory;
import com.alsoenergy.pageobjects.HomePage;
import com.alsoenergy.pageobjects.LoginPage;
import com.alsoenergy.pageobjects.SitePage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class SitePageTest {
	public static Logger logger = Logger.getLogger(HomePageTest.class);
	public static String appURL = Reader.getConfigPropertyVal("applicationUrl");
	public HomePage homeTestPage;
	public SitePage sitePage;
	public LoginPage loginTestPage;
	private CrossBrowser browser;
	public static  RemoteWebDriver driver;
	ExtentReports report;
	ExtentTest extentTestlogger;
	private SessionFactory sessionFactory;
	private RestTemplate restTemplate;
		@BeforeClass(alwaysRun = true)
		public void initSession() throws Exception  
		{	
			sessionFactory=new SessionFactory();
			restTemplate=sessionFactory.getRestTemplate();
			browser = new CrossBrowser();
			driver = browser.openBrowser(); 
			loginTestPage = PageFactory.initElements(driver, LoginPage.class);
			homeTestPage = PageFactory.initElements(driver, HomePage.class);
			sitePage = PageFactory.initElements(driver, SitePage.class);
			sessionFactory.loginToPortalViaAPI(
					sessionFactory.getRequestVerificationToken(restTemplate),
					restTemplate);
		}
	
	
	@Test(groups = { "testExecution" })
	public void verifySiteDeviceTest() {
		driver.navigate().to(appURL);
		loginTestPage.loginUser();
		List<WebElement> listSites = homeTestPage.verifySiteCount();
		sitePage.getDeviceCountForSites(listSites,restTemplate,sessionFactory);
	}

	@AfterClass(alwaysRun = true)
	public void cleanup() {
		try {
			driver.quit();
			logger.info("Driver quit.");
		} catch (WebDriverException e) {
			logger.error("Exception while closing the driver :: " + e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
	}
}
