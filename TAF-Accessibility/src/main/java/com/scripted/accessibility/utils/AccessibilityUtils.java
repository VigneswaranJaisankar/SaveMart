/**
 * 
 */
package com.scripted.accessibility.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.scripted.accessibility.main.Accessibility;

/**
 * The Class AccessibilityCopUtils represents Accessibility On Sites,
 * showReport, startExecution, scanPages and stopsExecution.
 */
public class AccessibilityUtils {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/** The driver. */
	private WebDriver driver;

	/** The accessibility */
	private Accessibility accessibilityCop;

	/**
	 * The Method performAccessibilityOnSites performs accessibilityTest on sites,
	 * scans tests and generate Reports and close the browser instance.
	 *
	 * @param excelPath
	 *            - the excel path
	 */
	public void performAccessibilityOnSites(final String excelPath) {
		// driver = BrowserTypes.getDriver();
		driver = Accessibility.getDriver();
		// driver = BrowserDriver.funcGetWebdriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		accessibilityCop = new Accessibility(driver);
		try {
			final ExcelReader excelReader = new ExcelReader(excelPath, "Sites");
			final List<String> sites = excelReader.readExcel();
			int counter = 1;
			for (final String site : sites) {
				System.out.println("Accessing Site : " + site);
				driver.get(site);
				accessibilityCop.generateAccessibilityReportForCurrentPage("Site" + counter);
				counter++;
			}
			accessibilityCop.generateOverallAccessibilityReport();
		} catch (final IOException ioe) {
			LOGGER.log(Level.SEVERE, ioe.getMessage());
		} finally {
			driver.close();
		}
	}

	/**
	 * The method showReport shows the report on the desktop.
	 */
	public void showReport() {
		File htmlFile = new File(
				System.getProperty("user.dir") + "/AccessibilityReports/AccessibilityTesting_Overall_Summary.html");
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The method startExecution starts the Accessibility test Execution.
	 * 
	 * @param applicationUrl
	 *            to given Url
	 */
	public void startExecution(final String applicationUrl) {
		driver = Accessibility.getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		accessibilityCop = new Accessibility(driver);
		driver.get(applicationUrl);
	}

	/**
	 * The method scanPage scans the page and generates Accessibility Report For
	 * CurrentPage.
	 */
	public void scanPage() {
		accessibilityCop.generateAccessibilityReportForCurrentPage("Site1");
	}

	/**
	 * The method stopExecution stops the current Execution and quits the driver
	 * instance.
	 */
	public void stopExecution() {
		accessibilityCop.generateOverallAccessibilityReport();
		try {
			driver.quit();
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}
}
