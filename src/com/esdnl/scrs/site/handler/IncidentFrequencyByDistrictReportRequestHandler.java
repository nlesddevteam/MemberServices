package com.esdnl.scrs.site.handler;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.axisChart.customRenderers.axisValue.renderers.ValueLabelPosition;
import org.jCharts.axisChart.customRenderers.axisValue.renderers.ValueLabelRenderer;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.encoders.PNGEncoder;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.ClusteredBarChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.types.ChartType;

import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.MonthlyIncidentFrequencyBean;
import com.esdnl.scrs.service.AdminReportingService;
import com.esdnl.servlet.RequestHandlerImpl;

public class IncidentFrequencyByDistrictReportRequestHandler extends RequestHandlerImpl {

	public IncidentFrequencyByDistrictReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"BULLYING-ANALYSIS-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			File chartPath = new File(ROOT_DIR + "/bullying/includes/images/charts/");

			if (!chartPath.exists())
				chartPath.mkdirs();

			String chartFilename = "monthly_district_incident_frequency.png";

			File chart = new File(chartPath.getAbsolutePath() + "/" + chartFilename);
			if (chart.exists())
				chart.delete();

			ArrayList<MonthlyIncidentFrequencyBean> rpt = AdminReportingService.getDistrictMonthlyFrequencyReport();

			try {
				generateChart(rpt, chartPath.getAbsolutePath() + "/" + chartFilename);
			}
			catch (ChartDataException e) {
				e.printStackTrace();
			}

			request.setAttribute("CHART", chartFilename);
			request.setAttribute("TITLE", "Monthly Incident Frequency By District");

		}
		catch (BullyingException e) {
			e.printStackTrace();
		}

		return "view_admin_report.jsp";
	}

	private void generateChart(ArrayList<MonthlyIncidentFrequencyBean> rbeans, String path) throws ChartDataException {

		String[] xAxisLabels = getXAxisLabels(rbeans);
		String xAxisTitle = "Month";
		String yAxisTitle = "Incident Count";
		String title = "Monthly Incident Frequency By District";
		DataSeries dataSeries = new DataSeries(xAxisLabels, xAxisTitle, yAxisTitle, title);

		double[][] data = getData(rbeans);

		String[] legendLabels = {
			"District Incident Count"
		};

		Paint[] paints = new Paint[] {
			Color.GREEN
		};

		ClusteredBarChartProperties chartTypeProperties = new ClusteredBarChartProperties();

		ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer(false, false, true, -1);
		valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.AT_TOP);
		valueLabelRenderer.useVerticalLabels(false);
		chartTypeProperties.addPostRenderEventListener(valueLabelRenderer);

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

	private String[] getXAxisLabels(ArrayList<MonthlyIncidentFrequencyBean> rbeans) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

		String[] labels = new String[rbeans.size()];

		for (int i = 0; i < labels.length; i++)
			labels[i] = sdf.format(rbeans.get(i).getMonth());

		return labels;
	}

	private double[][] getData(ArrayList<MonthlyIncidentFrequencyBean> rbeans) {

		double[][] data = new double[1][rbeans.size()];

		Arrays.fill(data[0], Double.NaN);

		for (int i = 0; i < rbeans.size(); i++) {
			data[0][i] = rbeans.get(i).getCount();
		}

		return data;
	}

}
