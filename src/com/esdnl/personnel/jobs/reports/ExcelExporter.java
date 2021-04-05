package com.esdnl.personnel.jobs.reports;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
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

	public void freezeHeader(Sheet sheet, int rows) {

		sheet.createFreezePane(0, rows);
	}

	public Cell applyBoldCellStyle(XSSFWorkbook wb, Cell cell) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);

		cs.setFont(f);

		cell.setCellStyle(cs);

		return cell;
	}

	public Cell applyTotalsRowCellStyle(XSSFWorkbook wb, Cell cell) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);

		cs.setFont(f);
		cs.setBorderTop(BorderStyle.THIN);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		cell.setCellStyle(cs);

		return cell;
	}

	public Cell createTotalsRowCell(XSSFWorkbook wb, Row row, int firstDataRowIndex, int lastDataRowIndex,
																	int cellIndex) {

		String columnLetter = CellReference.convertNumToColString(cellIndex);

		Cell cell = applyTotalsRowCellStyle(wb, row.createCell(cellIndex));
		cell.setCellFormula("SUM(" + columnLetter + firstDataRowIndex + ":" + columnLetter + lastDataRowIndex + ")");

		return cell;
	}

	@SuppressWarnings("unchecked")
	public Row createTotalsRow(	XSSFWorkbook wb, Sheet sheet, int firstDataRowIndex, int lastDataRowIndex,
															Object... cellIndexes) {

		Row row = sheet.createRow(sheet.getLastRowNum() + 1);

		for (Object obj : cellIndexes) {
			if (obj instanceof List<?>) {
				for (int idx : (List<Integer>) obj) {
					createTotalsRowCell(wb, row, firstDataRowIndex, lastDataRowIndex, idx);
				}
			}
			else if (obj instanceof Integer) {
				createTotalsRowCell(wb, row, firstDataRowIndex, lastDataRowIndex, (Integer) obj);
			}
		}

		return row;
	}

	public void createReportDateHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		SimpleDateFormat sdf_long = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a z");

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);
		f.setColor(IndexedColors.RED.getIndex());

		cs.setFont(f);
		//cs.setBorderTop(BorderStyle.THIN);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		Row row = sheet.createRow(rowIndex);
		Cell cell = null;

		cell = row.createCell(0);
		cell.setCellStyle(cs);
		cell.setCellValue("REPORT DATE");

		cell = row.createCell(1);
		cell.setCellStyle(cs);
		cell.setCellValue(sdf_long.format(Calendar.getInstance().getTime()));
	}

	public void createHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex, List<String> columnNames) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);

		cs.setFont(f);
		cs.setBorderBottom(BorderStyle.THIN);

		Row row = sheet.createRow(rowIndex);

		int cells = 0;

		Cell cell = null;

		for (String col : columnNames) {
			cell = row.createCell(cells++);
			cell.setCellStyle(cs);

			if (StringUtils.isNotBlank(col)) {
				cell.setCellValue(col);
			}
			else {
				cell.setBlank();
			}
		}
	}

	public List<Integer> createRange(int start, int end) {

		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
}
