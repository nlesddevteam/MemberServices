package com.esdnl.h1n1.site.handler;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
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
import org.jCharts.properties.AxisChartTypeProperties;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.ClusteredBarChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.LineChartProperties;
import org.jCharts.properties.PointChartProperties;
import org.jCharts.types.ChartType;

import com.awsd.school.bean.SchoolStatsSummaryBean;
import com.awsd.school.dao.SchoolStatsManager;
import com.esdnl.h1n1.bean.DailySummaryBean;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.DateUtils;

public class ViewAdminDailySummaryReportRequestHandler extends RequestHandlerImpl {

	private Date startDate;
	private Date endDate;
	private final int EXTRA_GRAPH_TICS = 5;

	public ViewAdminDailySummaryReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_index.jsp";

		try {
			if (form.exists("vd")) {
				Calendar tmp = Calendar.getInstance();

				tmp.clear();
				tmp.setTime(form.getDate("vd"));

				this.endDate = tmp.getTime();
			}
			else {
				Calendar tmp = Calendar.getInstance();
				tmp.set(Calendar.HOUR_OF_DAY, 0);
				tmp.clear(Calendar.MINUTE);
				tmp.clear(Calendar.SECOND);
				tmp.clear(Calendar.MILLISECOND);

				this.endDate = tmp.getTime();

			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(this.endDate);
			if (form.exists("vr"))
				cal.add(Calendar.MONTH, form.getInt("vr"));
			else
				cal.add(Calendar.MONTH, -1);
			this.startDate = cal.getTime();

			File chartPath = new File(ROOT_DIR + "/h1n1/includes/images/charts/");

			if (!chartPath.exists())
				chartPath.mkdirs();

			String chartFilename = "district_monthly_trend_" + Calendar.getInstance().getTimeInMillis() + ".png";

			DailySummaryBean[] trendData = DailyReportManager.getMonthlyTrendData(this.startDate, this.endDate);
			SchoolStatsSummaryBean statsSummary = SchoolStatsManager.getSchoolStatsSummaryBean();

			double[][] data = getChartData(trendData, statsSummary);

			try {
				generateChart(data, chartPath.getAbsolutePath() + "/" + chartFilename);
			}
			catch (ChartDataException e) {
				e.printStackTrace();
			}

			request.setAttribute("CHART", chartFilename);
			if (form.exists("chart_type"))
				request.setAttribute("CHARTTYPE", form.get("chart_type"));
			else
				request.setAttribute("CHARTTYPE", "2");
			if (form.exists("vr"))
				request.setAttribute("VIEWRANGE", form.get("vr"));
			else
				request.setAttribute("VIEWRANGE", "-1");
			request.setAttribute("DAILYSCHOOLINFOBEANS", DailyReportManager.getDailySchoolInfoBeans(this.endDate));
			request.setAttribute("DAILYSUMMARYBEAN", DailyReportManager.getDailySummaryBean(this.endDate));
			request.setAttribute("SCHOOLSTATSSUMMARYBEAN", statsSummary);
			request.setAttribute("TODAY", this.endDate);
		}
		catch (H1N1Exception e) {
			e.printStackTrace();
		}

		return path;

	}

	private void generateChart(double[][] data, String path) throws ChartDataException {

		String[] xAxisLabels = getXAxisLabels();
		String xAxisTitle = "Day of Month";
		String yAxisTitle = "Percentage Absent";
		String title = "Monthly Absentee Trends - Eastern School District";
		DataSeries dataSeries = new DataSeries(xAxisLabels, xAxisTitle, yAxisTitle, title);

		String[] legendLabels = {
				"Teachers", "Support Staff", "Students"
		};

		Paint[] paints = new Paint[] {
				Color.GREEN, Color.BLUE, Color.ORANGE
		};

		AxisChartTypeProperties chartTypeProperties = null;
		ChartType chartType = null;
		if (form.exists("chart_type")) {
			switch (form.getInt("chart_type")) {
			case 1:
				Stroke[] strokes = {
						LineChartProperties.DEFAULT_LINE_STROKE, LineChartProperties.DEFAULT_LINE_STROKE,
						LineChartProperties.DEFAULT_LINE_STROKE
				};

				Shape[] shapes = {
						PointChartProperties.SHAPE_CIRCLE, PointChartProperties.SHAPE_CIRCLE, PointChartProperties.SHAPE_CIRCLE
				};

				chartTypeProperties = new LineChartProperties(strokes, shapes);
				chartType = ChartType.LINE;
				break;
			case 2:
				chartTypeProperties = new ClusteredBarChartProperties();
				chartType = ChartType.BAR_CLUSTERED;
				break;
			}
		}
		else {
			chartTypeProperties = new ClusteredBarChartProperties();
			chartType = ChartType.BAR_CLUSTERED;
		}

		AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data, legendLabels, paints, chartType, chartTypeProperties);
		dataSeries.addIAxisPlotDataSet(axisChartDataSet);

		ChartProperties chartProperties = new ChartProperties();

		AxisProperties axisProperties = new AxisProperties();
		axisProperties.setXAxisLabelsAreVertical(true);

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

		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		String[] labels = new String[DateUtils.getDaysInRange(this.startDate, this.endDate) + EXTRA_GRAPH_TICS];

		Calendar cal = Calendar.getInstance();
		cal.setTime(this.startDate);

		for (int i = 0; i < labels.length; i++) {
			labels[i] = sdf.format(cal.getTime());
			cal.add(Calendar.DATE, 1);
		}

		return labels;
	}

	private double[][] getChartData(DailySummaryBean[] rbeans, SchoolStatsSummaryBean statsSummary) {

		double[][] data = new double[3][DateUtils.getDaysInRange(this.startDate, this.endDate) + EXTRA_GRAPH_TICS];

		Arrays.fill(data[0], Double.NaN);
		Arrays.fill(data[1], Double.NaN);
		Arrays.fill(data[2], Double.NaN);

		int idx = 0;
		for (int i = 0; i < rbeans.length; i++) {
			idx = DateUtils.getDaysInRange(this.startDate, rbeans[i].getSummaryDate()) - 1;

			data[0][idx] = rbeans[i].getTeacherSummary() / statsSummary.getTotalTeachers() * 100.0;
			data[1][idx] = rbeans[i].getSupportStaffSummary() / statsSummary.getTotalSupportStaff() * 100.0;
			data[2][idx] = rbeans[i].getStudentSummary() / statsSummary.getTotalStudents() * 100.0;
		}

		return data;
	}
}
