package com.scripted.accessibility.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel reader to read data from excel file.
 */
public class ExcelReader {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/** The FileInputStream. */
	private transient FileInputStream fis;

	/** The sheet name. */
	private transient String sheetName;

	/**
	 * public constructor ExcelReader is used to set file path and sheet name.
	 *
	 * @param filePath
	 *            Excel file path
	 * @param sheet
	 *            Sheet name
	 * @throws IOException
	 *             Throws exception if expected file is not present
	 */
	public ExcelReader(final String filePath, final String sheet) throws IOException {
		final File file = new File(filePath);
		fis = new FileInputStream(file);
		this.sheetName = sheet;
	}

	/**
	 * The readExcel method is used to read excel data from first column and return as list.
	 *
	 * @return List of data
	 * @throws IOException
	 *             Throws IOException exception
	 */
	public List<String> readExcel() throws IOException {
		final List<String> data = new ArrayList<String>();
		Workbook workBook = null;
		try {
			workBook = new XSSFWorkbook(fis);
			final Sheet sheet = workBook.getSheet(sheetName);
			final int rownum = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rownum; i++) {
				final Row row = sheet.getRow(i);
				final Cell cell = row.getCell(0);
				switch (cell.getCellType()) {
				case BOOLEAN:
					data.add(String.valueOf(cell.getBooleanCellValue()));
					break;
				case NUMERIC:
					data.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
					break;
				case STRING:
					data.add(cell.getStringCellValue());
					break;
				case BLANK:
					data.add("");
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());

		} finally {
			if (fis != null) {
				fis.close();
			}
			if (workBook != null) {
				workBook.close();
			}
		}
		return data;
	}
}
