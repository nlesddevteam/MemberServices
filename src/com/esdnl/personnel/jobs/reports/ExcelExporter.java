package com.esdnl.personnel.jobs.reports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelExporter {

	public ExcelExporter() {

	}

	public abstract String getFilename();

	public abstract boolean validateAccess();

	public abstract void createContents(XSSFWorkbook wb);

	public final XSSFWorkbook export() {

		XSSFWorkbook wb = null;

		if (validateAccess()) {
			wb = new XSSFWorkbook();

			createContents(wb);
		}

		return wb;
	}

	public final void autoFitColumnWidth(Sheet sheet, int cells) {

		for (int i = 0; i < cells; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public void freezeHeader(Sheet sheet) {

		sheet.createFreezePane(0, 1, 0, 1);
	}
}
