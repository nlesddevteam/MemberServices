package com.esdnl.h1n1.site.handler;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.encoders.PNGEncoder;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.ClusteredBarChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.types.ChartType;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.h1n1.bean.DailySchoolReportBean;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.util.DateUtils;

public class ViewAdminSchoolMonthlyReportRequestHandler extends RequestHandlerImpl {

	private School school;
	private Date month;

	public ViewAdminSchoolMonthlyReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("s"), new RequiredPatternFormElement("s", FormElementPattern.INTEGER_PATTERN)
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_monthly_school_report.jsp";

		try {
			school = SchoolDB.getSchool(form.getInt("s"));

			if (form.exists("y") && form.exists("m")) {
				Calendar tmp = Calendar.getInstance();
				tmp.clear();
				tmp.set(Calendar.YEAR, form.getInt("y"));
				tmp.set(Calendar.MONTH, form.getInt("m"));

				month = tmp.getTime();
			}
			else
				month = Calendar.getInstance().getTime();

			File chartPath = new File(ROOT_DIR + "/h1n1/includes/images/charts/");

			if (!chartPath.exists())
				chartPath.mkdirs();

			String chartFilename = "monthly_trend_" + school.getSchoolID() + new SimpleDateFormat("MMyyyy").format(month)
					+ ".png";

			DailySchoolReportBean[] rbeans = DailyReportManager.getDailySchoolReportBeans(school, month);
			try {
				generateChart(rbeans, chartPath.getAbsolutePath() + "/" + chartFilename);
			}
			catch (ChartDataException e) {
				e.printStackTrace();
			}

			request.setAttribute("CHART", chartFilename);
			request.setAttribute("MONTHLYDAILYREPORTBEANS", rbeans);
			request.setAttribute("MONTHLYSUMMARYBEAN", DailyReportManager.getMonthlySchoolSummaryBean(school, month));
			request.setAttribute("SCHOOLBEAN", school);
			request.setAttribute("TODAY", month);
		}
		catch (H1N1Exception e) {
			e.printStackTrace();
		}

		return path;
	}

	public void auditRequest() {

		ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

		audit.setAction("View Principal Monthly Report - " + new SimpleDateFormat("MMMM yyyy").format(month));
		audit.setActionType(ActionTypeConstant.VIEW);
		audit.setApplication(ApplicationConstant.getH1N1());
		audit.setObjectId(Integer.toString(school.getSchoolID()));
		audit.setObjectType(school.getClass().getName());
		audit.setWho(usr.getPersonnel().getFullName());

		audit.saveBean();
	}

	private void generateChart(DailySchoolReportBean[] rbeans, String path) throws ChartDataException {

		String[] xAxisLabels = getXAxisLabels();
		String xAxisTitle = "Day of Month";
		String yAxisTitle = "Percentage Absent";
		String title = "Monthly Absentee Trends - " + school.getSchoolName();
		DataSeries dataSeries = new DataSeries(xAxisLabels, xAxisTitle, yAxisTitle, title);

		double[][] data = getData(rbeans);

		String[] legendLabels = {
				"Teachers", "Support Staff", "Students"
		};
		Paint[] paints = new Paint[] {
				Color.GREEN, Color.BLUE, Color.ORANGE
		};

		ClusteredBarChartProperties chartTypeProperties = new ClusteredBarChartProperties();

		AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data, legendLabels, paints, ChartType.BAR_CLUSTERED, chartTypeProperties);
		dataSeries.addIAxisPlotDataSet(axisChartDataSet);

		ChartProperties chartProperties = new ChartProperties();
		AxisProperties axisProperties = new AxisProperties();
		LegendProperties legendProperties = new LegendProperties();

		AxisChart axisChart = new AxisChart(dataSeries, chartProperties, axisProperties, legendProperties, 600, 300);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			PNGEncoder.encode(axisChart, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private String[] getXAxisLabels() {

		String[] labels = new String[DateUtils.getDaysInMonth(month)];

		for (int i = 0; i < labels.length; i++)
			labels[i] = Integer.toString(i + 1);

		return labels;
	}

	private double[][] getData(DailySchoolReportBean[] rbeans) {

		double[][] data = new double[3][DateUtils.getDaysInMonth(month)];

		Arrays.fill(data[0], Double.NaN);
		Arrays.fill(data[1], Double.NaN);
		Arrays.fill(data[2], Double.NaN);

		Calendar tmp = Calendar.getInstance();
		int idx = 0;
		for (int i = 0; i < rbeans.length; i++) {
			tmp.setTime(rbeans[i].getDateAdded());
			idx = tmp.get(Calendar.DATE) - 1;

			data[0][idx] = rbeans[i].getTeacherAverage();
			data[1][idx] = rbeans[i].getSupportStaffAverage();
			data[2][idx] = rbeans[i].getStudentAverage();
		}

		return data;
	}
}
