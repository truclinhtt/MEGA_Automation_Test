package Utils;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {

	/**
	 * Function to initialize web driver and open it
	 * 
	 * @param driver	driver to initialize	
	 * @param url		value to open by web browse		
	 */
	public static void openURLwithChrome(WebDriver driver, String url) {
		System.setProperty("webdriver.chrome.driver", "webdrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get(url);
	}

	/**
	 * Function to wait for loading icon invisible
	 * 
	 * @param driver		current webdriver	
	 * @param seconds		time to wait	
	 */
	public static void waitForLoading(WebDriver driver, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-spinner.active")));
		System.out.println("{TEST] Waiting for loading " + seconds + " seconds");
	}

	/**
	 * Function to right click on an element
	 * 
	 * @param driver		current webdriver	
	 * @param element		a web element
	 */
	public static void rightClickOnElement(WebDriver driver, WebElement element) {
		// Right click on belement
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
	}

	/**
	 * Function to close browser
	 * 
	 * @param driver		current webdriver	
	 */
	public static void closeWebDriver(WebDriver driver) {
		driver.close();
	}
}
