package com.uiFramework.ust.experian.helper.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper1 {
	public FileOutputStream fileOut = null;
	public String path;
	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	public ExcelHelper1(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method will return 2D object Data for each record in excel sheet.
	 * 
	 * @param sheetName
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public String[][] getDataFromSheet(String sheetName, String ExcelName) {
		String dataSets[][] = null;
		try {
			// get sheet from excel workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);
			// count number of active tows
			int totalRow = sheet.getLastRowNum() + 1;
			// count number of active columns in row
			int totalColumn = sheet.getRow(0).getLastCellNum();
			// Create array of rows and column
			dataSets = new String[totalRow - 1][totalColumn];
			// Run for loop and store data in 2D array
			// This for loop will run on rows
			for (int i = 1; i < totalRow; i++) {
				XSSFRow rows = sheet.getRow(i);
				// This for loop will run on columns of that row
				for (int j = 0; j < totalColumn; j++) {
					// get Cell method will get cell
					XSSFCell cell = rows.getCell(j);

					// If cell of type String , then this if condition will work
					if (cell.getCellType() == Cell.CELL_TYPE_STRING)
						dataSets[i - 1][j] = cell.getStringCellValue();
					// If cell of type Number , then this if condition will work
					else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						String cellText = String.valueOf(cell.getNumericCellValue());
						dataSets[i - 1][j] = cellText;
					} else
						// If cell of type boolean , then this if condition will work
						dataSets[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
				}

			}
			return dataSets;
		} catch (Exception e) {
			System.out.println("Exception in reading Xlxs file" + e.getMessage());
			e.printStackTrace();
		}
		return dataSets;
	}

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			int col_Num = 0;
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			XSSFRow row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().equals(colName)) {
					col_Num = i;
					break;
				}
			}
			row = sheet.getRow(rowNum - 1);

			XSSFCell cell = row.getCell(col_Num);
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Object[][] dataSupplier(String excellocation,String sheetName) {

		try {
			
			 XSSFSheet sheet = workbook.getSheet(sheetName);
			int lastRowNum = sheet.getLastRowNum();
			int lastCellNum = sheet.getRow(0).getLastCellNum();
			Object[][] obj = new Object[lastRowNum][1];
			for (int i = 0; i < lastRowNum; i++) {
				Map<Object, Object> datamap = new HashMap<Object, Object>();
				for (int j = 0; j < lastCellNum; j++) {
					datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i + 1).getCell(j).toString());
				}
				obj[i][0] = datamap;
				//System.out.println(datamap);

			}

			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
