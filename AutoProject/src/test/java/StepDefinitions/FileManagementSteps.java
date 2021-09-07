package StepDefinitions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Utils.*;

public class FileManagementSteps {

	// Web driver
	public static WebDriver driver = null;

	@Given("I open the URL: {string}")
	public void i_open_the_url(String string) {
		openURL(string);
	}

	@When("I input email {string} and password {string}")
	public void i_input_email_and_password(String string, String string2) {
		List<WebElement> listElement = driver.findElements(By.xpath("//div[@class='bottom-buttons']/button/span"));
		for (int i = 0; i < listElement.size(); i++) {
			if (listElement.get(i).getText().contains("Accept all cookies")) {
				listElement.get(i).click();
				i = listElement.size();
			}
		}
		driver.findElement(By.id("login-name2")).sendKeys(string);
		driver.findElement(By.id("login-password2")).sendKeys(string2);
	}

	@When("press {string} button")
	public void press_login_button(String string) {
		clickOnButton(string);
	}

	@Then("I see my cloud drive")
	public void i_see_my_cloud_drive() {
		assertTrue(veriryLoggedInMegaPageAlready());
	}

	@Given("I logged in MEGA page already")
	public void i_logged_in_mega_page_already() {
		assertTrue(veriryLoggedInMegaPageAlready());
	}

	@When("I click on folder {string}")
	public void i_click_on_folder(String string) {
		clickOnButton(string);
	}

	@When("I right click on space and select option {string}")
	public void i_right_click_space_and_select_option(String string) {
		rightClickOnSpaceAndSelectOption(string);
	}

	@When("I input file name {string} with content {string} and close file")
	public void i_input_filename_with_content_and_close_file(String string, String string2) {
		assertTrue(createFileWithContent(string, string2));
	}

	@Then("I see the file {string}")
	public void i_see_the_file(String string) {
		assertTrue(verifyFileExist(string));
	}

	@When("I right click on file {string} and select option {string}")
	public void i_right_click_file_and_select_option(String string, String string2) {
		assertTrue(rightClickOnFileAndSelectOption(string, string2));
	}

	@When("I click on {string} button on confirm message")
	public void i_click_on_button_on_confirm_message(String buttonName) {
		clickOnButton(buttonName);
	}

	@Then("I cannot see the file {string}")
	public void i_cannot_see_the_file(String string) {
		assertFalse(verifyFileExist(string));
	}

	@When("I click on view {string}")
	public void i_click_on_view(String string) {
		clickOnView(string);
	}

	@Then("I close the current browser")
	public void i_close_the_current_browser() {
		Utils.closeWebDriver(driver);
	}

//  ============================================= Functions =============================================

	/**
	 * Function to initialize web driver and open it
	 * 
	 * @param url value to open by web browse
	 */
	public void openURL(String url) {
		System.setProperty("webdriver.chrome.driver", "webdrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get(url);
	}

	/**
	 * Function to wait implicitly
	 * 
	 * @param seconds time to wait implicitly
	 */
	public void implicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Function to check user logged in MEGA website already
	 * 
	 */
	public boolean veriryLoggedInMegaPageAlready() {
		try {
			driver.findElement(By.name("cloud-drive"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Function to wait for loading icon invisible
	 * 
	 * @param seconds time to wait
	 */
	public void waitForLoading(int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-spinner.active")));
	}

	/**
	 * Function to click on a button
	 * 
	 * @param button button name
	 */
	public void clickOnButton(String button) {
		try {
			WebElement element;
			System.out.println("[TEST] Click on button: " + button);
			switch (button.toLowerCase().trim()) {
			case "cloud drive":
				element = driver.findElement(By.cssSelector("button.cloud-drive"));
				element.click();
				break;
			case "recents":
				element = driver.findElement(By.cssSelector("button.recents"));
				element.click();
				break;
			case "rubbish bin":
				element = driver.findElement(By.cssSelector("button.rubbish-bin"));
				element.click();
				break;
			case "yes":
				element = driver.findElement(By.cssSelector("button.confirm"));
				element.click();
				break;
			case "no":
				element = driver.findElement(By.cssSelector("button.cancel"));
				element.click();
				break;
			case "save":
				element = driver.findElement(By.cssSelector("button.save-btn"));
				element.click();
				// Explicit wait for saving icon invisible
				waitForLoading(6);
				break;
			case "close":
				element = driver.findElement(By.cssSelector("button.close-btn"));
				element.click();
				break;
			case "file":
				element = driver.findElement(By.cssSelector("button.file-btn"));
				element.click();
				break;
			case "log in":
				element = driver.findElement(By.cssSelector("button.login-button"));
				element.click();
				break;
			case "create":
				element = driver.findElement(By.cssSelector("button.create-file"));
				element.click();
				break;
			}
			waitForLoading(3);
		} catch (Exception e) {
		}
	}

	/**
	 * Function to select a option on dropdown list after right clicking
	 * 
	 * @param option option name
	 */
	public void selectDropdownOption(String option) {
		try {
			WebElement element;
			System.out.println("[TEST] Select dropdown option: " + option);
			switch (option.toLowerCase().trim()) {
			case "open":
				element = driver.findElement(By.cssSelector("a.dropdown-item.edit-file-item"));
				element.click();
				break;
			case "remove":
				element = driver.findElement(By.cssSelector("a.dropdown-item.remove-item"));
				element.click();
				break;
			case "new folder":
				element = driver.findElement(By.cssSelector("a.dropdown-item.newfolder-item"));
				element.click();
				break;
			case "new text file":
				element = driver.findElement(By.cssSelector("a.dropdown-item.newfile-item"));
				element.click();
				break;
			case "restore":
				element = driver.findElement(By.cssSelector("a.dropdown-item.revert-item"));
				element.click();
				break;
			}
			waitForLoading(6);
		} catch (Exception e) {
		}
	}

	/**
	 * Function to click on an icon to show items by List View or Thumbnail View
	 * 
	 * @param option option name
	 */
	public void clickOnView(String viewType) {
		try {
			WebElement element;
			System.out.println("[TEST] Click on view: " + viewType);
			switch (viewType.toLowerCase().trim()) {
			case "thumbnail view":
				element = driver.findElement(By.cssSelector("a.fm-files-view-icon.block-view"));
				element.click();
				break;
			case "List View":
				element = driver.findElement(By.cssSelector("a.fm-files-view-icon.listing-view"));
				element.click();
				break;
			}
			waitForLoading(3);
		} catch (Exception e) {
		}
	}

	/**
	 * Function to right click on a blank space and select an option on dropdown
	 * list after right clicking
	 * 
	 * @param option option name
	 */
	public void rightClickOnSpaceAndSelectOption(String option) {
		// Right click on space
		Actions actions = new Actions(driver);
		WebElement rightClickElement = driver.findElement(By.cssSelector("div.fm-blocks-view>div.megaList"));
		actions.contextClick(rightClickElement).build().perform();
		// Select Option
		selectDropdownOption(option.toLowerCase().trim());
	}

	/**
	 * Function to create a file and input content for that file
	 * 
	 * @param fileName element/file name
	 * @param content  content to write to file
	 */
	public boolean createFileWithContent(String fileName, String content) {
		try {
			WebElement fileNameElement = driver.findElement(By.cssSelector("input[name='dialog-new-file']"));
			// Input file name
			fileNameElement.clear();
			fileNameElement.sendKeys(fileName);
			// Click on Create button
			clickOnButton("Create");
			//Check if file exists
			try {
				if (driver.findElement(By.cssSelector("div.duplicate>section>div>div.duplicated-input-warning"))!=null) {
					driver.findElement(By.cssSelector("button.cancel-create-file")).click();
					System.out.println("[TEST] Create file errorr: file exists already");
					return false;
				}
			}catch(Exception e) {
				
			}
			// Input content
			driver.findElement(By.cssSelector("section>div>div>textarea")).sendKeys(content);
			System.out.println("[TEST] Input content to file: " + content);
			// Save File and
			clickOnButton("Save");
			// Close File
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					driver.findElement(By.cssSelector("li>button.close-btn")));
		} catch (Exception e) {
			System.out.println("[TEST] Function createFileWithContent exception: " + e);
			return false;
		}
		return true;
	}

	/**
	 * Function to right click on a element/file and select an option on dropdown
	 * list after right clicking
	 * 
	 * @param fileName element/file name
	 * @param option   option name
	 */
	public boolean rightClickOnFileAndSelectOption(String fileName, String option) {
		// Right click on file
		Actions actions = new Actions(driver);
		try {
			List<WebElement> fileList = driver.findElements(By.cssSelector("a.file"));
			List<WebElement> spanList = driver.findElements(By.cssSelector("a.file>span.file-block-title"));
			for (int i = 0; i < fileList.size(); i++) {
				if (spanList.get(i).getText().trim().equals(fileName.trim())) {
					System.out.println("[TEST] Right click on file :" + fileList.get(i));
					actions.contextClick(fileList.get(i)).perform();
					i = fileList.size();
				}
			}
			// Select Option
			selectDropdownOption(option.toLowerCase().trim());
		} catch (Exception e) {
			System.out.println("[TEST] Function RightClickOnFileAndSelectOption exception " + e);
			return false;
		}
		return true;
	}

	/**
	 * Function to veriy the file is displayin in the browser
	 * 
	 * @param fileName name of the file
	 */
	public boolean verifyFileExist(String fileName) {
		List<WebElement> fileList = driver.findElements(By.cssSelector("span.file-block-title"));
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getText().trim().equals(fileName.trim())) {
				System.out.println("[TEST] The file exists: " + fileList.get(i).getText());
				return true;
			}
		}
		return false;
	}
}
