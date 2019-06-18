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

import com.esdnl.scrs.domain.BullyingBehaviorType;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.MonthlyIncidentFrequencyBean;
import com.esdnl.scrs.service.AdminReportingService;
import com.esdnl.servlet.RequestHandlerImpl;

public class BehaviourIncidentFrequencyByDistrictReportRequestHandler extends RequestHandlerImpl {

	public BehaviourIncidentFrequencyByDistrictReportRequestHandler() {

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

			String chartFilename = "monthly_district_behaviour_incident_frequency.png";

			File chart = new File(chartPath.getAbsolutePath() + "/" + chartFilename);
			if (chart.exists())
				chart.delete();

			TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = AdminReportingService.getDistrictMonthlyBehaviorFrequencyReport();

			try {
				generateChart(rpt, chartPath.getAbsolutePath() + "/" + chartFilename);
			}
			catch (ChartDataException e) {
				e.printStackTrace();
			}

			request.setAttribute("CHART", chartFilename);
			request.setAttribute("TITLE", "Monthly Bullying Type Incident Frequency By District");

		}
		catch (BullyingException e) {
			e.printStackTrace();
		}

		return "view_admin_report.jsp";
	}

	private void generateChart(TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans,
															String path) throws ChartDataException {

		TreeMap<Date, String> xAxisLabels = getXAxisLabels(rbeans);
		String xAxisTitle = "Month";
		String yAxisTitle = "Incident Count";
		String title = "Monthly Bullying Type Incident Frequency By District";
		DataSeries dataSeries = new DataSeries(xAxisLabels.values().toArray(new String[0]), xAxisTitle, yAxisTitle, title);

		double[][] data = getData(xAxisLabels, rbeans);

		String[] legendLabels = getLegendLabels(rbeans);

		Paint[] paints = getPaints(rbeans);

		ClusteredBarChartProperties chartTypeProperties = new ClusteredBarChartProperties();

		ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer(false, false, true, -1);
		valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.ON_TOP);
		valueLabelRenderer.useVerticalLabels(false);
		chartTypeProperties.addPostRenderEventListener(valueLabelRenderer);

		AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data, legendLabels, paints, ChartType.BAR_CLUSTERED, chartTypeProperties);
		dataSeries.addIAxisPlotDataSet(axisChartDataSet);

		ChartProperties chartProperties = new ChartProperties();

		AxisProperties axisProperties = new AxisProperties();

		LegendProperties legendProperties = new LegendProperties();
		legendProperties.setNumColumns(4);

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

	private TreeMap<Date, String> getXAxisLabels(	TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans) {

		TreeMap<Date, String> labels = new TreeMap<Date, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

		for (Entry<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> grade : rbeans.entrySet()) {
			for (Entry<Date, MonthlyIncidentFrequencyBean> m : grade.getValue().entrySet()) {
				if (!labels.containsKey(m.getKey()))
					labels.put(m.getKey(), sdf.format(m.getKey()));
			}
		}

		return labels;
	}

	private String[] getLegendLabels(TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans) {

		ArrayList<String> labels = new ArrayList<String>();

		for (BullyingBehaviorType r : rbeans.keySet())
			labels.add(r.getTypeName());

		return labels.toArray(new String[0]);
	}

	private Paint[] getPaints(TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans) {

		ArrayList<Color> paints = new ArrayList<Color>();

		for (int i = 0; i < rbeans.size(); i++)
			paints.add(getRandomPaint());

		return paints.toArray(new Color[0]);
	}

	private double[][] getData(TreeMap<Date, String> xAxisLabels,
															TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rbeans) {

		double[][] data = new double[rbeans.size()][xAxisLabels.size()];

		for (int i = 0; i < data.length; i++)
			Arrays.fill(data[i], Double.NaN);

		Date[] dates = xAxisLabels.keySet().toArray(new Date[0]);

		int idxRegion = 0;

		for (Entry<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> r : rbeans.entrySet()) {
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
