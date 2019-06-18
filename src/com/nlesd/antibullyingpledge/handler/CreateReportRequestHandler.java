package com.nlesd.antibullyingpledge.handler;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeGradeLevelReport;
import com.nlesd.antibullyingpledge.dao.*;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager.ReportSelection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.esdnl.servlet.RequestHandlerImpl;
public class CreateReportRequestHandler extends RequestHandlerImpl {
	public CreateReportRequestHandler()
	{
		requiredPermissions = new String[] {
				"MEMBERADMIN-VIEW"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		
	super.handleRequest(request, response);
	Integer reportID = Integer.parseInt(form.get("report"));
	ReportSelection rs = ReportSelection.getValue(reportID);
	try {
		//get data
			switch(rs)
				{
				case TOTAL_PLEDGES:
				{
				OutputStream out = response.getOutputStream();
				DefaultPieDataset dataset = new DefaultPieDataset();
			    dataset.setValue("Total Pledges Submitted", AntiBullyingPledgeManager.getTotalPledges());
			    dataset.setValue("Total Pledges Confirmed", AntiBullyingPledgeManager.getTotalPledgesConfirmed());
			    JFreeChart mychart = ChartFactory.createPieChart(
			             "Total Pledges vs Confirmed Pledges",  // chart title
			             dataset,             // data
			             true,               // include legend
			             true,
			             false
			         );
			    PiePlot colorConfigurator = (PiePlot) mychart.getPlot();
			    colorConfigurator.setSectionPaint("Total Pledges Submitted",Color.BLUE);
			    colorConfigurator.setSectionPaint("Total Pledges Confirmed",Color.YELLOW);
			    colorConfigurator.setExplodePercent("Total Pledges Confirmed", 0.30);
	            colorConfigurator.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
	            colorConfigurator.setLabelBackgroundPaint(new Color(220, 220, 220)); 
	            response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(out, mychart, 600, 600);
				out.close();
				break;
				}
				case PLEDGES_BY_GRADE:
					{
					List<AntiBullyingPledgeGradeLevelReport> list = AntiBullyingPledgeManager.getGradeLevelReport();
					OutputStream out = response.getOutputStream();
					DefaultCategoryDataset dcs = new DefaultCategoryDataset();
					//now loop through list and populate data set
					
					for(AntiBullyingPledgeGradeLevelReport report:list)
					{
						dcs.addValue(report.getConfirmedCount(),"Confirmed",report.getGradeName());
						dcs.addValue(report.getNotConfirmedCount(),"Not Confirmed",report.getGradeName());
					}
			        JFreeChart mychart = ChartFactory.createStackedBarChart("Pledges By Grade Level", "Grade Level", "Pledge Totals", dcs, PlotOrientation.VERTICAL, true, true, false);   
			        CategoryPlot plot = (CategoryPlot)mychart.getPlot();   
			        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
			        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
			        yAxis.setTickUnit(new NumberTickUnit(1));
			        response.setContentType("image/png");
					ChartUtilities.writeChartAsPNG(out, mychart, 800, 600);
					out.close();
					break;
					}
					default:
					{
						Integer numberofdays = Integer.parseInt(form.get("selectperiod"));
						TreeMap<Date,Integer> list = AntiBullyingPledgeManager.getPledgesByDateRange(numberofdays);
						
						OutputStream out = response.getOutputStream();
						DefaultCategoryDataset dcs = new DefaultCategoryDataset();
						//now loop through list and populate data set
						for (Map.Entry<Date,Integer> entry : list.entrySet()) {

						    dcs.addValue(entry.getValue(), "Dates", entry.getKey());
						    
						}
				        JFreeChart mychart = ChartFactory.createStackedBarChart("Total Pledges By Date", "Pledge Total", "Dates", dcs, PlotOrientation.VERTICAL, true, true, false);   
				        CategoryPlot plot = (CategoryPlot)mychart.getPlot();   
				        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
				        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
				        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
				        yAxis.setTickUnit(new NumberTickUnit(1));
				        response.setContentType("image/png");
						ChartUtilities.writeChartAsPNG(out, mychart, 800, 800);
						out.close();
						break;
					}
				}
		}
		catch (Exception e) {
		}
		return null;
		}
}
