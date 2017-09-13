package com.alsoenergy.tests;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alsoenergy.common.CrossBrowser;
import com.alsoenergy.common.Reader;
import com.alsoenergy.common.utility.SessionFactory;
import com.alsoenergy.pageobjects.HomePage;
import com.alsoenergy.pageobjects.LoginPage;
import com.alsoenergy.restapi.response.objects.Sites;

public class HomePageTest {
	public static Logger logger = Logger.getLogger(HomePageTest.class);
	// public WebDriver driver;
	public static String appURL = Reader.getConfigPropertyVal("applicationUrl");
	public static String chromeURL = Reader
			.getConfigPropertyVal("selenium.server.execution.path");
	public HomePage homeTestPage;
	public LoginPage loginTestPage;
	private CrossBrowser browser;
	public static RemoteWebDriver driver;
	private RestTemplate restTemplate;
	private SessionFactory sessionFactory;

	@BeforeClass(alwaysRun = true)
	public void initSession() throws Exception {
		sessionFactory = new SessionFactory();
		restTemplate = sessionFactory.getRestTemplate();
		browser = new CrossBrowser();
		driver = browser.openBrowser();
		loginTestPage = PageFactory.initElements(driver, LoginPage.class);
		homeTestPage = PageFactory.initElements(driver, HomePage.class);
		sessionFactory.loginToPortalViaAPI(
				sessionFactory.getRequestVerificationToken(restTemplate),
				restTemplate);
	}

	@Test(groups = { "sitetest" })
	public void verifySiteCountWithFooter() {
		driver.navigate().to(appURL);
		loginTestPage.loginUser();
		homeTestPage.verifySiteCount();
	}

	@Test(groups = { "testExecution" })
	public void verifySiteCountWithRestApi() {
		driver.navigate().to(appURL);
		Sites[] sitesList = homeTestPage.getSitesForPortfolio(sessionFactory,restTemplate);
		logger.info("resp api list of sites :" + sitesList.length);
		loginTestPage.loginUser();
		homeTestPage.verifySiteCountWithRestApi(sitesList);
	}


	
	@AfterTest(alwaysRun = true)
	public void cleanup() {
		try {
			driver.quit();
			logger.info("Driver quit.");
		} catch (WebDriverException e) {
			logger.error("Exception while closing the driver :: "
					+ e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
		}
	}
}
