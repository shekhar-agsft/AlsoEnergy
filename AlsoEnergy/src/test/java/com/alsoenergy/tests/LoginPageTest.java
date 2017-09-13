package com.alsoenergy.tests;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alsoenergy.common.CrossBrowser;
import com.alsoenergy.common.Reader;
import com.alsoenergy.common.SampleEmailUtility;
import com.alsoenergy.pageobjects.LoginPage;

public class LoginPageTest {

	public static Logger logger = Logger.getLogger(LoginPageTest.class);

	public LoginPage loginPageObject;
	
	 private CrossBrowser browser;
	 public static  RemoteWebDriver driver;
	 
	 private SampleEmailUtility se ; 
	
	  
	 
	//public WebDriver driver;
	public static String appURL = Reader.getConfigPropertyVal("applicationUrl");
	public static String chromeURL = Reader.getConfigPropertyVal("selenium.server.execution.path");

	@BeforeClass(alwaysRun = true)
	public void driverInit() throws Exception {
		se = new SampleEmailUtility();
		 browser = new CrossBrowser();
		 driver = browser.openBrowser(); 
		loginPageObject = PageFactory.initElements(driver, LoginPage.class);

	}

	/**
	 * Nilesh
	 */
	@Test(groups = { "testExecution" })
	public void loginTest() {
		System.out.println("*** Navigation to Application ***");
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		loginPageObject.loginUser();	
	}

	@AfterClass(alwaysRun = true)
	public void cleanup() {
		try {
			se.sendEmail();
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
