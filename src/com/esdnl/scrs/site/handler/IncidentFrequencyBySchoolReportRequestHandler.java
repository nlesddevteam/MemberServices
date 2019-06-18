package com.esdnl.scrs.site.handler;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;

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

import com.awsd.school.School;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.MonthlyIncidentFrequencyBean;
import com.esdnl.scrs.service.AdminReportingService;
import com.esdnl.servlet.RequestHandlerImpl;

public class IncidentFrequencyBySchoolReportRequestHandler extends RequestHandlerImpl {

	public IncidentFrequencyBySchoolReportRequestHandler() {

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

			String chartFilenamePrefix = "monthly_school_incident_frequency";

			TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = AdminReportingService.getSchoolMonthlyFrequencyReport();

			ArrayList<String> charts = null;
			try {
				charts = generateCharts(rpt, chartPath.getAbsolutePath(), chartFilenamePrefix, 4);
			}
			catch (ChartDataException e) {
				e.printStackTrace();
			}

			request.setAttribute("CHARTS", charts);
			request.setAttribute("TITLE", "Monthly Incident Frequency By School");

		}
		catch (BullyingException e) {
			e.printStackTrace();
		}

		return "view_admin_report.jsp";
	}

	private ArrayList<String> generateCharts(TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans,
																						String basepath, String filenamePrefix, int schoolsPerChart)
			throws ChartDataException {

		ArrayList<String> chartFilenames = new ArrayList<String>();
		int offset = 0;
		int numCharts = rbeans.size() / schoolsPerChart + (rbeans.size() % schoolsPerChart != 0 ? 1 : 0);

		for (int chart = 1; chart <= numCharts; chart++) {

			File chartFile = new File(basepath + "/" + filenamePrefix + "_" + chart + ".png");
			if (chartFile.exists())
				chartFile.delete();

			int numschools = ((rbeans.size() - offset) > schoolsPerChart) ? schoolsPerChart : (rbeans.size() - offset);

			TreeMap<Date, String> xAxisLabels = getXAxisLabels(rbeans, numschools, offset);
			String xAxisTitle = "Month";
			String yAxisTitle = "Incident Count";
			String title = "Monthly Incident Frequency By School";
			DataSeries dataSeries = new DataSeries(xAxisLabels.values().toArray(new String[0]), xAxisTitle, yAxisTitle, title);

			double[][] data = getData(xAxisLabels, rbeans, numschools, offset);

			String[] legendLabels = getLegendLabels(rbeans, numschools, offset);

			Paint[] paints = getPaints(numschools);

			ClusteredBarChartProperties chartTypeProperties = new ClusteredBarChartProperties();

			ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer(false, false, true, -1);
			valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.ON_TOP);
			valueLabelRenderer.useVerticalLabels(false);
			chartTypeProperties.addPostRenderEventListener(valueLabelRenderer);

			AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data, legendLabels, paints, ChartType.BAR_CLUSTERED, chartTypeProperties);
			dataSeries.addIAxisPlotDataSet(axisChartDataSet);

			ChartProperties chartProperties = new ChartProperties();
			chartProperties.setEdgePadding(25f);

			AxisProperties axisProperties = new AxisProperties();

			LegendProperties legendProperties = new LegendProperties();
			legendProperties.setNumColumns(3);

			AxisChart axisChart = new AxisChart(dataSeries, chartProperties, axisProperties, legendProperties, 700, 400);

			try {
				FileOutputStream fileOutputStream = new FileOutputStream(chartFile);
				PNGEncoder.encode(axisChart, fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();

				chartFilenames.add(chartFile.getName());
			}
			catch (Throwable throwable) {
				throwable.printStackTrace();
			}

			offset += numschools;
		}

		return chartFilenames;
	}

	private TreeMap<Date, String> getXAxisLabels(TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans,
																								int schoolsPerChart, int offset) {

		TreeMap<Date, String> labels = new TreeMap<Date, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

		int ptr = 0;
		for (Entry<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> school : rbeans.entrySet()) {

			if ((ptr - offset) >= schoolsPerChart)
				break;
			else if (ptr++ < offset)
				continue;

			for (Entry<Date, MonthlyIncidentFrequencyBean> m : school.getValue().entrySet()) {
				if (!labels.containsKey(m.getKey()))
					labels.put(m.getKey(), sdf.format(m.getKey()));
			}
		}

		return labels;
	}

	private String[] getLegendLabels(TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans,
																		int schoolsPerChart, int offset) {

		ArrayList<String> labels = new ArrayList<String>();

		int ptr = 0;
		for (School r : rbeans.keySet()) {
			if ((ptr - offset) >= schoolsPerChart)
				break;
			else if (ptr++ < offset)
				continue;
			labels.add(r.getSchoolName());
		}

		return labels.toArray(new String[0]);
	}

	private Paint[] getPaints(int schoolsPerChart) {

		ArrayList<Color> paints = new ArrayList<Color>();

		for (int i = 0; i < schoolsPerChart; i++)
			paints.add(getRandomPaint());

		return paints.toArray(new Color[0]);
	}

	private double[][] getData(TreeMap<Date, String> xAxisLabels,
															TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans, int schoolsPerChart,
															int offset) {

		double[][] data = new double[schoolsPerChart][xAxisLabels.size()];

		for (int i = 0; i < data.length; i++)
			Arrays.fill(data[i], Double.NaN);

		Date[] dates = xAxisLabels.keySet().toArray(new Date[0]);

		int idxRegion = 0;

		int ptr = 0;
		for (Entry<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> r : rbeans.entrySet()) {
			if ((ptr - offset) >= schoolsPerChart)
				break;
			else if (ptr++ < offset)
				continue;

			for (int idxDate = 0; idxDate < dates.length; idxDate++) {
				data[idxRegion][idxDate] = !r.getValue().containsKey(dates[idxDate]) ? 0
						: r.getValue().get(dates[idxDate]).getCount();
			}
			idxRegion++;
		}

		return data;
	}

	private Color getRandomPaint() {

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);

		// fill our array with random colors
		return new Color(red, green, blue);

	}

}
