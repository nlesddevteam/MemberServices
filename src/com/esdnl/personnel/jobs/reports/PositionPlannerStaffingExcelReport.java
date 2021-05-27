package com.esdnl.personnel.jobs.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PositionPlannerStaffingExcelReport extends ExcelExporter {

	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PositionPlannerStaffingExcelReport.class);

	private String schoolYear;
	private Collection<AllocationsRecord> allocations;
	private Collection<PermanentsRecord> permanents;
	private Collection<VacanciesRecord> vacancies;
	private Collection<RedundanciesRecord> redundancies;

	public PositionPlannerStaffingExcelReport(String schoolYear) {

		super();

		try {
			this.schoolYear = schoolYear;
			this.allocations = ReportsManager.getPositionPlannerAllocationsBySchoolYear(this.schoolYear);
			this.permanents = ReportsManager.getPositionPlannerPermanentsBySchoolYear(this.schoolYear);
			this.vacancies = ReportsManager.getPositionPlannerVacanciesBySchoolYear(this.schoolYear);
			this.redundancies = ReportsManager.getPositionPlannerRedundanciesBySchoolYear(this.schoolYear);
		}
		catch (ReportException e) {
			log.error("PositionPlannerStaffingExcelReport.PositionPlannerStaffingExcelReport(String schoolYear): SchoolYear["
					+ this.getSchoolYear() + "]", e);
		}
	}

	public String getSchoolYear() {

		return this.schoolYear;
	}

	@Override
	public String getFilename() {

		return "position_planner_staffing_" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".xlsx";
	}

	@Override
	public boolean validateAccess() {

		return true;
	}

	@Override
	public void createContents(XSSFWorkbook wb) {

		createAllocationsSheet(wb);
		createPermanentsSheet(wb);
		createVacanciesSheet(wb);
		createRedundanciesSheet(wb);
	}

	private void createRedundanciesSheet(XSSFWorkbook wb) {

		Sheet sheet1 = wb.createSheet("Redundancies - " + this.schoolYear);

		int nextRowIndex = createSummaryHeader(wb, sheet1, 0);
		createHeader(wb, sheet1, nextRowIndex, Arrays.asList("School Year", "Region", "School/Location", "Owner",
				"Seniority (yrs)", "Tenure", "Unit", "Rationale"));

		int numHeaderRows = sheet1.getLastRowNum() + 1;

		freezeHeader(sheet1, numHeaderRows);

		int rows = sheet1.getLastRowNum(), cells = 0;

		for (RedundanciesRecord rec : this.redundancies) {
			Row row = sheet1.createRow(++rows);

			cells = 0;

			row.createCell(cells++).setCellValue(rec.getSchoolYear());

			row.createCell(cells++).setCellValue(rec.getZoneName());

			row.createCell(cells++).setCellValue(rec.getLocation());

			row.createCell(cells++).setCellValue(rec.getOwner());

			row.createCell(cells++).setCellValue(rec.getSeniority());

			row.createCell(cells++).setCellValue(rec.getTenure());

			row.createCell(cells++).setCellValue(rec.getUnit());

			row.createCell(cells++).setCellValue(rec.getRationale());
		}

		int firstDataRowIndex = numHeaderRows + 1;
		int lastDataRowIndex = (this.redundancies.size() + numHeaderRows);

		createTotalsRow(wb, sheet1, firstDataRowIndex, lastDataRowIndex, 6);

		autoFitColumnWidth(sheet1, cells);
	}

	private void createAllocationsSheet(XSSFWorkbook wb) {

		Sheet sheet1 = wb.createSheet("Allocations - " + this.schoolYear);

		int nextRowIndex = createSummaryHeader(wb, sheet1, 0);
		createHeader(wb, sheet1, nextRowIndex,
				Arrays.asList("School Year", "Region", "School/Location", "Regular Units", "Administration Units",
						"Specialist Units", "LRT Units", "IRT Units", "Reading Specialist Units", "Adjustments Units",
						"TCH Extra Units", "Total TCH Units", "TLA Units", "TLA Extra Units", "Total TLA Units", "Total Units",
						"SA Hours", "SA Extra Hours", "Total SA Hours"));

		int numHeaderRows = sheet1.getLastRowNum() + 1;

		freezeHeader(sheet1, numHeaderRows);

		int rows = sheet1.getLastRowNum(), cells = 0;

		for (AllocationsRecord rec : this.allocations) {
			Row row = sheet1.createRow(++rows);

			cells = 0;

			row.createCell(cells++).setCellValue(rec.getSchoolYear());

			row.createCell(cells++).setCellValue(rec.getZoneName());

			row.createCell(cells++).setCellValue(rec.getLocation());

			row.createCell(cells++).setCellValue(rec.getRegularUnits());

			row.createCell(cells++).setCellValue(rec.getAdministrationUnits());

			row.createCell(cells++).setCellValue(rec.getSpecialistUnits());

			row.createCell(cells++).setCellValue(rec.getLrtUnits());

			row.createCell(cells++).setCellValue(rec.getIrt1Units());

			row.createCell(cells++).setCellValue(rec.getReadingSpecialistUnits());

			row.createCell(cells++).setCellValue(rec.getOtherUnits());

			row.createCell(cells++).setCellValue(rec.getTchExtraUnits());

			applyBoldCellStyle(wb, row.createCell(cells++)).setCellValue(rec.getTotalTchUnits());

			row.createCell(cells++).setCellValue(rec.getTlaUnits());

			row.createCell(cells++).setCellValue(rec.getTlaExtraUnits());

			applyBoldCellStyle(wb, row.createCell(cells++)).setCellValue(rec.getTotalTLAUnits());

			applyBoldCellStyle(wb, row.createCell(cells++)).setCellValue(rec.getTotalUnits());

			row.createCell(cells++).setCellValue(rec.getStudAsstHours());

			row.createCell(cells++).setCellValue(rec.getSaExtraHours());

			applyBoldCellStyle(wb, row.createCell(cells++)).setCellValue(rec.getTotalSAHours());
		}

		int firstDataRowIndex = numHeaderRows + 1;
		int lastDataRowIndex = (this.allocations.size() + numHeaderRows);

		createTotalsRow(wb, sheet1, firstDataRowIndex, lastDataRowIndex, createRange(3, 18));

		autoFitColumnWidth(sheet1, cells);
	}

	private void createVacanciesSheet(XSSFWorkbook wb) {

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");

		Sheet sheet1 = wb.createSheet("Vacancies - " + this.schoolYear);

		int nextRowIndex = createSummaryHeader(wb, sheet1, 0);
		createHeader(wb, sheet1, nextRowIndex,
				Arrays.asList("School Year", "Region", "School/Location", "Job Description", "Position Type", "Owner",
						"Seniority (yrs)", "Vacancy Reason", "Start Date", "End Date", "Unit", "Ad Requested", "Ad Posted",
						"Position Filled"));

		int numHeaderRows = sheet1.getLastRowNum() + 1;

		freezeHeader(sheet1, numHeaderRows);

		int rows = sheet1.getLastRowNum(), cells = 0;

		for (VacanciesRecord rec : this.vacancies) {
			Row row = sheet1.createRow(++rows);

			cells = 0;

			row.createCell(cells++).setCellValue(rec.getSchoolYear());

			row.createCell(cells++).setCellValue(rec.getZoneName());

			row.createCell(cells++).setCellValue(rec.getLocation());

			row.createCell(cells++).setCellValue(rec.getJobDescription());

			row.createCell(cells++).setCellValue(rec.getPositionType());

			row.createCell(cells++).setCellValue(rec.getOwner());

			row.createCell(cells++).setCellValue(rec.getSeniority());

			row.createCell(cells++).setCellValue(rec.getVacancyReason());

			if (rec.getStartDate() != null) {
				row.createCell(cells++).setCellValue(sdf.format(rec.getStartDate()));
			}
			else {
				row.createCell(cells++).setBlank();
			}

			if (rec.getEndDate() != null) {
				row.createCell(cells++).setCellValue(sdf.format(rec.getEndDate()));
			}
			else {
				row.createCell(cells++).setBlank();
			}

			row.createCell(cells++).setCellValue(rec.getUnit());

			row.createCell(cells++).setCellValue(rec.getAdRequested());

			row.createCell(cells++).setCellValue(rec.getAdPosted());

			row.createCell(cells++).setCellValue(rec.getPositionFilled());
		}

		int firstDataRowIndex = numHeaderRows + 1;
		int lastDataRowIndex = (this.vacancies.size() + numHeaderRows);

		createTotalsRow(wb, sheet1, firstDataRowIndex, lastDataRowIndex, 10);

		autoFitColumnWidth(sheet1, cells);

	}

	private void createPermanentsSheet(XSSFWorkbook wb) {

		Sheet sheet1 = wb.createSheet("Permanents - " + this.schoolYear);

		int nextRowIndex = createSummaryHeader(wb, sheet1, 0);
		createHeader(wb, sheet1, nextRowIndex,
				Arrays.asList("School Year", "Region", "School/Location", "Owner", "Seniority (yrs)", "Unit", "Assignment"));

		int numHeaderRows = sheet1.getLastRowNum() + 1;

		freezeHeader(sheet1, numHeaderRows);

		int rows = sheet1.getLastRowNum(), cells = 0;

		for (PermanentsRecord rec : this.permanents) {
			Row row = sheet1.createRow(++rows);

			cells = 0;

			row.createCell(cells++).setCellValue(rec.getSchoolYear());

			row.createCell(cells++).setCellValue(rec.getZoneName());

			row.createCell(cells++).setCellValue(rec.getLocation());

			row.createCell(cells++).setCellValue(rec.getOwner());

			row.createCell(cells++).setCellValue(rec.getSeniority());

			row.createCell(cells++).setCellValue(rec.getUnit());

			row.createCell(cells++).setCellValue(rec.getAssignment());
		}

		int firstDataRowIndex = numHeaderRows + 1;
		int lastDataRowIndex = (this.permanents.size() + numHeaderRows);

		createTotalsRow(wb, sheet1, firstDataRowIndex, lastDataRowIndex, 5);

		autoFitColumnWidth(sheet1, cells);

	}

	public int createSummaryHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		createReportDateHeader(wb, sheet, rowIndex++);
		createTotalAllocationUnitsHeader(wb, sheet, rowIndex++);
		createTotalPermanentUnitsHeader(wb, sheet, rowIndex++);
		createTotalRedunciesUnitsHeader(wb, sheet, rowIndex++);
		createTotalVacancyUnitsHeader(wb, sheet, rowIndex++);
		createOutstandingUnitsHeader(wb, sheet, rowIndex++);

		return rowIndex;
	}

	public void createTotalAllocationUnitsHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);
		f.setColor(IndexedColors.RED.getIndex());

		cs.setFont(f);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		Row row = sheet.createRow(rowIndex);
		Cell cell = null;

		cell = row.createCell(0);
		cell.setCellStyle(cs);
		cell.setCellValue("TOTAL ALLOCATION");

		cell = row.createCell(1);
		cell.setCellStyle(cs);
		cell.setCellValue(this.getTotalAllocationUnits());
	}

	public void createTotalPermanentUnitsHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);
		f.setColor(IndexedColors.RED.getIndex());

		cs.setFont(f);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		Row row = sheet.createRow(rowIndex);
		Cell cell = null;

		cell = row.createCell(0);
		cell.setCellStyle(cs);
		cell.setCellValue("PERMANENTS");

		cell = row.createCell(1);
		cell.setCellStyle(cs);
		cell.setCellValue(this.getTotalPermanentUnits());
	}

	public void createTotalRedunciesUnitsHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);
		f.setColor(IndexedColors.RED.getIndex());

		cs.setFont(f);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		Row row = sheet.createRow(rowIndex);
		Cell cell = null;

		cell = row.createCell(0);
		cell.setCellStyle(cs);
		cell.setCellValue("REDUNDANCIES");

		cell = row.createCell(1);
		cell.setCellStyle(cs);
		cell.setCellValue(this.getTotalRedundentUnits());
	}

	public void createTotalVacancyUnitsHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);
		f.setColor(IndexedColors.RED.getIndex());

		cs.setFont(f);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		Row row = sheet.createRow(rowIndex);
		Cell cell = null;

		cell = row.createCell(0);
		cell.setCellStyle(cs);
		cell.setCellValue("VACANCIES");

		cell = row.createCell(1);
		cell.setCellStyle(cs);
		cell.setCellValue(this.getTotalVacancyUnits());

		cell = row.createCell(2);
		cell.setCellStyle(cs);
		cell.setCellValue("FILLED");

		cell = row.createCell(3);
		cell.setCellStyle(cs);
		cell.setCellValue(this.getTotalVacanciesFilled());
	}

	public void createOutstandingUnitsHeader(XSSFWorkbook wb, Sheet sheet, int rowIndex) {

		CellStyle cs = wb.createCellStyle();

		Font f = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setBold(true);
		f.setColor(IndexedColors.RED.getIndex());

		cs.setFont(f);
		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cs.setFillPattern(FillPatternType.BRICKS);

		Row row = sheet.createRow(rowIndex);
		Cell cell = null;

		cell = row.createCell(0);
		cell.setCellStyle(cs);
		cell.setCellValue("OUTSTANDING");

		cell = row.createCell(1);
		cell.setCellStyle(cs);
		cell.setCellValue(this.getTotalOutstandingUnits());
	}

	public double getTotalTCHAllocationUnits() {

		return this.allocations.stream().mapToDouble(a -> a.getTotalTchUnits()).sum();
	}

	public double getTotalTLAAllocationUnits() {

		return this.allocations.stream().mapToDouble(a -> a.getTotalTLAUnits()).sum();
	}

	public double getTotalAllocationUnits() {

		return this.allocations.stream().mapToDouble(a -> a.getTotalUnits()).sum();
	}

	public double getTotalPermanentUnits() {

		return this.permanents.stream().mapToDouble(p -> p.getUnit()).sum();
	}

	public double getTotalRedundentUnits() {

		return this.redundancies.stream().mapToDouble(r -> r.getUnit()).sum();
	}

	public double getTotalVacancyUnits() {

		return this.vacancies.stream().mapToDouble(v -> v.getUnit()).sum();
	}

	public double getTotalVacanciesUnfilled() {

		return this.vacancies.stream().filter(v -> !v.getPositionFilled().contains("YES")).mapToDouble(
				v -> v.getUnit()).sum();
	}

	public double getTotalVacanciesFilled() {

		return this.vacancies.stream().filter(v -> v.getPositionFilled().contains("YES")).mapToDouble(
				v -> v.getUnit()).sum();
	}

	public double getTotalOutstandingUnits() {

		return this.getTotalAllocationUnits() - this.getTotalPermanentUnits() - this.getTotalVacanciesFilled();
	}

	public static class RedundanciesRecord {

		private String schoolYear;
		private String zone;
		private String location;
		private String owner;
		private double seniority;
		private String tenure;
		private double unit;
		private String rationale;

		public RedundanciesRecord(ResultSet rs) throws SQLException {

			this.zone = rs.getString("zone_name");
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

		public String getZoneName() {

			return this.zone;
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

	public static class AllocationsRecord {

		private String zone;
		private String location;
		private int allocationId;
		private String schoolYear;
		private String locationId;
		private double regularUnits;
		private double administrationUnits;
		private double guidanceUnits;
		private double specialistUnits;
		private double lrtUnits;
		private double irt1Units;
		private double otherUnits;
		private double irt2Units;
		private boolean enabled;
		private double tlaUnits;
		private double studAsstHours;
		private boolean published;
		private double readingSpecialistUnits;
		private double tchExtraUnits;
		private double tlaExtraUnits;
		private double saExtraHours;

		public AllocationsRecord(ResultSet rs) throws SQLException {

			this.zone = rs.getString("zone_name");
			this.location = rs.getString("LOC_DESCRIPTION");
			this.allocationId = rs.getInt("allocation_id");
			this.schoolYear = rs.getString("school_year");
			this.locationId = rs.getString("location_id");
			this.regularUnits = rs.getDouble("regular_units");
			this.administrationUnits = rs.getDouble("administration_units");
			this.guidanceUnits = rs.getDouble("guidance_units");
			this.specialistUnits = rs.getDouble("specialist_units");
			this.lrtUnits = rs.getDouble("lrt_units");
			this.irt1Units = rs.getDouble("irt1_units");
			this.otherUnits = rs.getDouble("other_units");
			this.irt2Units = rs.getDouble("irt2_units");
			this.enabled = rs.getBoolean("enabled");
			this.tlaUnits = rs.getDouble("tla_units");
			this.studAsstHours = rs.getDouble("stud_asst_hours");
			this.published = rs.getBoolean("published");
			this.readingSpecialistUnits = rs.getDouble("reading_specialist_units");
			this.tchExtraUnits = rs.getDouble("tchr_extra_units");
			this.tlaExtraUnits = rs.getDouble("tla_extra_units");
			this.saExtraHours = rs.getDouble("sa_extra_hours");
		}

		public String getZoneName() {

			return this.zone;
		}

		public String getLocation() {

			return location;
		}

		public int getAllocationId() {

			return allocationId;
		}

		public String getSchoolYear() {

			return schoolYear;
		}

		public String getLocationId() {

			return locationId;
		}

		public double getRegularUnits() {

			return regularUnits;
		}

		public double getAdministrationUnits() {

			return administrationUnits;
		}

		public double getGuidanceUnits() {

			return guidanceUnits;
		}

		public double getSpecialistUnits() {

			return specialistUnits;
		}

		public double getLrtUnits() {

			return lrtUnits;
		}

		public double getIrt1Units() {

			return irt1Units;
		}

		public double getOtherUnits() {

			return otherUnits;
		}

		public double getIrt2Units() {

			return irt2Units;
		}

		public boolean isEnabled() {

			return enabled;
		}

		public double getTlaUnits() {

			return tlaUnits;
		}

		public double getStudAsstHours() {

			return studAsstHours;
		}

		public boolean isPublished() {

			return published;
		}

		public double getReadingSpecialistUnits() {

			return readingSpecialistUnits;
		}

		public double getTchExtraUnits() {

			return tchExtraUnits;
		}

		public double getTotalTchUnits() {

			return this.administrationUnits + this.guidanceUnits + this.irt1Units + this.irt2Units + this.lrtUnits
					+ this.otherUnits + this.readingSpecialistUnits + this.regularUnits + this.specialistUnits
					+ this.tchExtraUnits;
		}

		public double getTlaExtraUnits() {

			return tlaExtraUnits;
		}

		public double getTotalTLAUnits() {

			return this.tlaUnits + this.tlaExtraUnits;
		}

		public double getTotalUnits() {

			return this.getTotalTchUnits() + this.getTotalTLAUnits();
		}

		public double getSaExtraHours() {

			return saExtraHours;
		}

		public double getTotalSAHours() {

			return this.studAsstHours + this.saExtraHours;
		}
	}

	public static class VacanciesRecord {

		private String schoolYear;
		private int positionId;
		private String zone;
		private String location;
		private String jobDescription;
		private String positionType;
		private String owner;
		private double seniority;
		private String vacancyReason;
		private Date startDate;
		private Date endDate;
		private double unit;
		private String adRequested;
		private String adPosted;
		private String positionFilled;

		public VacanciesRecord(ResultSet rs) throws SQLException {

			this.schoolYear = rs.getString("School Year");
			this.positionId = rs.getInt("Position ID");
			this.zone = rs.getString("zone_name");
			this.location = rs.getString("School/Location");
			this.jobDescription = rs.getString("Job Description");
			this.positionType = rs.getString("Position Type");
			this.owner = rs.getString("Owner");
			this.seniority = rs.getDouble("Seniority (yrs)");
			this.vacancyReason = rs.getString("Vacancy Reason");

			if (rs.getDate("Start Date") != null) {
				this.startDate = new Date(rs.getDate("Start Date").getTime());
			}
			else {
				this.startDate = null;
			}

			if (rs.getDate("End Date") != null) {
				this.endDate = new Date(rs.getDate("End Date").getTime());
			}
			else {
				this.endDate = null;
			}

			this.unit = rs.getDouble("Unit");
			this.adRequested = rs.getString("Ad Requested");
			this.adPosted = rs.getString("Ad Posted");
			this.positionFilled = rs.getString("Position Filled");
		}

		public String getSchoolYear() {

			return schoolYear;
		}

		public int getPositionId() {

			return positionId;
		}

		public String getZoneName() {

			return this.zone;
		}

		public String getLocation() {

			return location;
		}

		public String getJobDescription() {

			return jobDescription;
		}

		public String getPositionType() {

			return positionType;
		}

		public String getOwner() {

			return owner;
		}

		public double getSeniority() {

			return seniority;
		}

		public String getVacancyReason() {

			return vacancyReason;
		}

		public Date getStartDate() {

			return startDate;
		}

		public Date getEndDate() {

			return endDate;
		}

		public double getUnit() {

			return unit;
		}

		public String getAdRequested() {

			return adRequested;
		}

		public String getAdPosted() {

			return adPosted;
		}

		public String getPositionFilled() {

			return positionFilled;
		}
	}

	public static class PermanentsRecord {

		private String schoolYear;
		private String zone;
		private String location;
		private String owner;
		private double seniority;
		private double unit;
		private String assignment;

		public PermanentsRecord(ResultSet rs) throws SQLException {

			this.schoolYear = rs.getString("School Year");
			this.zone = rs.getString("zone_name");
			this.location = rs.getString("School/Location");
			this.owner = rs.getString("Owner");
			this.seniority = rs.getDouble("Seniority (yrs)");
			this.unit = rs.getDouble("Unit");
			this.assignment = rs.getString("Assignment");
		}

		public String getSchoolYear() {

			return schoolYear;
		}

		public String getZoneName() {

			return this.zone;
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

		public double getUnit() {

			return unit;
		}

		public String getAssignment() {

			return assignment;
		}
	}
}
