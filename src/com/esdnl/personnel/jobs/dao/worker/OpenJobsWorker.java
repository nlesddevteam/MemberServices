package com.esdnl.personnel.jobs.dao.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

import com.awsd.school.SchoolException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.personnel.jobs.service.OpenJobsExportService;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class OpenJobsWorker extends TimerTask {

	//FOR LIVE SERVER
	private String rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../nlesdweb/WebContent/";

	//FOR LOCAL HOST ONLY

	//private String rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../wtpwebapps/NLESDWEB/";

	public OpenJobsWorker() {

		super();
		System.err.println("<<<<<< OPEN JOBS TIMER STARTED >>>>>");
	}

	public void run() {

		try {
			synchronized (OpenJobsExportService.OPEN_JOBS) {
				OpenJobsExportService.OPEN_JOBS.clear();
				OpenJobsExportService.OPEN_JOBS.addAll(JobOpportunityManager.getJobOpportunityBeansVector("OPEN"));
				OpenJobsExportService.OPEN_JOBS.addAll(JobOpportunityManager.getJobOpportunityBeansVector("AWARDED"));
				System.err.println("<<<<<< OPEN JOBS RELOADED >>>>>");

				createJobFiles();

				createSubListFiles();
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	private void createJobFiles() {

		File job_tmp = null, job_real = null;
		PrintWriter writer = null;
		JobOpportunityBean[] jobs = null;
		JobOpportunityAssignmentBean[] ass = null;
		int jlist = 0;

		try {
			jobs = (JobOpportunityBean[]) OpenJobsExportService.OPEN_JOBS.toArray(new JobOpportunityBean[0]);

			for (int ii = 0; ii < JobTypeConstant.ALL.length; ii++) {
				if (JobTypeConstant.ALL[ii].equal(JobTypeConstant.INTERNALEXTERNAL)) {
					continue;
				}

				job_tmp = new File(rootbasepath + "employment/generated/index" + JobTypeConstant.ALL[ii].getValue() + ".tmp");

				if (!job_tmp.getParentFile().exists()) {
					job_tmp.getParentFile().mkdirs();
				}

				if (job_tmp.exists()) {
					System.err.println("<<<<< " + job_tmp.getName() + " FILE ALREADY EXIST >>>>>");

					if (job_tmp.delete()) {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
					}
				}

				writer = new PrintWriter(new FileWriter(job_tmp), true);

				if (jobs.length > 0) {

					jlist++;

					writer.println("<script>$('document').ready(function(){");
					writer.println("$('.educationalJobsList" + jlist
							+ "').DataTable({'order': [[ 0, 'desc' ]],'lengthMenu': [[25, 50, 100, 200, -1], [25, 50, 100, 200, 'All']] });");
					writer.println("});</script>");
					writer.println("<span class='jobCount" + jlist
							+ "' style='float:right;font-size:25px;color:rgba(0,0,0,0.3);position:relative;top:-30px;'></span><br/>");
					writer.println(
							"<span style='position:relative;top:-10px;font-size:10px;'>Positions are sorted by competition number by default. To find a particular job, use the search at right. You can also sort the listings by any column by clicking the heading.");
					writer.println("</span>");
					writer.println("<br/><table width='100%' class='table table-condensed table-striped educationalJobsList"
							+ jlist + "' style='font-size:11px;width:100%;'>");
					writer.println("<thead>");
					writer.println(
							"<tr><th width='15%'>COMPETITION #</th><th width='45%'>POSITION TITLE/LOCATION(S)</th><th width='10%'>REGION</th><th width='20%'>COMPETITION END DATE</th><th width='10%'>OPTIONS</th></tr>");
					writer.println("</thead>");
					writer.println("<tbody>");
					//writer.println(""+ JobTypeConstant.ALL[ii].getDescription() + "");				
					int jcount = 0;

					for (int i = 0; i < jobs.length; i++) {

						if ((JobTypeConstant.AWARDED.equal(JobTypeConstant.ALL[ii]) && jobs[i].isAwarded())
								|| (!jobs[i].isAwarded() && ((jobs[i].getJobType().getValue() == JobTypeConstant.ALL[ii].getValue())
										|| (jobs[i].getJobType().equals(JobTypeConstant.INTERNALEXTERNAL)
												&& JobTypeConstant.ALL[ii].equal(JobTypeConstant.INTERNALONLY))
										|| (jobs[i].getJobType().equals(JobTypeConstant.INTERNALEXTERNAL)
												&& JobTypeConstant.ALL[ii].equal(JobTypeConstant.EXTERNALONLY))))) {

							jcount++;
							ass = null;
							ass = (JobOpportunityAssignmentBean[]) jobs[i].toArray(new JobOpportunityAssignmentBean[0]);
							if (!jobs[i].isCancelled()) {
								writer.println("<tr id='" + jobs[i].getCompetitionNumber() + "'>");
							}
							else {
								writer.println(
										"<tr style='color:Red;background-color:#ffe6e6;' id='" + jobs[i].getCompetitionNumber() + "'>");
							}
							writer.println("<td>" + jobs[i].getCompetitionNumber() + "</td>");
							writer.println("<td>");
							writer.println("<b>" + jobs[i].getPositionTitle() + "</b><br/>");
							for (int j = 0; ((ass != null) && (j < ass.length)); j++) {
								writer.println(" &middot; " + ass[j].getLocationText() + "<br>");
							}
							writer.println("</td>");

							String thisRegion = ((ass[0].getLocationZone() != null) ? ass[0].getLocationZone().toString() : "N/A");

							if (thisRegion.equalsIgnoreCase("AVALON")) {
								writer.println(
										"<td class='region1solid' style='text-align:center;color:white;vertical-align:middle;'>AVALON</td>");
							}
							else if (thisRegion.equalsIgnoreCase("CENTRAL")) {
								writer.println(
										"<td class='region2solid' style='text-align:center;color:white;vertical-align:middle;'>CENTRAL</td>");
							}
							else if (thisRegion.equalsIgnoreCase("WESTERN")) {
								writer.println(
										"<td class='region3solid' style='text-align:center;color:white;vertical-align:middle;'>WESTERN</td>");
							}
							else if (thisRegion.equalsIgnoreCase("LABRADOR")) {
								writer.println(
										"<td class='region4solid' style='text-align:center;color:white;vertical-align:middle;'>LABRADOR</td>");
							}
							else {
								writer.println(
										"<td class='region5solid' style='text-align:center;color:white;vertical-align:middle;'>PROVINCIAL</td>");
							}

							if (!jobs[i].isCancelled()) {
								writer.println("<td>" + jobs[i].getFormatedCompetitionEndDate() + "</td>");
								writer.println(
										"<td><a class='no-print btn btn-xs btn-primary' href='/employment/view_job_post.jsp?comp_num="
												+ jobs[i].getCompetitionNumber() + "'>VIEW</a></td>");
							}
							else {
								writer.println("<td>POSITION CANCELLED</td>");
								writer.println("<td></td>");
							}
							writer.println("</tr>");
						}

					}

					writer.println("</tbody></table>");
					writer.println("<script>$('.jobCount" + jlist + "').html(" + jcount + ");</script>");
					if (jcount < 1) { //Place span up atop to write to with jq.				
						writer.println("<script>$('.educationalJobsList" + jlist + "').parent().css('display','none');</script>");
						writer.println("<script>$('.noPos" + jlist + "').css('display','block');</script>");
						//writer.println("<div style='text-align:center;'>Sorry, no positions currently available in this category.</div>");
					}

				}
				else {
					//writer.println("<div class='alert alert-info' style='text-align:center;'>Sorry, no positions available at this time. Thank you.</div>");
				}

				writer.println("<br/><br/>");

				writer.flush();
				writer.close();

				job_real = new File(rootbasepath + "employment/generated/index" + JobTypeConstant.ALL[ii].getValue() + ".html");
				if (job_real.exists()) {
					job_real.delete();
					System.err.println("<<<<<< EXISTING " + job_real.getName() + " FILE DELETED >>>>>>");
				}
				job_tmp.renameTo(job_real);
			}
		}
		catch (IOException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	private void createSubListFiles() throws SchoolException {

		File job_tmp = null, job_real = null;
		PrintWriter writer = null;
		TreeMap<String, ArrayList<SubListBean>> listMap = null;
		Collection<RegionBean> regions = null;
		int jslist = 0;
		int jcount = 0;
		int noJobs = 0;
		int regionCount = 0;
		int tjobCount = 0;
		//int totalSubTeacherJobs=0;
		//int totalSubTLAJobs=0;

		try {

			for (int ii = 0; ii < SubstituteListConstant.ALL.length; ii++) {

				listMap = SubListManager.getSubListBeans(SubstituteListConstant.ALL[ii]);

				job_tmp = new File(rootbasepath + "employment/generated/index_sublist_"
						+ SubstituteListConstant.ALL[ii].getValue() + ".tmp");

				if (!job_tmp.getParentFile().exists()) {
					job_tmp.getParentFile().mkdirs();
				}

				if (job_tmp.exists()) {
					System.err.println("<<<<< " + job_tmp.getName() + " FILE ALREADY EXIST >>>>>");

					if (job_tmp.delete()) {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
					}
				}

				writer = new PrintWriter(new FileWriter(job_tmp), true);

				if ((listMap != null) && (listMap.size() > 0)) {
					for (Map.Entry<String, ArrayList<SubListBean>> entry : listMap.entrySet()) {

						for (SchoolZoneBean zone : SchoolZoneService.getSchoolZoneBeans()) {
							noJobs = 1;
							regionCount++;
							tjobCount = 0;
							String sname = "provincial";
							sname = zone.getZoneName();
							String listBakColor;
							String listTitleColor;
							if (sname.toUpperCase().contains("EASTERN")) {
								listBakColor = "rgba(191, 0, 0, 0.1)";
								listTitleColor = "rgba(191, 0, 0, 1)";
							}
							else if (sname.toUpperCase().contains("AVALON")) {
								listBakColor = "rgba(191, 0, 0, 0.1)";
								listTitleColor = "rgba(191, 0, 0, 1)";
							}
							else if (sname.toUpperCase().contains("CENTRAL")) {
								listBakColor = "rgba(0, 191, 0, 0.1)";
								listTitleColor = "rgba(0, 191, 0, 1)";
							}
							else if (sname.toUpperCase().contains("WESTERN")) {
								listBakColor = "rgba(255, 132, 0, 0.1)";
								listTitleColor = "rgba(255, 132, 0, 1)";
							}
							else if (sname.toUpperCase().contains("LABRADOR")) {
								listBakColor = "rgba(127, 130, 255, 0.1)";
								listTitleColor = "rgba(127, 130, 255, 1)";
							}
							else {
								listBakColor = "rgb(153, 51, 51, 0.1)";
								listTitleColor = "rgba(153, 51, 51, 1)";
							}

							writer.println("<br/><div style='border:1px solid " + listTitleColor + ";'><div style='background:"
									+ listTitleColor
									+ ";width:100%;'><span style='font-size:16px;font-weight:bold;text-transform:Capitalize;color:white;'>&nbsp;"
									+ zone.getZoneName() + " Region</span></div>");

							regions = RegionManager.getRegionBeans(zone);

							if (regions != null && regions.size() > 0) {

								for (RegionBean region : regions) {
									jslist++;
									jcount = 0;

									writer.println("<div style='padding:5px;background-color:" + listBakColor + ";'>");
									writer.println("<script>$('document').ready(function(){");
									writer.println("$('.sublistsJobsList" + jslist
											+ "').DataTable({'order': [[ 0, 'asc' ]],'bLengthChange': false,'paging': false, 'lengthMenu': [[-1, 20, 50, 100, 200], ['All', 20, 50, 100, 200]] });");
									writer.println("});</script>");
									writer.println("<span style='font-weight:bold;padding-bottom:10px;text-transform:uppercase;color:"
											+ listTitleColor + ";'>" + region.getName()
											+ "</span><span style='float:right;font-size:20px;color:rgba(0,0,0,0.3);position:relative;top:-5px;' class='regionCNT"
											+ regionCount + "'></span><br/>");
									writer.println(
											"<span style='font-size:10px;'>Sub lists are sorted by name by default. To find a particular list, use the search at right. You can also sort the listings by any column by clicking the heading.</span>");
									writer.println("<table width='100%' class='table table-condensed table-striped sublistsJobsList"
											+ jslist + "' style='font-size:11px;width:100%;'>");
									writer.println("<thead>");
									writer.println(
											"<tr><th width='80%'>SUBLIST TITLE</th><th width='10%'>EXPIRY</th><th width='10%'>OPTIONS</th></tr>");
									writer.println("</thead>");
									writer.println("<tbody>");

									for (SubListBean list : entry.getValue()) {
										if (list.getRegion().getId() != region.getId())
											continue;
										jcount++;
										tjobCount++;
										noJobs = 0;
										writer.println("<tr id='" + list.getId() + "'>");
										writer.println("<td>" + list.getTitle() + "</td>");
										writer.println("<td>" + list.getExpiryDate() + "</td>");
										writer.println(
												"<td><a class='no-print btn btn-xs btn-primary' href='/employment/view_sub_list.jsp?list_id="
														+ list.getId() + "'>VIEW</a></td>");
										writer.println("</tr>");
									}
									writer.println("</tbody></table>");
									writer.println(
											"<script>$('.regionCNT" + regionCount + "').html('" + tjobCount + "&nbsp;');</script>");
									if (jcount < 1) {
										writer.println(
												"<script>$('.sublistsJobsList" + jslist + "').parent().css('display','none');</script>");

									}
									writer.println("</div>");
								}

							}
							else {

							}
							if (noJobs > 0) {
								writer.println(
										"<div style='padding:5px;'>Sorry, no sublist available in this region at this time. Thank you.</div>");

							}

							writer.println("</div>");

						}
					}
				}
				else {
					writer.println("<div class='alert alert-info'>Sorry, no list available at this time. Thank you.</div>");
				}

				writer.flush();
				writer.close();

				job_real = new File(rootbasepath + "employment/generated/index_sublist_"
						+ SubstituteListConstant.ALL[ii].getValue() + ".html");
				if (job_real.exists()) {
					job_real.delete();
					System.err.println("<<<<<< EXISTING " + job_real.getName() + " FILE DELETED >>>>>>");
				}
				job_tmp.renameTo(job_real);
			}
		}
		catch (JobOpportunityException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
		catch (RegionException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
		catch (IOException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}
}