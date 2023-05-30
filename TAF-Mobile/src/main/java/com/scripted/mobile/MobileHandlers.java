package com.scripted.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;


import com.scripted.selfhealing.SMMobHealer;

public class MobileHandlers {

	public static Logger LOGGER = LogManager.getLogger(MobileHandlers.class);
	public static SMMobHealer smmobileHealer = new SMMobHealer();
	public static MobileElement HealdLctr;
	public static Actions action = new Actions(MobileDriverSettings.getCurrentDriver());
	public static Boolean shflag=smmobileHealer.isShflag();


	public static By mobileElementBy(MobileElement mobileElement) {
		String mobileEle = mobileElement.toString();
		String locatorType, locator = null;
		if (mobileEle.contains("{") || mobileEle.contains("}")) {
			mobileEle = (mobileEle.substring(mobileEle.indexOf("{") + 1, mobileEle.indexOf("}")));
			locatorType = mobileEle.substring(mobileEle.indexOf(0) + 1, mobileEle.indexOf(": ")).replace("By.", "");
			locator = mobileEle.substring(mobileEle.indexOf(": ")).replaceFirst(": ", "");
		} else {
			mobileEle = mobileEle.replace("Located by By.", "");
			locatorType = StringUtils.substringBefore(mobileEle, ":");
			locator = StringUtils.substringAfter(mobileEle, ":").trim();

		}
		switch (locatorType) {
		case "xpath":
			return By.xpath(locator);
		case "css selector":
			return By.cssSelector(locator);
		case "id":
			return By.id(locator);
		case "tag name":
			return By.tagName(locator);
		case "name":
			return By.name(locator);
		case "link text":
			return By.linkText(locator);
		case "className":
			return By.className(locator);
		}
		return null;

	}
	
	public static void initiateHealCall(Exception e, MobileElement locator) {
		HealdLctr = smmobileHealer.initiateHealing(e, locator, MobileDriverSettings.getCurrentDriver());
		if (HealdLctr == null) {
         Assert.fail();
		}
		System.out.println("Object healing completed");
		LOGGER.info("Element healed: " + HealdLctr);
	}

	public static void click(MobileElement locator) {
		try {
//			MobileWaitHelper.waitForElement(locator);
			locator.click();			
			LOGGER.info("Click action completed successfully for the locator: " + locator);
		} catch (Exception e) {
			System.out.println(e);
			if (shflag) {
				initiateHealCall(e, locator);
				click(HealdLctr);
			} else {
				System.out.println("inside shflag false code");
				LOGGER.error("Error while enterting the text for the locator: " + locator + "Exception :" + e);
				Assert.fail("Error while entering the text for the locator: " + locator +"Exception :" + e);
			}

		}
	}

	public static void clickByJsExecutor(MobileElement locator) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) MobileDriverSettings.getCurrentDriver();
			js.executeScript("arguments[0].click();", locator);
			LOGGER.info("clickByJsExecutor action completed successfully for the locator: " + locator);
		} catch (Exception e) {
			e.printStackTrace();
			if (shflag) {
				initiateHealCall(e, locator);
				clickByJsExecutor(HealdLctr);
			} else {
				LOGGER.error("Error  while performing  the clickByJsExecutor action for the locator: " + locator
						+ "Exception :" + e);
				Assert.fail("Error  while performing  the clickByJsExecutor action for the locator: " + locator
						+ "Exception :" + e);
			}
		}
	}

	public static void enterText(MobileElement locator, String value) {
		try {
			locator.click();		
			locator.sendKeys(value);
//			hideKeyBoard();
			LOGGER.info("Text entered successfully" + locator);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				enterText(HealdLctr, value);
			} else {
				System.out.println("inside shflag false code");
				LOGGER.error("Error while enterting the text for the locator: " + locator + "Exception :" + e);
				Assert.fail("Error while entering the text for the locator: " + locator + "Exception :" + e);
			}

		}
	}


	public static void scrollAndClick(MobileElement locator, String scrollType) {
		try {
			MobileWaitHelper.scrollToElement(MobileHandlers.mobileElementBy(locator), scrollType).click();
			LOGGER.info("Scrolled and clicked the locator successfully" + locator);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				scrollAndClick(HealdLctr, scrollType);
			} else {
				LOGGER.error(
						"Error occurred while trying to scroll and click the locator: " + locator + "Exception :" + e);
				Assert.fail(
						"Error  occurred while trying to scroll and click the locator:" + locator + "Exception :" + e);
			}
		}

	}

	public static void scrollToElement(MobileElement locator, String scrollType) {
		try {
			WebElement element = MobileWaitHelper.scrollToElement(MobileHandlers.mobileElementBy(locator), scrollType);
			LOGGER.info("Error occurred while trying to scroll " + locator);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				scrollToElement(HealdLctr, scrollType);
			} else {

				LOGGER.error("Error occurred while trying to scroll to the locator: " + locator + "Exception :" + e);
				Assert.fail("Error  occurred while trying to scroll to the locator:" + locator + "Exception :" + e);
			}
		}

	}
	public static void hideKeyBoard() {
		boolean isKeyboardShown = ((HasOnScreenKeyboard) MobileDriverSettings.getCurrentDriver()).isKeyboardShown();
		if (isKeyboardShown == true) {
			MobileDriverSettings.getCurrentDriver().hideKeyboard();
		}
	}

	public static String getText(MobileElement locator) {
		return MobileDriverSettings.getCurrentDriver().findElement(mobileElementBy(locator)).getText().toString();

	}

	public static void clearof(MobileElement locator) {
		MobileWaitHelper.clearOf(mobileElementBy(locator));
	}

	public static void mobileButtonPress(String keyName) {

		switch (keyName.toLowerCase()) {
		case "home":
			MobileDriverSettings.getAndroidWebDriver().pressKey(new KeyEvent(AndroidKey.HOME));
			break;

		case "back":
			MobileDriverSettings.getAndroidWebDriver().pressKey(new KeyEvent(AndroidKey.BACK));
			break;

		case "enter":
			MobileDriverSettings.getAndroidWebDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
			break;

		case "delete":
			MobileDriverSettings.getAndroidWebDriver().pressKey(new KeyEvent(AndroidKey.DEL));
			break;
		default:
			LOGGER.error("Wrong button" + keyName);
			throw new IllegalArgumentException("Wrong button name: " + keyName);

		}
	}

	public static boolean dropDownSetByVal(MobileElement locator, String value) {
		boolean flag = false;
		try {
			Select dropdown = new Select(locator);
			List<WebElement> cmbOptions = dropdown.getOptions();
			for (int i = 0; i <= cmbOptions.size(); i++) {
				if (value.equalsIgnoreCase(cmbOptions.get(i).getText())) {
					dropdown.selectByIndex(i);
					flag = true;
					break;
				}
			}
			return flag;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				dropDownSetByVal(HealdLctr, value);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing the dropDownSetByVal action for the locator: " + locator
						+ "Exception :" + e);
				Assert.fail("Error while performing the dropDownSetByVal action for the locator: " + locator
						+ "Exception :" + e);
			}
		}
		return flag;
	}

	public static boolean dropDownSetByIndex(MobileElement locator, int index) {
		boolean flag = false;
		try {
			Select dropdown = new Select(locator);
			List<WebElement> cmbOptions = dropdown.getOptions();
			for (int i = 0; i <= cmbOptions.size(); i++) {
				if (!cmbOptions.get(i).getText().isEmpty()) {
					dropdown.selectByIndex(index);
					flag = true;
					break;
				}
			}
			return flag;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				dropDownSetByIndex(HealdLctr, index);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing the dropDownSetByIndex action for the locator: " + locator
						+ "Exception :" + e);
				Assert.fail("Error while performing the dropDownSetByIndex action for the locator: " + locator
						+ "Exception :" + e);
			}
		}
		return flag;
	}


	public static boolean chkboxIsChecked(MobileElement locator) {
		boolean eFlag = false;
		try {
			if (locator.isSelected()) {
				eFlag = true;
			} else {
				eFlag = false;
				Assert.fail("Checkbox is not checked");
			}
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				chkboxIsChecked(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing the chkboxIsChecked action for the locator: " + locator
						+ "Exception :" + e);
				Assert.fail("Error while performing the chkboxIsChecked action for the locator: " + locator
						+ "Exception :" + e);
			}
		}
		return eFlag;

	}
	
	public static boolean radiobtnNotChecked(MobileElement locator) {
		MobileWebWaitHelper.waitForElementPresence(locator);
		boolean eFlag = false;
		try {
			if (!locator.isSelected()) {
				eFlag = true;
			} else {
				eFlag = false;
				Assert.fail("Radio button is checked");
			}
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				chkboxIsChecked(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing radiobtnNotChecked action " + "Exception :" + e);
				Assert.fail("Error while performing radiobtnNotChecked action " + "Exception :" + e);
			}
		}
		return eFlag;

	}
	
	public static boolean radioBtnIsSelected(MobileElement locator) {
		MobileWebWaitHelper.waitForElementPresence(locator);

		boolean eFlag = false;
		try {
			if (locator.isSelected()) {
				eFlag = true;
			} else {
				eFlag = false;
				Assert.fail("Radio button is not selected");
			}
		}catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				chkboxIsChecked(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing radioBtnIsSelected action " + "Exception :" + e);
				Assert.fail("Error while performing radioBtnIsSelected action " + "Exception :" + e);
			}
		}
		return eFlag;

	}

	public static boolean radioBtnIsNotSelected(MobileElement locator) {
		MobileWebWaitHelper.waitForElementPresence(locator);
		boolean eFlag = false;
		try {
			if (!locator.isSelected()) {
				eFlag = true;
			} else {
				eFlag = false;
				Assert.fail("Radio button is  selected");
			}
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				chkboxIsChecked(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing radioBtnIsNotSelected action " + "Exception :" + e);
				Assert.fail("Error while performing radioBtnIsNotSelected action " + "Exception :" + e);
			}
		}
		return eFlag;

	}
	
	public static HashMap<Integer, String> sltCtrlReadAllVal(MobileElement locator) {
		HashMap<Integer, String> comboValuesMap = new HashMap<Integer, String>();
		try {
			MobileWebWaitHelper.waitForElement(locator);
			comboValuesMap = new HashMap<Integer, String>();
			Select dropdown = new Select(locator);
			List<WebElement> dd = dropdown.getOptions();
			for (int j = 0; j < dd.size(); j++) {
				comboValuesMap.put(j, dd.get(j).getText());
			}
			System.out.println(comboValuesMap);
			LOGGER.info("The values are " + comboValuesMap);
			return comboValuesMap;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while performing the sltCtrlReadAllVal action for the locator: " + locator
					+ "Exception :" + e);
			Assert.fail("Error while performing the sltCtrlReadAllVal action for the locator: " + locator
					+ "Exception :" + e);
		}
		return comboValuesMap;
	}

	public static String dropDownGetCurrentSelection(MobileElement locator) {
		String cmbSelectedValue = "";
		try {
			MobileWebWaitHelper.waitForElement(locator);
			Select seleObj = new Select(locator);
			cmbSelectedValue = seleObj.getFirstSelectedOption().getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while trying to get current selection of dropdown for the locator: " + locator
					+ "Exception :" + e);
			Assert.fail("Error while trying to get current selection of dropdown for the locator " + locator
					+ "Exception :" + e);
		}
		return cmbSelectedValue;

	}

	public static void dropDownSelectByIndex(MobileElement locator, int index) {
		try {
			MobileWebWaitHelper.waitForElement(locator);
			String cmbSelectedValue = "";
			Select dropdown = new Select(locator);
			List<WebElement> cmbOptions = dropdown.getOptions();
			cmbSelectedValue = cmbOptions.get(index).getText();
			LOGGER.info("Selected value :" + cmbSelectedValue);
			System.out.println(cmbSelectedValue);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"Error while trying to select index from drop down  the locator: " + locator + "Exception :" + e);
			Assert.fail(
					"Error while trying to select index from drop down  the locator: " + locator + "Exception :" + e);

		}
	}

	
	public static boolean dropDownSetByIndex(WebElement locator, int index) {
		boolean flag = false;
		try {
			MobileWebWaitHelper.waitForElement(locator);
			Select dropdown = new Select(locator);
			List<WebElement> cmbOptions = dropdown.getOptions();
			for (int i = 0; i <= cmbOptions.size(); i++) {
				if (!cmbOptions.get(i).getText().isEmpty()) {
					dropdown.selectByIndex(index);
					flag = true;
					break;
				}
			}
			LOGGER.info(" The action dropDownSetByIndex completed successfully ");
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"Error while trying to set drop down by index for the locator: " + locator + "Exception :" + e);
			Assert.fail("Error while trying to set drop down by index for the locator:" + locator + "Exception :" + e);
		}
		return flag;
	}

	
	public void swithBackFromFrame() {
		try {
			MobileDriverSettings.getCurrentDriver().switchTo().defaultContent();
			// getFrame().swithBackFromFrame();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while trying to perform switch back from frame" + " Exception: " + e);
			Assert.fail("Error while trying to perform switch back from frame" + " Exception: " + e);
		}
	}

	public static void switchToFrame(MobileElement locator) {
		try {
			MobileDriverSettings.getCurrentDriver().switchTo().frame(locator);
			LOGGER.info("Switched to the next frame successfully:" + locator);
		} catch (MobileAutomationException e) {
			if (shflag) {
				initiateHealCall(e, locator);
				switchToFrame(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while trying to switch to next frame  " + locator + "Exception :" + e);
				Assert.fail("Error while trying to switch to next frame  " + locator + "Exception :" + e);
			}
		}
	}

	public static void switchBackFromFrame() {
		try {
			MobileDriverSettings.getCurrentDriver().switchTo().defaultContent();
			LOGGER.info("switched back to the frame successfully");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while trying  to switch back to the frame " + "Exception :" + e);
			Assert.fail("Error while trying  to switch back to the frame " + "Exception :" + e);
		}
	}
	
	public static ArrayList<String> optionsSplit(String options) throws NullPointerException {
		ArrayList<String> tempOptionList = new ArrayList<String>();
		try {
			String[] items = options.split(":");
			tempOptionList = new ArrayList<String>(Arrays.asList(items));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while performing  action optionsSplit" + " Exception: " + e);
			Assert.fail("Error while performing  action optionsSplit" + " Exception: " + e);
		}
		return tempOptionList;
	}
	
	public static boolean multiDeselectByText(MobileElement locator, String options) {
		boolean flag = false;
		ArrayList<String> optionList = null;
		try {
			MobileWebWaitHelper.waitForElement(locator);
			optionList = optionsSplit(options);
			if (optionList!=null) {
			for (int i = 0; i < optionList.size(); i++) {
				Select multiple = new Select(locator);
				multiple.deselectByVisibleText(optionList.get(i));

			}

			flag = true;
			}
			return flag;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				multiDeselectByText(HealdLctr,options );
			} else {
				e.printStackTrace();
				LOGGER.error("Error while trying to perform multideselect by text " + " Exception: " + e);
				Assert.fail("Error while trying to perform multideselect by text " + " Exception: " + e);
			}
		
	}
		return flag;
}
	
	public static boolean multiDeselectAll(MobileElement locator) {
		boolean flag = false;
		try {
			Select multiple = new Select(locator);
			multiple.deselectAll();
			flag = true;
			return flag;
		}  catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				multiDeselectAll(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while trying to perform multideselect" + " Exception: " + e);
				Assert.fail("Error while trying to perform multideselect" + " Exception: " + e);
			}
		
	}
		return flag;

	}
	
	public static boolean multiSelectByText(MobileElement locator, String options) {
		boolean flag = false;
		try {
			MobileWebWaitHelper.waitForElement(locator);
			ArrayList<String> optionList = optionsSplit(options);
			for (int i = 0; i < optionList.size(); i++) {
				Select multiple = new Select(locator);
				multiple.selectByVisibleText(optionList.get(i));
			}
			flag = true;
			return flag;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				multiSelectByText(HealdLctr, options);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while trying to perform multiSelectByText" + " Exception: " + e);
				Assert.fail("Error while trying to perform multiSelectByText" + " Exception: " + e);
			}
		
	}
		return flag;

	}
	
	public static boolean multiSelectByIndex(MobileElement locator, String indexes) {
		boolean flag = false;
		ArrayList<String> indexList = null;
		try {
			MobileWebWaitHelper.waitForElement(locator);
			indexList = optionsSplit(indexes);
			if (indexList!=null) {
			ArrayList<Integer> intList = (ArrayList<Integer>) indexList.stream().map(Integer::parseInt)
					.collect(Collectors.toList());
			for (int i = 0; i < intList.size(); i++) {
				Select multiple = new Select(locator);
				multiple.selectByIndex(intList.get(i));
			}
			}
			flag = true;
			return flag;

		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				multiSelectByIndex(HealdLctr, indexes);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while trying to perform multiSelect by providing an index value" + " Exception: " + e);
				Assert.fail("Error while trying to perform multiSelect by providing an index value" + " Exception: " + e);
			}
		
	}
		return flag;
	}

	private static String[] getWindowHandles() {
		String[] handles = new String[MobileDriverSettings.getCurrentDriver().getWindowHandles().size()];
		try {
			MobileDriverSettings.getCurrentDriver().getWindowHandles().toArray(handles);
		} catch (Exception e) {
			LOGGER.error("Error occurred while getting window handles" + "Exception" + e);
		}
		return handles;
	}

	public static void switchToNewWindow() throws InterruptedException {
		Thread.sleep(1000);
		try {
			 String[] handles = getWindowHandles(); int i = handles.length - 1;
			 MobileDriverSettings.getCurrentDriver().switchTo().window(getWindowHandles()[i]);
			 LOGGER.info("switched to new window successfully");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while trying to switch to the new window " + "Exception :" + e);
			Assert.fail("Error while trying to switch to the new window " + "Exception :" + e);
		}
	}

	public static void closeNewWindow() {
		try {
			MobileDriverSettings.getCurrentDriver().close();
			LOGGER.info("closed the new window successfully");
			switchToNewWindow();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while trying to close the new window " + "Exception :" + e);
			Assert.fail("Error occurred while trying to close the new window " + "Exception :" + e);
			 Thread.currentThread().interrupt();
		}
	}

	public static void switchToDefaultWindow() throws InterruptedException {
		Thread.sleep(1000);
		try {
			String[] handles = getWindowHandles();
			int i = handles.length - 1;
			MobileDriverSettings.getCurrentDriver().switchTo().window(getWindowHandles()[0]);
			LOGGER.info("switched back to default window successfully");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while trying to switch back to default window" + "Exception :" + e);
			Assert.fail("Error occurred while trying to switch back to default window" + "Exception :" + e);
		}
	}

	public static Alert switchToAlert() {

		Alert alert = MobileDriverSettings.getCurrentDriver().switchTo().alert();
		return alert;

	}

	public static void acceptAlert() {
		try {
			Thread.sleep(1000);
			switchToAlert().accept();
			LOGGER.info("Alert accepted");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while trying to accept alert " + "Exception :" + e);
			Assert.fail("Error occurred while trying to accept alert " + "Exception :" + e);
			 Thread.currentThread().interrupt();
		}
	}

	public static void dismissAlert() {
		try {
			Thread.sleep(1000);
			switchToAlert().dismiss();
			LOGGER.info("dismissAlert action completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while doing the acceptAlert action " + "Exception :" + e);
			Assert.fail("Error occurred while doing the acceptAlert action " + "Exception :" + e);
			 Thread.currentThread().interrupt();
		}
	}
	
	

	// Code for table table check
	
	public static LinkedHashMap<String, Integer> getTblHeaderVal(MobileElement locator) {
		LinkedHashMap<String, Integer> headermap = new LinkedHashMap<String, Integer>();
		try {

			MobileElement mytableHead = locator.findElement(By.tagName("thead"));
			List<MobileElement> rowsTable = mytableHead.findElements(By.tagName("tr"));
			for (int row = 0; row < rowsTable.size(); row++) {
				List<MobileElement> colRow = rowsTable.get(row).findElements(By.tagName("th"));
				for (int column = 0; column < colRow.size(); column++) {
					headermap.put(colRow.get(column).getText(), column);
				}
			}
			LOGGER.info("getTblHeaderVal action completed successfully");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getTblHeaderVal(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while performing getTblHeaderVal action " + "Exception :" + e);
				Assert.fail("Error  while performing getTblHeaderVal action " + "Exception :" + e);
			}
		}
		return headermap;
	}
	
	public static int getTblRowCount(MobileElement locator) {
		MobileWebWaitHelper.waitForElement(locator);
		WebElement mytableHead = locator.findElement(By.tagName("thead"));
		List<WebElement> rowsTable = mytableHead.findElements(By.tagName("tr"));
		return rowsTable.size();
	}

	public static LinkedHashMap<String, Integer> getTblBodyVal(MobileElement locator) {
		LinkedHashMap<String, Integer> bodymap = new LinkedHashMap<String, Integer>();
		try {

			MobileElement mytableBody = locator.findElement(By.tagName("tbody"));
			List<MobileElement> rowsTable = mytableBody.findElements(By.tagName("tr"));
			for (int row = 0; row < rowsTable.size(); row++) {
				List<MobileElement> colRow = rowsTable.get(row).findElements(By.tagName("td"));
				for (int column = 0; column < colRow.size(); column++) {
					bodymap.put(colRow.get(column).getText(), column);
				}
			}
			LOGGER.info("getTblBodyVal action completed successfully");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getTblBodyVal(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing the getTblBodyVal action " + "Exception :" + e);
				Assert.fail("Error while performing the getTblBodyVal action " + "Exception :" + e);
			}
		}
		return bodymap;
	}

	public static String getTblTdVal(MobileElement locator, int rowIndex, int colIndex) {
		MobileElement ele = null;
		MobileElement rowele = null;
		MobileElement mytable = null;
		String tblCellValue = "";

		try {

			List<MobileElement> mytables = locator.findElements(By.tagName("tr"));
			rowele = mytables.get(rowIndex);
			List<MobileElement> rowsTable = rowele.findElements(By.tagName("td"));
			ele = rowsTable.get(colIndex);
			tblCellValue = ele.getText();
			LOGGER.info("The getTblTdVal action completed successfully");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getTblTdVal(HealdLctr, rowIndex, colIndex);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while performing the getTblTdVal action " + "Exception :" + e);
				Assert.fail("Error  while performing the getTblTdVal action " + "Exception :" + e);
			}
		}
		return tblCellValue;
	}

	public static String getTblThVal(MobileElement locator, int rowIndex, int colIndex) throws NullPointerException {

		MobileElement ele = null;
		MobileElement tblEle = null;
		//MobileElement rowEle = null;

		MobileElement myTblHead = null;
		List<MobileElement> myTblHeadRow = null;
		List<MobileElement> myTblHeadRowCol = null;
		//List<MobileElement> hdrRowCol = null;
		String tblCellValue = "";
		try {

			myTblHead = locator.findElement(By.tagName("thead"));
			myTblHeadRow = myTblHead.findElements(By.xpath("tr"));
			for (int row = 0; row < myTblHeadRow.size(); row++) {
				tblEle = myTblHeadRow.get(row);
				myTblHeadRowCol = tblEle.findElements(By.tagName("th"));
				//hdrRowCol = rowEle.findElements(By.tagName("th"));
				ele = myTblHeadRowCol.get(colIndex);
				tblCellValue = ele.getText();

			}
			LOGGER.info("getTblThVal action completed successfully");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getTblThVal(HealdLctr, rowIndex, colIndex);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while performing the getTblThVal action " + "Exception :" + e);
				Assert.fail("Error  while performing the getTblThVal action " + "Exception :" + e);
			}
		}
		return tblCellValue;

	}

	public static String getIndexofVal(MobileElement locator, String value) {
		MobileElement ele = null;
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		MobileElement tblEle = null;
		List<MobileElement> mytableBodycol = null;
		String indexVal = "";
		boolean Flag = false;
		try {

			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			for (int row = 0; row < mytableBodyrow.size(); row++) {
				tblEle = mytableBodyrow.get(row);
				mytableBodycol = tblEle.findElements(By.tagName("td"));
				for (int column = 0; column < mytableBodycol.size(); column++) {
					if (mytableBodycol.get(column).getText().equalsIgnoreCase(value)) {
						System.out.println("Index of " + value + "is (" + row + "," + column + ")");
						indexVal = row + "," + column;
						Flag = true;
						// break;
					}

				}
			}
			if (Flag == false) {
				LOGGER.info("The value mentioned for checking index  does not exist in the table");
			}
			LOGGER.info("getIndexofVal action completed successfully");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getIndexofVal(HealdLctr, value);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing the getIndexofVal action " + "Exception :" + e);
				Assert.fail("Error while performing the getIndexofVal action " + "Exception :" + e);
			}
		}
		return indexVal;

	}
	
	public static String getFirstIndexofVal(WebElement locator, String value) {
		WebElement mytableBody = null;
		List<WebElement> mytableBodyrow = null;
		WebElement tblEle = null;
		List<WebElement> mytableBodycol = null;
		String indexVal = "";
		boolean flag = false;
		try {
			MobileWebWaitHelper.waitForElementPresence(locator);
			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			for (int row = 0; row < mytableBodyrow.size(); row++) {
				tblEle = mytableBodyrow.get(row);
				mytableBodycol = tblEle.findElements(By.tagName("td"));
				for (int column = 0; column < mytableBodycol.size(); column++) {
					if (mytableBodycol.get(column).getText().equalsIgnoreCase(value)) {
						row = row + 1;
						column = column + 1;
						LOGGER.info("Index of " + value + "is (" + row + "," + column + ")");
						indexVal = row + "," + column;
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while performing the getFirstIndexofVal action " + "Exception :" + e);
			Assert.fail("Error while performing the getFirstIndexofVal action " + "Exception :" + e);
		}
		return indexVal;

	}

	public static LinkedHashMap<String, String> getColMapByHdrVal(MobileElement locator, String colHeader) {
		LinkedHashMap<String, String> colMap = new LinkedHashMap<String, String>();
		LinkedHashMap<String, Integer> headerMap = new LinkedHashMap<String, Integer>();
		MobileElement ele = null;
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		MobileElement tblEle = null;
		List<MobileElement> mytableBodycol = null;
		try {

			headerMap = getTblHeaderVal(locator);
			int colNum = headerMap.get(colHeader);
			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			for (int row = 0; row < mytableBodyrow.size(); row++) {
				tblEle = mytableBodyrow.get(row);
				mytableBodycol = tblEle.findElements(By.tagName("td"));
				colMap.put(mytableBodycol.get(colNum).getText(), "Row Number is " + row);
			}
			return colMap;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getColMapByHdrVal(HealdLctr, colHeader);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing the getColMapByHdrVal action " + "Exception :" + e);
				Assert.fail("Error while performing the getColMapByHdrVal action " + "Exception :" + e);

			}
		}
		return colMap;
	}

	public static LinkedHashMap<String, Integer> getRowMapByIndxVal(MobileElement locator, int rowIndex) {
		LinkedHashMap<String, Integer> rowMap = new LinkedHashMap<String, Integer>();
		HashMap<String, Integer> bodyMap = new HashMap<String, Integer>();
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		try {

			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			for (int i = 0; i < mytableBodyrow.get(rowIndex).findElements(By.tagName("td")).size(); i++) {

				rowMap.put(mytableBodyrow.get(rowIndex).findElements(By.tagName("td")).get(i).getText(), i);
			}
			return rowMap;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getRowMapByIndxVal(HealdLctr, rowIndex);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while perfoming the getRowMapByIndxVal action " + "Exception :" + e);
				Assert.fail("Error while perfoming the getRowMapByIndxVal action " + "Exception :" + e);
			}
		}
		return rowMap;
	}

	public static LinkedHashMap<String, Integer> getRowMapByHdrVal(MobileElement locator, String rowHeader) {
		LinkedHashMap<String, Integer> rowMap = new LinkedHashMap<String, Integer>();
		HashMap<String, Integer> bodyMap = new HashMap<String, Integer>();
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		try {

			bodyMap = getTblBodyVal(locator);
			int rowNum = bodyMap.get(rowHeader);

			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			for (int i = 0; i < mytableBodyrow.get(rowNum).findElements(By.tagName("td")).size(); i++) {

				rowMap.put(mytableBodyrow.get(rowNum).findElements(By.tagName("td")).get(i).getText(), i);
			}
			return rowMap;

		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getRowMapByHdrVal(HealdLctr, rowHeader);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while performing the getRowMapByHdrVal action " + "Exception :" + e);
				Assert.fail("Error  while performing the getRowMapByHdrVal action " + "Exception :" + e);
			}
		}
		return rowMap;
	}

	public static LinkedHashMap<String, Integer> getColMapByIndxVal(MobileElement locator, int colIndex) {
		LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		MobileElement mytableBodyrowCol = null;
		try {

			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			for (int i = 0; i < mytableBodyrow.size(); i++) {
				mytableBodyrowCol = mytableBodyrow.get(i).findElements(By.tagName("td")).get(colIndex);
				colMap.put(mytableBodyrowCol.getText() + i, colIndex);
				System.out.println(i);
			}
			return colMap;
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				getColMapByIndxVal(HealdLctr, colIndex);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while performing the getColMapByIndxVal action " + "Exception :" + e);
				Assert.fail("Error  while performing the getColMapByIndxVal action " + "Exception :" + e);
			}
		}
		return colMap;
	}

	public static void TblCelEleClick(MobileElement tbllocator, String value, int chkColIndex, String eleXpath) {
		WebElement mytableBody = null;
		List<WebElement> mytableBodyrow = null;
		WebElement eleRow = null;
		try {
			String index = getFirstIndexofVal(tbllocator, value);
			String[] arrSplit = index.split(",");

			MobileWebWaitHelper.waitForElement(tbllocator);
			mytableBody = tbllocator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			eleRow = mytableBodyrow.get(Integer.parseInt(arrSplit[0]));
			WebElement eleRowCol = eleRow.findElement(By.xpath("//td[" + chkColIndex + "]" + eleXpath));
			eleRowCol.click();
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, tbllocator);
				TblCelEleClick(HealdLctr, value , chkColIndex , eleXpath );
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while trying to click an element inside table" + "Exception :" + e);
				Assert.fail("Error  while trying to click an element inside table " + "Exception :" + e);
			}
		}
	}
	
	public static void TblCelChkboxClick(MobileElement locator, String value) {
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		try {
			String index = getIndexofVal(locator, value);
			String[] arrSplit = index.split(",");

			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			MobileElement eleRow = mytableBodyrow.get(Integer.parseInt(arrSplit[0]));
			MobileElement eleRowCol = eleRow.findElements(By.tagName("td")).get(Integer.parseInt(arrSplit[1]) - 1);
			eleRowCol.click();
			LOGGER.info("TblCelChkboxClick action completed successfully for the locator: " + locator);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				TblCelChkboxClick(HealdLctr, value);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while trying to click a checkbox inside table " + "Exception :" + e);
				Assert.fail("Error  while trying to click a checkbox inside table " + "Exception :" + e);
			}
		}
	}

	public static void TblCelLinkClick(MobileElement locator, String value) {
		MobileElement mytableBody = null;
		List<MobileElement> mytableBodyrow = null;
		try {
			String index = getIndexofVal(locator, value);
			String[] arrSplit = index.split(",");

			mytableBody = locator.findElement(By.tagName("tbody"));
			mytableBodyrow = mytableBody.findElements(By.tagName("tr"));
			MobileElement eleRow = mytableBodyrow.get(Integer.parseInt(arrSplit[0]));
			MobileElement eleRowCol = eleRow.findElements(By.tagName("td")).get(Integer.parseInt(arrSplit[1]));
			MobileElement ele = eleRowCol.findElement(By.tagName("a"));
			ele.click();
			LOGGER.info("TblCelLinkClick action completed successfully for the locator: " + locator);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				TblCelLinkClick(HealdLctr, value);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while trying to click a link inside table " + "Exception :" + e);
				Assert.fail("Error  while trying to click a link inside table " + "Exception :" + e);
			}
		}
	}
	
	public static void divSpanListBox(MobileElement locator, String value) {
		try {
			MobileWebWaitHelper.waitForElement(locator);
			locator.click();
			List<MobileElement> listSpan = locator.findElements(By.tagName("span"));
			for (WebElement span : listSpan) {
				if (span.getText().trim().equalsIgnoreCase(value)) {
					span.click();
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception :" + e);
			Assert.fail("Exception :" + e);
		}
	}

	public static void scrollDown(){
		JavascriptExecutor js = (JavascriptExecutor) MobileDriverSettings.getCurrentDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public static void scrollUp(){
		JavascriptExecutor js = (JavascriptExecutor) MobileDriverSettings.getCurrentDriver();
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	// Code for Assertions

	public static String fetchPropertyVal(MobileElement locator, String property) {
		String propValue = "";
		try {
			propValue = locator.getAttribute(property).toString();
			LOGGER.info(
					"Property value fetched  successfully for the locator: " + locator + "prop value :" + propValue);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				fetchPropertyVal(HealdLctr, property);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while fetching  the property value " + "Exception :" + e);
				Assert.fail("Error  while fetching  the property value " + "Exception :" + e);

			}
		}
		return propValue;
	}

	public static void verifyProperty(MobileElement locator, String property, String expected) {
		String actual = "";
		try {
			actual = fetchPropertyVal(locator, property);
			LOGGER.info("Property value fetched successfully for the locator: " + locator);
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				verifyProperty(HealdLctr, property, expected);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while trying to verify the property " + "Exception :" + e);
				Assert.fail("Error while trying to verify the property " + "Exception :" + e);
			}
		}
		if (!expected.equals(actual)) {
			LOGGER.info("property does not  matches" + "locator value: " + locator + " Expected value :" + expected
					+ "Actual value :" + actual);
			Assert.fail("Property does not matches");
		} else {
			LOGGER.info("Property matches" + "locator value: " + locator + " Expected value :" + expected
					+ "Actual value :" + actual);

		}

	}
	
	public static void doubleClick(MobileElement locator) {
		try {
			MobileWebWaitHelper.waitForElement(locator);
			action.doubleClick(locator).perform();
			LOGGER.info("Double click performed successfully");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				doubleClick(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing double click " + "Exception: " + e);
				Assert.fail("Error while performing double click " + "Exception: " + e);

			}
		}

	}

	public static void rightClick(MobileElement locator) {
		try {
			MobileWebWaitHelper.waitForElement(locator);
			action.contextClick(locator).perform();
			LOGGER.info("Right click performed successfully");
		}catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				rightClick(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error while performing right click " + "Exception: " + e);
				Assert.fail("Error while performing right click " + "Exception: " + e);
			}
		} 
	}

	public static void objExists(WebElement locator) {
		try {
			By byEle = MobileWebHandlers.webElementToBy(locator);
			MobileWebWaitHelper.waitForPresence(byEle, MobileWebWaitHelper.getElementTimeout());
			LOGGER.info("objExists check completed successfully, Object exists ");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while checking whether the object exists " + "Exception :" + e);
			Assert.fail("Error occurred while checking whether the object exists " + "Exception :" + e);
		}
	}


	public static boolean verifyText(MobileElement locator, String strVText) {
		boolean vflag = true;
		String actualText = "";
		try {
			if (locator.getTagName().equals("select")) {
				Select seleObj = new Select(locator);
				actualText = seleObj.getFirstSelectedOption().getText().trim();
				vflag = compareText(actualText, strVText);
			} else {

				actualText = locator.getText();
				if (actualText == null || actualText.isEmpty()) {
					actualText = locator.getAttribute("innerText");
					if (actualText == null || actualText.isEmpty()) {
						actualText = locator.getAttribute("value");
						vflag = compareText(actualText, strVText);
					} else {
						vflag = compareText(actualText, strVText);
					}

				} else {
					vflag = compareText(actualText, strVText);
				}
			}
			LOGGER.info("Text verified successfully for the locator: " + locator);

		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				verifyText(HealdLctr, strVText);
			} else {
				e.printStackTrace();
				LOGGER.error("Error  while trying to verify the text " + "Exception :" + e);
				Assert.fail("Error  while trying to verify the textr: " + locator);
			}
		}
		return vflag;
	}
	

	public static boolean compareText(String strActualText, String strCompText) {
		boolean compFlag = false;
		try {
			if (strActualText.equals(strCompText)) {
				compFlag = true;
				LOGGER.info("Text matches");
			} else {
				compFlag = false;
				Assert.fail(strActualText + " doesnot match with " + strCompText);

			}
			LOGGER.info("compareText action completed successfully ");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while doing the compareText action " + "Exception :" + e);
			Assert.fail("Error occurred while doing the compareText action " + "Exception :" + e);
		}
		return compFlag;
	}
	
	public static boolean existText(MobileElement locator) {
		boolean flag = true;
		try {

			if (locator.getAttribute("value").isEmpty()) {
				flag = false;
				Assert.fail("Text does not exists");
			} else {
				flag = true;
				LOGGER.info("Text exits " + locator);
			}
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				existText(HealdLctr);
			} else {

				e.printStackTrace();
				LOGGER.error("Error occurred while trying to check whether the text exists " + "Exception :" + e);
				Assert.fail("Error occurred while trying to check whether the text exists " + "Exception :" + e);
			}
		}
		return flag;
	}

	public static void clearText(MobileElement locator) {
		try {

			locator.clear();
			LOGGER.info("Text cleared successfully ");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				clearText(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error occurred while trying to clear text " + "Exception :" + e);
				Assert.fail("Error occurred while trying to clear text " + "Exception :" + e);

			}
		}
	}

	public static boolean objDisabled(MobileElement locator) {
		boolean eFlag = false;
		try {
			if (!locator.isEnabled()) {
				eFlag = true;
				LOGGER.info("Object is disabled");
			} else {
				eFlag = false;
				Assert.fail("Object is not disabled");
			}
			LOGGER.info("objDisabled check completed successfully ");

		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				objDisabled(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error occurred while checking whether the object is disabled " + "Exception :" + e);
				Assert.fail("Error occurred while checking whether the object is disabled " + "Exception :" + e);
			}
		}
		return eFlag;
	}

	public static void objExists(MobileElement locator) {
		try {
			By byEle = MobileHandlers.mobileElementBy(locator);
			LOGGER.info("objExists check completed successfully, Object exists ");
		} catch (Exception e) {
			if (shflag) {
				initiateHealCall(e, locator);
				objExists(HealdLctr);
			} else {
				e.printStackTrace();
				LOGGER.error("Error occurred while checking whether the object exists " + "Exception :" + e);
				Assert.fail("Error occurred while checking whether the object exists " + "Exception :" + e);
			}
		}
	}
	
	public static void AlertText(String alerttext) {
		try {
			Thread.sleep(1000);
			MobileDriverSettings.getCurrentDriver().switchTo().alert().sendKeys(alerttext);
			LOGGER.info("Text entered  successfully in Alert pop-up");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while entering text in alert pop-up " + "Exception :" + e);
			Assert.fail("Error occurred while entering text in alert pop-up " + "Exception :" + e);
			 Thread.currentThread().interrupt();
		}
	}
}
