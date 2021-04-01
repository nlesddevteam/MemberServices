package com.esdnl.personnel.jobs.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PositionPlannerRedundanciesExcelReport extends ExcelExporter {

	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PositionPlannerRedundanciesExcelReport.class);

	private String schoolYear;

	public PositionPlannerRedundanciesExcelReport(String schoolYear) {

		super();

		this.schoolYear = schoolYear;
	}

	public String getSchoolYear() {

		return this.schoolYear;
	}

	@Override
	public String getFilename() {

		return "position_planner_redundancies_" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".xlsx";
	}

	@Override
	public boolean validateAccess() {

		return true;
	}

	@Override
	public void createContents(XSSFWorkbook wb) {

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
		SimpleDateFormat sdf_long = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a z");

		Collection<ReportRecord> recs = null;

		Sheet sheet1 = wb.createSheet("Position Panner Redundancies - " + this.schoolYear);

		createHeader(wb, sheet1);

		try {
			recs = ReportsManager.getPositionPlannerRedundanciesBySchoolYear(this.schoolYear);

			int rows = 0, cells = 0;

			for (ReportRecord rec : recs) {
				Row row = sheet1.createRow(++rows);

				cells = 0;

				//cell 1 - School Year
				row.createCell(cells++).setCellValue(rec.getSchoolYear());

				//cell 2 - School/Location
				row.createCell(cells++).setCellValue(rec.getLocation());

				//cell 3 - Owner
				row.createCell(cells++).setCellValue(rec.getOwner());

				//cell 4 - Seniority (yrs)
				row.createCell(cells++).setCellValue(rec.getSeniority());

				//cell 5 - Tenure
				row.createCell(cells++).setCellValue(rec.getTenure());

				//cell 6 - Unit
				row.createCell(cells++).setCellValue(rec.getUnit());

				//cell 7 - Rationale
				row.createCell(cells++).setCellValue(rec.getRationale());
			}

			autoFitColumnWidth(sheet1, cells);
		}
		catch (ReportException e) {
			log.error("PositionPlannerRedundanciesExcellReport.createContents(XSSFWorkbook wb): SchoolYear["
					+ this.getSchoolYear() + "]", e);
		}
	}

	private void createHeader(XSSFWorkbook wb, Sheet sheet) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);

		cs.setFont(f);
		cs.setBorderBottom(BorderStyle.THIN);

		Row row = sheet.createRow(0);

		int cells = 0;

		Cell cell = null;

		//cell 1 - School Year
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("School Year");

		//cell 2 - School/Location
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("School/Location");

		//cell 3 - Owner
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("Owner");

		//cell 4 - Seniority (yrs)
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("Seniority (yrs)");

		//cell 5 - Tenure
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("Tenure");

		//cell 6 - Unit
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("Unit");

		//cell 7 - Rationale
		cell = row.createCell(cells++);
		cell.setCellStyle(cs);
		cell.setCellValue("Rationale");

		freezeHeader(sheet);
	}

	public static class ReportRecord {

		private String schoolYear;
		private String location;
		private String owner;
		private double seniority;
		private String tenure;
		private double unit;
		private String rationale;

		public ReportRecord(ResultSet rs) throws SQLException {

			this.schoolYear = rs.getString("School Year");
			this.location = rs.getString("School/Location");
			this.owner = rs.getString("Owner");
			this.seniority = rs.getDouble("Seniority (yrs)");
			this.tenure = rs.getString("Tenure");
			this.unit = rs.getDouble("Unit");
			this.rationale = rs.getString("Rationale");
		}

		public String getSchoolYear() {

			return schoolYear;
		}

		public String getLocation() {

			return location;
		}

		public String getOwner() {

			return owner;
		}

		public double getSeniority() {

			return seniority;
		}

		public String getTenure() {

			return tenure;
		}

		public double getUnit() {

			return unit;
		}

		public String getRationale() {

			return rationale;
		}

	}
}
