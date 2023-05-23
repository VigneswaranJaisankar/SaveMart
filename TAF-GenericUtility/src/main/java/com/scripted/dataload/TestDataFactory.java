package com.scripted.dataload;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class TestDataFactory {
	ExcelDriver excelDriver;
	ExcelConnector exclConn;
	public static Logger LOGGER = LogManager.getLogger(TestDataFactory.class);

	public TestDataObject GetData(String key, ExcelConnector exclConn) {
		String sheetName = null;
		this.exclConn = exclConn;
		String fileName = exclConn.getFileName();
		this.excelDriver = new ExcelDriver(fileName);
		TestDataObject excelData = new TestDataObject();
			sheetName = exclConn.getSheetName();
		try {
			if (sheetName == null) {
				throw new IllegalArgumentException("No sheet exists with name " + sheetName);
			}
			if (sheetName != null || !sheetName.equals(""))
				excelDriver.goToSheet(sheetName);
			int maxRowCount = exclConn.getMaxRows();
			excelData = setSheetData(excelData, excelDriver, maxRowCount);
		} catch (Exception e) {
			LOGGER.error("Error while trying to get data from excel " + "Exception :" + e);
			Assert.fail("Error while trying to get data from excel " + "Exception :" + e);
		}
		return excelData;
	}

	private TestDataObject setSheetData(TestDataObject excelData, ExcelDriver excelDriver, Integer maxRowCount) {
		try {

			int rowCount;
			boolean hasHeader = exclConn.isHasHeader();
			rowCount = excelDriver.getRowCount();
			if ((maxRowCount >= 0) && (maxRowCount < rowCount)) {
				rowCount = maxRowCount;
				if (hasHeader == true) {
					rowCount++;
				}
			}

			LinkedHashMap<String, Map<String, String>> rowData = new LinkedHashMap<String, Map<String, String>>();
			int intitalRowIndex;
			if (hasHeader == true) {
				intitalRowIndex = 1;
			} else {
				intitalRowIndex = 0;
			}
			for (int rowCounter = intitalRowIndex; rowCounter < rowCount; rowCounter++) {
				rowData.put(Integer.toString(rowCounter), excelDriver.getRowMap(rowCounter, hasHeader));
			}

			excelData.setTableData(rowData);

		} catch (Exception e) {

			LOGGER.error("Error while trying to get data from excel " + "Exception :" + e);
			Assert.fail("Error while trying to get data from excel " + "Exception :" + e);
		}
		return excelData;
	}
}