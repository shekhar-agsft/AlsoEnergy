package com.alsoenergy.pageobjects;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.tool.XSTCTester.TestCase;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
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
import com.alsoenergy.restapi.response.objects.Portfolio;
import com.alsoenergy.restapi.response.objects.Sites;

/**
 * @author agsuser
 *
 */
public class HomePage extends TestCase {

	public Logger logger = Logger.getLogger(HomePage.class);
	private FunctionLibrary functionLibrary;

	/*
	 * @FindBy(how = How.XPATH, using =
	 * " //*[@id='app']//div[contains(@class,'fixedDataTableRowLayout_rowWrapper')]//img[@src='/Content/images/ui/bldg.png']"
	 * ) public List<WebElement> siteList = new ArrayList<>();
	 */
	@FindBy(how = How.XPATH, using = "//div[@title='Total Sites']/div")
	public WebElement footerTotalSiteCount;

	@FindBy(how = How.XPATH, using = " //*[@id='app']//div[contains(@class,'fixedDataTableRowLayout_rowWrapper')]//div[contains(@class,'public_fixedDataTableCell_cellContent')]/div/div[2]/div")
	public List<WebElement> siteList = new ArrayList<>();

	@FindBy(how = How.XPATH, using = "(//div[@class='fixedDataTableRowLayout_body']//img[@src='/Content/images/ui/bldg.png']/parent::div/parent::div)[1]")
	public WebElement firstportFolioSite;

	/*
	 * @FindBy(how = How.XPATH, using =
	 * "(//*[@id='app']/div/div[1]/div/div[2]/div/div[2]/div[2]/div[1]") public
	 * WebElement firstportFolioSite;
	 */

	private WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		functionLibrary = new FunctionLibrary(driver);
	}

	/**
	 * 
	 * This function verifies the count of sites and count mentioned on footer
	 * are equal or not.
	 * 
	 * @return list of sites
	 */
	public List<WebElement> verifySiteCount() {
		logger.info("verifySiteCount: Inside verify site count page object:"
				+ driver.getCurrentUrl());
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOf(firstportFolioSite));
		if (functionLibrary.isElementPresent(firstportFolioSite)) {
			int siteCount = siteList.size();
			if (functionLibrary.isElementPresent(footerTotalSiteCount)) {
				String footerText = footerTotalSiteCount.getText();
				int footersiteCount = Integer.parseInt(footerText.substring(0,
						footerText.indexOf(" Sites")).trim());
				logger.info("verifySiteCount:  footer site count "
						+ footersiteCount);
				logger.info("verifySiteCount:  dashboard portfolio sites count "
						+ footersiteCount);
				if (siteCount != footersiteCount) {
					Assert.fail("verifySiteCount: Dashboard portfolio sites count and Footer total no sites count doesn't match :Current pag"
							+ driver.getCurrentUrl());
				} else {
					logger.info("verifySiteCount: Dashboard portfolio sites count and Footer total no sites count are equals");
				}
			} else {
				Assert.fail("verifySiteCount: Footer total sites count not present : current url "
						+ driver.getCurrentUrl());
			}
		} else {
			Assert.fail("verifySiteCount: Portfolio sites not present : current url "
					+ driver.getCurrentUrl());
		}
		return siteList;

	}

	public void verifySiteCountWithRestApi(Sites[] apiSiteList) {
		
		logger.info("verifySiteCount: Inside verify site count page object:"
				+ driver.getCurrentUrl());
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOf(firstportFolioSite));
		if (functionLibrary.isElementPresent(firstportFolioSite)) {
			int portfolioSitesCount = siteList.size();
			int i=siteList.size()-1;
			String failureMsg="";
			if (portfolioSitesCount == apiSiteList.length) {
				logger.info("Rest Api sites count and portfolio Sites Count are matching ");
				for(WebElement siteElement:siteList)
				{
					System.out.println("ss"+apiSiteList[i].getName());
					if(apiSiteList[i].getName().equals(siteElement.getText()))
					{
						logger.info("Portfolia site name '"+ siteElement.getText()+" ' match with rest api site name '"+apiSiteList[i].getName()+"'");
					}
					else
					{
						failureMsg=failureMsg+"Portfolia site name ' "+ siteElement.getText()+" ' match with rest api site name '"+apiSiteList[i].getName()+"'";
						logger.error("Portfolia site name ' "+ siteElement.getText()+" ' not match with rest api site name '"+apiSiteList[i].getName()+"'");
					}
					i--;
				}
				
				if(failureMsg!="")
				{
					Assert.fail("Portfolia below site not match with rest api site name\n"+failureMsg);
				}
				else
				{
					logger.info("sites names matched");
				}
				
			} else {
				Assert.fail("verifyRestApiSiteCount: Rest Api sites count doesn't not match with portfolioSite : Rest api count = "
						+ apiSiteList.length
						+ " : portfolioSite sites count ="
						+ portfolioSitesCount);
			}

		} else {
			Assert.fail("verifySiteCount: Portfolio sites not present : current url "
					+ driver.getCurrentUrl());
		}

	}
	
	public Sites[] getSitesForPortfolio(SessionFactory sessionFactory, RestTemplate restTemplate)
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		logger.info("Authorization"
				+ sessionFactory.getSessionCookies().get("AUTH"));
		headers.set("Authorization",
				sessionFactory.getSessionCookies().get("AUTH"));
		headers.set("Referer", "Test");
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Portfolio> response;
		response = restTemplate
				.exchange(
						Reader.getConfigPropertyVal("rest.api.sitelist")+"/9007199254750473?lastChanged=2017-09-04T09:18:50Z&mergeHash=11057242&version=7",
						HttpMethod.GET, entity, Portfolio.class);
		return response.getBody().getSites();
	}
}
