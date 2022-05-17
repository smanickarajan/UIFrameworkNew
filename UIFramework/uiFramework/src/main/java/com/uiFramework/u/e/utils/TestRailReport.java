package com.uiFramework.ust.experian.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.TestRail.Projects.Get;
import com.codepine.api.testrail.TestRail.Runs.Add;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Field;
import com.codepine.api.testrail.model.Project;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Run;
import com.codepine.api.testrail.model.Suite;
import com.uiFramework.ust.experian.helper.excel.ExcelHelper1;

import jdk.internal.dynalink.beans.CallerSensitiveDetector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestRailReport {
	public static TestRail testrail1;

	public static String TESTRAIL_USERNAME = "experianuk\\c50201a";

	public static String TESTRAIL_PASSWORD = "September@2018";

	public static String RAILS_ENGINE_URL = "http://testrail-gsg.experian.local/";

	public static Run runname;

	public static FileInputStream fis;
	public static XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	public static void createTestrailRun() throws IOException {
		testrail1 = TestRail.builder(RAILS_ENGINE_URL, TESTRAIL_USERNAME, TESTRAIL_PASSWORD).build();

		ArrayList<Integer> caseids = new ArrayList<>();
		String excelLocation = "C:\\UIFramework\\uiFramework\\src\\main\\resources\\testrailids\\TestRailids.xlsx";
		fis = new FileInputStream(excelLocation);
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int lastRowNum = sheet.getLastRowNum();

		for (int i = 0; i <= lastRowNum; i++) {

			String data = (sheet.getRow(i).getCell(0).toString());
			String[] datsplit = data.split("C");
			int railid = Integer.parseInt(datsplit[1]);
			caseids.add(railid);
		}

		System.out.println(caseids.get(2));
		runname = testrail1.runs()
				.add(7, new Run().setIncludeAll(false).setName("Weekly Regression").setCaseIds(caseids)).execute();

	}

	public static void updateTestRailStatus(int testcaseid, String status) {
		int statusid;
		if (status.equalsIgnoreCase("Pass")) {
			statusid = 1;
		} else if (status.equalsIgnoreCase("fail")) {
			statusid = 5;
		}
		else if (status.equalsIgnoreCase("Retest")) {
			statusid=4;
		}else {
			statusid=2;
		}
		List<ResultField> customResultFields = testrail1.resultFields().list().execute();
		int size = customResultFields.size();
		ResultField field = customResultFields.get(0);
		System.out.println("the size is "+size);
		System.out.println("the field is "+field.getName());
//List<ResultField> resultFields = (List<ResultField>) new ResultField().setName("test");
//t size = resultFields.size();
//stem.out.println(size);
testrail1.results().addForCase(runname.getId(), testcaseid, new Result().setStatusId(statusid), customResultFields).execute();
	}
}
