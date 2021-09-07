package StepDefinitions;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Utils.*;

public class FileDownloadSteps {
	// Web driver
	public static WebDriver driver = null;

	// Local download location
	private String localDownloadLocation = System.getProperty("user.dir") + "\\Downloads";

	// List to store downloaded file names
	List<String> downloadedFileList = new ArrayList<String>();

	@Given("I open the URL: {string} to download")
	public void i_open_the_url_to_download(String string) {
		// Set property for Chrome and open it
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", localDownloadLocation);

		// Adding cpabilities to ChromeOptions
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		System.setProperty("webdriver.chrome.driver", "webdrivers\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(string);
		Utils.waitForLoading(driver, 7);
	}

	@When("I select platform {string}")
	public void i_select_platform(String string) {
		selectPlatform(string);
	}

	@When("I click on All Downloads link")
	public void i_click_on_all_downloads_link() {
		driver.findElement(By.cssSelector("a.download-all-link")).click();
		// Switch to new tab
		ArrayList<String> currentHandleList = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(currentHandleList.get(currentHandleList.size() - 1));
	}

	@When("I download all Linux Distros in all folders")
	public void i_download_all_linux_distros_in_all_folders() {
		DownloadAllLinuxFiles();
	}

	@Then("all files are downloaded")
	public void alls_file_are_downloaded() {
		// Get the user.dir folder
		File folder = new File(localDownloadLocation);
		System.out.println("[TEST] Download folder: " + folder);
		// Compare filenames stored with file files are downloaded in the download location
		for (int j = 0; j < downloadedFileList.size(); j++) {
			assertTrue("Downloaded document is not found", verifyFileExist(downloadedFileList.get(j), folder));
		}
	}

	@Then("I close all the download browsers")
	public void i_close_all_the_download_browsers() {
		// Switch to new tab
		ArrayList<String> currentHandleList = new ArrayList<String>(driver.getWindowHandles());
		for (int i=currentHandleList.size()-1; i>=0; i-- ) {
			driver.switchTo().window(currentHandleList.get(i));
			driver.close();
		}
	}
//  ============================================= Functions =============================================

	/**
	 * Function to download all Linux Distros
	 * 
	 */
	public void DownloadAllLinuxFiles() {
		// Get URL
		String downloadURL = driver.getCurrentUrl();
		System.out.println("[TEST] The current URL: " + downloadURL);
		// Download all links in each folder/subfolder
		downloadFilesInFolder(downloadURL, downloadedFileList);
		// Wait 120 seconds to download
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			System.out.println("[TEST] Failed to download all Linux files with Exception: ");
			e.printStackTrace();
		}
	}

	/**
	 * Function to download all files in the folder including all files in sub folders
	 * 
	 * @param link               current URL
	 * @param downloadedFileList return list of downloaded filenames
	 */
	public void downloadFilesInFolder(String link, List<String> downloadedFileList) {
		String textName = null;
		String currentURL = null;
		// Get all links
		List<WebElement> listFolders = driver.findElements(By.cssSelector("table>tbody>tr>td>a"));
		System.out.println("[TEST] The number Folders under " + link + " : " + listFolders.size());
		int i = 0;
		// Check each link to download
		while (i < listFolders.size()) {
			// Get refesh list element because page objects are attached one at a time, 
			// when return back page, need to get the fresh ones.
			// Reference to https://www.selenium.dev/exceptions/#stale_element_reference.html
			List<WebElement> listFoldersRefresh = driver.findElements(By.cssSelector("table>tbody>tr>td>a"));
			List<WebElement> listIconsRefresh = driver.findElements(By.cssSelector("table>tbody>tr>td>img"));
			textName = listFoldersRefresh.get(i).getText();
			// Check this is folder or file
			if (textName.endsWith("/")) {
				// Download files in the folder
				System.out.println("[TEST] This is a folder: " + textName);
				listFoldersRefresh.get(i).click();
				currentURL = driver.getCurrentUrl();
				downloadFilesInFolder(currentURL, downloadedFileList);
				// Return the parent link back to download the next folder
				driver.get(link);
				System.out.println("[TEST] Going back to the URL: " + link);
			} else {
				// Download file
				System.out.println("[TEST] This is a file: " + textName);
				// Base on icon to know how to download the file
				if (listIconsRefresh.get(i).getAttribute("src").contains("unknown.gif")) {
					downloadedFileList.add(downloadUnknowFile(listFoldersRefresh.get(i)));
				} else {
					downloadedFileList.add(downloadFile(listFoldersRefresh.get(i)));
				}
			}
			++i;
		}
		System.out.println("[TEST] Complete downloading files in the link: " + link);
	}

	/**
	 * Function to download a file
	 * 
	 * @param element web element
	 */
	public String downloadFile(WebElement element) {
		String filename = element.getText();
		// Click on the link to download file, but for TEXT file we have to do another way.
		if (filename.endsWith(".txt") || filename.endsWith(".key") || filename.contains(".rpm-") || filename.endsWith(".xml") || filename.endsWith(".asc")) {
			downloadTextFile(element);
		} else {
			element.click();
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return filename;
	}

	/**
	 * Function to download a TEXT type file
	 * 
	 * @param element web element
	 */
	public String downloadTextFile(WebElement element) {
		String filename = element.getText();
		// TO DO: still finding solution
		return filename;
	}

	/**
	 * Function to download a TEXT type file
	 * 
	 * @param element web element
	 */
	public String downloadUnknowFile(WebElement element) {
		//Total number files are huge, and there are many files have big size. 
		//I limited to not download files that have “?” icon (unknown file type). 
		//=> Reduce the number of downloaded files and download time. 
		String filename = element.getText();
		return filename;
	}
	
	/**
	 * Function to veriy the file exists in the location
	 * 
	 * @param fileName       name of the file
	 * @param folderLocation location
	 */
	public boolean verifyFileExist(String fileName, File folderLocation) {
		// List the files on that folder
		File[] listOfFiles = folderLocation.listFiles();
		// Look for the file in the files
		for (File listOfFile : listOfFiles) {
			if (listOfFile.isFile()) {
				String fileNameInFolder = listOfFile.getName();
				if (fileNameInFolder.matches(fileName)) {
					System.out.println("[TEST] File " + listOfFile.getName() + " exists in the location: " + folderLocation);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Function to click on button to select platform to download
	 * 
	 * @param platform platform type
	 */
	public boolean selectPlatform(String platform) {
		try {
			WebElement element;
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.nav-button")));
			System.out.println("[TEST] Click on platform: " + platform);
			switch (platform.toLowerCase().trim()) {
			case "macOS":
				element = driver.findElement(By.cssSelector("a.nav-button.mac"));
				element.click();
				break;
			case "linux":
				element = driver.findElement(By.cssSelector("a.nav-button.linux"));
				System.out.println("[TEST] Linux element: " + element);
				element.click();
				System.out.println("[TEST] Click on platform linux here: " + platform);
				break;
			case "Windows":
				element = driver.findElement(By.cssSelector("a.nav-button.windows"));
				element.click();
				break;
			}
			Utils.waitForLoading(driver, 1);
		} catch (Exception e) {
			System.out.println("[TEST] Select platform exception: " + e);
			return false;
		}
		return true;
	}

}